package com.andres_k.components.gameComponents.gameObject.objects;

import com.andres_k.components.eventComponent.events.EventController;
import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.collisions.PhysicalObject;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.ComboController;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
import org.newdawn.slick.SlickException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andres_k on 10/07/2015.
 */
public class Player extends PhysicalObject {
    protected Map<String, Method> specialActions;
    protected EventController event;
    protected ComboController comboController;
    private long score;

    public Player(AnimatorController animatorController, EGameObject type, String id, float x, float y, float life, float damage, float speed, float weight) {
        super(animatorController, type, id, x, y, life, damage, speed, weight);

        this.event = new EventController();
        this.event.addEvent(EInput.MOVE_UP);
        this.event.addEvent(EInput.MOVE_DOWN);
        this.event.addEvent(EInput.MOVE_LEFT);
        this.event.addEvent(EInput.MOVE_RIGHT);
        this.event.addEvent(EInput.ATTACK_A);
        this.event.addEvent(EInput.ATTACK_B);
        this.event.addEvent(EInput.ATTACK_C);
        this.event.addEvent(EInput.ATTACK_D);
        this.event.addEvent(EInput.ATTACK_SPE);

        this.specialActions = new HashMap<>();

        try {
            this.comboController = new ComboController(this.type);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            this.comboController = null;
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        this.resetActions();
    }

    @Override
    public void update() throws SlickException {
        this.comboController.update();
        this.animatorController.update();
        this.executeLastActionEvent();
        this.animatorController.doCurrentAction(this);
        this.movement.update();
        if (this.animatorController.canSwitchCurrent()) {
            if (this.event.allInactive()) {
                this.moveFall();
            } else {
                this.executeLastDirectionEvent();
            }
        }
    }

    @Override
    public void resetActions() {
        this.comboController.reset();
        this.event.reset();
    }

    // ACTIONS

    private boolean moveFall() throws SlickException {
        if (!this.isOnEarth()
                && this.animatorController.currentAnimationType() != EAnimation.RECEIPT
                && this.animatorController.currentAnimationType() != EAnimation.FALL
                && this.animatorController.currentAnimationType() != EAnimation.FALL_FORCED
                && this.animatorController.canSwitchCurrent()) {
            Console.write("FALL");
            this.animatorController.changeAnimation(EAnimation.FALL);
            this.movement.resetGravity();
            return true;
        }
        return false;
    }

    private boolean moveRight() throws SlickException {
        if (this.movement.getMoveDirection() != EDirection.RIGHT || this.animatorController.currentAnimationType() != EAnimation.RUN) {
            this.animatorController.setEyesDirection(EDirection.RIGHT);
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.RIGHT);
            this.event.addStackEvent(EInput.MOVE_RIGHT);
            if (this.event.isActivated(EInput.MOVE_UP))
                this.moveUp();
            return true;
        }
        return false;
    }

    private boolean moveLeft() throws SlickException {
        if (this.movement.getMoveDirection() != EDirection.LEFT || this.animatorController.currentAnimationType() != EAnimation.RUN) {
            this.animatorController.setEyesDirection(EDirection.LEFT);
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.LEFT);
            this.event.addStackEvent(EInput.MOVE_LEFT);
            if (this.event.isActivated(EInput.MOVE_UP))
                this.moveUp();
            return true;
        }
        return false;
    }

    private boolean moveDown() throws SlickException {
        if (this.isOnEarth())
            this.animatorController.changeAnimation(EAnimation.DEFENSE);
        else
            this.animatorController.changeAnimation(EAnimation.FALL);
        this.event.addStackEvent(EInput.MOVE_DOWN);
        return true;
    }

    private boolean moveUp() throws SlickException {
        this.changeDirection();
        this.animatorController.changeAnimation(EAnimation.JUMP);
        if (!this.isOnEarth())
            this.animatorController.setCurrentAnimationIndex(1);
        this.setOnEarth(false);
        this.movement.resetGravity();
        this.event.addStackEvent(EInput.MOVE_UP);
        return true;
    }

    private void changeDirection() {
        EInput recentMove = this.event.getMoreRecentEventBetween(EInput.MOVE_RIGHT, EInput.MOVE_LEFT);
        if (recentMove == EInput.MOVE_RIGHT) {
            this.movement.setMoveDirection(EDirection.RIGHT);
        } else if (recentMove == EInput.MOVE_LEFT) {
            this.movement.setMoveDirection(EDirection.LEFT);
        } else {
            this.movement.setMoveDirection(EDirection.NONE);
        }
    }

    private boolean executeLastDirectionEvent() throws SlickException {
        if (this.animatorController.canSwitchCurrent()) {
            EInput last = this.event.getTheLastEvent();

            if (last != EInput.NOTHING) {
                if (last == EInput.MOVE_RIGHT) {
                    return this.moveRight();
                } else if (last == EInput.MOVE_LEFT) {
                    return this.moveLeft();
                } else if (last == EInput.MOVE_DOWN) {
                    return this.moveDown();
                } else if (last == EInput.MOVE_UP) {
                    return this.moveUp();
                }
            }
        }
        return false;
    }

    private boolean executeLastActionEvent() {
        EInput last = this.event.consumeStackEvent();

        if (last == EInput.NOTHING) {
            last = this.event.getTheLastEvent();
        }
        if (last != EInput.NOTHING && this.comboController != null) {
            return this.comboController.nextComboStep(this.animatorController, last);
        }
        return false;
    }

    // EVENT
    @Override
    public void eventPressed(EInput input) {
        if (this.isAlive()) {
            this.event.setActivated(input.getContainer(), true);
        }
    }

    @Override
    public void eventReleased(EInput input) {
        if (this.isAlive()) {
            this.event.setActivated(input.getContainer(), false);
        }
    }

    @Override
    public Object doTask(Object task) {
        if (task instanceof Pair) {
            Pair<ETaskType, Object> received = (Pair<ETaskType, Object>) task;

            if (received.getV1() == ETaskType.UPGRADE_SCORE && received.getV2() instanceof Integer) {
                this.score += (int) received.getV2();
            } else if (received.getV1() == ETaskType.CREATE && received.getV2() instanceof String) {
                if (this.specialActions.containsKey(received.getV2())) {
                    try {
                        this.specialActions.get(received.getV2()).invoke(this);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    // GETTERS

    public long getScore() {
        return this.score;
    }

    public String getPseudo() {
        if (this.id.contains(":")) {
            return this.id.substring(this.id.indexOf(":") + 1, this.id.length());
        } else {
            return this.id;
        }
    }

    public int getIdIndex() {
        if (this.id.contains(":")) {
            int index = this.id.indexOf(":");
            return Integer.valueOf(this.id.substring(index - 1, index));
        } else {
            return -1;
        }
    }
}

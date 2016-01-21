package com.andres_k.components.gameComponents.gameObject.objects;

import com.andres_k.components.eventComponent.events.EventController;
import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.collisions.PhysicalObject;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.ComboController;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EnumDirection;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.utils.stockage.Pair;

/**
 * Created by andres_k on 10/07/2015.
 */
public class Player extends PhysicalObject {
    protected EventController event;
    protected ComboController comboController;
    private long score;

    public Player(AnimatorController animatorController, EnumGameObject type, String id, float x, float y, float life, float damage, float speed, float weight) {
        super(animatorController, type, id, x, y, life, damage, speed, weight);

        this.event = new EventController();
        this.event.addEvent(EnumInput.MOVE_UP);
        this.event.addEvent(EnumInput.MOVE_DOWN);
        this.event.addEvent(EnumInput.MOVE_LEFT);
        this.event.addEvent(EnumInput.MOVE_RIGHT);
        this.event.addEvent(EnumInput.ATTACK_A);
        this.event.addEvent(EnumInput.ATTACK_B);


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
    public void update() {
        this.comboController.update();
        this.animatorController.doCurrentAction(this);
        this.movement.update();
        if (this.event.allInactive()) {
            this.moveFall();
        } else {
            this.executeEvent();
        }
        this.updateAnimation();
    }

    @Override
    public void resetActions() {
        this.comboController.reset();
        this.event.reset();
    }

    // ACTIONS

    private boolean moveFall() {
        if (!this.isOnEarth()
                && this.animatorController.currentAnimationType() != EnumAnimation.FALL
                && this.animatorController.canSwitchCurrent()) {
            this.animatorController.changeAnimation(EnumAnimation.FALL);
            this.movement.resetGravity();
            return true;
        }
        return false;
    }

    private boolean moveRight() {
        if (this.movement.getMoveDirection() != EnumDirection.RIGHT || this.animatorController.currentAnimationType() != EnumAnimation.RUN) {
            this.animatorController.setEyesDirection(EnumDirection.RIGHT);
            this.animatorController.changeAnimation(EnumAnimation.RUN);
            this.movement.setMoveDirection(EnumDirection.RIGHT);
            this.event.addStackEvent(EnumInput.MOVE_RIGHT);
            if (this.event.isActivated(EnumInput.MOVE_UP))
                this.moveUp();
            return true;
        }
        return false;
    }

    private boolean moveLeft() {
        if (this.movement.getMoveDirection() != EnumDirection.LEFT || this.animatorController.currentAnimationType() != EnumAnimation.RUN) {
            this.animatorController.setEyesDirection(EnumDirection.LEFT);
            this.animatorController.changeAnimation(EnumAnimation.RUN);
            this.movement.setMoveDirection(EnumDirection.LEFT);
            this.event.addStackEvent(EnumInput.MOVE_LEFT);
            if (this.event.isActivated(EnumInput.MOVE_UP))
                this.moveUp();
            return true;
        }
        return false;
    }

    private boolean moveDown() {
        if (this.isOnEarth())
            this.animatorController.changeAnimation(EnumAnimation.DEFENSE);
        else
            this.animatorController.changeAnimation(EnumAnimation.FALL);
        this.event.addStackEvent(EnumInput.MOVE_DOWN);
        return true;
    }

    private boolean moveUp() {
        this.changeDirection();
        this.animatorController.changeAnimation(EnumAnimation.JUMP);
        if (!this.isOnEarth())
            this.animatorController.setCurrentAnimationIndex(1);
        this.setOnEarth(false);
        this.movement.resetGravity();
        this.event.addStackEvent(EnumInput.MOVE_UP);
        return true;
    }

    private void changeDirection() {
        EnumInput recentMove = this.event.getMoreRecentEventBetween(EnumInput.MOVE_RIGHT, EnumInput.MOVE_LEFT);
        if (recentMove == EnumInput.MOVE_RIGHT) {
            this.movement.setMoveDirection(EnumDirection.RIGHT);
        } else if (recentMove == EnumInput.MOVE_LEFT) {
            this.movement.setMoveDirection(EnumDirection.LEFT);
        } else {
            this.movement.setMoveDirection(EnumDirection.NONE);
        }
    }

    private boolean executeEvent() {
        return this.executeLastActionEvent() || this.executeLastDirectionEvent();
    }

    private boolean executeLastDirectionEvent() {
        if (this.animatorController.canSwitchCurrent()) {
            EnumInput last = this.event.getTheLastEvent();

            if (last != EnumInput.NOTHING) {
                if (last == EnumInput.MOVE_RIGHT) {
                    return this.moveRight();
                } else if (last == EnumInput.MOVE_LEFT) {
                    return this.moveLeft();
                } else if (last == EnumInput.MOVE_DOWN) {
                    return this.moveDown();
                } else if (last == EnumInput.MOVE_UP) {
                    return this.moveUp();
                }
            }
        }
        return false;
    }

    private boolean executeLastActionEvent() {
        EnumInput last = this.event.consumeStackEvent();

        if (last != EnumInput.NOTHING && this.comboController != null) {
            return this.comboController.nextComboStep(this.animatorController, last);
        }
        return false;
    }

    // EVENT
    @Override
    public void eventPressed(EnumInput input) {
        if (this.isAlive()) {
            this.event.setActivated(input.getContainer(), true);
        }
    }

    @Override
    public void eventReleased(EnumInput input) {
        if (this.isAlive()) {
            this.event.setActivated(input.getContainer(), false);
        }
    }

    @Override
    public Object doTask(Object task) {
        if (task instanceof Pair) {
            Pair<EnumTask, Object> received = (Pair<EnumTask, Object>) task;

            if (received.getV1() == EnumTask.UPGRADE_SCORE && received.getV2() instanceof Integer) {
                this.score += (int) received.getV2();
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

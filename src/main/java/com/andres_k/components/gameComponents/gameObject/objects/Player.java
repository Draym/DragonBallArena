package com.andres_k.components.gameComponents.gameObject.objects;

import com.andres_k.components.eventComponent.events.EventController;
import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.animations.container.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.combo.ComboController;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EnumDirection;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.utils.stockage.Pair;

/**
 * Created by andres_k on 10/07/2015.
 */
public class Player extends GameObject {
    protected EventController event;
    protected ComboController combos;
    private long score;

    public Player(AnimatorController animatorController, EnumGameObject type, String id, float x, float y, float life, float damage, float speed, float weight) {
        super(animatorController, id, type, new Pair<>(x, y), life, damage, speed, weight);

        this.event = new EventController();
        this.event.addEvent(EnumInput.MOVE_UP);
        this.event.addEvent(EnumInput.MOVE_DOWN);
        this.event.addEvent(EnumInput.MOVE_LEFT);
        this.event.addEvent(EnumInput.MOVE_RIGHT);

        this.combos = new ComboController();
    }

    @Override
    public void clear() {

    }

    @Override
    public void update() {
        this.animatorController.doCurrentAction(this);
        this.movement.update();
        if (this.animatorController.canSwitchCurrent()) {
            if (this.event.allInactive()) {
                this.moveFall();
            } else {
                this.executeLastEvent();
            }
        }
        this.updateAnimation();
    }

    // ACTIONS

    private boolean moveFall() {
        if (!this.isOnEarth() && this.animatorController.getCurrentAnimation() != EnumAnimation.FALL) {
            this.animatorController.setCurrent(EnumAnimation.FALL);
            this.movement.resetGravity();
            return true;
        }
        return false;
    }

    private boolean moveRight() {
        if (this.animatorController.canSwitchCurrent() && (this.movement.getMoveDirection() != EnumDirection.RIGHT || this.animatorController.getCurrentAnimation() != EnumAnimation.RUN)) {
            this.animatorController.setEyesDirection(EnumDirection.RIGHT);
            this.animatorController.setCurrent(EnumAnimation.RUN);
            this.movement.setMoveDirection(EnumDirection.RIGHT);
            if (this.event.isActivated(EnumInput.MOVE_UP))
                this.moveUp();
            return true;
        }
        return false;
    }

    private boolean moveLeft() {
        if (this.animatorController.canSwitchCurrent() && (this.movement.getMoveDirection() != EnumDirection.LEFT || this.animatorController.getCurrentAnimation() != EnumAnimation.RUN)) {
            this.animatorController.setEyesDirection(EnumDirection.LEFT);
            this.animatorController.setCurrent(EnumAnimation.RUN);
            this.movement.setMoveDirection(EnumDirection.LEFT);
            if (this.event.isActivated(EnumInput.MOVE_UP))
                this.moveUp();
            return true;
        }
        return false;
    }

    private boolean moveDown() {
        if (this.animatorController.canSwitchCurrent()) {
            if (this.isOnEarth())
                this.animatorController.setCurrent(EnumAnimation.DEFENSE);
            else
                this.animatorController.setCurrent(EnumAnimation.FALL);
        }
        return false;
    }

    private boolean moveUp() {
        if (this.animatorController.canSwitchCurrent()) {
            this.changeDirection();
            this.animatorController.setCurrent(EnumAnimation.JUMP);
            if (!this.isOnEarth())
                this.animatorController.setIndex(1);
            this.setOnEarth(false);
            this.movement.resetGravity();
            return true;
        }
        return false;
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

    private void executeLastEvent() {
        EnumInput last = this.event.getTheLastEvent();

        if (last == EnumInput.MOVE_RIGHT) {
            this.moveRight();
        } else if (last == EnumInput.MOVE_LEFT) {
            this.moveLeft();
        } else if (last == EnumInput.MOVE_DOWN) {
            this.moveDown();
        } else if (last == EnumInput.MOVE_UP) {
            this.moveUp();
        }
    }

    // EVENT
    @Override
    public void eventPressed(EnumInput input) {
        if (this.isAlive()) {
            this.event.setActivated(input.returnContainer(), true);
        }
    }

    @Override
    public void eventReleased(EnumInput input) {
        if (this.isAlive()) {
            this.event.setActivated(input.returnContainer(), false);
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

package com.andres_k.components.gameComponents.gameObject.objects;

import com.andres_k.components.gameComponents.animations.Animator;
import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.collisions.EnumDirection;
import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 10/07/2015.
 */
public class Player extends GameObject {
    private long score;
    private float pushUpY;
    private float pushDownY;
    private float pushDownX;
    private float pushFallX;
    private float pushTravelX;

    public Player(Animator animator, EnumGameObject type, String id, float x, float y, float life, float damage, float speed, float weight) {
        super(animator, id, type, new Pair<>(x, y), life, damage, speed, weight);
        this.event.addEvent(EnumInput.MOVE_UP);
        this.event.addEvent(EnumInput.MOVE_DOWN);
        this.event.addEvent(EnumInput.MOVE_LEFT);
        this.event.addEvent(EnumInput.MOVE_RIGHT);
        this.pushUpY = 2f;
        this.pushDownY = 1.5f;
        this.pushDownX = this.pushTravelX / 1.2f;
        this.pushFallX = this.pushTravelX / 3;
        this.pushTravelX = 1f;
    }

    @Override
    public void clear() {

    }

    @Override
    public void draw(Graphics g) {
        if (this.animator != null) {
            if (this.animator.currentAnimation() != null) {
                g.drawAnimation(this.animator.currentAnimation(), this.graphicalX(), this.graphicalY());
            }
            if (this.animator.currentBodyAnimation() != null) {
                this.animator.currentBodyAnimation().draw(g, this.animator.currentFrame(), this.getPosX(), this.getPosY());
            }
        }
    }

    @Override
    public void update() {
        if (this.animator.getCurrentAnimation() == EnumAnimation.JUMP) {
            if (this.animator.getCurrentFrame() == 1) {
                this.movement.setPushY(-this.pushUpY);
                this.movement.setOnEarth(false);
            } else if (this.animator.currentAnimation().isStopped()) {
                if (this.event.isActivated(EnumInput.MOVE_UP)) {
                    this.moveUp();
                } else {
                    this.moveFall(this.pushDownX);
                }
            }
        } else if (this.animator.getCurrentAnimation() == EnumAnimation.FALL) {
            if (this.isOnEarth()) {
                if (this.event.allInactive()) {
                    this.idle();
                } else {
                    this.executeLastEvent();
                }
            }
        } else if (!this.isOnEarth() && this.event.allInactive()) {
            this.moveFall(this.pushTravelX);
        }
    }

    // ACTIONS

    private boolean moveFall(float valX) {
        if (!this.isOnEarth()) {
            if (valX != -1) {
                if (this.event.isActivated(EnumInput.MOVE_LEFT))
                    this.movement.setPushX(-valX);
                else if (this.event.isActivated(EnumInput.MOVE_RIGHT))
                    this.movement.setPushX(valX);
            }
            this.movement.setPushY(this.pushDownY);
            this.animator.setCurrent(EnumAnimation.FALL);
            return true;
        }
        return false;
    }

    private boolean moveRight() {
        if (this.animator.canSwitchCurrent() || this.animator.getCurrentAnimation() == EnumAnimation.JUMP) {
            this.animator.setDirection(EnumDirection.RIGHT);
            this.animator.setCurrent(EnumAnimation.RUN);
            this.movement.setPushX(this.pushTravelX);
            this.movement.setPushY(0);
            return true;
        }
        return false;
    }

    private boolean moveLeft() {
        if (this.animator.canSwitchCurrent() || this.animator.getCurrentAnimation() == EnumAnimation.JUMP) {
            this.animator.setDirection(EnumDirection.LEFT);
            this.animator.setCurrent(EnumAnimation.RUN);
            this.movement.setPushX(-this.pushTravelX);
            this.movement.setPushY(0);
            return true;
        }
        return false;
    }

    private boolean moveDown() {
        if (this.animator.canSwitchCurrent()) {
            this.movement.setPushX(0f);
            this.animator.setDirection(EnumDirection.NONE);
            if (this.isOnEarth()) {
                this.animator.setCurrent(EnumAnimation.DEF);
                return true;
            }
        }
        return false;
    }

    private boolean moveUp() {
        if (this.animator.canSwitchCurrent()) {
            this.animator.restartAnimation(EnumAnimation.JUMP);
            this.animator.setCurrent(EnumAnimation.JUMP);
            this.movement.setPushY(0);
            if (!this.isOnEarth())
                this.animator.setIndex(1);
            return true;
        }
        return false;
    }

    private void idle() {
        this.animator.setCurrent(EnumAnimation.IDLE);
        this.movement.stopMovement();
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
        boolean result = false;

        if (this.isAlive()) {
            if (input.isIn(EnumInput.MOVE_LEFT)) {
                result = this.moveLeft();
            } else if (input.isIn(EnumInput.MOVE_RIGHT)) {
                result = this.moveRight();
            } else if (input.isIn(EnumInput.MOVE_UP)) {
                result = this.moveUp();
            } else if (input.isIn(EnumInput.MOVE_DOWN)) {
                result = this.moveDown();
            }
            if (result)
                this.event.setActivated(input.returnContainer(), true);
        }
    }

    @Override
    public void eventReleased(EnumInput input) {
        if (this.isAlive()) {
            this.event.setActivated(input.returnContainer(), false);
            if (this.animator.getCurrentAnimation() != EnumAnimation.JUMP) {
                if (this.event.allInactive()) {
                    if (this.isOnEarth()) {
                        this.animator.setCurrent(EnumAnimation.IDLE);
                        this.movement.stopMovement();
                    } else {
                        this.moveFall(this.pushFallX);
                    }
                } else {
                    this.executeLastEvent();
                }
            }
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

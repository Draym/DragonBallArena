package com.andres_k.components.gameComponents.gameObject.objects;

import com.andres_k.components.gameComponents.animations.Animator;
import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.collisions.EnumDirection;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 10/07/2015.
 */
public class Player extends GameObject {
    private EnumInput current;
    private long score;

    public Player(Animator animator, EnumGameObject type, String id, float x, float y, float life, float damage, float speed, float weight) {
        super(animator, id, type, new Pair<>(x, y), life, damage, speed, weight);
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
                this.movement.setPushY(-2f);
                this.movement.setOnEarth(false);
            } else if (this.animator.currentAnimation().isStopped()) {
                this.movement.setPushY(1.5f);
                this.animator.setCurrent(EnumAnimation.FALL);
            }
        } else if (this.animator.getCurrentAnimation() == EnumAnimation.RUN) {
            if (this.animator.getDirection() == EnumDirection.LEFT) {
                this.movement.setPushX(-1);
                this.movement.setPushY(0);
            } else {
                this.movement.setPushX(1);
                this.movement.setPushY(0);
            }
        } else if (this.animator.getCurrentAnimation() == EnumAnimation.FALL) {
            if (this.isOnEarth()) {
                this.animator.setCurrent(EnumAnimation.IDLE);
                this.movement.stopMovement();
            }
        } else if (!this.isOnEarth()) {
            this.movement.setPushX(0f);
            this.movement.setPushY(1.5f);
            this.animator.setCurrent(EnumAnimation.FALL);
        }
    }

    @Override
    public void eventPressed(EnumInput input) {

        if (this.isAlive()) {
            if (input.isIn(EnumInput.MOVE_LEFT)) {
                this.animator.setDirection(EnumDirection.LEFT);
                this.animator.setCurrent(EnumAnimation.RUN);
            } else if (input.isIn(EnumInput.MOVE_RIGHT)) {
                this.animator.setDirection(EnumDirection.RIGHT);
                this.animator.setCurrent(EnumAnimation.RUN);
            } else if (input.isIn(EnumInput.MOVE_UP)) {
                this.animator.restartAnimation(EnumAnimation.JUMP);
                this.animator.setCurrent(EnumAnimation.JUMP);
                this.movement.setPushY(0);
                if (!this.isOnEarth())
                    this.animator.setIndex(1);
            } else if (input.isIn(EnumInput.MOVE_DOWN)) {
                this.animator.setCurrent(EnumAnimation.DEF);
            }
            this.current = input;
        }
    }

    @Override
    public void eventReleased(EnumInput input) {
        if (this.isAlive()) {
            if (input == this.current) {
                if (this.animator.getCurrentAnimation() != EnumAnimation.JUMP) {
                    if (this.isOnEarth()) {
                        this.animator.setCurrent(EnumAnimation.IDLE);
                        this.movement.stopMovement();
                    } else {
                        this.animator.setCurrent(EnumAnimation.FALL);
                        this.movement.setPushX(0f);
                        this.movement.setPushY(1.5f);
                    }
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
        return this.score / 10;
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

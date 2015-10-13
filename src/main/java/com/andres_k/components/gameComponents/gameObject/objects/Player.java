package com.andres_k.components.gameComponents.gameObject.objects;

import com.andres_k.components.gameComponents.animations.Animator;
import com.andres_k.components.gameComponents.animations.EnumAnimation;
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

    public Player(Animator animator, EnumGameObject type, String id, float x, float y, float life, float damage, float speed) {
        super(animator, id, type, new Pair<>(x, y), life, damage, speed);
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
        if (this.positions.getV2() < 200) {
            this.score += 1;
        }
        this.score += Math.ceil(this.calculateWithSpeed() - this.speed);
    }

    public void move() {
        if (this.move && !this.isNeedDelete()) {
            if (this.inTheMapAfterMove()) {
                this.positions.setV1(this.positions.getV1() + this.moveTo.getV1());
                this.positions.setV2(this.positions.getV2() + this.moveTo.getV2());
            } else {
                this.move = false;
                this.moveTo.setV1(0f);
                this.moveTo.setV2(0f);
            }
        }
    }

    @Override
    public void eventPressed(EnumInput input) {

        if (this.isAlive()) {
            if (input.isIn(EnumInput.MOVE_LEFT)) {
                this.animator.setCurrent(EnumAnimation.MOVE_LEFT);
                this.moveTo.setV1(-this.calculateWithSpeed());
                this.moveTo.setV2(0f);
                this.move = true;
            } else if (input.isIn(EnumInput.MOVE_RIGHT)) {
                this.animator.setCurrent(EnumAnimation.MOVE_RIGHT);
                this.moveTo.setV1(this.calculateWithSpeed());
                this.moveTo.setV2(0f);
                this.move = true;
            } else if (input.isIn(EnumInput.MOVE_UP)) {
                this.animator.setCurrent(EnumAnimation.BASIC);
                this.moveTo.setV1(0f);
                this.moveTo.setV2(-this.calculateWithSpeed());
                this.move = true;
            } else if (input.isIn(EnumInput.MOVE_DOWN)) {
                this.animator.setCurrent(EnumAnimation.BASIC);
                this.moveTo.setV1(0f);
                this.moveTo.setV2(this.calculateWithSpeed() / 2);
                this.move = true;
            }
            if (this.move) {
                this.current = input;
            }
        }
    }

    @Override
    public void eventReleased(EnumInput input) {
        if (this.isAlive()) {
            if (input == this.current) {
                this.animator.setCurrent(EnumAnimation.BASIC);
                this.moveTo.setV1(0f);
                this.moveTo.setV2(0f);
                this.move = false;
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

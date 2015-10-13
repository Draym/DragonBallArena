package com.andres_k.components.gameComponents.gameObject.objects;

import com.andres_k.components.gameComponents.animations.Animator;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 10/07/2015.
 */
public class Obstacle extends GameObject {

    public Obstacle(Animator animator, String id, EnumGameObject type, float posX, float posY, float life, float damage, float speed, float weight) {
        super(animator, id, type,new Pair<>(posX, posY), life, damage, speed, weight);
    }

    @Override
    public void clear() {
    }

    @Override
    public void draw(Graphics g) {
        if (this.animator != null) {
            if (animator.currentAnimation() != null) {
                g.drawAnimation(this.animator.currentAnimation(), this.graphicalX(), this.graphicalY());
            }
            if (this.animator.currentBodyAnimation() != null) {
                this.animator.currentBodyAnimation().draw(g, this.animator.currentFrame(), this.getPosX(), this.getPosY());
            }
        }
    }

    @Override
    public void update() {
        this.moveTo.setV2(this.calculateWithSpeed());
        this.move();
    }

    @Override
    public void eventPressed(EnumInput input) {
    }

    @Override
    public void eventReleased(EnumInput input) {
    }

    @Override
    public Object doTask(Object task) {
        return null;
    }
}

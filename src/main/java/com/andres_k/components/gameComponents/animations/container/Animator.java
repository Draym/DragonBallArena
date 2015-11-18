package com.andres_k.components.gameComponents.animations.container;

import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.bodies.BodyAnimation;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;

/**
 * Created by andres_k on 18/11/2015.
 */
public class Animator {
    private Animation animation;
    private BodyAnimation body;
    private AnimatorConfig config;
    private Color filter;

    public Animator() {
        this.animation = null;
        this.config = null;
        this.body = null;
        this.filter = new Color(1f, 1f, 1f);
    }

    public Animator(Animator animator) {
        if (animator.animation != null)
            this.animation = animator.animation.copy();
        else
            this.animation = null;
        this.body = animator.body;
        this.config = animator.config;
        this.filter = animator.filter;
    }

    // METHODS

    public void doAction(GameObject object, int frame) {
        if (config != null)
            this.config.doAction(object, frame);
    }

    public void restart() {
        if (this.animation != null)
            this.animation.restart();
        if (this.config != null)
            this.config.reset();
    }

    // ADD FUNCTIONS
    public void addAnimation(Animation animation) {
        this.animation = animation.copy();
    }

    public void addCollision(BodyAnimation body) {
        this.body = body;
    }

    public void addConfig(AnimatorConfig config) {
        this.config = config;
    }

    // GETTERS

    public Animation getAnimation() {
        return this.animation;
    }

    public AnimatorConfig getConfig() {
        return this.config;
    }

    public BodyAnimation getBodyAnimation() {
        return this.body;
    }

    public Pair<EnumAnimation, Integer> getNext() {
        try {
            return this.config.getNext();
        } catch (Exception e) {
            return null;
        }
    }

    public Color getFilter() {
        return this.filter;
    }
}

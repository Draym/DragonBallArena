package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.gameComponents.bodies.BodyAnimation;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.graphicComponents.effects.EffectManager;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by andres_k on 18/11/2015.
 */
public class Animator {
    protected EffectManager effectManager;
    protected Animation animation;
    protected BodyAnimation body;
    protected AnimatorConfig config;
    protected Color filter;
    private int currentFrame;

    public Animator() {
        this.animation = null;
        this.config = null;
        this.body = null;
        this.filter = new Color(1f, 1f, 1f);
        this.currentFrame = 0;
        this.effectManager = new EffectManager();
    }

    public Animator(Animator animator) {
        if (animator.animation != null) {
            this.animation = animator.animation.copy();
        }
        else {
            this.animation = null;
        }
        this.body = animator.body;
        this.config = animator.config;
        this.filter = animator.filter;
        this.currentFrame = animator.currentFrame;
        this.effectManager = new EffectManager(animator.effectManager);
    }

    // METHODS

    public void doAction(GameObject object, int frame) {
        if (this.config != null) {
            this.config.doAction(object, frame);
        }
    }

    public void draw(Graphics g, float x, float y, boolean flipX, boolean flipY) {
        if (this.animation != null) {
            Image image = this.getAnimation().getCurrentFrame().getFlippedCopy(flipX, flipY);
            if (this.effectManager.hasActivity()) {
                this.effectManager.draw(g, image, x, y);
            } else {
                g.drawImage(image, x, y);
            }
        }
    }

    public void update(long delta) {
        if (this.animation != null) {
            this.animation.update(delta);
            if (this.currentFrame != this.animation.getFrame()) {
                this.currentFrame = this.animation.getFrame();
                this.effectManager.activateEffects(this.currentFrame);
            }
        }
    }

    public void restart() {
        if (this.animation != null)
            this.animation.restart();
        if (this.config != null)
            this.config.reset();
    }

    // EFFECTS
    public void addEffect(int index, int priority, Effect effect) {
        this.effectManager.addEffect(index, priority, effect);
    }

    public void playEffect(int priority, Effect effect) {
        this.effectManager.playEffect(priority, effect);
    }

    public boolean hasEffectActivity() {
        return this.effectManager.hasActivity();
    }

    public boolean effectIsRunning(String id) {
        return this.effectManager.effectIsRunning(id);
    }

    public boolean effectIsActive(EffectType type) {
        return this.effectManager.effectIsActive(type);
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

    public Pair<EAnimation, Integer> getNext() {
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

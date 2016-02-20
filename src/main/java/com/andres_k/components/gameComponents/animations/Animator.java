package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.gameComponents.bodies.BodyAnimation;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.resourceComponent.sounds.SoundController;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andres_k on 18/11/2015.
 */
public class Animator {
    protected Map<Integer, ESound> soundEffects;
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
        this.soundEffects = new HashMap<>();
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
        this.soundEffects = new HashMap<>();
        this.soundEffects.putAll(animator.soundEffects);
    }

    // METHODS

    public void doAction(GameObject object, int frame) {
        if (config != null)
            this.config.doAction(object, frame);
    }

    public void update(long delta) {
        this.animation.update(delta);
        if (this.currentFrame != this.animation.getFrame()) {
            this.currentFrame = this.animation.getFrame();
            if (this.soundEffects.containsKey(this.currentFrame)) {
                SoundController.get().play(this.soundEffects.get(this.currentFrame));
            }
        }
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

    public void addSoundEffect(ESound sound, int frame) {
        this.soundEffects.put(frame, sound);
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

package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.gameComponents.bodies.BodyAnimation;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andres_k on 17/11/2015.
 */
public class AnimatorContainer {
    private Map<Integer, Animator> animators;
    private int index;

    public AnimatorContainer() {
        this.animators = new HashMap<>();
        this.index = 0;
    }

    public AnimatorContainer(AnimatorContainer animatorContainer) {
        this.animators = new HashMap<>();
        for (Map.Entry<Integer, Animator> entry : animatorContainer.animators.entrySet()) {
            this.animators.put(entry.getKey(), new Animator(entry.getValue()));
        }
        this.index = animatorContainer.index;
    }

    // METHODS
    public void doAction(GameObject object, int frame) {
        this.animators.get(this.index).doAction(object, frame);
    }

    public void restart() {
        this.index = 0;
        for (Map.Entry<Integer, Animator> entry : this.animators.entrySet()) {
            entry.getValue().restart();
        }
    }

    // ADD FUNCTIONS
    public void addAnimation(Animation animation, int index) {
        if (!this.animators.containsKey(index))
            this.animators.put(index, new Animator());
        this.animators.get(index).addAnimation(animation);
    }

    public void addCollision(BodyAnimation body, int index) {
        if (!this.animators.containsKey(index))
            this.animators.put(index, new Animator());
        this.animators.get(index).addCollision(body);
    }

    public void addConfig(AnimatorConfig config, int index) {
        if (!this.animators.containsKey(index))
            this.animators.put(index, new Animator());
        this.animators.get(index).addConfig(config);
    }

    // GETTERS

    public Animation getAnimation(int index) throws SlickException {
        try {
            return this.animators.get(index).getAnimation();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Animator getAnimator(int index) throws SlickException {
        try {
            return this.animators.get(index);
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Animator getCurrentAnimator() {
        return this.animators.get(this.index);
    }

    public Animation getCurrentAnimation() {
        return this.animators.get(this.index).getAnimation();
    }

    public AnimatorConfig getConfig() {
        return this.animators.get(this.index).getConfig();
    }

    public BodyAnimation getBodyAnimation() {
        return this.animators.get(this.index).getBodyAnimation();
    }

    public Pair<EAnimation, Integer> getNext() {
        return this.animators.get(this.index).getNext();
    }

    public int getIndex() {
        return this.index;
    }

    public Color getFilter() {
        return this.animators.get(this.index).getFilter();
    }

    // SETTERS

    public boolean setIndex(int value) {
        if (this.animators.containsKey(value)) {
            this.index = value;
            return true;
        }
        return false;
    }
}

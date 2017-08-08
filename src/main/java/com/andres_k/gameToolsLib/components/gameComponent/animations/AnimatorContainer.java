package com.andres_k.gameToolsLib.components.gameComponent.animations;

import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.gameToolsLib.components.gameComponent.animations.details.AnimationConfigItem;
import com.andres_k.gameToolsLib.components.gameComponent.animations.details.AnimationRepercussionItem;
import com.andres_k.gameToolsLib.components.gameComponent.bodies.BodyAnimation;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by com.andres_k on 17/11/2015.
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
    public void addAnimation(Animation animation, int index, float scale) {
        if (!this.animators.containsKey(index))
            this.animators.put(index, new Animator());
        this.animators.get(index).addAnimation(animation, scale);
    }

    public void addCollision(String jsonValue, int index) throws JSONException {
        if (!this.animators.containsKey(index))
            this.animators.put(index, new Animator());
        this.animators.get(index).addCollision(jsonValue);
    }

    public void addConfig(AnimationConfigItem config, int index) {
        if (!this.animators.containsKey(index))
            this.animators.put(index, new Animator());
        this.animators.get(index).addConfig(config);
    }

    public void addRepercussion(AnimationRepercussionItem repercussion, int index) {
        if (!this.animators.containsKey(index))
            this.animators.put(index, new Animator());
        this.animators.get(index).addRepercussion(repercussion);
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

    public AnimationConfigItem getConfig() {
        return this.animators.get(this.index).getConfig();
    }

    public AnimationRepercussionItem getRepercussion() {
        return this.animators.get(this.index).getRepercussion();
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

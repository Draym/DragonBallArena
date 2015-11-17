package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.gameComponents.bodies.BodyAnimation;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andres_k on 17/11/2015.
 */
public class Animator {
    private List<Animation> animations;
    private List<BodyAnimation> bodies;
    private AnimatorConfig config;
    private Color filter;

    public Animator() {
        this.animations = new ArrayList<>();
        this.bodies = new ArrayList<>();
        this.filter = new Color(1f, 1f, 1f);
        this.config = null;
    }

    public Animator(Animator animator) {
        this.animations = new ArrayList<>();
        this.bodies = new ArrayList<>();

        this.animations.addAll(animator.animations.stream().map(Animation::copy).collect(Collectors.toList()));
        this.bodies.addAll(animator.bodies);
        this.filter = animator.filter;
        this.config = animator.config;
    }

    // METHODS

    public void doAction(GameObject object, int frame) {
        if (config != null)
            this.config.doAction(object, frame);
    }

    public void restart() {
        this.animations.forEach(org.newdawn.slick.Animation::restart);
        if (config != null)
            this.config.reset();
    }

    // ADD FUNCTIONS
    public void addAnimation(Animation animation) {
        this.animations.add(animation.copy());
    }

    public void addCollision(BodyAnimation body) {
        this.bodies.add(body);
    }

    public void addConfig(AnimatorConfig config) {
        this.config = config;
    }

    // GETTERS

    public Animation getAnimation(int index) {
        if (index >= 0 && index < this.animations.size()) {
            return this.animations.get(index);
        }
        return null;
    }

    public AnimatorConfig getConfig() {
        return this.config;
    }

    public BodyAnimation getBodyAnimation(int index) {
        return this.bodies.get(index);
    }

    public Pair<EnumAnimation, Integer> getNext() {
        return this.config.getNext();
    }

    public Color getFilter() {
        return this.filter;
    }

    public boolean canSetIndex(int value) {
        return value >= 0 && value < this.animations.size();
    }
}

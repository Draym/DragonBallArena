package com.andres_k.components.graphicComponents.background;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 08/02/2016.
 */
public abstract class BackgroundComponent {
    protected AnimatorController animator;
    protected float x;
    protected float y;

    protected boolean ready;
    protected boolean running;

    public BackgroundComponent(AnimatorController animator) throws SlickException {
        this(animator, 0, 0);
    }

    public BackgroundComponent(AnimatorController animator, float x, float y) throws SlickException {
        this.ready = false;
        this.running = false;
        this.x = x;
        this.y = y;
        this.animator = animator;
        this.initialize();
        this.ready = true;
    }

    // FUNCTIONS
    public void draw(Graphics g) throws SlickException {
        this.animator.draw(g, this.x, this.y);
    }

    public void update() {
        this.animator.update();
    }

    public abstract void initialize() throws SlickException;

    public void playEffect(int priority, Effect effect) {
        this.animator.playEffect(priority, effect);
    }

    // GETTERS
    public boolean isReady() {
        return this.ready;
    }

    public boolean isRunning() {
        return this.running;
    }

    public boolean hasActivity() {
        return this.animator.hasEffectActivity();
    }

    public boolean effectIsRunning(String id) {
        return this.animator.effectIsRunning(id);
    }

    public boolean effectIsActive(EffectType type) {
        return this.animator.effectIsActive(type);
    }
}

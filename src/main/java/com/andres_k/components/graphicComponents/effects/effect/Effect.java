package com.andres_k.components.graphicComponents.effects.effect;

import com.andres_k.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.utils.configs.GameConfig;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 25/01/2016.
 */
public abstract class Effect {
    private String id;
    private EffectType type;
    private int priority;
    protected boolean running;
    protected int delta;
    private long duration;
    private boolean activated;

    protected Effect(String id, EffectType type, long duration) {
        this.running = false;
        this.id = id;
        this.type = type;
        this.duration = duration;
        this.delta = 0;
        this.activated = false;
    }

    public Effect(Effect effect) {
        this.running = effect.running;
        this.id = effect.id;
        this.type = effect.type;
        this.duration = effect.duration;
        this.delta = effect.delta;
        this.activated = effect.activated;
    }

    public boolean update() {
        this.delta += GameConfig.currentTimeLoop;
        return true;
    }


    public abstract void draw(Graphics g);

    public abstract boolean applyChanges(ImageConfiguration conf);

    public void play() {
        this.running = true;
    }

    // GETTERS
    public String getId() {
        return this.id;
    }

    public EffectType getType() {
        return this.type;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public boolean isRunning() {
        return this.running;
    }

    public long getDuration() {
        return this.duration;
    }

    public int getPriority() {
        return this.priority;
    }

    // SETTERS
    public void setPriority(int value) {
        this.priority = value;
    }

    @Override
    public String toString() {
        return "{id: " + this.id + ", type: " + this.type + "}";
    }
}

package com.andres_k.components.graphicComponents.effects.effect;

import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 25/01/2016.
 */
public abstract class Effect {
    private String id;
    private EnumEffect type;
    protected boolean running;
    private long duration;

    protected Effect(String id, EnumEffect type, long duration) {
        this.running = false;
        this.id = id;
        this.type = type;
        this.duration = duration;
    }

    public abstract void update();
    public abstract void draw(Graphics g);

    public void play() {
        this.running = true;
    }

    // GETTERS
    public String getId() {
        return this.id;
    }

    public EnumEffect getType() {
        return this.type;
    }

    public boolean isRunning() {
        return this.running;
    }

    public long getDuration() {
        return this.duration;
    }
}

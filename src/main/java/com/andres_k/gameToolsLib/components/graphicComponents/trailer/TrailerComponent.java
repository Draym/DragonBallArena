package com.andres_k.gameToolsLib.components.graphicComponents.trailer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by kevin on 26/04/2017.
 */
public abstract class TrailerComponent {

    protected boolean running;
    protected boolean finished;
    protected boolean started;

    protected TrailerComponent() {
        this.running = false;
        this.started = false;
        this.finished = false;
    }

    public void reset() {
        this.running = false;
        this.started = false;
        this.finished = false;
    }

    public abstract void draw(Graphics g) throws SlickException;

    public abstract void update();

    public boolean isRunning() {
        return this.running;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public boolean isStarted() {
        return this.started;
    }
}

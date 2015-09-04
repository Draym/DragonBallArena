package com.andres_k.components.graphicComponents.userInterface.elements;

import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ActivatedTimer;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ColorRect;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 23/06/2015.
 */
public abstract class InterfaceElement {
    protected ColorRect body;
    protected ActivatedTimer activatedTimer;
    protected boolean reachable[];
    protected EnumOverlayElement type;

    // FUNCTION
    protected void parentInit(ColorRect body, EnumOverlayElement type, boolean activated, boolean reachable[]){
        this.body = body;
        this.activatedTimer = new ActivatedTimer(activated);
        this.type = type;
        this.reachable = reachable;
    }

    public void start(){
        this.activatedTimer.startTimer();
    }

    public void stop(){
        this.activatedTimer.stopTimer();
    }

    public abstract void doTask(Object task) throws SlickException;

    public abstract void leave();

    public abstract void draw(Graphics g);

    public abstract void update();

    public abstract void clearData();

    public abstract Object eventPressed(int key, char c);

    public abstract Object eventReleased(int key, char c);

    public abstract Object isOnFocus(int x, int y);

    public boolean sliderMove(int x, int y){
        return false;
    }

    // GETTERS
    public boolean isActivated(){
        return this.activatedTimer.isActivated();
    }

    public boolean[] getReachable(){
        return this.reachable;
    }

    public EnumOverlayElement getType(){
        return this.type;
    }

    public ColorRect getBody(){
        return this.body;
    }

    // SETTERS
    public void setReachable(boolean[] reachable){
        this.reachable = reachable;
    }
}

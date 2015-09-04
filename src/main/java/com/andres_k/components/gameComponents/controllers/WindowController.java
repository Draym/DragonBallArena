package com.andres_k.components.gameComponents.controllers;

import com.andres_k.components.graphicComponents.graphic.WindowBasedGame;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 08/07/2015.
 */
public abstract class WindowController extends Observable implements Observer {
    protected StateBasedGame stateWindow = null;
    protected WindowBasedGame window;

    public abstract void enter() throws SlickException;

    public abstract void leave();

    public abstract void init() throws SlickException, JSONException;

    public abstract void renderWindow(Graphics g);

    public abstract void updateWindow(GameContainer gameContainer) throws SlickException;

    public abstract void keyPressed(int key, char c);

    public abstract void keyReleased(int key, char c);

    public abstract void mouseReleased(int button, int x, int y);

    public abstract void mousePressed(int button, int x, int y);

    public void setStateWindow(StateBasedGame stateWindow){
        this.stateWindow = stateWindow;
    }

    public void setWindow(WindowBasedGame window){
        this.window = window;
    }
}

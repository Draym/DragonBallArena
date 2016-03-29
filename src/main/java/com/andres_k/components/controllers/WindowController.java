package com.andres_k.components.controllers;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.graphicComponents.background.BackgroundManager;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.LocalTaskManager;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 08/07/2015.
 */
public abstract class WindowController implements Observer {
    protected StateBasedGame stateWindow = null;
    protected WindowBasedGame window;
    protected BackgroundManager backgroundManager;
    protected ELocation location;
    protected int idWindow;
    protected LocalTaskManager taskManager;

    protected WindowController(ELocation location, int idWindow) {
        this.location = location;
        this.taskManager = new LocalTaskManager(this.location);
        this.taskManager.register(this.location.getId(), this);
        this.backgroundManager = new BackgroundManager();
        this.idWindow = idWindow;
    }

    public abstract void enter() throws SlickException;

    public abstract void leave();

    public abstract void init() throws SlickException;

    public void renderWindow(Graphics g) throws SlickException {
        this.backgroundManager.draw(g);
    }

    public void update(GameContainer gameContainer) throws SlickException {
        this.backgroundManager.update();
    }

    public void beforeEnter() {
    }

    public abstract void keyPressed(int key, char c);

    public abstract void keyReleased(int key, char c);

    public void eventControllerReceived(EInput event, EInput input) {
    }

    public abstract void mouseReleased(int button, int x, int y);

    public abstract void mousePressed(int button, int x, int y);

    public void register(LocalTaskManager manager) {
        manager.register(this.location.getId(), this.taskManager);
    }

    // GETTERS
    public BackgroundManager getBackgroundManager() {
        return this.backgroundManager;
    }

    // SETTERS
    public void setStateWindow(StateBasedGame stateWindow) {
        this.stateWindow = stateWindow;
    }

    public void setWindow(WindowBasedGame window) {
        this.window = window;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof TaskComponent) {
            TaskComponent received = (TaskComponent) arg;

            if (received.getTarget().equals(this.location)) {
                if (received.getTask() instanceof EnumWindow && !received.getTask().equals(EnumWindow.EXIT)) {
                    this.stateWindow.enterState(((EnumWindow) received.getTask()).getId());
                } else if (received.getTask() instanceof ETaskType) {
                    if (received.getTask() == ETaskType.EXIT) {
                        this.window.quit();
                    } else if (received.getTask() == ETaskType.LAUNCH) {
                        this.beforeEnter();
                        this.stateWindow.enterState(this.idWindow);
                    }
                }
            }
        }
    }
}

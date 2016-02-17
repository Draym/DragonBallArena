package com.andres_k.components.controllers;

import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.configs.GameConfig;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.Observable;

/**
 * Created by andres_k on 09/02/2016.
 */
public class SelectVersusController extends WindowController {
    protected SelectVersusController() {
        super(ELocation.SELECT_VERSUS_CONTROLLER);
    }

    @Override
    public void enter() throws SlickException {

    }

    @Override
    public void leave() {
        GameConfig.typePlayer.clear();
    }

    @Override
    public void init() throws SlickException, JSONException, NoSuchMethodException {

    }

    @Override
    public void keyPressed(int key, char c) {

    }

    @Override
    public void keyReleased(int key, char c) {

    }

    @Override
    public void mouseReleased(int button, int x, int y) {

    }

    @Override
    public void mousePressed(int button, int x, int y) {

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof TaskComponent) {
            TaskComponent received = (TaskComponent) arg;

            if (received.getTarget().equals(ELocation.SELECT_VERSUS_CONTROLLER)) {
                if (received.getTask() instanceof EnumWindow && !received.getTask().equals(EnumWindow.EXIT)) {
                    this.stateWindow.enterState(((EnumWindow) received.getTask()).getId());
                }
            }
        }
    }
}

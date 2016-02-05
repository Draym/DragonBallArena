package com.andres_k.components.controllers;

import com.andres_k.components.gameComponents.resources.ResourceManager;
import com.andres_k.components.graphicComponents.background.Background;
import com.andres_k.components.graphicComponents.background.EBackground;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.Observable;

/**
 * Created by andres_k on 08/07/2015.
 */
public class HomeController extends WindowController {

    public HomeController() throws JSONException, SlickException {
        super(ELocation.HOME_CONTROLLER);
    }

    @Override
    public void enter() throws SlickException {
    }

    @Override
    public void leave() {
    }

    @Override
    public void init() throws SlickException {
        this.background = new Background(ResourceManager.get().getBackgroundAnimator(EBackground.HOME_SCREEN), 0, -9);
    }

    @Override
    public void renderWindow(Graphics g) {
        this.background.draw(g);
    }

    @Override
    public void update(GameContainer gameContainer) throws SlickException {
        this.background.update();
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

            if (received.getTarget().equals(ELocation.HOME_CONTROLLER)) {
                if (received.getTask() instanceof EnumWindow) {
                    if (this.stateWindow != null) {
                        this.stateWindow.enterState(((EnumWindow) received.getTask()).getValue());
                    }
                } else if (received.getTask() instanceof ETaskType) {
                    if (received.getTask() == ETaskType.EVENT) {
                        this.window.quit();
                    }
                }
                //todo a refaire !
                /*
                else if (received.getTask() instanceof MessageGameNew) {
                    if (((MessageGameNew) received.getTask()).getType() == EnumTask.START) {
                        CentralTaskManager.get().sendRequest(TaskFactory.createTask(EnumLocation.HOME_CONTROLLER, EnumLocation.GAME_CONTROLLER, received.getTask()));
                    } else if (((MessageGameNew) received.getTask()).getType() == EnumTask.NEXT) {
                        List<String> values = ((MessageGameNew) received.getTask()).getValues();

                        if (values.size() > 0) {
                            GlobalVariable.currentPlayer = Integer.valueOf(values.get(0));
                            GlobalVariable.currentPlayer = (GlobalVariable.currentPlayer > GlobalVariable.maxPlayer ? GlobalVariable.maxPlayer : GlobalVariable.currentPlayer);
                        }
                    }
                }*/
            }
        }
    }
}

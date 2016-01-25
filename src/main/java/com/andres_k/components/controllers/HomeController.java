package com.andres_k.components.controllers;

import com.andres_k.components.gameComponents.resources.ResourceManager;
import com.andres_k.components.graphicComponents.background.Background;
import com.andres_k.components.graphicComponents.background.EnumBackground;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.networkComponents.networkSend.messageInterface.MessageGameNew;
import com.andres_k.components.taskComponent.EnumTargetTask;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.stockage.Tuple;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.List;
import java.util.Observable;

/**
 * Created by andres_k on 08/07/2015.
 */
public class HomeController extends WindowController {

    public HomeController() throws JSONException, SlickException {
    }

    @Override
    public void enter() {
    }

    @Override
    public void leave() {
    }

    @Override
    public void init() throws SlickException {
        this.background = new Background(ResourceManager.get().getBackgroundAnimator(EnumBackground.HOME_SCREEN), 0, -9);
    }

    @Override
    public void renderWindow(Graphics g) {
        this.background.draw(g);
    }

    @Override
    public void updateWindow(GameContainer gameContainer) {
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
        if (arg instanceof Tuple) {
            Tuple<EnumTargetTask, EnumTargetTask, Object> received = (Tuple<EnumTargetTask, EnumTargetTask, Object>) arg;

            if (received.getV1().equals(EnumTargetTask.WINDOWS) && received.getV2().equals(EnumTargetTask.INTERFACE)) {
                if (received.getV3() instanceof EnumWindow) {
                    if (this.stateWindow != null) {
                        this.stateWindow.enterState(((EnumWindow) received.getV3()).getValue());
                    }
                } else if (received.getV3() instanceof EnumOverlayElement) {
                    if (received.getV3() == EnumOverlayElement.EXIT) {
                        this.window.quit();
                    }
                } else if (received.getV3() instanceof MessageGameNew) {
                    if (((MessageGameNew) received.getV3()).getType() == EnumOverlayElement.GO) {
                        this.setChanged();
                        this.notifyObservers(TaskFactory.createTask(EnumTargetTask.INTERFACE, EnumTargetTask.GAME, received.getV3()));
                    } else if (((MessageGameNew) received.getV3()).getType() == EnumOverlayElement.NEXT) {
                        List<String> values = ((MessageGameNew) received.getV3()).getValues();

                        if (values.size() > 0){
                            Integer value = Integer.valueOf(values.get(0));
                            GlobalVariable.currentPlayer = value;
                            GlobalVariable.currentPlayer = (GlobalVariable.currentPlayer > GlobalVariable.maxPlayer ? GlobalVariable.maxPlayer : GlobalVariable.currentPlayer);
                        }
                    }
                }
            }
        }
    }
}

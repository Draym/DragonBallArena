package com.andres_k.components.controllers.select;

import com.andres_k.components.controllers.WindowController;
import com.andres_k.components.graphicComponents.background.EBackground;
import com.andres_k.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.configs.GameConfig;
import org.newdawn.slick.SlickException;

import java.util.Observable;

/**
 * Created by andres_k on 09/02/2016.
 */
public class SelectVersusController extends WindowController {
    public SelectVersusController() {
        super(ELocation.SELECT_VERSUS_CONTROLLER);
    }

    @Override
    public void enter() throws SlickException {
        GameConfig.typePlayer.clear();
    }

    @Override
    public void leave() {
    }

    @Override
    public void init() throws SlickException {
        this.backgroundManager.addComponent(EBackground.SELECT_SCREEN, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.SELECT_SCREEN), 0, 0));
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

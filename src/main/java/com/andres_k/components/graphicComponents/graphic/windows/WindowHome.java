package com.andres_k.components.graphicComponents.graphic.windows;

import com.andres_k.components.controllers.HomeController;
import com.andres_k.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.windows.HomeGUI;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.resourceComponent.sounds.MusicController;
import com.andres_k.components.taskComponent.LocalTaskManager;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by andres_k on 08/07/2015.
 */
public class WindowHome extends WindowBasedGame {

    public WindowHome(int idWindow, LocalTaskManager windowsTask) throws JSONException, SlickException {
        super(idWindow, new HomeController(), new HomeGUI());
        this.gui.register(windowsTask);
        this.controller.register(windowsTask);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.initBeforeEnter();

        GlobalVariable.appGameContainer.setDisplayMode(WindowConfig.getWHomeSizeX(), WindowConfig.getWHomeSizeY(), false);
        MusicController.get().loop(ESound.BACKGROUND_HOME);
    }


    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        MusicController.get().stop(ESound.BACKGROUND_HOME);
        this.clean();
    }

    @Override
    public void quit() {
        this.clean();
        this.container.exit();
    }
}

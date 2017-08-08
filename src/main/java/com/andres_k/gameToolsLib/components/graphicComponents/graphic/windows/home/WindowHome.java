package com.andres_k.gameToolsLib.components.graphicComponents.graphic.windows.home;

import com.andres_k.custom.component.controllers.home.MyHomeController;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.gameToolsLib.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.custom.component.graphicComponents.userInterface.windowGUI.home.HomeGUI;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.gameToolsLib.components.resourceComponents.sounds.MusicController;
import com.andres_k.gameToolsLib.components.taskComponent.LocalTaskManager;
import com.andres_k.gameToolsLib.utils.configs.WindowConfig;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by com.andres_k on 08/07/2015.
 */
public class WindowHome extends WindowBasedGame {

    public WindowHome(LocalTaskManager windowsTask) throws JSONException, SlickException {
        super(EnumWindow.HOME.getId(), new MyHomeController(EnumWindow.HOME.getId()), new HomeGUI());
        this.gui.register(windowsTask);
        this.controller.register(windowsTask);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        try {
            this.initBeforeEnter();
        } catch (Exception e) {
            throw new SlickException(e.getMessage());
        }

        WindowConfig.get().switchWindow(EnumWindow.getById(this.idWindow));
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

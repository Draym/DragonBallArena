package com.andres_k.gameToolsLib.components.graphicComponents.graphic.windows.game;

import com.andres_k.custom.component.controllers.game.MyGameController;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.gameToolsLib.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.custom.component.graphicComponents.userInterface.windowGUI.game.GameGUI;
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
public class WindowGame extends WindowBasedGame {

    public WindowGame(LocalTaskManager windowsTask) throws JSONException {
        super(EnumWindow.GAME.getId(), new MyGameController(EnumWindow.GAME.getId()), new GameGUI());
        this.gui.register(windowsTask);
        this.controller.register(windowsTask);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.initBeforeEnter();

        WindowConfig.get().switchWindow(EnumWindow.getById(this.idWindow));
        MusicController.get().loop(ESound.BACKGROUND_GAME);
        MusicController.get().changeVolume(0.5f);
    }


    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        MusicController.get().stop(ESound.BACKGROUND_GAME);
        MusicController.get().changeVolume(1.0f);
        this.clean();
    }

    @Override
    public void quit() {
        this.clean();
        this.stateWindow.enterState(EnumWindow.HOME.getId());
    }
}

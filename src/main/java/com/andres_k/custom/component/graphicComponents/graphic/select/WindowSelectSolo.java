package com.andres_k.custom.component.graphicComponents.graphic.select;

import com.andres_k.custom.component.controllers.select.SelectSoloController;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.custom.component.graphicComponents.userInterface.windowGUI.select.SelectSoloGUI;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.gameToolsLib.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.gameToolsLib.components.resourceComponents.sounds.MusicController;
import com.andres_k.gameToolsLib.components.taskComponent.LocalTaskManager;
import com.andres_k.gameToolsLib.utils.configs.WindowConfig;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by com.andres_k on 09/02/2016.
 */
public class WindowSelectSolo extends WindowBasedGame {
    public WindowSelectSolo(LocalTaskManager windowsTask) throws JSONException, SlickException {
        super(EnumWindow.SELECT_SOLO.getId(), new SelectSoloController(EnumWindow.SELECT_SOLO.getId()), new SelectSoloGUI());
        this.gui.register(windowsTask);
        this.controller.register(windowsTask);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.initBeforeEnter();

        WindowConfig.get().switchWindow(EnumWindow.getById(this.idWindow));
        MusicController.get().loop(ESound.BACKGROUND_SELECT);
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        MusicController.get().stop(ESound.BACKGROUND_SELECT);
        this.clean();
    }
}

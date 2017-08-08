package com.andres_k.custom.component.graphicComponents.graphic.multiWait;

import com.andres_k.custom.component.controllers.waiting.BattleConnectionController;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.custom.component.graphicComponents.userInterface.windowGUI.waiting.BattleConnectionGUI;
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
 * Created by com.andres_k on 19/03/2016.
 */
public class WindowBattleConnection extends WindowBasedGame {
    public WindowBattleConnection(LocalTaskManager windowsTask) throws JSONException, SlickException {
        super(EnumWindow.BATTLE_CONNECTION.getId(), new BattleConnectionController(EnumWindow.BATTLE_CONNECTION.getId()), new BattleConnectionGUI());
        this.gui.register(windowsTask);
        this.controller.register(windowsTask);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.initBeforeEnter();

        WindowConfig.get().switchWindow(EnumWindow.getById(this.idWindow));
        MusicController.get().loop(ESound.BACKGROUND_CONNECTION);
    }

    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        MusicController.get().stop(ESound.BACKGROUND_CONNECTION);
        this.clean();
    }
}

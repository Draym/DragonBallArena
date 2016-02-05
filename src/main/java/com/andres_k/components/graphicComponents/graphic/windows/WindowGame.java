package com.andres_k.components.graphicComponents.graphic.windows;

import com.andres_k.components.controllers.GameController;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.windows.GameGUI;
import com.andres_k.components.soundComponents.ESound;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.taskComponent.ELocation;
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
public class WindowGame extends WindowBasedGame {

    public WindowGame(int idWindow, LocalTaskManager windowsTask) throws JSONException {
        super(idWindow, new GameController(), new GameGUI());
        windowsTask.register(ELocation.GAME_CONTROLLER.getId(), this.controller);
        windowsTask.register(ELocation.GAME_GUI.getId(), this.gui);
    }

    public void initContents() throws SlickException {
        if (this.needContentsInit) {
            try {
                this.controller.init();
            } catch (JSONException | NoSuchMethodException e) {
                throw new SlickException(e.getMessage());
            }
            this.gui.init();
            this.needContentsInit = false;
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        GlobalVariable.appGameContainer.setDisplayMode(WindowConfig.getWBigSizeX(), WindowConfig.getWBigSizeY(), false);
        this.initContents();

        this.container.setTargetFrameRate(60);
        this.container.setShowFPS(GlobalVariable.showFps);
        this.container.setAlwaysRender(true);
        this.container.setVSync(true);

        this.delta = 0;
        MusicController.loop(ESound.BACKGROUND_GAME);
        this.gui.enter();
        this.controller.enter();
    }


    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        MusicController.stop(ESound.BACKGROUND_GAME);
        this.clean();
    }

    @Override
    public void quit() {
        this.clean();
        this.stateWindow.enterState(EnumWindow.HOME.getValue());
    }
}

package com.andres_k.components.graphicComponents.graphic.windows;

import com.andres_k.components.controllers.HomeController;
import com.andres_k.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.windows.HomeGUI;
import com.andres_k.components.soundComponents.EnumSound;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.taskComponent.EnumLocation;
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
        windowsTask.register(EnumLocation.HOME_CONTROLLER.getLocation(), this.controller);
        windowsTask.register(EnumLocation.HOME_GUI.getLocation(), this.gui);
    }

    @Override
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
        GlobalVariable.appGameContainer.setDisplayMode(WindowConfig.getWLowSizeX(), WindowConfig.getWLowSizeY(), false);
        this.initContents();

        this.container.setTargetFrameRate(60);
        this.container.setShowFPS(GlobalVariable.showFps);
        this.container.setAlwaysRender(false);
        this.container.setVSync(false);

        this.delta = 0;

        MusicController.loop(EnumSound.BACKGROUND_HOME);
        this.gui.enter();
        this.controller.enter();
    }


    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        MusicController.stop(EnumSound.BACKGROUND_HOME);
        this.clean();
    }

    @Override
    public void quit() {
        this.clean();
        this.container.exit();
    }
}

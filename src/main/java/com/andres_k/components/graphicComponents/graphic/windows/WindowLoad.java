package com.andres_k.components.graphicComponents.graphic.windows;

import com.andres_k.components.controllers.LoadController;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.windows.LoadGUI;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.resourceComponent.sounds.MusicController;
import com.andres_k.components.taskComponent.LocalTaskManager;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.tools.Console;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andres_k on 08/07/2015.
 */
public class WindowLoad extends WindowBasedGame {

    public WindowLoad(int idWindow, LocalTaskManager windowsTask) throws JSONException, SlickException {
        super(idWindow, new LoadController(), new LoadGUI());
        this.gui.register(windowsTask);
        this.controller.register(windowsTask);
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container = gameContainer;
        this.stateWindow = stateBasedGame;

        new Thread(() -> {
            try {
                ResourceManager.get().prerequisiteSound();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        try {
            ResourceManager.get().prerequisiteMusic();
        } catch (Exception e) {
            throw new SlickException(e.getMessage());
        }

        try {
            ResourceManager.get().prerequisiteContents();
        } catch (Exception e) {
            throw new SlickException(e.getMessage());
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                MusicController.get().loop(ESound.BACKGROUND_LOAD);
            }
        }, 700);
        this.controller.setStateWindow(this.stateWindow);
        this.controller.setWindow(this);
        Console.write("TotalToInitialise : " + ResourceManager.get().getTotalToInitialise());
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.initBeforeEnter();

        WindowConfig.get().switchWindow(EnumWindow.getById(this.idWindow));
    }


    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        MusicController.get().stop(ESound.BACKGROUND_LOAD);
        this.clean();
    }

    @Override
    public void quit() {
        this.clean();
        this.container.exit();
    }
}

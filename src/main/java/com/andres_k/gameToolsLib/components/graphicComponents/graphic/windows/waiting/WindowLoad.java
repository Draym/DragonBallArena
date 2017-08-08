package com.andres_k.gameToolsLib.components.graphicComponents.graphic.windows.waiting;

import com.andres_k.custom.component.controllers.waiting.MyLoadController;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.gameToolsLib.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.custom.component.graphicComponents.userInterface.windowGUI.waiting.LoadGUI;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.ResourceManager;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.gameToolsLib.components.resourceComponents.sounds.MusicController;
import com.andres_k.gameToolsLib.components.taskComponent.LocalTaskManager;
import com.andres_k.gameToolsLib.utils.configs.WindowConfig;
import com.andres_k.gameToolsLib.utils.tools.Console;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by com.andres_k on 08/07/2015.
 */
public class WindowLoad extends WindowBasedGame {

    public WindowLoad(LocalTaskManager windowsTask) throws JSONException, SlickException {
        super(EnumWindow.LOAD.getId(), new MyLoadController(EnumWindow.LOAD.getId()), new LoadGUI());
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

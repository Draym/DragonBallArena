package com.andres_k.components.graphicComponents.graphic.windows;

import com.andres_k.components.controllers.LoadController;
import com.andres_k.components.gameComponents.resources.ResourceManager;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.components.graphicComponents.userInterface.overlay.windowOverlay.LoadOverlay;
import com.andres_k.components.soundComponents.EnumSound;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.tools.Console;
import org.codehaus.jettison.json.JSONException;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by andres_k on 08/07/2015.
 */
public class WindowLoad extends WindowBasedGame {

    private long delta;
    private int index;
    private boolean loadCompleted;

    public WindowLoad(int idWindow, GenericSendTask taskManager) throws JSONException, SlickException {
        super(idWindow, new LoadController(), new LoadOverlay(), taskManager);
        this.index = 1;
        this.loadCompleted = false;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container = gameContainer;
        this.stateWindow = stateBasedGame;

        new Thread(() -> {
            try {
                ResourceManager.get().prerequisiteMusic();
                MusicController.loop(EnumSound.BACKGROUND_LOAD);
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                ResourceManager.get().prerequisiteSound();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            ResourceManager.get().prerequisiteContents();
        } catch (NoSuchMethodException | JSONException e) {
            throw new SlickException(e.getMessage());
        }

        this.controller.setStateWindow(this.stateWindow);
        this.controller.setWindow(this);
        Console.write("TotalToInitialise : " + ResourceManager.get().getTotalToInitialise());
    }

    @Override
    public void initContents() throws SlickException {
        if (this.needContentsInit) {
            try {
                this.controller.init();
            } catch (JSONException | NoSuchMethodException e) {
                throw new SlickException(e.getMessage());
            }
            this.overlay.initElementsComponent();
            this.needContentsInit = false;
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        GlobalVariable.appGameContainer.setDisplayMode(WindowConfig.getWMediumSizeX(), WindowConfig.getWMediumSizeY(), false);
        this.initContents();

        this.container.setTargetFrameRate(60);
        this.container.setShowFPS(GlobalVariable.showFps);
        this.container.setAlwaysRender(false);
        this.container.setVSync(false);
        this.delta = 0;

        this.overlay.enter();
        this.controller.enter();
    }


    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        MusicController.stop(EnumSound.BACKGROUND_LOAD);
        this.clean();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        this.delta += i;

        if (this.delta > GlobalVariable.timeLoop) {
            GlobalVariable.currentTimeLoop = this.delta;

            this.controller.updateWindow(gameContainer);
            this.overlay.updateOverlay();
            if (!this.loadCompleted && !this.controller.getBackground().hasActivity()) {
                try {
                    this.loadCompleted = ResourceManager.get().initialise(index);
                    Console.write("index {" + this.index + "} -> " + (this.loadCompleted ? "loadCompleted" : "running") + " " + ResourceManager.get().getPercentInitialised() + "%\n");
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | JSONException e) {
                    throw new SlickException(e.getMessage());
                }
                ++this.index;
            }
            this.delta = 0;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        if (this.loadCompleted && key == Keyboard.KEY_RETURN) {
            this.stateWindow.enterState(EnumWindow.HOME.getValue());
        } else {
            super.keyReleased(key, c);
        }
    }

    @Override
    public void quit() {
        this.clean();
        this.container.exit();
    }
}

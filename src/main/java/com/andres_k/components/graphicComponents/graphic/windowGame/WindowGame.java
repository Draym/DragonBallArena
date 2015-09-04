package com.andres_k.components.graphicComponents.graphic.windowGame;

import com.andres_k.components.gameComponents.animations.AnimatorOverlayData;
import com.andres_k.components.gameComponents.controllers.GameController;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.components.graphicComponents.userInterface.overlay.windowOverlay.GameOverlay;
import com.andres_k.components.soundComponents.EnumSound;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.tools.ConsoleWrite;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by andres_k on 08/07/2015.
 */
public class WindowGame extends WindowBasedGame {
    private long delta;

    public WindowGame(int idWindow, GenericSendTask interfaceTask) throws JSONException {
        this.idWindow = idWindow;

        this.animatorOverlay = new AnimatorOverlayData();

        this.controller = new GameController();
        interfaceTask.addObserver(this.controller);
        this.controller.addObserver(interfaceTask);

        this.overlay = new GameOverlay();
        interfaceTask.addObserver(this.overlay);
        this.overlay.addObserver(interfaceTask);
    }

    @Override
    public int getID() {
        return this.getIdWindow();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container = gameContainer;
        this.stateWindow = stateBasedGame;

        ConsoleWrite.debug("\n START INIT");
        try {
            this.animatorOverlay.init();
        } catch (JSONException e) {
            throw new SlickException(e.getMessage());
        }
        ConsoleWrite.debug("END INIT");
        this.controller.setStateWindow(this.stateWindow);
        this.controller.setWindow(this);

        try {
            this.controller.init();
        } catch (JSONException e) {
            throw new SlickException(e.getMessage());
        }
        this.overlay.initElementsComponent(this.animatorOverlay);
    }


    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container.setTargetFrameRate(60);
        this.container.setShowFPS(GlobalVariable.showFps);
        this.container.setAlwaysRender(true);
        this.container.setVSync(true);

        this.delta = 0;
        MusicController.loop(EnumSound.BACKGROUND_GAME);
        this.overlay.enter();
        this.controller.enter();
        GlobalVariable.appGameContainer.setDisplayMode(WindowConfig.getW2SizeX(), WindowConfig.getW2SizeY(), false);
    }


    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        MusicController.stop(EnumSound.BACKGROUND_GAME);
        this.controller.leave();
        this.clean();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        this.controller.renderWindow(graphics);
        this.overlay.draw(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        this.delta += i;

        if (this.delta > 30) {
            this.controller.updateWindow(gameContainer);
            this.overlay.updateOverlay();
            this.delta = 0;
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        boolean absorbed = this.overlay.event(key, c, EnumInput.PRESSED);
        if (!absorbed) {
            this.controller.keyPressed(key, c);
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        boolean absorbed = this.overlay.event(key, c, EnumInput.RELEASED);
        if (!absorbed) {
            this.controller.keyReleased(key, c);
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        this.controller.mousePressed(button, x, y);
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        if (!this.overlay.isOnFocus(x, y)) {
            this.controller.mouseReleased(button, x, y);
        }
    }

    @Override
    public void quit() {
        this.clean();
        this.stateWindow.enterState(EnumWindow.INTERFACE.getValue());
    }

    @Override
    public void clean() {
        this.overlay.leave();
    }

}

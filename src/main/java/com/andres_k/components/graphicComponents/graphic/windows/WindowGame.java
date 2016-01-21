package com.andres_k.components.graphicComponents.graphic.windows;

import com.andres_k.components.controllers.GameController;
import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.components.graphicComponents.userInterface.overlay.windowOverlay.GameOverlay;
import com.andres_k.components.soundComponents.EnumSound;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by andres_k on 08/07/2015.
 */
public class WindowGame extends WindowBasedGame {
    private long delta;

    public WindowGame(int idWindow, GenericSendTask interfaceTask) throws JSONException {
        super(idWindow, new GameController(), new GameOverlay());

        interfaceTask.addObserver(this.controller);
        this.controller.addObserver(interfaceTask);
        interfaceTask.addObserver(this.overlay);
        this.overlay.addObserver(interfaceTask);
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container = gameContainer;
        this.stateWindow = stateBasedGame;

        this.controller.setStateWindow(this.stateWindow);
        this.controller.setWindow(this);
    }

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
        this.initContents();

        this.container.setTargetFrameRate(60);
        this.container.setShowFPS(GlobalVariable.showFps);
        this.container.setAlwaysRender(true);
        this.container.setVSync(true);

        this.delta = 0;
        MusicController.loop(EnumSound.BACKGROUND_GAME);
        this.overlay.enter();
        this.controller.enter();
        GlobalVariable.appGameContainer.setDisplayMode(WindowConfig.getWBigSizeX(), WindowConfig.getWBigSizeY(), false);
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

        if (this.delta > GlobalVariable.timeLoop) {
            GlobalVariable.currentTimeLoop = this.delta;
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
        this.stateWindow.enterState(EnumWindow.HOME.getValue());
    }

    @Override
    public void clean() {
        this.overlay.leave();
    }

}

package com.andres_k.components.graphicComponents.graphic;

import com.andres_k.components.controllers.WindowController;
import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.UserInterface;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.configs.GlobalVariable;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by andres_k on 08/07/2015.
 */
public abstract class WindowBasedGame extends BasicGameState {
    protected int idWindow;

    protected boolean needContentsInit;

    protected GameContainer container;
    protected StateBasedGame stateWindow;

    protected WindowController controller;
    protected UserInterface gui;

    protected long delta;

    protected WindowBasedGame(int idWindow, WindowController controller, UserInterface gui) {
        this.idWindow = idWindow;
        this.needContentsInit = true;
        this.controller = controller;
        this.gui = gui;
    }

    public void quit() {
        this.clean();
    }

    public void clean() {
        this.controller.leave();
        this.gui.leave();
    }

    @Override
    public int getID() {
        return idWindow;
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

    protected void initBeforeEnter() throws SlickException {
        this.initContents();

        this.delta = 0;
        this.gui.enter();
        this.controller.enter();

        this.container.setTargetFrameRate(GameConfig.maxFps);
        this.container.setShowFPS(GlobalVariable.showFps);
        this.container.setAlwaysRender(false);
        this.container.setVSync(true);
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container = gameContainer;
        this.stateWindow = stateBasedGame;

        this.controller.setStateWindow(this.stateWindow);
        this.controller.setWindow(this);
    }

    @Override
    public abstract void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException;

    @Override
    public abstract void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException;

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        this.controller.renderWindow(graphics);
        this.gui.draw(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        this.delta += i;

        if (this.delta > GameConfig.timeLoop) {
            GameConfig.currentTimeLoop = this.delta;
            this.controller.update(gameContainer);
            this.gui.update();
            this.delta = 0;
            this.myMouseMoved(gameContainer);
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        boolean absorbed = this.gui.event(key, c, EInput.KEY_PRESSED);
        if (!absorbed) {
            this.controller.keyPressed(key, c);
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        boolean absorbed = this.gui.event(key, c, EInput.KEY_RELEASED);
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
        if (!this.gui.isOnClick(x, y)) {
            this.controller.mouseReleased(button, x, y);
        }
    }

    @Override
    public void mouseWheelMoved(int newValue) {
    }

    public void myMouseMoved(GameContainer container) {
        int posX = container.getInput().getMouseX();
        int posY = container.getInput().getMouseY();

        this.gui.isOnFocus(posX, posY);
    }
}

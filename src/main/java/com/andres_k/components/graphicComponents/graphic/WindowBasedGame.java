package com.andres_k.components.graphicComponents.graphic;

import com.andres_k.components.controllers.WindowController;
import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.graphicComponents.userInterface.overlay.Overlay;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.utils.configs.GlobalVariable;
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
    protected Overlay overlay;

    protected long delta;

    protected WindowBasedGame(int idWindow, WindowController controller, Overlay overlay, GenericSendTask taskManager) {
        this.idWindow = idWindow;
        this.needContentsInit = true;
        this.controller = controller;
        this.overlay = overlay;

        taskManager.addObserver(this.controller);
        this.controller.addObserver(taskManager);

        taskManager.addObserver(this.overlay);
        this.overlay.addObserver(taskManager);
    }

    public abstract void initContents() throws SlickException;


    public void quit() {
        this.clean();
    }

    public void clean() {
        this.controller.leave();
        this.overlay.leave();
    }

    @Override
    public int getID() {
        return idWindow;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container = gameContainer;
        this.stateWindow = stateBasedGame;

        this.controller.setStateWindow(this.stateWindow);
        this.controller.setWindow(this);
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
}

package com.andres_k.components.graphicComponents.graphic;

import com.andres_k.components.controllers.WindowController;
import com.andres_k.components.graphicComponents.background.Background;
import com.andres_k.components.graphicComponents.userInterface.overlay.Overlay;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by andres_k on 08/07/2015.
 */
public abstract class WindowBasedGame extends BasicGameState {
    protected int idWindow;

    protected Background background;
    protected boolean needContentsInit;

    protected GameContainer container;
    protected StateBasedGame stateWindow;

    protected WindowController controller;
    protected Overlay overlay;

    protected WindowBasedGame(int idWindow) {
        this(idWindow, null, null);
    }

    protected WindowBasedGame(int idWindow, WindowController controller, Overlay overlay) {
        this.idWindow = idWindow;
        this.needContentsInit = true;
        this.controller = controller;
        this.overlay = overlay;
    }

    public abstract void clean();

    public abstract void quit();

    public abstract void initContents() throws SlickException;

    // GETTERS

    @Override
    public int getID() {
        return idWindow;
    }
}

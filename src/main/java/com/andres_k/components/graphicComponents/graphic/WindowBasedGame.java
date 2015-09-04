package com.andres_k.components.graphicComponents.graphic;

import com.andres_k.components.gameComponents.animations.AnimatorOverlayData;
import com.andres_k.components.gameComponents.controllers.WindowController;
import com.andres_k.components.graphicComponents.userInterface.overlay.Overlay;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by andres_k on 08/07/2015.
 */
public abstract class WindowBasedGame extends BasicGameState {
    protected int idWindow;

    protected WindowController controller;
    protected GameContainer container;
    protected StateBasedGame stateWindow;

    protected AnimatorOverlayData animatorOverlay;
    protected Overlay overlay;


    public abstract void clean();

    public abstract void quit();

    // GETTERS

    public int getIdWindow(){
        return this.idWindow;
    }
}

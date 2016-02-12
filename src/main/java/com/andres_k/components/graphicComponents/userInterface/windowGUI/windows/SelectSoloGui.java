package com.andres_k.components.graphicComponents.userInterface.windowGUI.windows;

import com.andres_k.components.graphicComponents.userInterface.windowGUI.UserInterface;
import com.andres_k.components.taskComponent.ELocation;
import org.newdawn.slick.SlickException;

import java.util.Observable;

/**
 * Created by andres_k on 09/02/2016.
 */
public class SelectSoloGui extends UserInterface {
    public SelectSoloGui() {
        super(ELocation.SELECT_SOLO_GUI);
    }

    @Override
    public void init() throws SlickException {

        this.initElements();
    }

    @Override
    public void initOnEnter() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

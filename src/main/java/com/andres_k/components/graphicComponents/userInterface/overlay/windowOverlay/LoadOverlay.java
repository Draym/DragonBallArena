package com.andres_k.components.graphicComponents.userInterface.overlay.windowOverlay;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlay;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.overlay.Overlay;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.Observable;

/**
 * Created by andres_k on 25/01/2016.
 */
public class LoadOverlay extends Overlay {

    public LoadOverlay() throws JSONException {
        super(EnumOverlay.LOAD);
    }

    @Override
    public void initElements() {

    }

    @Override
    public void initElementsComponent() throws SlickException {

    }

    @Override
    public void initElement(EnumOverlayElement element) throws SlickException {

    }

    @Override
    public void enter() throws SlickException {

    }

    @Override
    public void doTask(Object task) throws SlickException {

    }

    @Override
    public boolean event(int key, char c, EnumInput type) {
        return false;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

package com.andres_k.components.graphicComponents.userInterface.overlay;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.graphicComponents.userInterface.types.InterfaceElement;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.utils.configs.ConfigPath;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 05/07/2015.
 */
public abstract class Overlay extends Observable implements Observer {
    protected int current;
    protected OverlayConfigs overlayConfigs;
    protected Map<EnumOverlayElement, InterfaceElement> elements;
    protected GenericSendTask genericSendTask;

    protected Overlay() throws JSONException {
        this.current = 0;
        this.overlayConfigs = new OverlayConfigs(ConfigPath.preferenceOverlay, ConfigPath.dataOverlay);

        this.genericSendTask = new GenericSendTask();
        this.genericSendTask.addObserver(this);

        this.elements = new LinkedHashMap<>();
    }

    // INIT
    public abstract void initElements();
    public abstract void initElementsComponent() throws SlickException;

    public void initPreference() {
        this.overlayConfigs.getAvailablePreference().entrySet().stream().filter(entry -> this.elements.containsKey(entry.getKey())).forEach(entry -> this.elements.get(entry.getKey()).setReachable(entry.getValue()));
    }

    public abstract void initElement(EnumOverlayElement element) throws SlickException;

    // FUNCTIONS
    public abstract void enter() throws SlickException;

    public void leave() {
        for (Map.Entry<EnumOverlayElement, InterfaceElement> entry : this.elements.entrySet()) {
            entry.getValue().leave();
        }
    }

    public void draw(Graphics g) {
        for (Map.Entry<EnumOverlayElement, InterfaceElement> entry : this.elements.entrySet()) {
            boolean[] reachable = entry.getValue().getReachable();
            if (reachable[this.current]) {
                entry.getValue().draw(g);
            }
        }
    }

    public void updateOverlay() {
        for (Map.Entry<EnumOverlayElement, InterfaceElement> entry : this.elements.entrySet()) {
            entry.getValue().update();
        }
    }

    public void sliderMove(int x, int y) {
        for (Map.Entry<EnumOverlayElement, InterfaceElement> entry : this.elements.entrySet()) {
            entry.getValue().sliderMove(x, y);
        }
    }

    public abstract void doTask(Object task) throws SlickException;

    public abstract boolean event(int key, char c, EnumInput type);

    public boolean isOnFocus(int x, int y) {
        for (Map.Entry<EnumOverlayElement, InterfaceElement> entry : this.elements.entrySet()) {
            boolean[] reachable = entry.getValue().getReachable();
            if (reachable[this.current]) {
                if (entry.getValue().isOnFocus(x, y) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFocused() {
        for (Map.Entry<EnumOverlayElement, InterfaceElement> entry : this.elements.entrySet()) {
            boolean[] reachable = entry.getValue().getReachable();
            if (reachable[this.current]) {
                if (entry.getValue().isActivated()) {
                    return true;
                }
            }
        }
        return false;
    }
}

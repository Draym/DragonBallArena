package com.andres_k.components.graphicComponents.userInterface.windowGUI;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.GuiElement;
import com.andres_k.components.graphicComponents.userInterfaceDeprecated.overlay.GUIConfigs;
import com.andres_k.components.taskComponent.EnumLocation;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Created by andres_k on 01/02/2016.
 */
public abstract class UserInterface implements Observer {
    protected GUIConfigs GUIConfigs;
    protected List<GuiElement> elements;
    private EnumLocation location;

    protected UserInterface(EnumLocation location) {
        this.elements = new ArrayList<>();
    }

    public abstract void init();

    public abstract void initOnEnter();

    protected void initElements() {
        this.elements.forEach(GuiElement::init);
    }

    public void enter() {
        this.initOnEnter();
        this.elements.forEach(GuiElement::enter);
    }

    public void leave() {
        this.elements.forEach(GuiElement::leave);
    }

    public boolean event(int key, char c, EnumInput type) {
        for (GuiElement guiElement : this.elements) {
            if (guiElement.event(key, c, type)) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        this.elements.forEach(GuiElement::update);
    }

    public void draw(Graphics g) {
        this.elements.forEach((guiElement) -> guiElement.draw(g));
    }

    public boolean isOnFocus(int x, int y) {
        for (GuiElement guiElement : this.elements) {
            if (guiElement.isOnFocus(x, y)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOnClick(int x, int y) {
        for (GuiElement guiElement : this.elements) {
            if (guiElement.isOnClick(x, y)) {
                return true;
            }
        }
        return false;
    }

    GuiElement getElementById(String id) {
        for (GuiElement guiElement : this.elements) {
            GuiElement tmp = guiElement.getFromId(id);
            if (tmp != null) {
                return tmp;
            }
        }
        return null;
    }

    public EnumLocation getLocation() {
        return this.location;
    }
}

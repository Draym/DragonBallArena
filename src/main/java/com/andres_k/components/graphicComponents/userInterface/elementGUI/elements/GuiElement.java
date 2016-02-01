package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.ElementID;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EnumElementType;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.Observer;

/**
 * Created by andres_k on 01/02/2016.
 */
public abstract class GuiElement implements Observer {
    protected String id;
    protected boolean focused;
    protected boolean activated;
    protected boolean turnOn;
    protected EnumElementType type;
    protected Rectangle container;

    protected GuiElement(EnumElementType type, Rectangle container, boolean activated) {
        this(type, ElementID.unknown, container, activated);
    }

    protected GuiElement(EnumElementType type, String id, Rectangle container, boolean activated) {
        this.type = type;
        this.id = id;
        this.container = container;
        this.activated = activated;
        this.focused = false;
        this.turnOn = false;
    }

    public abstract void init();

    public abstract void enter();

    public abstract void leave();

    public void update() {
    }

    public void draw(Graphics g) {
    }

    public void draw(Graphics g, float x, float y) {
    }

    public boolean event(int key, char c, EnumInput type) {
        return false;
    }

    public boolean isOnFocus(int x, int y) {
        this.focused = false;
        if (this.activated) {
            this.focused = this.container.contains(x, y);
        }
        return this.focused;
    }

    public boolean isOnClick(int x, int y) {
        return this.isOnFocus(x, y);
    }

    public boolean isActivated() {
        return this.activated;
    }

    public boolean isFocused() {
        return this.focused;
    }

    public Rectangle getContainer() {
        return this.container;
    }

    public String getId() {
        return this.id;
    }

    public EnumElementType getType() {
        return this.type;
    }

    public GuiElement getFromId(String id) {
        if (this.getId().equals(id)) {
            return this;
        }
        return null;
    }

    public void setActivated(boolean value) {
        this.activated = value;
    }

    public void setPosX(float value) {
        this.container.setX(value);
    }

    public void setPosY(float value) {
        this.container.setY(value);
    }

    public void setId(String value) {
        this.id = value;
    }
}

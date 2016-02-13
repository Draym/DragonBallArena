package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.modal;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.eventComponent.input.InputEvent;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by andres_k on 09/02/2016.
 */
public class Modal extends GuiElement {
    protected GuiElement content;

    public Modal(String id, ColorShape body, GuiElement content) {
        super(EGuiType.MODAL, id, body, false);
        this.content = content;
    }

    @Override
    public void init() {
        this.content.init();
    }

    @Override
    public void enter() {
        this.content.enter();
    }

    @Override
    public void leave() {
        this.content.leave();
    }

    @Override
    public void activate() {
        super.activate();
        this.content.activate();
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.content.deactivate();
    }

    @Override
    public void update() {
        this.content.update();
    }

    @Override
    public void draw(Graphics g) {
        if (this.activated) {
            this.body.draw(g);
            this.content.draw(g);
        }
    }

    @Override
    public void draw(Graphics g, float decalX, float decalY) {
        if (this.activated) {
            this.body.draw(g);
            this.content.draw(g, decalX, decalY);
        }
    }

    @Override
    public void draw(Graphics g, ColorShape container) {
        if (this.activated) {
            this.body.draw(g);
            this.content.draw(g, container);
        }
    }

    @Override
    public boolean event(InputEvent input) {
        if (input.key == Input.KEY_ESCAPE && input.type == EInput.KEY_RELEASED) {
            if (this.activated) {
                this.OnKill();
            }
        }
        return false;
    }

    // GETTERS
    @Override
    public boolean isOnFocus(float x, float y) {
        if (!this.activated) {
            this.focused = false;
            return false;
        }

        boolean result = this.content.isOnFocus(x, y);
        if (!result && this.activated && this.body != null) {
            result = this.body.isOnFocus(x, y);
            if (result) {
                this.OnFocus();
            }
        }
        this.focused = result;
        return this.focused;
    }

    @Override
    public boolean isOnClick(float x, float y) {
        if (!this.activated) {
            this.focused = false;
            return false;
        }

        boolean result = this.content.isOnClick(x, y);
        if (!result && this.activated && this.focused) {
            this.OnClick();
        }
        this.clicked = this.focused;
        return this.clicked;
    }

    @Override
    public GuiElement getFromId(String id) {
        if (this.getId().equals(id)) {
            return this;
        }
        return this.content.getFromId(id);
    }

    @Override
    public ColorShape getBody() {
        return this.content.getBody();
    }

    @Override
    public boolean isAlive() {
        return this.content.isAlive();
    }

    @Override
    public boolean isBodyPrintable() {
        return this.content.isBodyPrintable();
    }

    @Override
    public boolean isEmpty() {
        return this.content.isEmpty();
    }

    @Override
    public boolean isNull() {
        return this.content.isNull();
    }

    @Override
    public Color getBodyColor() {
        return this.content.getBodyColor();
    }

    @Override
    public float getAbsoluteWidth() {
        return this.content.getAbsoluteWidth();
    }

    @Override
    public float getAbsoluteHeight() {
        return this.content.getAbsoluteHeight();
    }

    // SETTERS
    @Override
    public void setId(String value) {
        this.id = value;
    }

    @Override
    public void setActivated(boolean value) {
        this.activated = value;
    }

    @Override
    public void setPosX(float value) {
        this.content.setPosX(value);
    }

    @Override
    public void setPosY(float value) {
        this.content.setPosY(value);
    }

    @Override
    public void setBody(ColorShape body) {
        this.content.setBody(body);
    }

    @Override
    public void setBodyColor(Color color) {
        this.content.setBodyColor(color);
    }
}

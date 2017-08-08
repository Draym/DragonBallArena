package com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.modal;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.gameToolsLib.components.eventComponents.input.InputEvent;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.stockage.Tuple;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Created by com.andres_k on 09/02/2016.
 */
public class Modal extends GuiElement {
    protected GuiElement content;
    protected boolean canBeActivatedOnEvent;

    public Modal(String id, ColorShape body, GuiElement content) {
        this(id, body, content, false);
    }

    public Modal(String id, ColorShape body, GuiElement content, boolean canBeActivatedOnEvent) {
        super(EGuiType.MODAL, id, body, false);
        this.content = content;
        this.canBeActivatedOnEvent = canBeActivatedOnEvent;
    }

    @Override
    public void init() throws SlickException {
        this.reset();
        this.content.init();
    }

    @Override
    public void enter() {
        this.reset();
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
                this.OnKill(true);
            } else if (this.canBeActivatedOnEvent) {
                this.OnCreate(false);
            }
        }
        if (this.activated) {
            return this.content.event(input);
        }
        return false;
    }

    @Override
    public Object doTask(Object task) {
        Object result;

        if ((result = super.doTask(task)) != null) {
            return result;
        }
        if (task instanceof Tuple && ((Tuple) task).getV1() instanceof ETaskType) {
            if (((Tuple) task).getV1().equals(ETaskType.SETTER)) {
                if (((Tuple) task).getV2().equals("bodySize") && ((Tuple) task).getV3() instanceof Pair) {
                    this.body.setSizes((float)((Pair) ((Tuple) task).getV3()).getV1(), (float)((Pair) ((Tuple) task).getV3()).getV2());
                    return true;
                }
            }
        }
        return null;
    }

    // GETTERS
    @Override
    public boolean isOnFocus(float x, float y) {
        if (!this.activated) {
            this.focused = false;
            return false;
        }

        boolean save = this.focused;
        this.focused = this.content.isOnFocus(x, y);
        if (!this.focused && this.activated && this.body != null) {
            this.focused = this.body.isOnFocus(x, y);
            if (this.focused) {
                this.OnFocus(save);
            }
        }
        return this.focused;
    }

    @Override
    public boolean isOnClick(float x, float y) {
        if (!this.activated) {
            this.focused = false;
            this.clicked = false;
            return false;
        }
        boolean save = this.clicked;
        this.clicked = this.content.isOnClick(x, y);
        if (!this.clicked && this.activated && this.focused) {
            this.OnClick(save);
        }
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

    public GuiElement getContent() {
        return this.content;
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
    public void setPosX(float posX) {
        this.content.setPosX(posX);
    }

    @Override
    public void setPosY(float posY) {
        this.content.setPosY(posY);
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

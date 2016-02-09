package com.andres_k.components.graphicComponents.userInterface.elementGUI;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorShape;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 01/02/2016.
 */
public abstract class GuiElement implements Observer {
    protected String id;
    protected boolean focused;
    protected boolean activated;
    protected boolean turnOn;
    protected EGuiType type;
    protected ColorShape body;

    protected GuiElement(EGuiType type, String id, ColorShape body, boolean activated) {
        this.type = type;
        this.id = id;
        this.body = body;
        this.activated = activated;
        this.focused = false;
        this.turnOn = false;
    }

    public abstract void init();

    public abstract void enter();

    public abstract void leave();

    public void clear() {
        this.leave();
    }

    public void update() {
    }

    public void draw(Graphics g) {
    }

    public void draw(Graphics g, float decalX, float decalY) {
    }

    public void draw(Graphics g, ColorShape container) {
    }

    public boolean event(int key, char c, EInput type) {
        return false;
    }

    public Object doTask(Object task) {
        if (task instanceof ETaskType) {
            ETaskType action = (ETaskType) task;

            if (action == ETaskType.START_ACTIVITY) {
                this.setActivated(true);
            } else if (action == ETaskType.STOP_ACTIVITY) {
                this.setActivated(false);
            } else if (action == ETaskType.GETTER) {
                return this;
            }
        }
        return null;
    }

    // GETTERS
    public boolean isOnFocus(float x, float y) {
        this.focused = false;
        if (this.activated && this.body != null) {
            this.focused = this.body.isOnFocus(x, y);
        }
        return this.focused;
    }

    public boolean isOnClick(float x, float y) {
        this.turnOn = false;
        if (this.activated) {
            this.turnOn = this.isOnFocus(x, y);
        }
        return this.turnOn;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public boolean isAlive() {
        return true;
    }

    public final boolean isFocused() {
        return this.focused;
    }

    public ColorShape getBody() {
        return this.body;
    }

    public final String getId() {
        return this.id;
    }

    public final EGuiType getType() {
        return this.type;
    }

    public GuiElement getFromId(String id) {
        if (this.getId().equals(id)) {
            return this;
        }
        return null;
    }

    public boolean isBodyPrintable() {
        return this.body != null && this.body.isPrintable();
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public Color getBodyColor() {
        if (this.body != null) {
            return this.body.getColor();
        } else {
            return Color.transparent;
        }
    }

    public float getAbsoluteWidth() {
        if (this.body == null)
            return 0;
        return this.body.getMaxX() - this.body.getMinX();
    }

    public float getAbsoluteHeight() {
        if (this.body == null)
            return 0;
        return this.body.getMaxY() - this.body.getMinY();
    }

    // SETTERS
    public void setActivated(boolean value) {
        this.activated = value;
    }

    public void setPosX(float value) {
        this.body.setPosX(value);
    }

    public void setPosY(float value) {
        this.body.setPosY(value);
    }

    public void setId(String value) {
        this.id = value;
    }

    public void setBody(ColorShape body) {
        if (this.body != null) {
            if (body.getColor() == null) {
                body.setColor(this.body.getColor());
            }
        }
        this.body = body;
    }

    public void setBodyColor(Color color) {
        if (this.body != null) {
            this.body.setColor(color);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof TaskComponent)
            this.doTask(((TaskComponent) arg).getTask());
    }
}

package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorShape;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 01/02/2016.
 */
public class Button extends Element {
    private Element content;
    private TaskComponent task;

    protected Button(Element element, TaskComponent task) {
        super(EGuiType.BUTTON, element.getId(), element.isActivated());
        this.task = task;
        this.content = element;
    }

    @Override
    public void init() {
        this.content.init();
    }

    @Override
    public void enter() {
        this.content.leave();
    }

    // FUNCTIONS
    @Override
    public void leave() {
        this.content.leave();
    }

    @Override
    public void draw(Graphics g) {
        if (this.activated) {
            this.content.draw(g);
        }
    }

    @Override
    public void draw(Graphics g, float decalX, float decalY) {
        if (this.activated) {
            this.content.draw(g, decalX, decalY);
        }
    }

    @Override
    public void draw(Graphics g, ColorShape container) {
        if (this.activated) {
            this.content.draw(g, container);
        }
    }

    @Override
    public void update() {
        this.content.update();
    }

    @Override
    public boolean replace(Element element) throws SlickException {
        this.content.leave();
        this.content = element;
        return true;
    }

    @Override
    public Object doTask(Object task) {
        Object result;

        if ((result = super.doTask(task)) != null) {
            return result;
        } else if (task instanceof Pair && ((Pair) task).getV1() == ETaskType.GETTER && ((Pair) task).getV2().equals("target")) {
            return this.task.getTarget();
        } else {
            return this.content.doTask(task);
        }
    }

    @Override
    public String toString() {
        return this.content.toString();
    }

    // GETTERS
    @Override
    public boolean isActivated() {
        return this.content.isActivated();
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
    public float getAbsoluteWidth() {
        return this.content.getAbsoluteWidth();
    }

    @Override
    public float getAbsoluteHeight() {
        return this.content.getAbsoluteHeight();
    }

    @Override
    public ColorShape getBody() {
        return this.content.getBody();
    }

    @Override
    public boolean isOnFocus(float x, float y) {
        this.focused = false;
        if (this.activated) {
            this.focused = this.content.isOnFocus(x, y);
        }
        return focused;
    }

    @Override
    public boolean isOnClick(float x, float y) {
        this.turnOn = false;
        if (this.activated && this.content.isOnFocus(x, y)) {
            CentralTaskManager.get().sendRequest(this.task);
            this.turnOn = true;
        }
        return turnOn;
    }

    // SETTERS
    @Override
    public void setBody(ColorShape body) {
        this.content.setBody(body);
    }

    @Override
    public void setBodyColor(Color color) {
        this.content.setBodyColor(color);
    }
}

package com.andres_k.components.graphicComponents.userInterfaceDeprecated.items.elements;

import com.andres_k.components.graphicComponents.userInterfaceDeprecated.types.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterfaceDeprecated.items.tools.ColorRect;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 03/07/2015.
 */
public class ButtonElement extends Element {
    private Element element;
    private EnumOverlayElement target;

    public ButtonElement(Element element, EnumOverlayElement type) {
        this.element = element;
        this.type = type;
        this.target = type;
        this.id = element.getId();
    }

    public ButtonElement(Element element, EnumOverlayElement type, EnumOverlayElement target) {
        this.element = element;
        this.type = type;
        this.target = target;
        this.id = element.getId();
    }

    // FUNCTIONS
    @Override
    public void leave() {
        this.element.leave();
    }

    @Override
    public void draw(Graphics g) {
        this.element.draw(g);
    }

    @Override
    public void draw(Graphics g, ColorRect body) {
        this.element.draw(g, body);
    }

    @Override
    public void update() {
        this.element.update();
    }

    @Override
    public boolean replace(Element element) {
        this.element.leave();
        this.element = element;
        return true;
    }

    @Override
    public Object doTask(Object task) {
        if (task instanceof Pair && ((Pair) task).getV1() == EnumTask.GETTER && ((Pair) task).getV2().equals("target")) {
            return this.target;
        } else {
            return this.element.doTask(task);
        }
    }

    @Override
    public String toString() {
        return this.element.toString();
    }

    // GETTERS
    @Override
    public boolean isActivated() {
        return this.element.isActivated();
    }

    @Override
    public boolean isEmpty() {
        return this.element.isEmpty();
    }

    @Override
    public boolean isNull() {
        return this.element.isNull();
    }

    @Override
    public float getAbsoluteWidth() {
        return this.element.getAbsoluteWidth();
    }

    @Override
    public float getAbsoluteHeight() {
        return this.element.getAbsoluteHeight();
    }

    @Override
    public ColorRect getBody() {
        return this.element.getBody();
    }

    @Override
    public Object isOnFocus(float x, float y) {
        if (this.element.isOnFocus(x, y) != null) {
            return this.target;
        } else {
            return null;
        }
    }

    // SETTERS
    @Override
    public void setBody(ColorRect body) {
        this.element.setBody(body);
    }

    @Override
    public void setBodyColor(Color color){
        this.element.setBodyColor(color);
    }
}

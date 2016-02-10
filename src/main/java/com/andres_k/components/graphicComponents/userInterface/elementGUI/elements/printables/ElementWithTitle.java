package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 04/02/2016.
 */
public class ElementWithTitle extends Element {
    private Element title;
    private GuiElement content;
    private Pair<Float, Float> decalTitle;

    public ElementWithTitle(Element title, Pair<Float, Float> decalTitle, GuiElement content, boolean activated) {
        this(null, title, decalTitle, content, activated);
    }

    public ElementWithTitle(ColorShape container, Element title, Pair<Float, Float> decalTitle, GuiElement content, boolean activated) {
        this(ELocation.UNKNOWN.getId(), container, title, decalTitle, content, activated);
    }

    public ElementWithTitle(String id, ColorShape container, Element title, Pair<Float, Float> decalTitle, GuiElement content, boolean activated) {
        super(EGuiType.PRINTABLE, id, container, PositionInBody.MIDDLE_MID, activated);
        this.title = title;
        this.content = content;
        this.decalTitle = decalTitle;
    }

    @Override
    public boolean replace(Element element) throws SlickException {
        if (element instanceof TextElement) {
            this.title.replace(element);
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        this.title.init();
        this.content.init();
    }

    @Override
    public void enter() {
        this.title.enter();
        this.content.enter();
    }

    @Override
    public void leave() {
        this.title.leave();
        this.content.leave();
    }

    @Override
    public void activate() {
        super.activate();
        this.title.activate();
        this.content.activate();
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.title.deactivate();
        this.content.deactivate();
    }

    @Override
    public void draw(Graphics g) {
        if (this.activated) {
            this.draw(g, 0, 0);
        }
    }

    @Override
    public void draw(Graphics g, float decalX, float decalY) {
        if (this.activated) {
            if (this.body != null) {
                this.body.draw(g);

                this.content.draw(g, this.body.cloneAndDecalFrom(decalX, decalY));
                this.title.draw(g, this.body.cloneAndDecalFrom(decalX + this.decalTitle.getV1(), decalY + this.decalTitle.getV2()));
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorShape body) {
        if (this.activated) {

            body.draw(g);
            this.content.draw(g, body.cloneAndDecalFrom(this.body.getMinX(), this.body.getMinY()));
            this.title.draw(g, body.cloneAndDecalFrom(this.body.getMinX() + this.decalTitle.getV1(), this.body.getMinY() + this.decalTitle.getV2()));
        }
    }

    @Override
    public Object doTask(Object task) {
        Object result;

        if ((result = super.doTask(task)) != null) {
            return result;
        } else if ((result = this.title.doTask(task)) != null) {
            return result;
        } else if ((result = this.content.doTask(task)) != null) {
            return result;
        }
        return null;
    }

    @Override
    public GuiElement getFromId(String id) {
        if (this.getId().equals(id)) {
            return this;
        } else if (this.title.getId().equals(id)) {
            return this.title;
        } else if (this.content.getId().equals(id)) {
            return this.content;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        if (this.title.isEmpty()) {
            return true;
        } else if (this.content.isEmpty()) {
            return true;
        }
        return false;

    }

    @Override
    public boolean isNull() {
        if (this.title.isNull()) {
            return true;
        } else if (this.content.isNull()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean event(int key, char c, EInput type) {
        if (this.activated) {
            if (this.title.event(key, c, type)) {
                return true;
            } else if (this.content.event(key, c, type)) {
                return true;
            }
        }
        return false;
    }

    // GETTERS
    @Override
    public boolean isOnFocus(float x, float y) {
        this.focused = false;
        if (this.activated) {
            if (this.title.isOnFocus(x - this.getBody().getMinX() - this.decalTitle.getV1(), y - this.getBody().getMinY() - this.decalTitle.getV2())) {
                this.focused = true;
            }
            if (this.content.isOnFocus(x - this.getBody().getMinX(), y - this.getBody().getMinY())) {
                this.focused = true;
            }
        }
        return this.focused;
    }

    @Override
    public boolean isOnClick(float x, float y) {
        this.turnOn = false;
        if (this.activated) {
            if (this.title.isOnClick(x - this.getBody().getMinX() - this.decalTitle.getV1(), y - this.getBody().getMinY() - this.decalTitle.getV2())) {
                this.turnOn = true;
            }
            if (this.content.isOnClick(x - this.getBody().getMinX(), y - this.getBody().getMinY())) {
                this.turnOn = true;
            }
        }
        return this.turnOn;
    }

    @Override
    public float getAbsoluteWidth() {
        return this.content.getAbsoluteWidth();
    }

    @Override
    public float getAbsoluteHeight() {
        return this.content.getAbsoluteHeight();
    }

}

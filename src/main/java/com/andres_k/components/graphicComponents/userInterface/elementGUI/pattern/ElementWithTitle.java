package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern;

import com.andres_k.components.eventComponent.input.InputEvent;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.taskComponent.ELocation;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 04/02/2016.
 */
public class ElementWithTitle extends GuiElement {
    private Element title;
    private GuiElement content;

    public ElementWithTitle(ColorShape container, Element title, GuiElement content, boolean activated) {
        this(ELocation.UNKNOWN.getId(), container, title, content, activated);
    }

    public ElementWithTitle(Element title, GuiElement content, boolean activated) {
        this(ELocation.UNKNOWN.getId(), title, content, activated);
    }

    public ElementWithTitle(String id, Element title, GuiElement content, boolean activated) {
        super(EGuiType.PRINTABLE, id, content.getBody().cloneIt(), activated);
        this.body.setColor(null);
        this.title = title;
        this.content = content;
        this.content.getBody().setPosition(0, 0);
    }

    public ElementWithTitle(String id, ColorShape container, Element title, GuiElement content, boolean activated) {
        super(EGuiType.PRINTABLE, id, container, activated);
        this.title = title;
        this.content = content;
    }

    @Override
    public void init() {
        this.reset();
        this.title.init();
        this.content.init();
    }

    @Override
    public void enter() {
        this.reset();
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
            ColorShape container = this.body.cloneAndDecalFrom(decalX, decalY);
            container.draw(g);
            this.content.draw(g, container);
            this.title.draw(g, container);
        }
    }

    @Override
    public void draw(Graphics g, ColorShape body) {
        if (this.activated) {
            ColorShape container = body.cloneAndDecalFrom(this.body.getMinX(), this.body.getMinY());
            container.draw(g);
            this.content.draw(g, container.cloneAndChangeSize(container.getSizeX() - this.content.getBody().getMinX(), container.getSizeY()));
            this.title.draw(g, container);
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
        }
        GuiElement result;

        result = this.title.getFromId(id);
        if (result == null) {
            result = this.content.getFromId(id);
        }
        return result;
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
    public boolean event(InputEvent input) {
        if (this.activated) {
            if (this.title.event(input)) {
                return true;
            } else if (this.content.event(input)) {
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
            if (this.title.isOnFocus(x - this.getBody().getMinX(), y - this.getBody().getMinY())) {
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
        this.clicked = false;
        if (this.activated) {
            if (this.title.isOnClick(x - this.getBody().getMinX(), y - this.getBody().getMinY())) {
                this.clicked = true;
            }

            if (this.content.isOnClick(x - this.getBody().getMinX(), y - this.getBody().getMinY())) {
                this.clicked = true;
            }
        }
        return this.clicked;
    }

    @Override
    public float getAbsoluteWidth() {
        return this.title.getBody().compileWidth(this.content.getBody());
    }

    @Override
    public float getAbsoluteHeight() {
        return this.title.getBody().compileHeight(this.content.getBody());
    }

    @Override
    public void setSizes(float sizeX, float sizeY) {
        this.content.setSizes(sizeX - this.content.getBody().getMinX(), sizeY);
    }

}

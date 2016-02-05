package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorShape;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 04/02/2016.
 */
public class Printable extends Element {
    private TextElement content;
    private ImageElement background;

    public Printable(String id, ColorShape container, TextElement content, boolean activated) {
        this(id, container, content, null, activated);
    }

    public Printable(String id, ColorShape container, ImageElement background, boolean activated) {
        this(id, container, null, background, activated);
    }

    public Printable(String id, ColorShape container, TextElement content, ImageElement background, boolean activated) {
        super(EGuiType.PRINTABLE, id, container, PositionInBody.MIDDLE_MID, activated);
        this.content = content;
        this.background = background;
    }

    @Override
    public boolean replace(Element element) throws SlickException {
        if (element instanceof TextElement) {
            this.content.replace(element);
            return true;
        } else if (element instanceof ImageElement) {
            this.background.replace(element);
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        this.content.init();
        this.background.init();
    }

    @Override
    public void enter() {
        this.content.enter();
        this.background.enter();
    }

    @Override
    public void leave() {
        this.content.leave();
        this.background.leave();
    }

    @Override
    public void draw(Graphics g) {
        if (this.activated) {
            this.content.draw(g, this.body);
            this.background.draw(g, this.body);
        }
    }

    @Override
    public void draw(Graphics g, float decalX, float decalY) {
        if (this.activated) {
            ColorShape body = this.body.cloneIt();
            body.setPosition(body.getMinX() + decalX, body.getMinY() + decalY);

            this.content.draw(g, body);
            this.background.draw(g, body);
        }
    }

    @Override
    public void draw(Graphics g, ColorShape container) {
        if (this.activated) {
            this.content.draw(g, container);
            this.background.draw(g, container);
        }
    }

    @Override
    public Object doTask(Object task) {
        Object result;

        if ((result = super.doTask(task)) != null) {
            return result;
        } else if ((result = this.content.doTask(task)) != null) {
            return result;
        } else if ((result = this.background.doTask(task)) != null) {
            return result;
        }
        return null;
    }

    public GuiElement getFromId(String id) {
        if (this.getId().equals(id)) {
            return this;
        } else if (this.content.getId().equals(id)) {
            return this.content;
        } else if (this.background.getId().equals(id)) {
            return this.background;
        }
        return null;
    }

    public boolean isEmpty() {
        if (this.content.isEmpty()) {
            return true;
        } else if (this.background.isEmpty()) {
            return true;
        }
        return false;

    }

    @Override
    public boolean isNull() {
        if (this.content.isNull()) {
            return true;
        } else if (this.background.isNull()) {
            return true;
        }
        return false;
    }
}

package com.andres_k.components.graphicComponents.userInterface.items.listElements;

import com.andres_k.components.graphicComponents.userInterface.items.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.items.tools.ColorRect;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 27/06/2015.
 */
public class ImageListElement extends ListElement {
    private boolean changed;

    public ImageListElement(ColorRect body) {
        this.body = body;
        this.elements = new ArrayList<>();
        this.changed = false;
    }

    public ImageListElement() {
        this.body = null;
        this.elements = new ArrayList<>();
        this.changed = false;
    }

    // FUNCTIONS

    @Override
    public void draw(Graphics g) {
        if (this.body != null) {
            this.body.draw(g);
            for (Element element : this.elements) {
                element.draw(g);
            }
        }
    }

    @Override
    public void update() {
        boolean removed = false;

        for (int i = 0; i < this.elements.size(); ++i) {
            if (!this.elements.get(i).isActivated()) {
                this.elements.remove(i);
                removed = true;
                --i;
            }
        }
        if (removed){
            this.updatePosition();
        }
        if (this.changed){
            this.updatePosition();
        }
    }

    @Override
    public void addAllToPrint(List<Object> messageData, Element.PositionInBody positionInBody) {
        this.elements.clear();
        for (Object message : messageData) {
            this.addToPrint(message, positionInBody);
        }
    }

    @Override
    public void addToPrint(Object object, Element.PositionInBody positionInBody) {
        if (object instanceof Element){
            this.elements.add(0, (Element) object);
            this.changed = true;
        }
    }

    @Deprecated
    @Override
    public void addToPrint(Object object, long time, Element.PositionInBody positionInBody) {
        if (object instanceof Element){
            this.elements.add(0, (Element) object);
            this.changed = true;
        }
    }

    @Override
    public Object isOnFocus(float x, float y) {
        for (Element element : this.elements) {
            if (element.isOnFocus(x, y) != null) {
                if (!element.isEmpty()) {
                    return element;
                }
            }
        }
        if (body.isOnFocus(x, y)){
            return null;
        }
        return null;
    }

    @Override
    protected void updatePosition() {
        if (this.body != null) {
            float currentX = this.body.getMinX();
            float currentY = this.body.getMaxY();

            for (Element element : this.elements) {
                if (currentY - element.getAbsoluteHeight() >= this.body.getMinY()) {
                    currentY -= element.getAbsoluteHeight();
                    element.setBody(new ColorRect(new Rectangle(currentX, currentY, this.body.getSizeX(), element.getAbsoluteHeight())));
                } else {
                    element.setBody(new ColorRect(new Rectangle(-1f, -1f, this.body.getSizeX(), element.getAbsoluteHeight())));
                }
            }
            this.changed = false;
        }
    }

}

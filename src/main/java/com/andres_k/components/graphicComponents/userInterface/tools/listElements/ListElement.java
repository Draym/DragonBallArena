package com.andres_k.components.graphicComponents.userInterface.tools.listElements;

import com.andres_k.components.graphicComponents.userInterface.tools.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ColorRect;
import org.newdawn.slick.Graphics;

import java.util.List;

/**
 * Created by andres_k on 27/06/2015.
 */
public abstract class ListElement {
    protected List<Element> elements;
    protected ColorRect body;
    protected float border;

    public void leave() {
        for (Element element : this.elements) {
            element.leave();
        }
    }

    public void clear() {
        for (Element element : this.elements) {
            element.leave();
        }
        this.elements.clear();
    }

    public abstract void draw(Graphics g);

    public abstract void update();

    protected abstract void updatePosition();

    public abstract void addAllToPrint(List<Object> messageData, Element.PositionInBody positionInBody);

    public abstract void addToPrint(Object object, Element.PositionInBody positionInBody);

    public abstract void addToPrint(Object object, long time, Element.PositionInBody positionInBody);

    public void sendTask(Object task) {
        for (Element element : this.elements) {
            element.doTask(task);
        }
    }

    public Object isOnFocus(float x, float y) {
        for (Element element : this.elements) {
            if (element.isOnFocus(x, y) != null && element.isEmpty() == false) {
                return element;
            }
        }
        return null;
    }

    public int containsHeadElement(String id) {
        for (int i = 0; i < this.elements.size(); ++i) {
            if (this.containsHeadId(this.elements.get(i).getId(), id)) {
                return i;
            }
        }
        return -1;
    }

    public int containsElement(String id) {
        for (int i = 0; i < this.elements.size(); ++i) {
            if (this.elements.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    public Element getElement(String id) {
        for (Element element : this.elements) {
            if (this.containsHeadId(element.getId(), id)) {
                return element;
            }
        }
        return null;
    }

    public void deleteElement(String id) {
        for (int i = 0; i < this.elements.size(); ++i) {
            if (this.elements.get(i).getId().equals(id)) {
                this.elements.remove(i);
                --i;
            }
        }
    }

    protected boolean containsHeadId(String head, String id) {
        if (head.contains(":")) {
            return head.substring(0, head.indexOf(":")).equals(id.substring(0, id.indexOf(":")));
        } else {
            return head.equals(id);
        }
    }

    // GETTERS
    public ColorRect getBody() {
        return this.body;
    }

    // SETTERS
    public void setBody(ColorRect body) {
        this.body = body;
        this.updatePosition();
    }
}

package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex;

import com.andres_k.components.eventComponent.input.InputEvent;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.TextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import com.andres_k.utils.tools.Console;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 01/02/2016.
 */
public class ComplexElement extends GuiElement {
    protected List<GuiElement> items;

    public ComplexElement(ColorShape container, boolean activated) {
        this(ELocation.UNKNOWN.getId(), container, activated);
    }

    public ComplexElement(String id, ColorShape container, boolean activated) {
        this(EGuiType.COMPLEX, id, container, activated);
    }

    protected ComplexElement(EGuiType type, String id, ColorShape container, boolean activated) {
        super(type, id, container, activated);
        this.items = new ArrayList<>();
    }

    @Override
    public void init() throws SlickException {
        this.reset();
        for (GuiElement item : this.items) {
            item.init();
        }
    }

    @Override
    public void enter() {
        this.reset();
        this.items.forEach(GuiElement::enter);
    }

    @Override
    public void leave() {
        this.items.forEach(GuiElement::leave);
    }

    @Override
    public void activate() {
        super.activate();
        this.items.forEach(GuiElement::activate);
    }

    @Override
    public void deactivate() {
        super.deactivate();

        Console.write("Complex: " + this + " -> DEACTIVATE");
        this.items.forEach(GuiElement::deactivate);
    }

    @Override
    public void clear() {
        super.clear();
        this.clearItems();
    }

    @Override
    public void update() {
        this.items.forEach(GuiElement::update);
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
            this.body.cloneAndDecalFrom(decalX, decalY).draw(g);

            for (GuiElement item : this.items) {
                item.draw(g, this.body.getMinX() + decalX, this.body.getMinY() + decalY);
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorShape body) {
        if (this.activated) {
            ColorShape container = body.cloneAndDecalFrom(this.body.getMinX(), this.body.getMinY());
            container.draw(g);
            for (GuiElement item : this.items) {
                item.draw(g, container);
            }
        }
    }

    @Override
    public Object doTask(Object task) {
        if (task instanceof Pair) {
            if (((Pair) task).getV1().equals(ETaskType.ADD) && ((Pair) task).getV2() instanceof GuiElement) {
                GuiElement item = (GuiElement) ((Pair) task).getV2();
                try {
                    if (!(item instanceof TextElement)) {
                        item.init();
                    }
                    this.addItem(item);
                    return true;
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            } else if (((Pair) task).getV1().equals(ETaskType.DELETE) && ((Pair) task).getV2() instanceof String) {
                this.deleteItem((String) ((Pair) task).getV2());
                return true;
            }
        } else if (task instanceof Tuple) {
            if (((Tuple) task).getV1().equals(ETaskType.RELAY) && ((Tuple) task).getV2() instanceof String) {
                GuiElement item = this.getElementById((String) ((Tuple) task).getV2());
                if (item != null) {
                    item.doTask(((Tuple) task).getV3());
                }
            }
        }
        return super.doTask(task);
    }

    @Override
    public boolean event(InputEvent input) {
        if (this.activated) {
            for (GuiElement item : this.items) {
                if (item.event(input)) {
                    return true;
                }
            }
        }
        return false;
    }

    // GETTERS
    @Override
    public boolean isOnFocus(float x, float y) {
        this.focused = false;
        if (this.activated) {
            this.items.stream().filter(item -> item.isOnFocus(x - this.getBody().getMinX(), y - this.getBody().getMinY())).forEach(item -> this.focused = true);
        }
        return this.focused;
    }

    @Override
    public boolean isOnClick(float x, float y) {
        boolean result = false;
        if (this.activated) {
            for (int i = this.items.size() - 1; i >= 0; --i) {
                if (this.items.get(i).isOnClick(x - this.getBody().getMinX(), y - this.getBody().getMinY())) {
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public GuiElement getFromId(String id) {
        if (this.getId().equals(id)) {
            return this;
        }
        for (GuiElement item : this.items) {
            GuiElement tmp = item.getFromId(id);
            if (tmp != null) {
                return tmp;
            }
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public boolean isNull() {
        for (GuiElement item : this.items) {
            if (item.isNull()) {
                return true;
            }
        }
        return false;
    }

    // MODIFIER
    public void addItem(GuiElement item) {
        if (item != null) {
            this.items.add(item);
        }
    }

    public void addItems(List<GuiElement> items) {
        this.items.addAll(items);
    }

    public void deleteItem(GuiElement item) {
        this.deleteItem(item.getId());
    }

    public void deleteItem(String id) {
        int pos;

        if ((pos = this.getElementPositionById(id)) != -1) {
            this.items.remove(pos);
        }
    }

    public void clearItems() {
        this.items.clear();
    }

    // GETTERS
    public List<GuiElement> getItems() {
        return this.items;
    }

    public GuiElement getElementById(String id) {
        for (GuiElement item : this.items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public GuiElement getFirstElementByType(EGuiType type) {
        for (GuiElement item : this.items) {
            if (item.getType() == type) {
                return item;
            }
        }
        return null;
    }

    public int getElementPositionById(String id) {
        for (int i = 0; i < this.items.size(); ++i) {
            if (this.items.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public float getAbsoluteWidth() {
        float minX = 0;
        float maxX = 0;
        boolean firstMin = true;
        boolean firstMax = true;

        for (GuiElement item : this.items) {
            if (item.getLocationMinX() < minX || firstMin) {
                minX = item.getLocationMinX();
                firstMin = false;
            }
            if (item.getLocationMaxX() > maxX || firstMax) {
                maxX = item.getLocationMaxX();
                firstMax = false;
            }
        }
        return maxX - minX;
    }

    @Override
    public float getAbsoluteHeight() {
        float minY = 0;
        float maxY = 0;
        boolean firstMin = true;
        boolean firstMax = true;

        for (GuiElement item : this.items) {
            if (item.getLocationMinY() < minY || firstMin) {
                minY = item.getLocationMinY();
                firstMin = false;
            }
            if (item.getLocationMaxY() > maxY || firstMax) {
                maxY = item.getLocationMaxY();
                firstMax = false;
            }
        }
        return maxY - minY;
   }
}
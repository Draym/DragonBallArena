package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.list;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.taskComponent.ELocation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 08/02/2016.
 */
public class ListElement extends ComplexElement {
    protected float marginX;
    protected float marginY;
    protected boolean changed;
    protected List<ColorShape> positions;

    public ListElement(ColorShape body, float marginX, float marginY, boolean activated) {
        this(ELocation.UNKNOWN.getId(), body, marginX, marginY, activated);
    }

    public ListElement(String id, ColorShape body, float marginX, float marginY, boolean activated) {
        super(EGuiType.LIST, id, body, activated);
        this.changed = false;
        this.marginX = marginX;
        this.marginY = marginY;
        this.positions = new ArrayList<>();
    }

    @Override
    public void update() {
        boolean removed = false;

        for (int i = 0; i < this.items.size(); ++i) {
            if (!this.items.get(i).isAlive()) {
                this.items.remove(i);
                removed = true;
                --i;
            }
        }
        if (removed) {
            this.updatePosition();
        }
        if (this.changed) {
            this.updatePosition();
        }
    }

    @Override
    public void draw(Graphics g, float decalX, float decalY) {
        if (this.activated) {
            this.body.cloneAndDecalFrom(decalX, decalY).draw(g);

            for (int i = 0; i < this.positions.size(); ++i) {
                if (i >= this.items.size()) {
                    break;
                }
                this.items.get(i).draw(g, this.positions.get(i).cloneAndDecalFrom(this.body.getMinX() + decalX, this.body.getMinY() + decalY));
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorShape body) {
        if (this.activated) {
            ColorShape container = body.cloneAndDecalFrom(this.body.getMinX(), this.body.getMinY());
            container.draw(g);

            for (int i = 0; i < this.positions.size(); ++i) {
                if (i >= this.items.size()) {
                    break;
                }
                this.items.get(i).draw(g, container.cloneAndDecalFrom(this.positions.get(i)));
            }
        }
    }

    // MODIFIER
    @Override
    public void addItem(GuiElement item) {
        super.addItem(item);
        this.changed = true;
    }

    @Override
    public void addItems(List<GuiElement> items) {
        super.addItems(items);
        this.changed = true;
    }

    @Override
    public void deleteItem(String id) {
        super.deleteItem(id);
        this.changed = true;
    }

    @Override
    public void clearItems() {
        super.clearItems();
        this.changed = true;
    }

    protected void updatePosition() {
        if (this.body != null) {
            float currentX = this.marginX;
            float currentY = 0;

            for (GuiElement element : this.items) {
                this.positions.add(new ColorRect(new Rectangle(currentX, currentY, this.body.getSizeX() - (2 * this.marginX), element.getAbsoluteHeight())));
                currentY += element.getAbsoluteHeight() + this.marginY;

                if (currentY >= this.body.getSizeY()) {
                    break;
                }
            }
            this.changed = false;
        }
    }

    // GETTER
    @Override
    public boolean isOnFocus(float x, float y) {
        this.focused = false;
        if (this.activated) {
            for (int i = 0; i < this.positions.size(); ++i) {
                if (i >= this.items.size()) {
                    break;
                }
                this.items.get(i).isOnFocus(x - this.getBody().getMinX() - this.positions.get(i).getMinX(), y - this.getBody().getMinY() - this.positions.get(i).getMinY());
            }
        }
        return this.focused;
    }

    @Override
    public boolean isOnClick(float x, float y) {
        if (this.activated) {
            for (int i = 0; i < this.positions.size(); ++i) {
                if (i >= this.items.size()) {
                    break;
                }
                if (this.items.get(i).isOnClick(x - this.getBody().getMinX() - this.positions.get(i).getMinX(), y - this.getBody().getMinY() - this.positions.get(i).getMinY()))
                    return true;
            }
        }
        return false;
    }


}

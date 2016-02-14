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
    protected int startPos;

    public ListElement(ColorShape body, float marginX, float marginY, boolean activated) {
        this(ELocation.UNKNOWN.getId(), body, marginX, marginY, activated);
    }

    public ListElement(String id, ColorShape body, float marginX, float marginY, boolean activated) {
        super(EGuiType.LIST, id, body, activated);
        this.changed = false;
        this.marginX = marginX;
        this.marginY = marginY;
        this.positions = new ArrayList<>();
        this.startPos = 0;
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

            int pos;
            for (int i = 0; i < this.positions.size(); ++i) {
                pos = this.startPos + i;
                if (pos >= this.items.size()) {
                    break;
                }
                this.items.get(pos).draw(g, this.positions.get(i).cloneAndDecalFrom(this.body.getMinX() + decalX, this.body.getMinY() + decalY));
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorShape body) {
        if (this.activated) {
            ColorShape container = body.cloneAndDecalFrom(this.body.getMinX(), this.body.getMinY());
            container.draw(g);

            int pos;
            for (int i = 0; i < this.positions.size(); ++i) {
                pos = this.startPos + i;
                if (pos >= this.items.size()) {
                    break;
                }
                this.items.get(pos).draw(g, container.cloneAndDecalFrom(this.positions.get(i)));
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

            this.positions.clear();

            for (int i = this.startPos; i < this.items.size(); ++i) {
                ColorShape container = new ColorRect(new Rectangle(currentX, currentY, this.body.getSizeX() - (2 * this.marginX), this.items.get(i).getAbsoluteHeight()));
                this.positions.add(container);
                currentY += this.items.get(i).getAbsoluteHeight() + this.marginY;

                this.items.get(i).setSizes(container.getSizeX(), container.getSizeY());
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
            int pos;
            for (int i = 0; i < this.positions.size(); ++i) {
                pos = this.startPos + i;
                if (pos >= this.items.size()) {
                    break;
                }
                this.items.get(pos).isOnFocus(x - this.getBody().getMinX() - this.positions.get(i).getMinX(), y - this.getBody().getMinY() - this.positions.get(i).getMinY());
            }
        }
        return this.focused;
    }

    @Override
    public boolean isOnClick(float x, float y) {
        boolean result = false;

        if (this.activated) {
            int pos;
            for (int i = 0; i < this.positions.size(); ++i) {
                pos = this.startPos + i;
                if (pos >= this.items.size()) {
                    break;
                }
                if (this.items.get(pos).isOnClick(x - this.getBody().getMinX() - this.positions.get(i).getMinX(), y - this.getBody().getMinY() - this.positions.get(i).getMinY()))
                    result = true;
            }
        }
        return result;
    }


}

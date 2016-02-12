package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.list;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.taskComponent.ELocation;
import org.newdawn.slick.geom.Rectangle;

import java.util.List;

/**
 * Created by andres_k on 08/02/2016.
 */
public class ListElement extends ComplexElement {
    protected float marginX;
    protected float marginY;
    protected boolean changed;

    public ListElement(ColorShape body, float marginX, float marginY, boolean activated) {
        this(ELocation.UNKNOWN.getId(), body, marginX, marginY, activated);
    }

    public ListElement(String id, ColorShape body, float marginX, float marginY, boolean activated) {
        super(EGuiType.LIST, id, body, activated);
        this.changed = false;
        this.marginX = marginX;
        this.marginY = marginY;
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
            boolean tooManyItems = false;

            for (GuiElement element : this.items) {
                if (!tooManyItems) {
                    element.setActivated(true);
                    element.setBody(new ColorRect(new Rectangle(currentX, currentY, element.getAbsoluteWidth(), element.getAbsoluteHeight())));
                } else {
                    element.setActivated(false);
                }
                currentY += element.getAbsoluteHeight() + this.marginY;

                if (currentY >= this.body.getSizeY()) {
                    tooManyItems = true;
                }
            }
            this.changed = false;
        }
    }


}

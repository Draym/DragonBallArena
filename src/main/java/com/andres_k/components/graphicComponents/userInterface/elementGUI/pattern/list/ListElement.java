package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.list;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorShape;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.utils.tools.Console;
import org.newdawn.slick.geom.Rectangle;

import java.util.List;

/**
 * Created by andres_k on 08/02/2016.
 */
public class ListElement extends ComplexElement {
    protected int marginX;
    protected int marginY;
    protected boolean changed;

    public ListElement(ColorShape body, int marginX, int marginY, boolean activated) {
        this(ELocation.UNKNOWN.getId(), body, marginX, marginY, activated);
    }

    public ListElement(String id, ColorShape body, int marginX, int marginY, boolean activated) {
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
                    Console.write("add Item");
                } else {
                    element.setActivated(false);
                    Console.write("mask Item");
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

package com.andres_k.components.graphicComponents.userInterface.elementGUI.tools;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by andres_k on 27/06/2015.
 */
public class ColorRect extends ColorShape {

    public ColorRect(Rectangle body) {
        super(body, null);
    }

    public ColorRect(Rectangle body, Color color) {
        super(body, color);
    }

    public ColorRect(Rectangle body, Color color, boolean printable) {
        super(body, color, printable);
    }

    // SETTERS

    @Override
    public ColorShape cloneIt() {
        return new ColorRect(new Rectangle(this.body.getMinX(), this.body.getMinY(), this.body.getWidth(), this.body.getHeight()), this.color);
    }

    @Override
    public void setSizes(float sizeX, float sizeY){
        ((Rectangle)this.body).setSize(sizeX, sizeY);
    }
}

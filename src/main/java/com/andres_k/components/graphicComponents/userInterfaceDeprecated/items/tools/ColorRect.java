package com.andres_k.components.graphicComponents.userInterfaceDeprecated.items.tools;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by andres_k on 27/06/2015.
 */
@Deprecated
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

    // GETTERS

    public Rectangle getBody() {
        return (Rectangle)this.body;
    }

    // SETTERS

    public void setSizes(float sizeX, float sizeY){
        ((Rectangle)this.body).setSize(sizeX, sizeY);
    }
}

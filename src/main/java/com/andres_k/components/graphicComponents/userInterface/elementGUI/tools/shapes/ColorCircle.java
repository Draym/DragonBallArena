package com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;

/**
 * Created by andres_k on 10/02/2016.
 */
public class ColorCircle extends ColorShape {
    public ColorCircle(Circle body) {
        super(body, null);
    }

    public ColorCircle(Circle body, Color color) {
        super(body, color);
    }

    public ColorCircle(Circle body, Color color, boolean printable) {
        super(body, color, printable);
    }

    // SETTERS

    @Override
    public ColorShape cloneIt() {
        return new ColorCircle(new Circle(this.body.getCenterX(), this.body.getCenterY(), ((Circle)this.body).getRadius()), this.color);
    }

    @Override
    public void setSizes(float sizeX, float sizeY){
        ((Circle)this.body).setRadius(sizeX / 2);
    }
}

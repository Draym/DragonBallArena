package com.andres_k.components.graphicComponents.effects.items;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

/**
 * Created by andres_k on 26/01/2016.
 */
public class ColorShape {
    private Shape body;
    private Color color;

    public ColorShape(Shape body, Color color) {
        this.body = body;
        this.color = color;
    }

    // FUNCTIONS
    public void draw(Graphics g) {
        if (this.color != null) {
            g.setColor(this.color);
            g.fill(this.body);
        }
    }

    // GETTERS

    public Color getColor() {
        return this.color;
    }

    public Shape getBody() {
        return this.body;
    }


    // SETTERS
    public void setPosition(float x, float y) {
        this.body.setX(x);
        this.body.setY(y);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void changeColor(float r, float g, float b, float a) {
        this.color.r = r / 255.0F;
        this.color.g = g / 255.0F;
        this.color.b = b / 255.0F;
        this.color.a = a / 255.0F;
    }
}

package com.andres_k.components.graphicComponents.userInterface.elementGUI.tools;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

/**
 * Created by andres_k on 26/01/2016.
 */
public abstract class ColorShape {
    protected Shape body;
    protected Color color;
    protected boolean printable;

    public ColorShape(Shape body, Color color) {
        this(body, color, true);
    }

    public ColorShape(Shape body, Color color, boolean printable) {
        this.body = body;
        this.color = color;
        this.printable = printable;
    }

    // FUNCTIONS
    public void draw(Graphics g) {
        if (this.color != null && this.isPrintable()) {
            g.setColor(this.color);
            g.fill(this.body);
        }
    }

    public boolean isOnFocus(float x, float y) {
        return this.body.contains(x, y);
    }

    public abstract ColorShape cloneIt();

    public ColorShape cloneAndDecalFrom(float x, float y) {
        ColorShape shape = this.cloneIt();

        shape.setPosition(shape.getMinX() + x, shape.getMinY() + y);
        return shape;
    }

    // GETTERS

    public float getMaxX() {
        return this.body.getMinX() + this.getSizeX();
    }

    public float getMaxY() {
        return this.body.getMinY() + this.getSizeY();
    }

    public float getMinX() {
        return this.body.getMinX();
    }

    public float getMinY() {
        return this.body.getMinY();
    }

    public float getSizeX() {
        return this.body.getWidth();
    }

    public float getSizeY() {
        return this.body.getHeight();
    }

    public Color getColor() {
        return this.color;
    }

    public Shape getBody() {
        return this.body;
    }

    public boolean isPrintable() {
        return this.printable;
    }

    // SETTERS

    public void setPosition(float x, float y) {
        this.body.setX(x);
        this.body.setY(y);
    }

    public void setPosX(float x) {
        this.body.setX(x);
    }

    public void setPosY(float y) {
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

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }

    public abstract void setSizes(float sizeX, float sizeY);

    @Override
    public String toString() {
        return "[" + this.getMinX() + ", " + this.getMinY() + "] (" + this.getBody().getWidth() + ", " + this.getBody().getHeight() + ")";
    }
}

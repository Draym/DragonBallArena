package com.andres_k.components.graphicComponents.userInterface.tools.elements;

import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ColorRect;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 27/06/2015.
 */
public abstract class Element {
    protected ColorRect body;
    protected String id;
    protected EnumOverlayElement type;
    protected PositionInBody position;

    public static enum PositionInBody {
        LEFT_UP, MIDDLE_UP, RIGHT_UP,
        LEFT_MID, MIDDLE_MID, RIGHT_MID,
        LEFT_DOWN, MIDDLE_DOWN, RIGHT_DOWN
    }

    protected void init(ColorRect body, String id, PositionInBody position, EnumOverlayElement type){
        this.body = body;
        this.id = id;
        this.position = position;
        this.type = type;
    }

    public abstract void leave();

    public abstract void draw(Graphics g);

    public abstract void draw(Graphics g, ColorRect body);

    public abstract void update();

    public abstract boolean replace(Element element) throws SlickException;

    public abstract Object doTask(Object task);

    // GETTERS
    public Object isOnFocus(float x, float y){
        if (this.body != null && this.body.isOnFocus(x, y)){
            return true;
        }
        return null;
    }

    public abstract boolean isActivated();

    public abstract boolean isEmpty();

    public abstract boolean isNull();

    public String getId(){
        return this.id;
    }

    public EnumOverlayElement getType(){
        return this.type;
    }

    public ColorRect getBody(){
        return this.body;
    }

    public Color getBodyColor(){
        if (this.body != null) {
            return this.body.getColor();
        } else {
            return Color.transparent;
        }
    }

    public abstract float getAbsoluteWidth();

    public abstract float getAbsoluteHeight();

    public boolean isBodyPrintable(){
        if (this.body != null){
            return this.body.isPrintable();
        }
        return false;
    }

    // SETTERS
    public void setBody(ColorRect body){
        if (this.body != null) {
            if (body.getColor() == null){
                body.setColor(this.body.getColor());
            }
        }
        this.body = body;
    }

    public void setBodyColor(Color color){
        if (this.body == null){
            this.body = new ColorRect(null);
        }
        this.body.setColor(color);
    }

    public abstract String toString();
}

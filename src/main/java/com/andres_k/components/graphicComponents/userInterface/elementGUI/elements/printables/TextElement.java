package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorShape;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

/**
 * Created by andres_k on 04/02/2016.
 */
public class TextElement extends Element {
    private StringTimer textTimer;
    private TrueTypeFont font;
    private float size;
    private Color textColor;

    public TextElement(StringTimer textTimer, Color textColor, PositionInBody position, boolean activated) {
        this(ELocation.UNKNOWN.getId(), textTimer, textColor, position, activated);
    }

    public TextElement(String id, StringTimer textTimer, Color textColor, PositionInBody position, boolean activated) {
        this(id, textTimer, textColor, EFont.BASIC, 16, position, activated);
    }
    public TextElement(String id, StringTimer textTimer, Color textColor, EFont font, float size, PositionInBody position, boolean activated) {
        this(id, textTimer, textColor, font, size, null, position, activated);
    }

    public TextElement(String id, StringTimer textTimer, Color textColor, EFont font, float size, ColorShape body, PositionInBody position, boolean activated) {
        super(EGuiType.TEXT, id, body, position, activated);
        this.textTimer = textTimer;
        this.textColor = textColor;
        this.size = size;

        Font item = ResourceManager.get().getFont(font);
        item = item.deriveFont(this.size);
        this.font = new TrueTypeFont(item, true);
    }

    @Override
    public void init() {
    }

    @Override
    public void enter() {
    }

    @Override
    public void leave() {
        this.textTimer.leave();
    }

    @Override
    public void draw(Graphics g) {
        if (this.activated) {
            this.draw(g, 0, 0);
        }
    }

    @Override
    public void draw(Graphics g, float decalX, float decalY) {
        if (this.activated) {
            if (this.body != null) {
                Pair<Float, Float> position = this.getChoicePosition(this.body);
                position.setV1(position.getV1() + decalX);
                position.setV2(position.getV2() + decalY);

                this.body.draw(g);
                this.drawText(this.body, position);
            } else {
                this.drawString(this.textTimer.getValue(), decalX, decalY);
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorShape container) {
        if (this.activated) {
            ColorShape body = container.cloneIt();

            Pair<Float, Float> position = this.getChoicePosition(body);
            if (this.body != null) {
                if (body.getColor() == null) {
                    body.setColor(this.body.getColor());
                }
                position.setV1(this.body.getMinX() + position.getV1());
                position.setV2(this.body.getMinY() + position.getV2());
            }

            body.draw(g);
            //Console.write("pos: " + position + " in " + body + "##" + this.getAbsoluteWidth() + ", " + this.getAbsoluteHeight() + "##");
            this.drawText(body, position);
        }
    }

    private void drawText(ColorShape body, Pair<Float, Float> position) {
        this.drawString(this.textTimer.getValue(), position.getV1(), position.getV2());
    }

    private void drawString(String value, float posX, float posY) {
        this.font.drawString(posX, posY, value, this.textColor);
    }

    /*
    private Pair<Float, Float> getChoicePosition(ColorShape body, String value) {
        float x = body.getMinX();
        float y = body.getMinY();

        if (this.position == PositionInBody.MIDDLE_MID || this.position == PositionInBody.MIDDLE_UP) {
            float sizeX = (body.getSizeX() / 2) - ((value.length() * this.size) / 2);

            sizeX = (sizeX < 0 ? 0 : sizeX);
            x += sizeX;
        } else if (this.position == PositionInBody.RIGHT_MID || this.position == PositionInBody.RIGHT_UP) {
            float sizeX = (body.getSizeX() - (value.length() * this.size));

            sizeX = (sizeX < 0 ? 0 : sizeX);
            x += sizeX;
        }
        return new Pair<>(x, y);
    }*/

    @Override
    public void update() {
    }

    @Override
    public boolean replace(Element element) throws SlickException {
        if (element.getType() == EGuiType.TEXT) {
            TextElement newElement = (TextElement) element;

            this.textTimer.replace(newElement.textTimer);
            this.textColor = newElement.textColor;
            return true;
        }
        return false;
    }

    @Override
    public Object doTask(Object task) {
        Object result;

        if ((result = super.doTask(task)) != null) {
            return result;
        } else if (task instanceof String) {
            if (this.textTimer.getValue().contains(":")) {
                String v1 = this.textTimer.getValue().substring(0, this.textTimer.getValue().indexOf(":") + 1);
                String v2 = (String) task;
                this.textTimer.setValue(v1 + v2);
                return true;
            } else {
                this.textTimer.setValue((String) task);
            }
        }
        return null;
    }

    public void addToValue(int position, String add) {
        this.textTimer.add(position, add);
    }

    public void deleteValue(int start, int number) {
        this.textTimer.delete(start, number);
    }

    // GETTERS
    @Override
    public boolean isAlive() {
        return this.textTimer.isActivated();
    }

    @Override
    public boolean isEmpty() {
        return this.textTimer.getValue().equals("");
    }

    @Override
    public boolean isNull() {
        return (this.textTimer == null);
    }

    @Override
    public float getAbsoluteWidth() {
        if (this.body != null) {
            return this.body.getSizeX();
        }
        return (this.textTimer.getValue().length() * this.size) / 1.40f;
    }

    @Override
    public float getAbsoluteHeight() {
        if (this.body != null) {
            return this.body.getSizeY();
        }
        return this.size * 1.2f;
    }

    public String getValue() {
        return this.textTimer.getValue();
    }

    public Color getTextColor() {
        return this.textColor;
    }

    // SETTERS
    public void setValue(String value) {
        this.textTimer.setValue(value);
    }

    @Override
    public String toString() {
        if (this.textTimer != null) {
            return this.textTimer.getValue();
        } else {
            return null;
        }
    }
}

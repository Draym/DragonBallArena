package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorShape;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.StringTools;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 04/02/2016.
 */
public class TextElement extends Element {
    private StringTimer textTimer;
    private Color textColor;

    public TextElement(StringTimer textTimer, Color textColor, PositionInBody position, boolean activated) {
        this(ELocation.UNKNOWN.getId(), textTimer, textColor, position, activated);
    }

    public TextElement(String id, StringTimer textTimer, Color textColor, PositionInBody position, boolean activated) {
        this(id, textTimer, textColor, null, position, activated);
    }

    public TextElement(String id, StringTimer textTimer, Color textColor, ColorShape body, PositionInBody position, boolean activated) {
        super(EGuiType.TEXT, id, body, position, activated);
        this.textTimer = textTimer;
        this.textColor = textColor;
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
                int begin = this.textTimer.getValue().length() - (int) (this.body.getSizeX() / StringTools.charSizeX());
                begin = (begin < 0 ? 0 : begin);

                String value = this.textTimer.getValue().substring(begin);

                Pair<Float, Float> position = this.getChoicePosition(this.body, value);

                this.body.draw(g);
                g.setColor(this.textColor);
                g.drawString(value, position.getV1() + decalX, position.getV2() + decalY);
            } else {
                g.drawString(this.textTimer.getValue(), decalX, decalY);
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorShape body) {
        if (this.activated) {
            int begin = this.textTimer.getValue().length() - (int) (body.getSizeX() / StringTools.charSizeX());
            begin = (begin < 0 ? 0 : begin);

            String value = this.textTimer.getValue().substring(begin);

            Pair<Float, Float> position = this.getChoicePosition(body, value);

            ColorShape tmp = body.cloneIt();
            if (this.body != null && tmp.getColor() == null) {
                tmp.setColor(this.body.getColor());
            }

            tmp.draw(g);
            g.setColor(this.textColor);
            g.drawString(value, position.getV1(), position.getV2());
        }
    }

    private Pair<Float, Float> getChoicePosition(ColorShape body, String value) {
        float x = body.getMinX();
        float y = body.getMinY();

        if (this.position == PositionInBody.MIDDLE_MID || this.position == PositionInBody.MIDDLE_UP) {
            float sizeX = (body.getSizeX() / 2) - ((value.length() * StringTools.charSizeX()) / 2);

            sizeX = (sizeX < 0 ? 0 : sizeX);
            x += sizeX;
        } else if (this.position == PositionInBody.RIGHT_MID || this.position == PositionInBody.RIGHT_UP) {
            float sizeX = (body.getSizeX() - (value.length() * StringTools.charSizeX()));

            sizeX = (sizeX < 0 ? 0 : sizeX);
            x += sizeX;
        }
        return new Pair<>(x, y);
    }

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
    public boolean isActivated() {
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
        return this.textTimer.getValue().length() * StringTools.charSizeX();
    }

    @Override
    public float getAbsoluteHeight() {
        return StringTools.charSizeY();
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

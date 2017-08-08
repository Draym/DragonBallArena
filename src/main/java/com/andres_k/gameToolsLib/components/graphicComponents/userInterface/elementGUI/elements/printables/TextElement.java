package com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.printables;

import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.gameToolsLib.components.resourceComponents.fonts.EFont;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.ResourceManager;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.stockage.Tuple;
import com.andres_k.gameToolsLib.utils.tools.ColorTools;
import com.andres_k.gameToolsLib.utils.tools.Console;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;

/**
 * Created by com.andres_k on 04/02/2016.
 */
public class TextElement extends Element {
    protected StringTimer textTimer;
    protected TrueTypeFont font;
    protected float size;
    protected Color textColor;
    protected EFont fontType;

    public TextElement(StringTimer textTimer, Color textColor, boolean activated) {
        this(ELocation.UNKNOWN.getId(), textTimer, textColor, activated);
    }

    public TextElement(String id, StringTimer textTimer, Color textColor, boolean activated) {
        this(id, textTimer, textColor, EFont.BASIC, 16, activated);
    }

    public TextElement(StringTimer textTimer, Color textColor, EFont font, float size, boolean activated) {
        this(ELocation.UNKNOWN.getId(), textTimer, textColor, font, size, null, PositionInBody.LEFT_UP, activated);
    }

    public TextElement(String id, StringTimer textTimer, Color textColor, EFont font, float size, boolean activated) {
        this(id, textTimer, textColor, font, size, null, PositionInBody.LEFT_UP, activated);
    }

    public TextElement(StringTimer textTimer, Color textColor, EFont font, float size, float decalX, float decalY, PositionInBody position, boolean activated) {
        this(ELocation.UNKNOWN.getId(), textTimer, textColor, font, size, decalX, decalY, position, activated);
    }

    public TextElement(String id, StringTimer textTimer, Color textColor, EFont font, float size, float decalX, float decalY, PositionInBody position, boolean activated) {
        this(id, textTimer, textColor, font, size, new ColorRect(new Rectangle(decalX, decalY, textTimer.getAbsoluteWidth(size), textTimer.getAbsoluteHeight(size))), position, activated);
    }

    public TextElement(String id, StringTimer textTimer, Color textColor, EFont font, float size, ColorShape body, PositionInBody position, boolean activated) {
        super(EGuiType.TEXT, id, body, position, activated);
        this.textTimer = textTimer;
        this.textColor = textColor;
        this.size = size;
        this.fontType = font;
        this.font = null;
        if (this.body == null) {
            this.body = new ColorRect(new Rectangle(0, 0, 0, 0));
        }
        if (this.body.getSizeX() <= 0 && this.body.getSizeY() <= 0) {
            this.body.setSizes(textTimer.getAbsoluteWidth(this.size), this.textTimer.getAbsoluteHeight(this.size));
        }
    }

    @Override
    public void init() {
        this.reset();
        this.createFont();
    }

    @Override
    public void enter() {
        this.reset();
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
            ColorShape container = this.body.cloneAndDecalFrom(decalX, decalY);
            Pair<Float, Float> position = this.getChoicePosition(container);
            container.draw(g);
            this.drawText(position);
        }
    }

    @Override
    public void draw(Graphics g, ColorShape body) {
        if (this.activated) {
            ColorShape container = body.cloneAndDecalFrom(this.body.getMinX(), this.body.getMinY());

            Pair<Float, Float> position = this.getChoicePosition(container);

            if (container.getColor() == null) {
                container.setColor(this.body.getColor());
            }
            container.draw(g);
            this.drawText(position);
        }
    }

    private void createFont() {
        Font item = ResourceManager.get().getFont(this.fontType);
        item = item.deriveFont(this.size);
        this.font = new TrueTypeFont(item, true);
    }

    private void drawText(Pair<Float, Float> position) {
        this.drawString(this.textTimer.getValue(), position.getV1(), position.getV2());
    }

    private void drawString(String value, float posX, float posY) {
        if (this.font == null) {
            this.createFont();
        }
        this.font.drawString(posX, posY, value, this.textColor);
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

        Console.write("Received: " + task);
        if ((result = super.doTask(task)) != null) {
            return result;
        } else if (task instanceof Tuple && ((Tuple) task).getV1() instanceof ETaskType) {
            ETaskType order = (ETaskType) ((Tuple) task).getV1();
            Object target = ((Tuple) task).getV2();
            Object value = ((Tuple) task).getV3();

            if (order == ETaskType.SETTER) {
                if (target.equals("value") && value instanceof String) {
                    this.textTimer.setValue((String) value);
                } else if (target.equals("color") && value instanceof ColorTools.Colors) {
                    this.body.setColor(ColorTools.get((ColorTools.Colors) value));
                }
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
        return this.textTimer.getAbsoluteWidth(this.size);
    }

    @Override
    public float getAbsoluteHeight() {
        if (this.body != null) {
            return this.body.getSizeY();
        }
        return this.textTimer.getAbsoluteHeight(this.size);
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
            return "(" + super.toString() + " " + this.textTimer.getValue() + ")";
        } else {
            return null;
        }
    }
}

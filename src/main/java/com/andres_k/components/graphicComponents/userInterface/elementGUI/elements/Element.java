package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 01/02/2016.
 */
public abstract class Element extends GuiElement {
    protected PositionInBody position;

    public static enum PositionInBody {
        LEFT_UP, MIDDLE_UP, RIGHT_UP,
        LEFT_MID, MIDDLE_MID, RIGHT_MID,
        LEFT_DOWN, MIDDLE_DOWN, RIGHT_DOWN
    }

    protected Element(EGuiType type, String id, boolean activated) {
        this(type, id, null, PositionInBody.MIDDLE_MID, activated);
    }

    protected Element(EGuiType type, String id, ColorShape container, PositionInBody position, boolean activated) {
        super(type, id, container, activated);
        this.position = position;
    }

    public abstract boolean replace(Element element) throws SlickException;

    protected Pair<Float, Float> getChoicePosition(ColorShape body) {
        if (body == null || (this.getAbsoluteWidth() == 0 && this.getAbsoluteHeight() == 0)) {
            return new Pair<>(0f, 0f);
        }
        float x = body.getMinX();
        float y = body.getMinY();

        if (this.position == PositionInBody.MIDDLE_MID) {
            float sizeX = (body.getSizeX() / 2) - (this.getAbsoluteWidth() / 2);
            float sizeY = (body.getSizeY() / 2) - (this.getAbsoluteHeight() / 2);

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.RIGHT_MID) {
            float sizeX = (body.getSizeX() - this.getAbsoluteWidth());
            float sizeY = (body.getSizeY() / 2) - (this.getAbsoluteHeight() / 2);

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.MIDDLE_DOWN) {
            float sizeX = (body.getSizeX() / 2) - (this.getAbsoluteWidth() / 2);
            float sizeY = (body.getSizeY() - this.getAbsoluteHeight());

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.RIGHT_DOWN) {
            float sizeX = (body.getSizeX() - this.getAbsoluteWidth());
            float sizeY = (body.getSizeY() - this.getAbsoluteHeight());

            sizeX = (sizeX < 0 ? 0 : sizeX);
            sizeY = (sizeY < 0 ? 0 : sizeY);
            x += sizeX;
            y += sizeY;
        } else if (this.position == PositionInBody.LEFT_DOWN) {
            float sizeY = (body.getSizeY() - this.getAbsoluteHeight());

            sizeY = (sizeY < 0 ? 0 : sizeY);
            y += sizeY;
        } else if (this.position == PositionInBody.MIDDLE_UP) {
            float sizeX = (body.getSizeX() / 2) - (this.getAbsoluteWidth() / 2);

            sizeX = (sizeX < 0 ? 0 : sizeX);
            x += sizeX;
        } else if (this.position == PositionInBody.RIGHT_UP) {
            float sizeX = (body.getSizeX() - this.getAbsoluteWidth());

            sizeX = (sizeX < 0 ? 0 : sizeX);
            x += sizeX;
        }

        return new Pair<>(x, y);
    }
}

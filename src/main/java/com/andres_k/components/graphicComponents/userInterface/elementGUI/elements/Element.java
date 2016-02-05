package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorShape;
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

    protected Element(EGuiType type, String id, ColorShape container, PositionInBody position, boolean activated) {
        super(type, id, container, activated);
        this.position = position;
    }

    protected Element(EGuiType type, String id, boolean activated) {
        this(type, id, null, PositionInBody.MIDDLE_MID, activated);
    }

    public abstract boolean replace(Element element) throws SlickException;

}

package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.specific;

import com.andres_k.components.eventComponent.input.InputEvent;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.SelectionTextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.utils.configs.NetworkServerConfig;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by andres_k on 30/03/2016.
 */
public class SelectionIPElement extends SelectionTextElement {
    public SelectionIPElement(StringTimer textTimer, Color textColor, EFont font, float size, float decalX, float decalY, PositionInBody position, boolean activated) {
        this(ELocation.UNKNOWN.getId(), textTimer, textColor, font, size, new ColorRect(new Rectangle(decalX, decalY, textTimer.getAbsoluteWidth(size), textTimer.getAbsoluteHeight(size))), position, activated);
    }

    public SelectionIPElement(String id, StringTimer textTimer, Color textColor, EFont font, float size, ColorShape body, PositionInBody position, boolean activated) {
        super(id, textTimer, textColor, font, size, body, position, activated);
    }

    @Override
    public boolean event(InputEvent input) {
        boolean empty = this.textTimer.getValue().equals("...");
        if (super.event(input)) {
            if (empty) {
                this.textTimer.setValue("");
                super.event(input);
            }
            if (this.textTimer.getValue().isEmpty()) {
                this.textTimer.setValue("...");
            }
            NetworkServerConfig.setAddress(this.textTimer.getValue());
            return true;
        }
        return false;
    }
}
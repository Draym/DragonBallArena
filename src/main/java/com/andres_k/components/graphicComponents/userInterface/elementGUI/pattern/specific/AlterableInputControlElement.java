package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.specific;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.eventComponent.input.InputData;
import com.andres_k.components.eventComponent.input.InputEvent;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.SelectionTextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.utils.tools.Console;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by andres_k on 18/03/2016.
 */
public class AlterableInputControlElement extends SelectionTextElement {

    public AlterableInputControlElement(String id, StringTimer textTimer, Color textColor, EFont font, float size, float decalX, float decalY, PositionInBody position, boolean activated) {
        this(id, textTimer, textColor, font, size, new ColorRect(new Rectangle(decalX, decalY, textTimer.getAbsoluteWidth(size), textTimer.getAbsoluteHeight(size))), position, activated);
    }

    public AlterableInputControlElement(String id, StringTimer textTimer, Color textColor, EFont font, float size, ColorShape body, PositionInBody position, boolean activated) {
        super(id, textTimer, textColor, font, size, body, position, activated);
    }

    @Override
    public boolean event(InputEvent input) {
        if (this.isActivated() && this.isClicked() && input.type == EInput.KEY_RELEASED) {
            String newValue = Input.getKeyName(input.key);
            Console.write("NEW VALUE: " + newValue);
            if (InputData.setAvailableInput(EInput.getEnumByValue(this.getId()), newValue)) {
                this.textTimer.setValue(newValue);
                return true;
            }
        }
        return false;
    }

}

package com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.list;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.gameToolsLib.components.eventComponents.input.InputEvent;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import org.newdawn.slick.Input;

/**
 * Created by com.andres_k on 14/02/2016.
 */
public class ScrollableList extends ListElement {

    public ScrollableList(ColorShape body, float marginX, float marginY, boolean activated) {
        super(body, marginX, marginY, activated);
    }

    public ScrollableList(String id, ColorShape body, float marginX, float marginY, boolean activated) {
        super(id, body, marginX, marginY, activated);
    }

    @Override
    public boolean event(InputEvent input) {
        boolean result = false;

        if (input.type == EInput.KEY_RELEASED) {
            if (input.key == Input.KEY_UP) {
                this.startPos = (this.startPos > 0 ? this.startPos - 1 : this.startPos);
                this.updatePosition();
                result = true;
            } else if (input.key == Input.KEY_DOWN) {
                this.startPos = (this.startPos < (this.items.size() - this.positions.size()) ? this.startPos + 1 : this.startPos);
                this.updatePosition();
                result = true;
            }
        } else if (input.type == EInput.KEY_PRESSED) {
            if (input.key == Input.KEY_UP || input.key == Input.KEY_DOWN) {
                result = true;
            }
        }
        return (result || super.event(input));
    }
}

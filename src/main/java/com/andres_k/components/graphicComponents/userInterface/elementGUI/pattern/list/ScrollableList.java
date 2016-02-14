package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.list;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.eventComponent.input.InputEvent;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import org.newdawn.slick.Input;

/**
 * Created by andres_k on 14/02/2016.
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
        boolean result = super.event(input);

        if (input.type == EInput.KEY_RELEASED) {
            if (input.key == Input.KEY_UP) {
                this.startPos = (this.startPos > 0 ? this.startPos - 1 : this.startPos);
                this.updatePosition();
            } else if (input.key == Input.KEY_DOWN) {
                this.startPos = (this.startPos < (this.items.size() - this.positions.size()) ? this.startPos + 1 : this.startPos);
                this.updatePosition();
            }
        }
        return result;
    }
}

package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EStatus;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.utils.stockage.Pair;

import java.util.List;

/**
 * Created by andres_k on 17/02/2016.
 */
public class ButtonLock extends Button {
    public ButtonLock(Element element, List<Pair<EStatus, Object>> tasks) {
        super(element, tasks);
    }

    @Override
    public boolean isOnClick(float x, float y) {
        if (this.activated && !this.clicked && this.content.isOnClick(x, y)) {
            this.clicked = true;
            this.OnClick(false);
        }
        return this.clicked;
    }

}

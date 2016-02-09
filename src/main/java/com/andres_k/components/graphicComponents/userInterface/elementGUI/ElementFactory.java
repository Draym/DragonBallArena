package com.andres_k.components.graphicComponents.userInterface.elementGUI;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ElementWithTitle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.TextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 09/02/2016.
 */
public class ElementFactory {
    public static TextElement createText(String text, Color color, int size, EFont font) {
        return new TextElement(ELocation.UNKNOWN.getId(), new StringTimer(text), color, font, size, Element.PositionInBody.MIDDLE_MID, true);
    }

    public static ElementWithTitle createButton(String text, Color color, int size, EFont font, EGuiElement button, TaskComponent task) throws SlickException {
        return new ElementWithTitle(createText(text, color, size, font), new Pair<>(30f, 0f), new Button(new ImageElement(ResourceManager.get().getGuiAnimator(button), true), task), true);
    }
}

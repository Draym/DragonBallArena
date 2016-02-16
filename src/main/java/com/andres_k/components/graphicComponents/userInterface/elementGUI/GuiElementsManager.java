package com.andres_k.components.graphicComponents.userInterface.elementGUI;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andres_k on 16/02/2016.
 */
public class GuiElementsManager {
    private Map<String, GuiElement> elements;

    private GuiElementsManager () {
        this.elements = new HashMap<>();
    }

    private static class SingletonHolder {
        private final static GuiElementsManager instance = new GuiElementsManager();
    }

    public static GuiElementsManager get() {
        return SingletonHolder.instance;
    }

    public void add(String id, GuiElement element) {
        this.elements.put(id, element);
    }

    public GuiElement getElement(String id) {
        if (this.elements.containsKey(id)) {
            return this.elements.get(id);
        }
        return null;
    }
}

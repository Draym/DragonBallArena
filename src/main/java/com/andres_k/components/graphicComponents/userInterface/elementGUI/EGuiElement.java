package com.andres_k.components.graphicComponents.userInterface.elementGUI;

/**
 * Created by andres_k on 05/02/2016.
 */
public enum EGuiElement {
    /* Admin */
    NULL("NULL"),
    LOAD_GUI("LOAD_GUI"),
    HOME_GUI("HOME_GUI"),
    GAME_GUI("GAME_GUI"),

    LOAD_BAR("LOAD_BAR", LOAD_GUI.getValue()),
    LOADING_EMPTY("LOADING_EMPTY", LOAD_GUI.getValue()),
    LOADING_FULL("LOADING_FULL", LOAD_GUI.getValue());

    private final String value;
    private final String type;

    EGuiElement(String value) {
        this.value = value;
        this.type = value;
    }

    EGuiElement(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return this.type;
    }

    public static EGuiElement getEnumByValue(String value) {
        EGuiElement[] enums = EGuiElement.values();
        for (EGuiElement type : enums) {
            if (type.getValue().equals(value))
                return type;
        }
        return NULL;
    }

    public static EGuiElement getEnumByType(String value) {
        EGuiElement[] enums = EGuiElement.values();
        for (EGuiElement type : enums) {
            if (value.equals(type.getValue())) {
                return type;
            }
        }
        return NULL;
    }

    public boolean isIn(EGuiElement dir) {
        EGuiElement current = EGuiElement.getEnumByValue(this.value);

        while (!current.getValue().equals(current.getType())) {
            if (current == dir) {
                return true;
            } else {
                current = EGuiElement.getEnumByType(current.getType());
            }
        }
        return (current == dir);
    }
}

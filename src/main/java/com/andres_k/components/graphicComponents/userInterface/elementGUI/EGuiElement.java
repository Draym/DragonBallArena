package com.andres_k.components.graphicComponents.userInterface.elementGUI;

/**
 * Created by andres_k on 05/02/2016.
 */
public enum EGuiElement {
    /* Admin */
    NULL("NULL"),
    BUTTON("BUTTON"),
    CONTAINER("CONTAINER"),
    COMPONENT("COMPONENT"),

    PANEL1("PANEL1", CONTAINER.getValue()),
    PANEL2("PANEL2", CONTAINER.getValue()),
    PANEL3("PANEL3", CONTAINER.getValue()),
    PANEL4("PANEL4", CONTAINER.getValue()),

    BUTTON_PLAY_SOLO("BUTTON_PLAY_SOLO", BUTTON.getValue()),
    BUTTON_PLAY_VERSUS("BUTTON_PLAY_VERSUS", BUTTON.getValue()),
    BUTTON_PLAY_MULTI("BUTTON_PLAY_MULTI", BUTTON.getValue()),
    BUTTON_SETTING("BUTTON_SETTING", BUTTON.getValue()),
    BUTTON_CONTROLS("BUTTON_CONTROLS", BUTTON.getValue()),
    BUTTON_SLIDER("BUTTON_SLIDER", BUTTON.getValue()),
    BUTTON_CLOSE("BUTTON_CLOSE", BUTTON.getValue()),


    TAB_STATUS("TAB_STATUS", COMPONENT.getValue()),
    SLIDER("SLIDER", COMPONENT.getValue()),
    SLIDER_VALUE("SLIDER_VALUE", COMPONENT.getValue()),
    LOAD_BAR("LOAD_BAR", COMPONENT.getValue()),
    LOADING_ANIM("LOADING_ANIM", COMPONENT.getValue());

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

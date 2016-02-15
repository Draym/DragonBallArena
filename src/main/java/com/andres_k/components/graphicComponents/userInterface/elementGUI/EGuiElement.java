package com.andres_k.components.graphicComponents.userInterface.elementGUI;

import com.andres_k.components.gameComponents.gameObject.EGameObject;

/**
 * Created by andres_k on 05/02/2016.
 */
public enum EGuiElement {
    /* Admin */
    NULL("NULL"),
    BUTTON("BUTTON"),
    CONTAINER("CONTAINER"),
    COMPONENT("COMPONENT"),
    CARDS("CARDS"),
    AVATAR("AVATAR"),

    // container
    PANEL1("PANEL1", CONTAINER.getValue()),
    PANEL2("PANEL2", CONTAINER.getValue()),
    PANEL3("PANEL3", CONTAINER.getValue()),
    PANEL4("PANEL4", CONTAINER.getValue()),

    // button
    BUTTON_PLAY_SOLO("BUTTON_PLAY_SOLO", BUTTON.getValue()),
    BUTTON_PLAY_VERSUS("BUTTON_PLAY_VERSUS", BUTTON.getValue()),
    BUTTON_PLAY_MULTI("BUTTON_PLAY_MULTI", BUTTON.getValue()),
    BUTTON_SETTING("BUTTON_SETTING", BUTTON.getValue()),
    BUTTON_CONTROLS("BUTTON_CONTROLS", BUTTON.getValue()),
    BUTTON_SLIDER("BUTTON_SLIDER", BUTTON.getValue()),
    BUTTON_CLOSE("BUTTON_CLOSE", BUTTON.getValue()),

    // component
    TAB_STATUS("TAB_STATUS", COMPONENT.getValue()),
    SLIDER("SLIDER", COMPONENT.getValue()),
    SLIDER_VALUE("SLIDER_VALUE", COMPONENT.getValue()),
    LOAD_BAR("LOAD_BAR", COMPONENT.getValue()),
    LOADING_ANIM("LOADING_ANIM", COMPONENT.getValue()),

    CARDS_GOKU("CARDS_GOKU", CARDS.getValue()),
    CARDS_GOHAN("CARDS_GOHAN", CARDS.getValue()),
    CARDS_VEGETA("CARDS_VEGETA", CARDS.getValue()),
    CARDS_PICOLO("CARDS_PICOLO", CARDS.getValue()),
    CARDS_KAME_SENNIN("CARDS_KAME_SENNIN", CARDS.getValue()),
    CARDS_CELL("CARDS_CELL", CARDS.getValue()),
    CARDS_BUU("CARDS_BUU", CARDS.getValue()),
    CARDS_FRIEEZA("CARDS_FRIEEZA", CARDS.getValue()),

    // GUI_CARDS_AVATAR
    AVATAR_GOKU(EGameObject.GOKU.getValue(), AVATAR.getValue()),
    AVATAR_GOHAN(EGameObject.GOHAN.getValue(), AVATAR.getValue()),
    AVATAR_VEGETA(EGameObject.VEGETA.getValue(), AVATAR.getValue()),
    AVATAR_PICOLO(EGameObject.PICOLO.getValue(), AVATAR.getValue()),
    AVATAR_KAME_SENNIN(EGameObject.KAME_SENNIN.getValue(), AVATAR.getValue()),
    AVATAR_CELL(EGameObject.CELL.getValue(), AVATAR.getValue()),
    AVATAR_BUU(EGameObject.BUU.getValue(), AVATAR.getValue()),
    AVATAR_FRIEEZA(EGameObject.FRIEEZA.getValue(), AVATAR.getValue());

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

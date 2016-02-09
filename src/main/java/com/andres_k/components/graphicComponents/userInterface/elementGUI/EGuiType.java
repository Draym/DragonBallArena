package com.andres_k.components.graphicComponents.userInterface.elementGUI;

/**
 * Created by andres_k on 01/02/2016.
 */
public enum EGuiType {
    COMPLEX("COMPLEX"),
    MENU("MENU"),
    TABLE("TABLE"),
    CHAT("CHAT"),
    PRINTABLE("PRINTABLE"),
    POP_UP("POP_UP"),
    SELECTOR("SELECTOR"),
    LIST("LIST"),

    BUTTON("BUTTON"),
    TEXT("TEXT"),
    IMAGE("IMAGE"),
    BORDER("BORDER");

    private String value;

    EGuiType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

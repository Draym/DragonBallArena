package com.andres_k.components.graphicComponents.graphic;

/**
 * Created by andres_k on 17/03/2015.
 */
public enum EnumWindow {
    EXIT(-1),
    LOAD(0), HOME(1), GAME(2);

    private int value;

    EnumWindow(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}

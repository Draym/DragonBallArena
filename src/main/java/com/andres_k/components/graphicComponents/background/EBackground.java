package com.andres_k.components.graphicComponents.background;

/**
 * Created by andres_k on 20/01/2016.
 */
public enum EBackground {
    //background
    LOAD_SCREEN("LOAD_SCREEN"),
    HOME_SCREEN("HOME_SCREEN"),
    SELECT_SCREEN("SELECT_SCREEN"),
    BEFORE_BATTLE_SCREEN("BEFORE_BATTLE_SCREEN"),

    //map
    VALLEY_MAP("VALLEY_MAP");

    String value;

    EBackground(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}

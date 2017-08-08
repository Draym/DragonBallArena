package com.andres_k.custom.component.graphicComponents.background;

/**
 * Created by com.andres_k on 20/01/2016.
 */
public enum EBackground {
    //background
    LOAD_SCREEN("LOAD_SCREEN"),
    HOME_SCREEN("HOME_SCREEN"),
    SELECT_SCREEN("SELECT_SCREEN"),
    BATTLE_CONNECTION_SCREEN("BATTLE_CONNECTION_SCREEN"),

    //map
    VALLEY_MAP("VALLEY_MAP"),

    //logo
    LOGO("LOGO");

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

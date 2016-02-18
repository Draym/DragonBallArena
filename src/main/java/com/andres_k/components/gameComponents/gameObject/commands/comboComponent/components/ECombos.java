package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components;

/**
 * Created by andres_k on 30/11/2015.
 */
public enum ECombos {
    HANDATTACK("HANDATTACK"),
    MOVE_HANDATTACK_RIGHT("MOVE_HANDATTACK_RIGHT"),
    MOVE_HANDATTACK_LEFT("MOVE_HANDATTACK_LEFT"),
    HAND_FLY_PROPELS("HAND_FLY_PROPELS"),
    RUSH_RIGHT("RUSH_RIGHT"),
    RUSH_LEFT("RUSH_LEFT");


    private String value;

    ECombos(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

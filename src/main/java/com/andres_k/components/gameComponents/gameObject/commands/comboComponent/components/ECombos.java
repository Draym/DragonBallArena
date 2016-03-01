package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components;

/**
 * Created by andres_k on 30/11/2015.
 */
public enum ECombos {
    HAND_ATTACK("HAND_ATTACK"),
    MOVE_HAND_ATTACK_RIGHT("MOVE_HAND_ATTACK_RIGHT"),
    MOVE_HAND_ATTACK_LEFT("MOVE_HAND_ATTACK_LEFT"),
    RUSH_ATTACK("RUSH_ATTACK"),
    HAND_FLY_PROPELS("HAND_FLY_PROPELS"),
    RUSH_RIGHT("RUSH_RIGHT"),
    RUSH_LEFT("RUSH_LEFT"),
    KI_BASIC_ATTACK("KI_BASIC_ATTACK"),
    KI_SPE_ATTACK("KI_SPE_ATTACK");


    private String value;

    ECombos(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

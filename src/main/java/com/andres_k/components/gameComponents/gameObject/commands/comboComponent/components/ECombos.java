package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components;

/**
 * Created by andres_k on 30/11/2015.
 */
public enum ECombos {
    RUSH_ATTACK("RUSH_ATTACK"),
    HAND_ATTACK("HAND_ATTACK"),
    MOVE_HAND_ATTACK_RIGHT("MOVE_HAND_ATTACK_RIGHT"),
    MOVE_HAND_ATTACK_LEFT("MOVE_HAND_ATTACK_LEFT"),
    HAND_FLY_PROPELS("HAND_FLY_PROPELS"),

    DEFENSE_MODE("DEFENSE_MODE"),
    BLOCK("BLOCK"),
    JUMP_KICK_ATTACK("JUMP_KICK_ATTACK"),
    SPIRAL_KICK_ATTACK("SPIRAL_KICK_ATTACK"),
    KICK_PROPELS_ATTACK("KICK_PROPELS_ATTACK"),
    KICK_ATTACK("KICK_ATTACK"),

    RUSH_RIGHT("RUSH_RIGHT"),
    RUSH_LEFT("RUSH_LEFT"),

    KI_CHARGE("KI_CHARGE"),
    KI_EXPLODE("KI_EXPLODE"),
    KI_BASIC_ATTACK("KI_BASIC_ATTACK"),
    KI_SPE_ATTACK("KI_SPE_ATTACK"),
    KI_FINAL_ATTACK("KI_FINAL_ATTACK");


    private String value;

    ECombos(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components;

/**
 * Created by andres_k on 30/11/2015.
 */
public enum ECombos {
    // GOKU
    HANDATTACK("HANDATTACK"),
    MOVERIGHT_HANDATTACK("MOVERIGHT_HANDATTACK"),
    MOVELEFT_HANDATTACK("MOVELEFT_HANDATTACK"),
    HAND_FLY_PROPELS("HAND_FLY_PROPELS"),

    // VEGETA
    VEGETA_HANDATTACK("VEGETA_HANDATTACK");

    private String value;

    ECombos(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

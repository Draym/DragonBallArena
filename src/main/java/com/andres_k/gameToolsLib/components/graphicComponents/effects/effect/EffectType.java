package com.andres_k.gameToolsLib.components.graphicComponents.effects.effect;

/**
 * Created by com.andres_k on 25/01/2016.
 */
public enum EffectType {
    FLASH("FLASH"),
    SHAKE_SCREEN("SHAKE_SCREEN"),
    SHAKE_IMAGE("SHAKE_IMAGE"),
    ZOOM("ZOOM"),
    HIDE("HIDE"),
    ANIMATION("ANIMATION"),
    SOUND("SOUND"),
    CLEAR("CLEAR");

    public String value;

    EffectType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

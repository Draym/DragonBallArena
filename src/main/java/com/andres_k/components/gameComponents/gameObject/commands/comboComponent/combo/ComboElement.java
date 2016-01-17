package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.combo;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.gameComponents.animations.EnumAnimation;

/**
 * Created by andres_k on 07/12/2015.
 */
public class ComboElement {
    private EnumInput input;
    private EnumAnimation animType;
    private int animIndex;
    private EnumAnimation hitType;
    private int hitIndex;
    private boolean change;
    private int duration;


    public ComboElement(EnumInput input, EnumAnimation animType, int animIndex, boolean change, int duration) {
        this.input = input;
        this.animType = animType;
        this.animIndex = animIndex;
        this.hitType = EnumAnimation.NULL;
        this.hitIndex = 0;
        this.change = change;
        this.duration = duration;
    }

    public ComboElement(EnumInput input, EnumAnimation animType, int animIndex, EnumAnimation hitType, int hitIndex, boolean change, int duration) {
        this.input = input;
        this.animType = animType;
        this.animIndex = animIndex;
        this.hitType = hitType;
        this.hitIndex = hitIndex;
        this.change = change;
        this.duration = duration;
    }

    // GETTERS

    public int getHitIndex() {
        return this.hitIndex;
    }

    public EnumAnimation getHitType() {
        return this.hitType;
    }

    public EnumInput getInput() {
        return this.input;
    }

    public EnumAnimation getAnimType() {
        return this.animType;
    }

    public int getAnimIndex() {
        return this.animIndex;
    }

    public boolean haveToChange() {
        return this.change;
    }

    public int getDuration() {
        return this.duration;
    }
}

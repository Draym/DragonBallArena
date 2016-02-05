package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.combo;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.EAnimation;

/**
 * Created by andres_k on 07/12/2015.
 */
public class ComboElement {
    private EInput input;
    private EAnimation animType;
    private int animIndex;
    private EAnimation hitType;
    private int hitIndex;
    private boolean change;
    private int duration;


    public ComboElement(EInput input, EAnimation animType, int animIndex, boolean change, int duration) {
        this.input = input;
        this.animType = animType;
        this.animIndex = animIndex;
        this.hitType = EAnimation.NULL;
        this.hitIndex = 0;
        this.change = change;
        this.duration = duration;
    }

    public ComboElement(EInput input, EAnimation animType, int animIndex, EAnimation hitType, int hitIndex, boolean change, int duration) {
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

    public EAnimation getHitType() {
        return this.hitType;
    }

    public EInput getInput() {
        return this.input;
    }

    public EAnimation getAnimType() {
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

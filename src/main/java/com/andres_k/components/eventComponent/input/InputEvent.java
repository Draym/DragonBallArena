package com.andres_k.components.eventComponent.input;

/**
 * Created by andres_k on 13/02/2016.
 */
public class InputEvent {
    public int key;
    public char value;
    public EInput type;

    public InputEvent(int key, char value, EInput type) {
        this.key = key;
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return "(" + this.key + "=" + this.value + ", " + this.type + ")";
    }
}

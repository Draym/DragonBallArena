package com.andres_k.components.eventComponent.input;

/**
 * Created by andres_k on 13/02/2016.
 */
public class InputEvent {
    public int key;
    public char c;
    public EInput type;

    public InputEvent(int key, char c, EInput type) {
        this.key = key;
        this.c = c;
        this.type = type;
    }
}

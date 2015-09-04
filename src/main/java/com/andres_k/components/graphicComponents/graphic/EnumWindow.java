package com.andres_k.components.graphicComponents.graphic;

/**
 * Created by andres_k on 17/03/2015.
 */
public enum EnumWindow {
    EXIT(-1),
    INTERFACE(0), GAME(1);

    private int value;
     EnumWindow(int value) {
         this.value = value;
    }
    public int getValue(){
        return this.value;
    }
}

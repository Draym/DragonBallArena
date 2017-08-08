package com.andres_k.custom.component.graphicComponents.graphic;

/**
 * Created by com.andres_k on 17/03/2015.
 */
public enum EnumWindow {
    EXIT(-1),
    LOAD(0), HOME(1), GAME(2),
    SELECT_SOLO(3), SELECT_VERSUS(4), SELECT_MULTI(5),
    BATTLE_CONNECTION(6);


    private int id;

    EnumWindow(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static EnumWindow getById(int id) {
        EnumWindow[] windows = EnumWindow.values();

        for (EnumWindow window : windows) {
            if (window.getId() == id) {
                return window;
            }
        }
        return EXIT;
    }
}

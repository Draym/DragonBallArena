package com.andres_k.components.gameComponents.resources;

/**
 * Created by andres_k on 13/03/2015.
 */
public enum ESprites {
    //index
    NOTHING(0),
    ITEM(1),
    PLAYER(2),
    SCREEN_BACKGROUND(4),
    MAP_BACKGROUND(5),

    // GUI
    LOAD_GUI(6),
    HOME_GUI(7),
    GAME_GUI(8),
    SELECT_GUI(9),

    // LoadGui
    LOAD_BAR(LOAD_GUI.getIndex()),
    LOADING_EMPTY(LOAD_GUI.getIndex()),

    //gamePlayer
    GOKU(PLAYER.getIndex()),

    //gameItem
    WALL(ITEM.getIndex()),
    GROUND(ITEM.getIndex()),

    //background
    LOAD_SCREEN(SCREEN_BACKGROUND.getIndex()),
    HOME_SCREEN(SCREEN_BACKGROUND.getIndex()),
    LOGO(SCREEN_BACKGROUND.getIndex()),

    //map
    VALLEY_MAP(MAP_BACKGROUND.getIndex());

    private final int index;

    ESprites(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

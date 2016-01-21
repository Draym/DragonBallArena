package com.andres_k.components.gameComponents.resources;

/**
 * Created by andres_k on 13/03/2015.
 */
public enum EnumSprites {
    //index
    NOTHING(0),
    ITEM(1),
    PLAYER(2),
    ROUND(6),
    MENU_ITEMS(7),
    MENU_BACKGROUND(8),
    MAP_BACKGROUND(9),

    //roundOverlay
    NEW_GAME(ROUND.getIndex()), END_GAME(ROUND.getIndex()), TIMER(ROUND.getIndex()), NEW_ROUND(ROUND.getIndex()),
    //menuOverlay
    EXIT(MENU_ITEMS.getIndex()), SETTINGS(MENU_ITEMS.getIndex()), CONTROLS(MENU_ITEMS.getIndex()), SCREEN(MENU_ITEMS.getIndex()),
    NEW(MENU_ITEMS.getIndex()), GO(MENU_ITEMS.getIndex()), NEXT(MENU_ITEMS.getIndex()), SAVE(MENU_ITEMS.getIndex()), HIGHSCORE(MENU_ITEMS.getIndex()),
    TOPSCORE(MENU_ITEMS.getIndex()), ALPHABET(MENU_ITEMS.getIndex()),

    //gamePlayer
    GOKU(PLAYER.getIndex()),

    //gameItem
    WALL(ITEM.getIndex()),
    GROUND(ITEM.getIndex()),

    //background
    LOAD_SCREEN(MENU_BACKGROUND.getIndex()),
    HOME_SCREEN(MENU_BACKGROUND.getIndex()),


    //map
    VALLEY_MAP(MAP_BACKGROUND.getIndex());

    private final int index;

    EnumSprites(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

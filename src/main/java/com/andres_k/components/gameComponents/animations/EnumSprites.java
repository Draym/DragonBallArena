package com.andres_k.components.gameComponents.animations;


/**
 * Created by andres_k on 13/03/2015.
 */
public enum EnumSprites {
    //index
    NOTHING(0),
    ITEM(1),
    ROUND(6),
    MENU(7),

    //roundOverlay
    NEW_GAME(ROUND.getIndex()), END_GAME(ROUND.getIndex()), TIMER(ROUND.getIndex()), NEW_ROUND(ROUND.getIndex()),
    //menuOverlay
    EXIT(MENU.getIndex()), SETTINGS(MENU.getIndex()), CONTROLS(MENU.getIndex()), SCREEN(MENU.getIndex()),
    NEW(MENU.getIndex()), GO(MENU.getIndex()), NEXT(MENU.getIndex()), SAVE(MENU.getIndex()), HIGHSCORE(MENU.getIndex()),
    TOPSCORE(MENU.getIndex()), ALPHABET(MENU.getIndex());

    //game
    //SPACESHIP(ITEM.getIndex());

    private final int index;

    EnumSprites(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

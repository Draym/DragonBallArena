package com.andres_k.components.resourceComponent.resources;

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
    BUTTON(6),
    COMPONENT(7),
    CONTAINER(8),
    CARDS(9),
    AVATAR(10),

    // GUI_COMPONENT
    TAB_STATUS(COMPONENT.getIndex()),
    LOAD_BAR(COMPONENT.getIndex()),
    SLIDER(COMPONENT.getIndex()),
    SLIDER_VALUE(COMPONENT.getIndex()),
    LOADING_ANIM(COMPONENT.getIndex()),

    // GUI_CONTAINER
    PANEL1(CONTAINER.getIndex()),
    PANEL2(CONTAINER.getIndex()),
    PANEL3(CONTAINER.getIndex()),
    PANEL4(CONTAINER.getIndex()),

    // GUI_BUTTON
    BUTTON_PLAY_SOLO(BUTTON.getIndex()),
    BUTTON_PLAY_VERSUS(BUTTON.getIndex()),
    BUTTON_PLAY_MULTI(BUTTON.getIndex()),
    BUTTON_SETTING(BUTTON.getIndex()),
    BUTTON_CONTROLS(BUTTON.getIndex()),
    BUTTON_SLIDER(BUTTON.getIndex()),
    BUTTON_CLOSE(BUTTON.getIndex()),
    BUTTON_EXIT(BUTTON.getIndex()),
    BUTTON_RESUME(BUTTON.getIndex()),
    BUTTON_COMBO(BUTTON.getIndex()),

    // GUI_CARDS
    CARDS_GOKU(CARDS.getIndex()),
    CARDS_GOHAN(CARDS.getIndex()),
    CARDS_VEGETA(CARDS.getIndex()),
    CARDS_PICOLO(CARDS.getIndex()),
    CARDS_KAME_SENNIN(CARDS.getIndex()),
    CARDS_CELL(CARDS.getIndex()),
    CARDS_BUU(CARDS.getIndex()),
    CARDS_FRIEEZA(CARDS.getIndex()),

    // GUI_CARDS_AVATAR
    AVATAR_GOKU(AVATAR.getIndex()),
    AVATAR_GOHAN(AVATAR.getIndex()),
    AVATAR_VEGETA(AVATAR.getIndex()),
    AVATAR_PICOLO(AVATAR.getIndex()),
    AVATAR_KAME_SENNIN(AVATAR.getIndex()),
    AVATAR_CELL(AVATAR.getIndex()),
    AVATAR_BUU(AVATAR.getIndex()),
    AVATAR_FRIEEZA(AVATAR.getIndex()),

    //gamePlayer
    GOKU(PLAYER.getIndex()),

    //gameItem
    WALL(ITEM.getIndex()),
    GROUND(ITEM.getIndex()),

    //background
    LOAD_SCREEN(SCREEN_BACKGROUND.getIndex()),
    HOME_SCREEN(SCREEN_BACKGROUND.getIndex()),
    SELECT_SCREEN(SCREEN_BACKGROUND.getIndex()),
    BATTLE_SCREEN(SCREEN_BACKGROUND.getIndex()),
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

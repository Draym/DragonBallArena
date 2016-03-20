package com.andres_k.components.resourceComponent.resources;

/**
 * Created by andres_k on 13/03/2015.
 */
public enum ESprites {
    //index
    NOTHING(0),
    ITEM(1),
    PLAYER(2),
    ENTITY(3),
    SCREEN_BACKGROUND(4),
    MAP_BACKGROUND(5),

    // GUI
    BUTTON(6),
    COMPONENT(7),
    CONTAINER(8),
    CARDS(9),
    AVATAR(10),
    ICON(11),

    // GUI_COMPONENT
    TAB_STATUS(COMPONENT.getIndex()),
    LOAD_BAR(COMPONENT.getIndex()),
    SLIDER(COMPONENT.getIndex()),
    SLIDER_VALUE(COMPONENT.getIndex()),
    LOADING_ANIM(COMPONENT.getIndex()),
    DISABLED(COMPONENT.getIndex()),
    HEALTH_BAR(COMPONENT.getIndex()),
    KI_BAR(COMPONENT.getIndex()),
    ENERGY_BAR(COMPONENT.getIndex()),
    STATE_PLAYER(COMPONENT.getIndex()),

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
    BUTTON_LOCK(BUTTON.getIndex()),

    // GUI_PLAYER_CARDS
    CARDS_ALL(CARDS.getIndex()),

    // GUI_PLAYER_AVATAR
    AVATAR_GOKU(AVATAR.getIndex()),
    AVATAR_GOHAN(AVATAR.getIndex()),
    AVATAR_VEGETA(AVATAR.getIndex()),
    AVATAR_PICOLO(AVATAR.getIndex()),
    AVATAR_KAME_SENNIN(AVATAR.getIndex()),
    AVATAR_CELL(AVATAR.getIndex()),
    AVATAR_BUU(AVATAR.getIndex()),
    AVATAR_FRIEEZA(AVATAR.getIndex()),
    AVATAR_BORDER(AVATAR.getIndex()),

    // GUI_PLAYER_ICON
    ICON_GOKU(ICON.getIndex()),
    ICON_GOHAN(ICON.getIndex()),
    ICON_VEGETA(ICON.getIndex()),
    ICON_PICOLO(ICON.getIndex()),
    ICON_KAME_SENNIN(ICON.getIndex()),
    ICON_CELL(ICON.getIndex()),
    ICON_BUU(ICON.getIndex()),
    ICON_FRIEEZA(ICON.getIndex()),

    //gamePlayer
    GOKU(PLAYER.getIndex()),
    VEGETA(PLAYER.getIndex()),
    GOKU_S1(PLAYER.getIndex()),
    VEGETA_S1(PLAYER.getIndex()),

    //gameItem
    WALL(ITEM.getIndex()),
    GROUND(ITEM.getIndex()),

    //gameEntity
    KI_BURST(ENTITY.getIndex()),
    VEGETA_KI_BLAST_BACK(ENTITY.getIndex()),
    VEGETA_KI_BLAST_HEAD(ENTITY.getIndex()),
    KI_EXPLODE(ENTITY.getIndex()),
    KI_BLAST(ENTITY.getIndex()),
    KAMEHA(ENTITY.getIndex()),
    KAMEHA_Back(ENTITY.getIndex()),
    KAMEHA_Body(ENTITY.getIndex()),
    FINAL_FLASH(ENTITY.getIndex()),
    FINAL_FLASH_Back(ENTITY.getIndex()),
    FINAL_FLASH_Body(ENTITY.getIndex()),
    GENKIDAMA(ENTITY.getIndex()),

    //background
    LOAD_SCREEN(SCREEN_BACKGROUND.getIndex()),
    HOME_SCREEN(SCREEN_BACKGROUND.getIndex()),
    SELECT_SCREEN(SCREEN_BACKGROUND.getIndex()),
    BATTLE_CONNECTION_SCREEN(SCREEN_BACKGROUND.getIndex()),
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

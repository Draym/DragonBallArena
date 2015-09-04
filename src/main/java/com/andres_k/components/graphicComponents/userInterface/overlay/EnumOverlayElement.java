package com.andres_k.components.graphicComponents.userInterface.overlay;

import com.andres_k.components.gameComponents.gameObject.EnumGameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 23/06/2015.
 */
public enum EnumOverlayElement {
    // index
    NOTHING("nothing", -1),
    CHAT("chat", 1),
    POP_ELEMENT("popElement", 2),
    TABLE("table", 3),
    GENERIC("generic", 4),

    // index

    TABLE_ROUND(TABLE.value + "Round", TABLE.index),
    TABLE_ROUND_NEW(TABLE_ROUND.value + "New", TABLE_ROUND.index),
    TABLE_ROUND_END(TABLE_ROUND.value + "END", TABLE_ROUND.index),

    TABLE_STAT(TABLE.value + "Stat", TABLE.index),
    TABLE_LIST(TABLE.value + "List", TABLE.index),
    TABLE_ICON(TABLE.value + "Icon", TABLE.index),

    GENERIC_USER_STAT(GENERIC.value + "UserStat", GENERIC.index),

    TABLE_MENU(TABLE.value + "Menu", TABLE.index),
    TABLE_MENU_HOME(TABLE.value + " Home", TABLE.index),
    TABLE_MENU_NEWGAME(TABLE.value + " NewGame", TABLE.index),
    TABLE_MENU_NEWGAME_LAUNCH(TABLE_MENU_NEWGAME.value + "Launch", TABLE.index),
    TABLE_MENU_SETTINGS(TABLE_MENU.value + "Settings", TABLE_MENU.index),
    TABLE_MENU_CONTROLS(TABLE_MENU.value + "Controls", TABLE_MENU.index),
    TABLE_MENU_SCREEN(TABLE_MENU.value + "Screen", TABLE_MENU.index),


    // primitive
    IMAGE("image"),
    STRING("string"),
    BORDER("border"),
    SELECT_FIELD("selectField"),
    BACKGROUND("background"),
    ALPHABET("alphabet"),

    // button
    BUTTON("button"),
    GO("go"),
    NEXT("next"),
    SAVE("save"),
    SCORE("score"),
    HIGHSCORE("highScore"),

    //animator
    TOPSCORE("topScore"),

    // roundAnimator

    NEW_GAME("newGame"), END_GAME("endGame"), TIMER("stat"), NEW_ROUND("newRound"),

    // menuAnimator
    EXIT("exit"), SETTINGS("settings"), CONTROLS("controls"), SCREEN("screen"), NEW("new"),

    // item
    SOUNDS_VALUE("soundsValue"), MUSICS_VALUE("musicsValue"),
    SOUNDS_GRAPH("soundsVolume"), MUSICS_GRAPH("musicsVolume");

    private int index;
    private String value;

    EnumOverlayElement(String value) {
        this.index = 0;
        this.value = value;
    }

    EnumOverlayElement(String value, int index) {
        this.value = value;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public EnumOverlayElement getEnumByIndex() {
        EnumOverlayElement[] enums = EnumOverlayElement.values();
        for (int i = 0; i < enums.length; ++i) {
            EnumOverlayElement item = enums[i];
            if (item.getIndex() == this.index) {
                return item;
            }
        }
        return NOTHING;
    }

    public static EnumOverlayElement getEnumByValue(String value) {
        EnumOverlayElement[] enums = EnumOverlayElement.values();
        for (int i = 0; i < enums.length; ++i) {
            EnumOverlayElement item = enums[i];
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return NOTHING;
    }

    public List<EnumOverlayElement> getSameIndexList() {
        List<EnumOverlayElement> result = new ArrayList<>();

        EnumOverlayElement[] enums = EnumOverlayElement.values();
        for (int i = 0; i < enums.length; ++i) {
            EnumOverlayElement item = enums[i];
            if (item.getIndex() == this.index && item != this) {
                result.add(item);
            }
        }
        return result;
    }

    public static EnumOverlayElement getOverlayElementByGameObject(EnumGameObject gameObject) {
        return NOTHING;
    }

    public static List<EnumOverlayElement> getChildren(EnumOverlayElement target){
        List<EnumOverlayElement> targets = new ArrayList<>();

        EnumOverlayElement[] enums = EnumOverlayElement.values();
        for (int i = 0; i < enums.length; ++i) {
            EnumOverlayElement item = enums[i];
            if (item.getValue().contains(target.getValue())) {
                targets.add(item);
            }
        }
        return targets;
    }

    public String getValue(){
        return this.value;
    }
}

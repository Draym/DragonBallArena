package com.andres_k.components.graphicComponents.input;

/**
 * Created by andres_k on 16/03/2015.
 */
public enum EnumInput {
    // TYPE
    NOTHING("NOTHING"),
    RELEASED("RELEASED"), PRESSED("PRESSED"),
    KEY_RELEASED("KEY"+RELEASED.value), KEY_PRESSED("KEY" + PRESSED.value),
    MOUSE_RELEASED("MOUSE"+RELEASED.value), MOUSE_PRESSED("MOUSE" + PRESSED.value),


    // MENU
    OVERLAY_1("OVERLAY_1"), OVERLAY_2("OVERLAY_2"),

    // MOVES
    MOVE_UP("MOVE_UP"), MOVE_DOWN("MOVE_DOWN"),
    MOVE_LEFT("MOVE_LEFT"), MOVE_RIGHT("MOVE_RIGHT"),

    // ACTIONS
    RUSH("RUSH"),
    ATTACK_A("ATTACK_A"),
    ATTACK_B("ATTACK_B"),
    ATTACK_C("ATTACK_C"),
    ATTACK_D("ATTACK_D"),

    // PLAYERS
    MOVE_UP_P1(0, MOVE_UP.value + "_P1"), MOVE_DOWN_P1(0, MOVE_DOWN.value + "_P1"),
    MOVE_LEFT_P1(0, MOVE_LEFT.value + "_P1"), MOVE_RIGHT_P1(0, MOVE_RIGHT.value + "_P1"),
    MOVE_UP_P2(1, MOVE_UP.value + "_P2"), MOVE_DOWN_P2(1, MOVE_DOWN.value + "_P2"),
    MOVE_LEFT_P2(1, MOVE_LEFT.value + "_P2"), MOVE_RIGHT_P2(1, MOVE_RIGHT.value + "_P2");


    private final int index;
    private final String value;

    EnumInput(String value) {
        this.index = -1;
        this.value = value;
    }

    EnumInput(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public int getIndex() {
        return this.index;
    }

    public static EnumInput getEnumByIndex(int index) {
        EnumInput[] enums = EnumInput.values();

        for (EnumInput type : enums) {
            if (index == type.getIndex()) {
                return type;
            }
        }
        return NOTHING;
    }

    public static EnumInput getEnumByValue(String value) {
        EnumInput[] enums = EnumInput.values();

        for (EnumInput type : enums) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return NOTHING;
    }

    public static int getIndexByValue(String value) {
        EnumInput[] enums = EnumInput.values();

        for (EnumInput type : enums) {
            if (type.getValue().equals(value)) {
                return type.getIndex();
            }
        }
        return NOTHING.getIndex();
    }

    public boolean isIn(EnumInput input){
        return this.getValue().contains(input.getValue());
    }

    public EnumInput returnContainer() {
        EnumInput[] enums = EnumInput.values();

        for (EnumInput type : enums) {
            if (this.isIn(type)) {
                return type;
            }
        }
        return NOTHING;
    }
}

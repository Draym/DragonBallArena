package com.andres_k.components.eventComponent.input;

/**
 * Created by andres_k on 16/03/2015.
 */
public enum EInput {
    // TYPE
    NOTHING("NOTHING"),
    INFINITE("INFINITE"),
    RELEASED("RELEASED"), PRESSED("PRESSED"),
    KEY_RELEASED("KEY"+RELEASED.value), KEY_PRESSED("KEY" + PRESSED.value),
    MOUSE_RELEASED("MOUSE"+RELEASED.value), MOUSE_PRESSED("MOUSE" + PRESSED.value),


    // MENU
    OVERLAY_1("OVERLAY_1"), OVERLAY_2("OVERLAY_2"),

    // MOVES
    MOVE_UP("MOVE_UP"), MOVE_DOWN("MOVE_DOWN"),
    MOVE_LEFT("MOVE_LEFT"), MOVE_RIGHT("MOVE_RIGHT"),

    // ACTIONS
    ATTACK_A("ATTACK_A"),
    ATTACK_B("ATTACK_B"),
    ATTACK_C("ATTACK_C"),
    ATTACK_D("ATTACK_D"),
    ATTACK_SPE("ATTACK_SPE"),

    // PLAYERS
    MOVE_UP_P1(0, MOVE_UP.value + "_P1"), MOVE_DOWN_P1(0, MOVE_DOWN.value + "_P1"),
    MOVE_LEFT_P1(0, MOVE_LEFT.value + "_P1"), MOVE_RIGHT_P1(0, MOVE_RIGHT.value + "_P1"),
    ATTACK_A_P1(0, ATTACK_A.value + "_P1"), ATTACK_B_P1(0, ATTACK_B.value + "_P1"),
    ATTACK_C_P1(0, ATTACK_C.value + "_P1"), ATTACK_D_P1(0, ATTACK_D.value + "_P1"),
    ATTACK_SPE_P1(0, ATTACK_SPE.value + "_P1"),
    MOVE_UP_P2(1, MOVE_UP.value + "_P2"), MOVE_DOWN_P2(1, MOVE_DOWN.value + "_P2"),
    MOVE_LEFT_P2(1, MOVE_LEFT.value + "_P2"), MOVE_RIGHT_P2(1, MOVE_RIGHT.value + "_P2"),
    ATTACK_A_P2(1, ATTACK_A.value + "_P2"), ATTACK_B_P2(1, ATTACK_B.value + "_P2"),
    ATTACK_C_P2(1, ATTACK_C.value + "_P2"), ATTACK_D_P2(1, ATTACK_D.value + "_P2"),
    ATTACK_SPE_P2(1, ATTACK_SPE.value + "_P2");


    private final int index;
    private final String value;

    EInput(String value) {
        this.index = -1;
        this.value = value;
    }

    EInput(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public int getIndex() {
        return this.index;
    }

    public static EInput getEnumByIndex(int index) {
        EInput[] enums = EInput.values();

        for (EInput type : enums) {
            if (index == type.getIndex()) {
                return type;
            }
        }
        return NOTHING;
    }

    public static EInput getEnumByValue(String value) {
        EInput[] enums = EInput.values();

        for (EInput type : enums) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return NOTHING;
    }

    public static int getIndexByValue(String value) {
        EInput[] enums = EInput.values();

        for (EInput type : enums) {
            if (type.getValue().equals(value)) {
                return type.getIndex();
            }
        }
        return NOTHING.getIndex();
    }

    public boolean isIn(EInput input){
        return this.getValue().contains(input.getValue());
    }

    public EInput getContainer() {
        EInput[] enums = EInput.values();

        for (EInput type : enums) {
            if (this.isIn(type)) {
                return type;
            }
        }
        return NOTHING;
    }
}

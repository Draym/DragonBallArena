package com.andres_k.components.gameComponents.gameObject;

/**
 * Created by andres_k on 13/03/2015.
 */
public enum EnumGameObject {
    /* Admin */
    NULL("NULL"),

    // primary
    UNBREAKABLE("UNBREAKABLE"),
    SOLID("SOLID"),
    FILMY("FILMY"),

    //collisions
    ATTACK_BODY("attackBody", "FILMY"),
    DEFENSE_BODY("defenseBody", "SOLID"),
    BLOCK_BODY("blockBody", "UNBREAKABLE"),

    //types
    ANIMATED("ANIMATED", "SOLID"),
    DEADPAN("DEADPAN", "UNBREAKABLE"),
    PLATFORM("PLATFORM", "DEADPAN"),
    BORDER("BORDER", "DEADPAN"),
    PLAYER("PLAYER", "ANIMATED"),
    MONSTER("MONSTER", "ANIMATED"),

    //items
    GROUND("GROUND", "PLATFORM"),
    WALL("WALL", "BORDER"),
    GOKU("GOKU", "PLAYER");


    private final String value;
    private final String type;

    EnumGameObject(String value) {
        this.value = value;
        this.type = value;
    }

    EnumGameObject(String value, String type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return this.type;
    }

    public static EnumGameObject getEnumByValue(String value) {
        EnumGameObject[] enums = EnumGameObject.values();
        int valuesNumber = enums.length;
        for (int i = 0; i < valuesNumber; i++) {
            EnumGameObject type = enums[i];
            if (type.getValue().equals(value))
                return type;
        }
        return NULL;
    }

    public static EnumGameObject getEnumByType(String value) {
        EnumGameObject[] enums = EnumGameObject.values();
        int enumsNumber = enums.length;
        for (int i = 0; i < enumsNumber; i++) {
            EnumGameObject type = enums[i];
            if (value.equals(type.getValue())) {
                return type;
            }
        }
        return NULL;
    }

    public boolean isIn(EnumGameObject dir) {
        EnumGameObject current = EnumGameObject.getEnumByValue(this.value);

        while (!current.getValue().equals(current.getType())) {
            if (current == dir) {
                return true;
            } else {
                current = EnumGameObject.getEnumByType(current.getType());
            }
        }
        if (current == dir) {
            return true;
        }
        return false;
    }
}

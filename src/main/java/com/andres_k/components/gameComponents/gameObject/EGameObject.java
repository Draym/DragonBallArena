package com.andres_k.components.gameComponents.gameObject;

/**
 * Created by andres_k on 13/03/2015.
 */
public enum EGameObject {
    /* Admin */
    NULL("NULL"),

    // primary
    UNBREAKABLE("UNBREAKABLE"),
    SOLID("SOLID"),
    FILMY("FILMY"),

    //collisions
    ATTACK_BODY("attackBody", FILMY.getValue()),
    DEFENSE_BODY("defenseBody", SOLID.getValue()),
    BLOCK_BODY("blockBody", UNBREAKABLE.getValue()),

    //types
    ANIMATED("ANIMATED", SOLID.getValue()),
    DEADPAN("DEADPAN", UNBREAKABLE.getValue()),
    PLATFORM("PLATFORM", DEADPAN.getValue()),
    BORDER("BORDER", DEADPAN.getValue()),
    PLAYER("PLAYER", ANIMATED.getValue()),
    ENTITY("ENTITY", ANIMATED.getValue()),

    //items
    GROUND("Ground", PLATFORM.getValue()),
    WALL("Wall", BORDER.getValue()),

    //entities
    KAMEHA("KAMEHA", ENTITY.getValue()),

    //players
    GOKU("Goku", PLAYER.getValue()),
    GOHAN("Gohan", PLAYER.getValue(), false),
    VEGETA("Vegeta", PLAYER.getValue(), false),
    PICOLO("Picolo", PLAYER.getValue(), false),
    KAME_SENNIN("KameSennin", PLAYER.getValue(), false),
    CELL("Cell", PLAYER.getValue(), false),
    BUU("Buu", PLAYER.getValue(), false),
    FRIEEZA("Frieeza", PLAYER.getValue(), false);


    private final String value;
    private final String type;
    private final boolean available;

    EGameObject(String value) {
        this.value = value;
        this.type = value;
        this.available = true;
    }

    EGameObject(String value, String type) {
        this.value = value;
        this.type = type;
        this.available = true;
    }

    EGameObject(String value, String type, boolean available) {
        this.value = value;
        this.type = type;
        this.available = available;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return this.type;
    }

    public static EGameObject getEnumByValue(String value) {
        EGameObject[] enums = EGameObject.values();
        for (EGameObject type : enums) {
            if (type.getValue().equals(value))
                return type;
        }
        return NULL;
    }

    public static EGameObject getEnumByType(String value) {
        EGameObject[] enums = EGameObject.values();
        for (EGameObject type : enums) {
            if (value.equals(type.getValue())) {
                return type;
            }
        }
        return NULL;
    }

    public boolean isIn(EGameObject dir) {
        EGameObject current = EGameObject.getEnumByValue(this.value);

        while (!current.getValue().equals(current.getType())) {
            if (current == dir) {
                return true;
            } else {
                current = EGameObject.getEnumByType(current.getType());
            }
        }
        return (current == dir);
    }

    public boolean isAvailable() {
        return this.available;
    }

    @Override
    public String toString() {
        return this.value;
    }
}

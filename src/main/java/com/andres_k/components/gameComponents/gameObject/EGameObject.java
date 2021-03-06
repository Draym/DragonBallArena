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
    MAP("Map", UNBREAKABLE.getValue()),
    GROUND("Ground", PLATFORM.getValue()),
    WALL("Wall", BORDER.getValue()),

    //entities
    KI_EXPLODE("KI_EXPLODE", ENTITY.getValue()),
    KI_BLAST("KI_BLAST", ENTITY.getValue()),
    KI_BURST("KI_BURST", ENTITY.getValue()),
    KI_FINAL("KI_FINAL", ENTITY.getValue()),
    VEGETA_KI_BLAST_BACK("VEGETA_KI_BLAST_BACK", KI_BLAST.getValue()),
    VEGETA_KI_BLAST_HEAD("VEGETA_KI_BLAST_HEAD", KI_BLAST.getValue()),
    MEGA_KAMEHA("MEGA_KAMEHA", ENTITY.getValue()),
    MEGA_KAMEHA_Back("MEGA_KAMEHA_Back", ENTITY.getValue()),
    MEGA_KAMEHA_Body("MEGA_KAMEHA_Body", ENTITY.getValue()),
    KAMEHA("KAMEHA", ENTITY.getValue()),
    KAMEHA_Back("KAMEHA_Back", ENTITY.getValue()),
    KAMEHA_Body("KAMEHA_Body", ENTITY.getValue()),
    FINAL_FLASH("FINAL_FLASH", ENTITY.getValue()),
    FINAL_FLASH_Back("FINAL_FLASH_Back", ENTITY.getValue()),
    FINAL_FLASH_Body("FINAL_FLASH_Body", ENTITY.getValue()),
    GENKIDAMA("GENKIDAMA", ENTITY.getValue()),
    BIG_BANG("BIG_BANG", ENTITY.getValue()),

    //players
    GOKU("Goku", PLAYER.getValue()),
    GOHAN("Gohan", PLAYER.getValue(), false),
    VEGETA("Vegeta", PLAYER.getValue()),
    PICOLO("Picolo", PLAYER.getValue(), false),
    KAME_SENNIN("KameSennin", PLAYER.getValue(), false),
    CELL("Cell", PLAYER.getValue(), false),
    BUU("Buu", PLAYER.getValue(), false),
    FRIEEZA("Frieeza", PLAYER.getValue(), false),

    // transform
    GOKU_S1("Goku_S1", PLAYER.getValue()),
    VEGETA_S1("Vegeta_S1", PLAYER.getValue());


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

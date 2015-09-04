package com.andres_k.components.gameComponents.gameObject;

/**
 * Created by andres_k on 13/03/2015.
 */
public enum EnumGameObject {
    /* Admin */
    NULL("null"),
    UNBREAKABLE("unbreakable"),

    //collisions
    ATTACK_BODY("attackBody"),
    DEFENSE_BODY("defenseBody"),
    BLOCK_BODY("blockBody"),

    //item
    PLAYER("player");


    private final String value;

    EnumGameObject(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static EnumGameObject getEnumByValue(String value) {
        EnumGameObject[] tanks = EnumGameObject.values();
        int valuesNumber = tanks.length;
        for (int i = 0; i < valuesNumber; i++) {
            EnumGameObject type = tanks[i];
            if (type.getValue().equals(value))
                return type;
        }
        return NULL;
    }

    public static EnumGameObject getEnemyEnum(EnumGameObject type){
        EnumGameObject[] tanks = EnumGameObject.values();
        int valuesNumber = tanks.length;
        for (int i = 0; i < valuesNumber; i++) {
            EnumGameObject enemy = tanks[i];
            if (enemy.getValue().equals(type.getValue() + "Enemy"))
                return enemy;
        }
        return type;
    }
}

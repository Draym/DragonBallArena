package com.andres_k.components.gameComponents.animations;


/**
 * Created by andres_k on 13/03/2015.
 */
public enum EnumAnimation {
    IDLE(true),
    EXPLODE(false),
    RUSH(true),
    RUN(true),
    JUMP(false),
    DEFENSE(false),
    BLOCK(false),
    FALL(true),
    RECEIPT(false),

    // attacks
    HAND_ATTACK(false);

    private boolean loop;

    EnumAnimation(boolean loop) {
        this.loop = loop;
    }

    public boolean isLoop() {
        return this.loop;
    }

    public static boolean checkLoop(EnumAnimation value) {
        EnumAnimation[] enums = EnumAnimation.values();
        int enumsNumber = enums.length;
        for (int i = 0; i < enumsNumber; i++) {
            EnumAnimation type = enums[i];
            if (type.equals(value)) {
                return type.isLoop();
            }
        }
        return false;
    }
}

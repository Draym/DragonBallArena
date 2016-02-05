package com.andres_k.components.gameComponents.animations;


/**
 * Created by andres_k on 13/03/2015.
 */
public enum EAnimation {
    // STATE
    NULL(true),
    IDLE(true),
    EXPLODE(false),

    // MOVE
    RUSH(true),
    RUN(true),
    JUMP(false),
    FALL(true),
    RECEIPT(false),

    // DEFENSE
    DEFENSE(false),
    BLOCK(false),

    // TOUCHED
    TOUCHED_SIMPLE(false),
    TOUCHED_MEDIUM(false),
    TOUCHED_PROPELS(false),
    TOUCHED_FLIP(false),
    TOUCHED_FALL(true),
    TOUCHED_RECEIPT(false),

    // ATTACK
    HAND_ATTACK(false),
    HAND_FLY_PROPELS(false),

    // SPE ATTACK

    // ULTIMATE ATTACK

    // FINAL
    WIN(false),
    LOSE(false);

    private boolean loop;

    EAnimation(boolean loop) {
        this.loop = loop;
    }

    public boolean isLoop() {
        return this.loop;
    }

    public static boolean checkLoop(EAnimation value) {
        EAnimation[] enums = EAnimation.values();
        for (EAnimation type : enums) {
            if (type.equals(value)) {
                return type.isLoop();
            }
        }
        return false;
    }
}

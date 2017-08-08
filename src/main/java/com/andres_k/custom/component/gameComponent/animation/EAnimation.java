package com.andres_k.custom.component.gameComponent.animation;


/**
 * Created by com.andres_k on 13/03/2015.
 */
public enum EAnimation {
    // STATE
    NULL(true),
    IDLE(true),
    EXPLODE(false),

    //BUTTON
    ON_FOCUS(true),
    ON_CLICK(true),

    // MOVE
    RUSH(false),
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
    TOUCHED_PROJECTED(false),
    TOUCHED_FLIP(false),
    TOUCHED_RECEIPT(false),
    TOUCHED_FALL(false),

    // ATTACK
    JUMP_HAND_ATTACK(false),
    MOVE_HAND_ATTACK(false),
    RUSH_ATTACK(false),
    HAND_ATTACK(false),
    HAND_FLY_PROPELS(false),
    KNEES_ATTACK(false),
    JUMP_KICK(false),
    SPIRAL_KICK(false),
    KICK_ATTACK(false),
    KICK_PROPELS(false),
    TRANSPOSITION(false),
    KI_CHARGE(false),
    KI_BASIC_ATTACK(false),
    KI_SPE_ATTACK(false),
    KI_SIMPLE_PROPELS(false),
    KI_EXPLODE(false),
    KI_FINAL_ATTACK(false),

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

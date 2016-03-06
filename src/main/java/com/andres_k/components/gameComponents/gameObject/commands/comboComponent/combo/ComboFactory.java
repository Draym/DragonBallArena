package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.combo;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components.ECombos;

/**
 * Created by andres_k on 30/11/2015.
 */
public class ComboFactory {
    public static Combo createCombo(EGameObject object, ECombos type) {
        if (object == EGameObject.GOKU) {
            return ComboFactory.createGokuCombo(type);
        }
        return null;
    }

    // GENERIC
    private static void addInfiniteElement(Combo combo, EInput input, EAnimation anim, int index, boolean change, int duration) {
        combo.addElement(new ComboElement(input, anim, index, change, duration));
        combo.addElement(new ComboElement(EInput.INFINITE, anim, index, change, duration));
    }

    private static void addInfiniteElement(Combo combo, EInput input, EAnimation animType, int animIndex, EAnimation hitType, int hitIndex, boolean change, int duration) {
        combo.addElement(new ComboElement(input, animType, animIndex, hitType, hitIndex, change, duration));
        combo.addElement(new ComboElement(EInput.INFINITE, animType, animIndex, hitType, hitIndex, change, duration));
    }

    private static void addElement(Combo combo, EInput input, EAnimation anim, int index, boolean change, int duration, int numbers) {
        for (int i = 0; i < numbers; ++i) {
            combo.addElement(new ComboElement(input, anim, index, change, duration));
        }
    }

    private static void addElement(Combo combo, EInput input, EAnimation animType, int animIndex, EAnimation hitType, int hitIndex, boolean change, int duration, int number) {
        for (int i = 0; i < number; ++i) {
            combo.addElement(new ComboElement(input, animType, animIndex, hitType, hitIndex, change, duration));
        }
    }

    // GOKU
    public static Combo createGokuCombo(ECombos type) {
        Combo combo = new Combo();

        if (type == ECombos.HAND_ATTACK) {
            addElement(combo, EInput.ATTACK_A, EAnimation.HAND_ATTACK, 0, EAnimation.TOUCHED_SIMPLE, 0, true, 250, 1);
            addElement(combo, EInput.ATTACK_A, EAnimation.HAND_ATTACK, 1, true, 250, 3);
        } else if (type == ECombos.MOVE_HAND_ATTACK_RIGHT) {
            addElement(combo, EInput.MOVE_RIGHT, EAnimation.NULL, 0, false, -1, 1);
            addElement(combo, EInput.ATTACK_A, EAnimation.HAND_ATTACK, 1, EAnimation.TOUCHED_SIMPLE, 0, true, 250, 2);
        } else if (type == ECombos.MOVE_HAND_ATTACK_LEFT) {
            addElement(combo, EInput.MOVE_LEFT, EAnimation.NULL, 0, false, -1, 1);
            addElement(combo, EInput.ATTACK_A, EAnimation.HAND_ATTACK, 1, EAnimation.TOUCHED_SIMPLE, 0, true, 250, 2);
        } else if (type == ECombos.HAND_FLY_PROPELS) {
            addElement(combo, EInput.ATTACK_B, EAnimation.HAND_FLY_PROPELS, 0, EAnimation.TOUCHED_MEDIUM, 0, true, 250, 1);
        } else if (type == ECombos.RUSH_RIGHT) {
            addInfiniteElement(combo, EInput.MOVE_RIGHT, EAnimation.NULL, 0, false, 100);
            addElement(combo, EInput.ATTACK_SPE, EAnimation.NULL, 0, false, 100, 1);
            addInfiniteElement(combo, EInput.MOVE_RIGHT, EAnimation.RUSH, 0, true, 100);
            addElement(combo, EInput.ATTACK_A, EAnimation.RUSH_ATTACK, 1, true, 100, 1);
        } else if (type == ECombos.RUSH_LEFT) {
            addInfiniteElement(combo, EInput.MOVE_LEFT, EAnimation.NULL, 0, false, 100);
            addElement(combo, EInput.ATTACK_SPE, EAnimation.NULL, 0, false, 100, 1);
            addInfiniteElement(combo, EInput.MOVE_LEFT, EAnimation.RUSH, 0, true, 100);
            addElement(combo, EInput.ATTACK_A, EAnimation.RUSH_ATTACK, 1, true, 100, 1);
        } else if (type == ECombos.KI_SPE_ATTACK) {
            addElement(combo, EInput.ATTACK_SPE, EAnimation.NULL, 0, false, 150, 1);
            addElement(combo, EInput.ATTACK_D, EAnimation.KI_SPE_ATTACK, 0, true, 100, 1);
        } else if (type == ECombos.RUSH_ATTACK) {
            addElement(combo, EInput.ATTACK_SPE, EAnimation.NULL, 0, false, 250, 1);
            addElement(combo, EInput.ATTACK_A, EAnimation.RUSH_ATTACK, 0, true, 100, 1);
        } else if (type == ECombos.KI_BASIC_ATTACK) {
            addElement(combo, EInput.ATTACK_D, EAnimation.KI_BASIC_ATTACK, 0, true, 250, 1);
            addElement(combo, EInput.ATTACK_D, EAnimation.KI_BASIC_ATTACK, 1, true, 250, 1);
            addElement(combo, EInput.ATTACK_D, EAnimation.KI_BASIC_ATTACK, 2, true, 250, 1);
            addElement(combo, EInput.ATTACK_D, EAnimation.KI_BASIC_ATTACK, 1, true, 250, 1);
            addElement(combo, EInput.ATTACK_D, EAnimation.KI_BASIC_ATTACK, 2, true, 250, 1);
            addElement(combo, EInput.ATTACK_D, EAnimation.KI_BASIC_ATTACK, 1, true, 250, 1);
            addElement(combo, EInput.ATTACK_D, EAnimation.KI_BASIC_ATTACK, 2, true, 250, 1);
        } else if (type == ECombos.JUMP_KICK_ATTACK) {
            addElement(combo, EInput.ATTACK_C, EAnimation.JUMP_KICK, 0, true, 250, 1);
            addElement(combo, EInput.ATTACK_C, EAnimation.JUMP_KICK, 1, true, 250, 3);
        } else if (type == ECombos.SPIRAL_KICK_ATTACK) {
            addElement(combo, EInput.ATTACK_SPE, EAnimation.NULL, 0, false, 250, 1);
            addElement(combo, EInput.ATTACK_C, EAnimation.SPIRAL_KICK, 0, true, 250, 1);
            addElement(combo, EInput.ATTACK_C, EAnimation.SPIRAL_KICK, 1, true, 250, 3);
        } else if (type == ECombos.KICK_PROPELS_ATTACK) {
            addElement(combo, EInput.ATTACK_SPE, EAnimation.NULL, 0, false, 250, 1);
            addElement(combo, EInput.ATTACK_B, EAnimation.KICK_PROPELS, 0, true, 250, 1);
        } else if (type == ECombos.KI_CHARGE) {
            addElement(combo, EInput.ATTACK_SPE, EAnimation.NULL, 0, false, 250, 1);
            addElement(combo, EInput.ATTACK_SPE, EAnimation.KI_CHARGE, 0, true, 250, 1);
            addInfiniteElement(combo, EInput.ATTACK_SPE, EAnimation.KI_CHARGE, 1, true, 250);
        }
        return combo;
    }
}

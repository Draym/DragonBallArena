package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.combo;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components.EnumCombos;

/**
 * Created by andres_k on 30/11/2015.
 */
public class ComboFactory {
    public static Combo createCombo(EnumGameObject object, EnumCombos type) {
        if (object == EnumGameObject.GOKU) {
            return ComboFactory.createGokuCombo(type);
        }
        return null;
    }

    // GENERIC
    private static void addInfiniteElement(Combo combo, EnumInput input, EnumAnimation anim, int index, boolean change, int duration) {
        combo.addElement(new ComboElement(input, anim, index, change, duration));
        combo.addElement(new ComboElement(EnumInput.INFINITE, anim, index, change, duration));
    }

    private static void addInfiniteElement(Combo combo, EnumInput input, EnumAnimation animType, int animIndex, EnumAnimation hitType, int hitIndex, boolean change, int duration) {
        combo.addElement(new ComboElement(input, animType, animIndex, hitType, hitIndex, change, duration));
        combo.addElement(new ComboElement(EnumInput.INFINITE, animType, animIndex, hitType, hitIndex, change, duration));
    }

    private static void addElement(Combo combo, EnumInput input, EnumAnimation anim, int index, boolean change, int duration, int numbers) {
        for (int i = 0; i < numbers; ++i) {
            combo.addElement(new ComboElement(input, anim, index, change, duration));
        }
    }

    private static void addElement(Combo combo, EnumInput input, EnumAnimation animType, int animIndex, EnumAnimation hitType, int hitIndex, boolean change, int duration, int number) {
        for (int i = 0; i < number; ++i) {
            combo.addElement(new ComboElement(input, animType, animIndex, hitType, hitIndex, change, duration));
        }
    }

    // GOKU
    public static Combo createGokuCombo(EnumCombos type) {
        Combo combo = new Combo();

        if (type == EnumCombos.HANDATTACK) {
            addElement(combo, EnumInput.ATTACK_A, EnumAnimation.HAND_ATTACK, 0, EnumAnimation.TOUCHED_SIMPLE, 0, true, 400, 1);
            addElement(combo, EnumInput.ATTACK_A, EnumAnimation.HAND_ATTACK, 1, true, 250, 3);
        } else if (type == EnumCombos.MOVERIGHT_HANDATTACK) {
            addInfiniteElement(combo, EnumInput.MOVE_RIGHT, EnumAnimation.RUN, 0, false, -1);
            addElement(combo, EnumInput.ATTACK_A, EnumAnimation.HAND_ATTACK, 1, EnumAnimation.TOUCHED_SIMPLE, 0, true, 250, 2);
        } else if (type == EnumCombos.MOVELEFT_HANDATTACK) {
            addInfiniteElement(combo, EnumInput.MOVE_LEFT, EnumAnimation.RUN, 0, false, -1);
            addElement(combo, EnumInput.ATTACK_A, EnumAnimation.HAND_ATTACK, 1, EnumAnimation.TOUCHED_SIMPLE, 0, true, 250, 2);
        } else if (type == EnumCombos.HAND_FLY_PROPELS) {
            addElement(combo, EnumInput.ATTACK_B, EnumAnimation.HAND_FLY_PROPELS, 0, EnumAnimation.TOUCHED_MEDIUM, 0, true, 250, 1);
        }
        return combo;
    }
}

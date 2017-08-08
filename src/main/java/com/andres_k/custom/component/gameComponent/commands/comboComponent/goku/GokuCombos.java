package com.andres_k.custom.component.gameComponent.commands.comboComponent.goku;


import com.andres_k.custom.component.gameComponent.commands.comboComponent.ECombos;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.commands.comboComponent.combo.ComboFactory;
import com.andres_k.gameToolsLib.components.gameComponent.commands.comboComponent.components.ComboManager;

/**
 * Created by com.andres_k on 30/11/2015.
 */
public class GokuCombos extends ComboManager {
    public GokuCombos() {
        super();
        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.HAND_ATTACK));
        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.MOVE_HAND_ATTACK_RIGHT));
        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.MOVE_HAND_ATTACK_LEFT));

        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.HAND_FLY_PROPELS));

        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.RUSH_RIGHT));
        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.RUSH_LEFT));

        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.DEFENSE_MODE));
        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.BLOCK));

        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.RUSH_ATTACK));
        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.JUMP_KICK_ATTACK));
        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.SPIRAL_KICK_ATTACK));
        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.KICK_PROPELS_ATTACK));

        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.KI_CHARGE));
        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.KI_BASIC_ATTACK));
        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.KI_SPE_ATTACK));
        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.KI_FINAL_ATTACK));
    }
}

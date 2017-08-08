package com.andres_k.custom.component.gameComponent.commands.comboComponent.vegeta;


import com.andres_k.custom.component.gameComponent.commands.comboComponent.ECombos;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.commands.comboComponent.combo.ComboFactory;
import com.andres_k.gameToolsLib.components.gameComponent.commands.comboComponent.components.ComboManager;

/**
 * Created by com.andres_k on 14/03/2016.
 */
public class VegetaCombos extends ComboManager {
    public VegetaCombos() {
        super();
        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.HAND_ATTACK));
        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.MOVE_HAND_ATTACK_RIGHT));
        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.MOVE_HAND_ATTACK_LEFT));

        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.HAND_FLY_PROPELS));

        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.RUSH_RIGHT));
        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.RUSH_LEFT));

        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.DEFENSE_MODE));
        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.BLOCK));

        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.RUSH_ATTACK));
        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.KICK_PROPELS_ATTACK));
        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.KICK_ATTACK));

        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.KI_CHARGE));
        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.KI_EXPLODE));
        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.KI_BASIC_ATTACK));
        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.KI_SPE_ATTACK));
        this.combos.add(ComboFactory.createCombo(EGameObject.VEGETA, ECombos.KI_FINAL_ATTACK));
    }
}

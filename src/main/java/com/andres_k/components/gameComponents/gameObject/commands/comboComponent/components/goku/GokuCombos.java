package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components.goku;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.combo.ComboFactory;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components.ComboManager;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components.ECombos;

/**
 * Created by andres_k on 30/11/2015.
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

        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.RUSH_ATTACK));

        this.combos.add(ComboFactory.createCombo(EGameObject.GOKU, ECombos.SPE_ATTACK_1));
    }


}

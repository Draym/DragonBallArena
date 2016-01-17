package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components.goku;

import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.combo.ComboFactory;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components.ComboManager;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components.EnumCombos;

/**
 * Created by andres_k on 30/11/2015.
 */
public class GokuCombos extends ComboManager {
    public GokuCombos() {
        super();
        this.combos.add(ComboFactory.createCombo(EnumGameObject.GOKU, EnumCombos.HANDATTACK));
        this.combos.add(ComboFactory.createCombo(EnumGameObject.GOKU, EnumCombos.MOVERIGHT_HANDATTACK));
        this.combos.add(ComboFactory.createCombo(EnumGameObject.GOKU, EnumCombos.MOVELEFT_HANDATTACK));

        this.combos.add(ComboFactory.createCombo(EnumGameObject.GOKU, EnumCombos.HAND_FLY_PROPELS));
    }


}

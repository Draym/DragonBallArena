package com.andres_k.gameToolsLib.components.gameComponent.commands.comboComponent;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.commands.comboComponent.components.ComboManager;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import com.andres_k.gameToolsLib.utils.stockage.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by com.andres_k on 19/11/2015.
 */
public class ComboController {
    private ComboManager combos;
    private List<EInput> history;
    private long counter;

    public ComboController(EGameObject type) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        StringBuilder myPath = new StringBuilder();

        myPath.append("com.andres_k.custom.component.gameComponent.commands.comboComponent.");
        myPath.append(type.getValue().toLowerCase());
        myPath.append(".");
        myPath.append(type.getValue());
        myPath.append("Combos");
        this.combos = (ComboManager) ClassLoader.getSystemClassLoader().loadClass(myPath.toString()).newInstance();
        this.history = new ArrayList<>();
        this.counter = -1;
    }

    public void update() {
        if (this.counter >= 0)
            this.counter += GameConfig.currentTimeLoop;
    }
    public void reset() {
        this.combos.reset();
        this.history.clear();
    }

    public boolean nextComboStep(AnimatorController animatorController, EInput input) {
        if (this.counter >= this.combos.getCurrentDuration() && this.combos.getCurrentDuration() != -1)
            this.history.clear();
            this.history.add(input);
            if (this.combos.hasCombo(this.history)) {
                this.counter = 0;
                return true;
            }
            this.counter = -1;
            this.reset();

        return false;
    }

    public Pair<EAnimation, Integer> getCurrentAnimation() {
        return new Pair<>(this.combos.getCombo().getCurrentAnimation(), this.combos.getCombo().getCurrentAnimIndex());
    }
}
package com.andres_k.components.gameComponents.gameObject.commands.comboComponent;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components.ComboManager;
import com.andres_k.utils.configs.GlobalVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 19/11/2015.
 */
public class ComboController {
    private ComboManager combos;
    private List<EnumInput> history;
    private long counter;

    public ComboController(EnumGameObject type) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        StringBuilder myPath = new StringBuilder();

        myPath.append("com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components.");
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
            this.counter += GlobalVariable.currentTimeLoop;
    }
    public void reset() {
        this.combos.reset();
        this.history.clear();
    }

    public boolean nextComboStep(AnimatorController animatorController, EnumInput input) {
        if (this.counter >= this.combos.getCurrentDuration() && this.combos.getCurrentDuration() != -1)
            this.history.clear();
        this.history.add(input);
/*
        Console.write("/---");
        for (EnumInput val : this.history)
            Console.write(val.getValue());
        Console.write("---\\");
*/
        if (this.combos.hasCombo(this.history)) {
            this.counter = 0;
            animatorController.changeAnimation(this.combos.getCombo().getCurrentAnimation(), this.combos.getCombo().getCurrentAnimIndex());
            return true;
        }
        this.counter = -1;
        this.reset();
        return false;
    }
}
package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.components;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.combo.Combo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 30/11/2015.
 */
public class ComboManager {
    protected List<Combo> combos;
    protected Combo current;

    protected ComboManager() {
        this.combos = new ArrayList<>();
        this.current = null;
    }

    public void reset() {
        if (this.current != null)
            this.current.reset();
        this.current = null;
    }

    public boolean hasCombo(List<EnumInput> inputs) {
        if (this.current != null) {
            if (this.followCurrent(inputs)) {
                return true;
            }
        }
        while (true) {
            if (this.createCurrent(inputs)) {
                return true;
            }
            if (this.current == null) {
                return false;
            }
        }
    }

    private boolean followCurrent(List<EnumInput> inputs) {
        return this.current.hasInputs(inputs);
    }

    private boolean createCurrent(List<EnumInput> inputs) {
        if (this.current != null)
            this.current.reset();
        for (Combo tmp : this.combos) {
            if (tmp.hasInputs(inputs)) {
                this.current = tmp;
                return true;
            }
        }
        this.current = null;
        return false;
    }

    // GETTERS

    public int getCurrentDuration() {
        if (this.current != null) {
            return this.current.getCurrentDuration();
        }
        return 0;
    }

    public Combo getCombo() {
        return this.current;
    }
}

package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.combo;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.EAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 30/11/2015.
 */
public class Combo {
    private List<ComboElement> elements;
    private boolean created;
    private int current;

    public Combo() {
        this.elements = new ArrayList<>();
        this.created = false;
        this.current = 0;
    }

    // INIT

    public void addElement(ComboElement element) {
        this.elements.add(element);
    }

    public void reset() {
        this.current = 0;
    }

    // METHODS

    public boolean hasInputs(List<EInput> inputs) {
        for (int i = 0; i < inputs.size(); ++i) {
            int result = this.checkInput(i, inputs.get(i));
            if (result == -1) {
                return false;
            } else if (result == 1) {
                inputs.remove(i);
                --i;
            }
        }
        return true;
    }

    private int checkInput(int index, EInput input) {
        int pos = index;

        if (pos >= this.elements.size()) {
            pos = this.elements.size() - 1;
        }
        if (this.elements.get(pos).getInput() == EInput.INFINITE) {
            if (this.elements.get(pos - 1).getInput() == input) {
                this.current = pos;
                return 1;
            } else {
                pos += 1;
            }
        } else if (pos != index) {
            return -1;
        }
        if (pos < this.elements.size() && this.elements.get(pos).getInput() == input) {
            this.current = pos;
            return 0;
        }
        return -1;
    }

    // GETTERS

    public boolean isCreated() {
        return this.created;
    }

    public EAnimation getCurrentAnimation() {
        try {
            if (!this.elements.get(this.current).haveToChange())
                return EAnimation.NULL;
            return this.elements.get(this.current).getAnimType();
        } catch (Exception e) {
            return EAnimation.NULL;
        }
    }

    public Integer getCurrentAnimIndex() {
        try {
            return this.elements.get(this.current).getAnimIndex();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getCurrentDuration() {
        try {
            return this.elements.get(this.current).getDuration();
        } catch (Exception e) {
            return 0;
        }
    }
}

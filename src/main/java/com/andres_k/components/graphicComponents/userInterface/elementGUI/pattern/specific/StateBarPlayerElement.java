package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.specific;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.stockage.Tuple;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by andres_k on 08/03/2016.
 */
public class StateBarPlayerElement extends ComplexElement {
    private AnimatorController player;
    private boolean flip;
    private int nbrKi;
    private int nbrEnergy;

    public StateBarPlayerElement(String id, ColorShape container, AnimatorController player, boolean flip, boolean activated) {
        super(id + GlobalVariable.id_delimiter + EGuiElement.STATE_PLAYER, container, activated);
        this.player = player;
        this.flip = flip;
        this.nbrKi = 6;
        this.nbrEnergy = 5;
    }

    @Override
    public void reset() {
        super.reset();
    }

    @Override
    public void init() throws SlickException {
        ImageElement state = new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.STATE_PLAYER), true);
        state.setFlip(this.flip);
        this.addItem(state);
        if (this.flip) {
            this.addItem(new ImageElement(new ColorRect(new Rectangle(state.getAbsoluteWidth() - 25 - 70, 8, 0, 0)), this.player, true));
            this.addItem(new ImageElement(EGuiElement.HEALTH_BAR.getValue(), new ColorRect(new Rectangle(state.getAbsoluteWidth() - 105 - 333, 16, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.HEALTH_BAR), this.flip, true));
            int x = 97;
            for (int i = 0; i < this.nbrEnergy; ++i) {
                this.addItem(new ImageElement(EGuiElement.ENERGY_BAR.getValue() + GlobalVariable.id_delimiter + i, new ColorRect(new Rectangle(state.getAbsoluteWidth() - x - 27, 46, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.ENERGY_BAR), this.flip, true));
                x += 17;
            }
            x = 117;
            for (int i = 0; i < this.nbrKi; ++i) {
                this.addItem(new ImageElement(EGuiElement.KI_BAR.getValue() + GlobalVariable.id_delimiter + i, new ColorRect(new Rectangle(state.getAbsoluteWidth() - x - 19, 4, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.KI_BAR), this.flip, true));
                x += 19;
            }
        } else {
            this.addItem(new ImageElement(new ColorRect(new Rectangle(25, 8, 0, 0)), this.player, true));
            this.addItem(new ImageElement(EGuiElement.HEALTH_BAR.getValue(), new ColorRect(new Rectangle(105, 16, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.HEALTH_BAR), this.flip, true));
            int x = 97;
            for (int i = 0; i < this.nbrEnergy; ++i) {
                this.addItem(new ImageElement(EGuiElement.ENERGY_BAR.getValue() + GlobalVariable.id_delimiter + i, new ColorRect(new Rectangle(x, 46, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.ENERGY_BAR), this.flip, true));
                x += 17;
            }
            x = 117;
            for (int i = 0; i < this.nbrKi; ++i) {
                this.addItem(new ImageElement(EGuiElement.KI_BAR.getValue() + GlobalVariable.id_delimiter + i, new ColorRect(new Rectangle(x, 4, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.KI_BAR), this.flip, true));
                x += 19;
            }
        }
    }

    @Override
    public Object doTask(Object task) {
        if (task instanceof Tuple) {
            if (((Tuple) task).getV1().equals(ETaskType.SETTER) && ((Tuple) task).getV2() instanceof String) {
                if (((Tuple) task).getV2().equals("ki") && ((Tuple) task).getV3() instanceof Float) {
                    this.updateKi((float) ((Tuple) task).getV3());
                } else if (((Tuple) task).getV2().equals("energy") && ((Tuple) task).getV3() instanceof Float) {
                    this.updateEnergy((float) ((Tuple) task).getV3());
                } else if (((Tuple) task).getV2().equals("life") && ((Tuple) task).getV3() instanceof Float) {
                    this.updateLife((float) ((Tuple) task).getV3());
                }
            }
        }
        return super.doTask(task);
    }

    private void updateKi(float value) {
        float eachPercent = 100 / this.nbrKi;
        float result = value / eachPercent;

        for (int i = 0; i < this.nbrKi; ++i) {
            if (result - 1 > 0) {
                GuiElement img = this.getElementById(EGuiElement.KI_BAR.getValue() + GlobalVariable.id_delimiter + i);
                if (img != null) {
                    img.doTask(new Tuple<>(ETaskType.CUT, "body_X", 1f));
                }
            } else {
                if (result > 0) {
                    GuiElement img = this.getElementById(EGuiElement.KI_BAR.getValue() + GlobalVariable.id_delimiter + i);
                    if (img != null) {
                        img.doTask(new Tuple<>(ETaskType.CUT, "body_X", result));
                    }
                } else {
                    GuiElement img = this.getElementById(EGuiElement.KI_BAR.getValue() + GlobalVariable.id_delimiter + i);
                    if (img != null) {
                        img.doTask(new Tuple<>(ETaskType.CUT, "body_X", 0f));
                    }
                }
            }
            result -= 1;
        }
    }

    private void updateEnergy(float value) {
        float eachPercent = 100 / this.nbrEnergy;
        float result = value / eachPercent;

        for (int i = 0; i < this.nbrEnergy; ++i) {
            if (result - 1 > 0) {
                GuiElement img = this.getElementById(EGuiElement.ENERGY_BAR.getValue() + GlobalVariable.id_delimiter + i);
                if (img != null) {
                    img.doTask(new Tuple<>(ETaskType.CUT, "body_Y", 1f));
                }
            } else {
                if (result > 0) {
                    GuiElement img = this.getElementById(EGuiElement.ENERGY_BAR.getValue() + GlobalVariable.id_delimiter + i);
                    if (img != null) {
                        img.doTask(new Tuple<>(ETaskType.CUT, "body_Y", result));
                    }
                } else {
                    GuiElement img = this.getElementById(EGuiElement.ENERGY_BAR.getValue() + GlobalVariable.id_delimiter + i);
                    if (img != null) {
                        img.doTask(new Tuple<>(ETaskType.CUT, "body_Y", 0f));
                    }
                }
            }
            result -= 1;
        }
    }

    private void updateLife(float value) {
        GuiElement img = this.getElementById(EGuiElement.HEALTH_BAR.getValue());
        if (img != null) {
            img.doTask(new Tuple<>(ETaskType.CUT, "body_X", value));
        }
    }
}

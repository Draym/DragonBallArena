package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.specific;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.utils.configs.GlobalVariable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by andres_k on 08/03/2016.
 */
public class StateBarPlayerElement extends ComplexElement {
    private AnimatorController player;
    private boolean flip;

    public StateBarPlayerElement(String id, ColorShape container, AnimatorController player, boolean flip, boolean activated) {
        super(id + GlobalVariable.id_delimiter + EGuiElement.STATE_PLAYER, container, activated);
        this.player = player;
        this.flip = flip;
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
            for (int i = 0; i < 5; ++i) {
                this.addItem(new ImageElement(EGuiElement.ENERGY_BAR.getValue() + GlobalVariable.id_delimiter + i, new ColorRect(new Rectangle(state.getAbsoluteWidth() - x - 27, 46, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.ENERGY_BAR), this.flip, true));
                x += 17;
            }
            x = 117;
            for (int i = 0; i < 6; ++i) {
                this.addItem(new ImageElement(EGuiElement.KI_BAR.getValue() + GlobalVariable.id_delimiter + i, new ColorRect(new Rectangle(state.getAbsoluteWidth() - x - 19, 4, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.KI_BAR), this.flip, true));
                x += 19;
            }
        } else {
            this.addItem(new ImageElement(new ColorRect(new Rectangle(25, 8, 0, 0)), this.player, true));
            this.addItem(new ImageElement(EGuiElement.HEALTH_BAR.getValue(), new ColorRect(new Rectangle(105, 16, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.HEALTH_BAR), this.flip, true));
            int x = 97;
            for (int i = 0; i < 5; ++i) {
                this.addItem(new ImageElement(EGuiElement.ENERGY_BAR.getValue() + GlobalVariable.id_delimiter + i, new ColorRect(new Rectangle(x, 46, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.ENERGY_BAR), this.flip, true));
                x += 17;
            }
            x = 117;
            for (int i = 0; i < 6; ++i) {
                this.addItem(new ImageElement(EGuiElement.KI_BAR.getValue() + GlobalVariable.id_delimiter + i, new ColorRect(new Rectangle(x, 4, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.KI_BAR), this.flip, true));
                x += 19;
            }
        }
    }

    @Override
    public Object doTask(Object task) {
        return super.doTask(task);
    }

}

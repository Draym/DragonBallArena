package com.andres_k.components.resourceComponent.resources.data;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.resourceComponent.resources.ESprites;
import com.andres_k.components.resourceComponent.resources.factory.AnimatorFactory;
import com.andres_k.components.resourceComponent.resources.factory.AnimatorGuiFactory;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGuiData extends DataManager {
    private AnimatorFactory animatorFactory;
    private HashMap<EGuiElement, AnimatorController> animator;

    public AnimatorGuiData() {
        this.animatorFactory = new AnimatorGuiFactory();
        this.animator = new HashMap<>();
    }

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException {
        this.initialiseMethods();

        this.addAnimator(this.animatorFactory.getAnimator(ESprites.PANEL1), EGuiElement.PANEL1);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.LOAD_BAR), EGuiElement.LOAD_BAR);
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initContainers")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initButtons")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initComponents")));
    }

    public void initContainers() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.PANEL2), EGuiElement.PANEL2);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.PANEL3), EGuiElement.PANEL3);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.PANEL4), EGuiElement.PANEL4);
    }

    public void initButtons() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.BUTTON_PLAY_SOLO), EGuiElement.BUTTON_PLAY_SOLO);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.BUTTON_PLAY_VERSUS), EGuiElement.BUTTON_PLAY_VERSUS);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.BUTTON_PLAY_MULTI), EGuiElement.BUTTON_PLAY_MULTI);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.BUTTON_SETTING), EGuiElement.BUTTON_SETTING);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.BUTTON_CONTROLS), EGuiElement.BUTTON_CONTROLS);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.BUTTON_SLIDER), EGuiElement.BUTTON_SLIDER);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.BUTTON_CLOSE), EGuiElement.BUTTON_CLOSE);
    }

    public void initComponents() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.LOADING_ANIM), EGuiElement.LOADING_ANIM);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.SLIDER), EGuiElement.SLIDER);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.SLIDER_VALUE), EGuiElement.SLIDER_VALUE);
    }

    private void addAnimator(AnimatorController animatorController, EGuiElement type) {
        this.animator.put(type, animatorController);
    }

    // GETTERS
    public AnimatorController getAnimator(EGuiElement index) throws SlickException {
        if (this.animator.containsKey(index)) {
            return new AnimatorController(this.animator.get(index));
        }
        throw new SlickException("[ERROR]: The content of " + index.getValue() + " is missing.");
    }

}


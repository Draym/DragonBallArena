package com.andres_k.components.gameComponents.resources.data;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.resources.ESprites;
import com.andres_k.components.gameComponents.resources.factory.AnimatorFactory;
import com.andres_k.components.gameComponents.resources.factory.AnimatorGuiFactory;
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

        this.addAnimator(this.animatorFactory.getAnimator(ESprites.LOADING_EMPTY), EGuiElement.LOADING_EMPTY);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.LOAD_BAR), EGuiElement.LOAD_BAR);
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("init")));
    }

    public void init() throws NoSuchMethodException, SlickException, JSONException {
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


package com.andres_k.components.resourceComponent.resources.data;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.resourceComponent.resources.ESprites;
import com.andres_k.components.resourceComponent.resources.factory.AnimatorBackgroundFactory;
import com.andres_k.components.resourceComponent.resources.factory.AnimatorFactory;
import com.andres_k.components.graphicComponents.background.EBackground;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorBackgroundData extends DataManager {
    private AnimatorFactory animatorFactory;
    private HashMap<EBackground, AnimatorController> animator;


    public AnimatorBackgroundData() {
        this.animatorFactory = new AnimatorBackgroundFactory();
        this.animator = new HashMap<>();
    }

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException {
        this.initialiseMethods();
        this.initLoadScreen();
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initHomeScreen")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initMaps")));
    }

    public void initLoadScreen() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.LOAD_SCREEN), EBackground.LOAD_SCREEN);
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.LOGO), EBackground.LOGO);
    }

    public void initHomeScreen() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.HOME_SCREEN), EBackground.HOME_SCREEN);
    }

    public void initMaps() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(ESprites.VALLEY_MAP), EBackground.VALLEY_MAP);
    }

    private void addAnimator(AnimatorController animatorController, EBackground type) {
        this.animator.put(type, animatorController);
    }


    // GETTERS
    public AnimatorController getAnimator(EBackground index) throws SlickException {
        if (this.animator.containsKey(index)) {
            return this.animator.get(index);
        }
        throw new SlickException("[ERROR]: The content of " + index.getValue() + " is missing.");
    }

}


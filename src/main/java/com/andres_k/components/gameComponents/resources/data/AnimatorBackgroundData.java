package com.andres_k.components.gameComponents.resources.data;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.resources.factory.AnimatorFactory;
import com.andres_k.components.graphicComponents.background.EnumBackground;
import com.andres_k.components.gameComponents.resources.EnumSprites;
import com.andres_k.components.gameComponents.resources.factory.AnimatorBackgroundFactory;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorBackgroundData extends DataManager {
    private AnimatorFactory animatorFactory;
    private HashMap<EnumBackground, AnimatorController> animator;


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
        this.addAnimator(this.animatorFactory.getAnimator(EnumSprites.LOAD_SCREEN), EnumBackground.LOAD_SCREEN);
    }

    public void initHomeScreen() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(EnumSprites.HOME_SCREEN), EnumBackground.HOME_SCREEN);
    }

    public void initMaps() throws NoSuchMethodException, SlickException, JSONException {
        this.addAnimator(this.animatorFactory.getAnimator(EnumSprites.VALLEY_MAP), EnumBackground.VALLEY_MAP);
    }

    private void addAnimator(AnimatorController animatorController, EnumBackground type) {
        this.animator.put(type, animatorController);
    }


    // GETTERS
    public AnimatorController getAnimator(EnumBackground index) throws SlickException {
        if (this.animator.containsKey(index)) {
            return this.animator.get(index);
        }
        return null;
    }

}


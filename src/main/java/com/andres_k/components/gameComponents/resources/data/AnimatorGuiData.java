package com.andres_k.components.gameComponents.resources.data;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.resources.factory.AnimatorFactory;
import com.andres_k.components.gameComponents.resources.factory.AnimatorGuiFactory;
import com.andres_k.components.graphicComponents.userInterfaceDeprecated.types.EnumOverlayElement;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGuiData extends DataManager {
    private AnimatorFactory animatorFactory;
    private HashMap<EnumOverlayElement, AnimatorController> animator;

    public AnimatorGuiData() {
        this.animatorFactory = new AnimatorGuiFactory();
        this.animator = new HashMap<>();
    }

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException {
        this.initialiseMethods();
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("init")));
    }

    public void init() throws NoSuchMethodException, SlickException, JSONException {
    }

    private void addAnimator(AnimatorController animatorController, EnumOverlayElement type) {
        this.animator.put(type, animatorController);
    }

    // GETTERS
    public AnimatorController getAnimator(EnumOverlayElement index) throws SlickException {
        if (this.animator.containsKey(index)) {
            return new AnimatorController(this.animator.get(index));
        }
        return null;
    }

}


package com.andres_k.components.resourceComponent.resources.factory;

import com.andres_k.components.resourceComponent.resources.ESprites;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 13/03/2015.
 */
public abstract class AnimatorFactory {
    public abstract AnimatorController getAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException;
}

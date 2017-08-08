package com.andres_k.gameToolsLib.components.resourceComponents.resources.factory;

import com.andres_k.custom.component.resourceComponents.resources.ESprites;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by com.andres_k on 13/03/2015.
 */
public abstract class AnimatorFactory {
    public abstract AnimatorController getAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException;
}

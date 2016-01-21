package com.andres_k.components.gameComponents.resources.factory;

import com.andres_k.components.gameComponents.resources.EnumSprites;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 13/03/2015.
 */
public abstract class AnimatorFactory {
    public abstract AnimatorController getAnimator(EnumSprites index) throws SlickException, JSONException, NoSuchMethodException;
}

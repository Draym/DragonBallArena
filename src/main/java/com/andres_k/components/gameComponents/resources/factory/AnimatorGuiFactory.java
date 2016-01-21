package com.andres_k.components.gameComponents.resources.factory;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.resources.EnumSprites;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 20/01/2016.
 */
public class AnimatorGuiFactory extends AnimatorFactory {
    @Override
    public AnimatorController getAnimator(EnumSprites index) throws SlickException, JSONException, NoSuchMethodException {
        return null;
    }
}

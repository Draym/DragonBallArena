package com.andres_k.components.gameComponents.resources.factory;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.resources.ESprites;
import com.andres_k.utils.configs.ConfigPath;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 20/01/2016.
 */
public class AnimatorBackgroundFactory extends AnimatorFactory {
    @Override
    public AnimatorController getAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException {
        if (index.getIndex() == ESprites.SCREEN_BACKGROUND.getIndex()) {
            return this.getMenuBackgroundAnimator(index);
        } else if (index.getIndex() == ESprites.MAP_BACKGROUND.getIndex()) {
            return this.getMapBackgroundAnimator(index);
        }
        return null;
    }

    public AnimatorController getMenuBackgroundAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.LOAD_SCREEN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.backgroundIMG + "/backgroundLoad.jpg"));
        } else if (index == ESprites.HOME_SCREEN) {
            Animation animation = AnimationFactory.loadAnimation(ConfigPath.backgroundIMG + "/Home/backgroundHome ", ".jpg", 37, 37 /*125*/ /*192*/, true, 80);
            animation.setPingPong(true);
            animatorController.addAnimation(EAnimation.IDLE, 0, animation);
        }
        return animatorController;
    }

    public AnimatorController getMapBackgroundAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.VALLEY_MAP) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.mapIMG + "/valley_map.png"));
        }
        return animatorController;
    }
}

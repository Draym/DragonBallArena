package com.andres_k.components.resourceComponent.resources.factory;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.resourceComponent.resources.ESprites;
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
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_background + "/backgroundLoad.jpg"));
        } else if (index == ESprites.LOGO) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_background + "/logo.png"));
        } else if (index == ESprites.SELECT_SCREEN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_background + "/backgroundSelect.jpg"));
        } else if (index == ESprites.BATTLE_SCREEN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_background + "/backgroundBattle.jpg"));
        } else if (index == ESprites.HOME_SCREEN) {
            Animation animation = AnimationFactory.loadAnimation(ConfigPath.image_background + "/Home/backgroundHome ", ".jpg", 37, /*37*/ 125 /*192*/, true, 80);
            animation.setPingPong(true);
            animatorController.addAnimation(EAnimation.IDLE, 0, animation);
        }
        return animatorController;
    }

    public AnimatorController getMapBackgroundAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.VALLEY_MAP) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_map + "/valley_map.png"));
        }
        return animatorController;
    }
}

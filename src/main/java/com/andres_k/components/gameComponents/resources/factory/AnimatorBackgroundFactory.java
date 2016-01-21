package com.andres_k.components.gameComponents.resources.factory;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.resources.EnumSprites;
import com.andres_k.utils.configs.ConfigPath;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 20/01/2016.
 */
public class AnimatorBackgroundFactory extends AnimatorFactory {
    @Override
    public AnimatorController getAnimator(EnumSprites index) throws SlickException, JSONException, NoSuchMethodException {
        if (index.getIndex() == EnumSprites.MENU_BACKGROUND.getIndex()) {
            return this.getMenuBackgroundAnimator(index);
        } else if (index.getIndex() == EnumSprites.MAP_BACKGROUND.getIndex()) {
            return this.getMapBackgroundAnimator(index);
        }
        return null;
    }

    public AnimatorController getMenuBackgroundAnimator(EnumSprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == EnumSprites.LOAD_SCREEN) {
            Animation animation = new Animation();
            Image img = new Image(ConfigPath.backgroundIMG + "/backgroundLoad.jpg");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.HOME_SCREEN) {
            Animation animation = AnimationFactory.loadAnimation(ConfigPath.backgroundIMG + "/Home/backgroundHome ", ".jpg", 37, 37 /*125*/ /*192*/, true, 80);
            animation.setPingPong(true);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        }
        return animatorController;
    }

    public AnimatorController getMapBackgroundAnimator(EnumSprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == EnumSprites.VALLEY_MAP) {
            Animation animation = new Animation();
            Image img = new Image(ConfigPath.mapIMG + "/valley_map.png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        }
        return animatorController;
    }
}

package com.andres_k.components.resourceComponent.resources.factory;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.resourceComponent.resources.ESprites;
import com.andres_k.utils.configs.ConfigPath;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by andres_k on 20/01/2016.
 */
public class AnimatorGuiFactory extends AnimatorFactory {
    @Override
    public AnimatorController getAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException {
        if (index.getIndex() == ESprites.LOAD_GUI.getIndex()) {
            return this.getLoadGuiAnimator(index);
        } else if (index.getIndex() == ESprites.HOME_GUI.getIndex()) {
            return this.getHomeGuiAnimator(index);
        } else if (index.getIndex() == ESprites.GAME_GUI.getIndex()) {
            return this.getGameGuiAnimator(index);
        } else if (index.getIndex() == ESprites.SELECT_GUI.getIndex()) {
            return this.getSelectGuiAnimator(index);
        }
        return null;
    }

    public AnimatorController getLoadGuiAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.LOAD_BAR) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/Load" + "/loadingBar.png"));
        } else if (index == ESprites.LOADING_EMPTY) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/Load" + "/loadingEmpty.png"));
        }
        return animatorController;
    }

    public AnimatorController getHomeGuiAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.MENU) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/Home" + "/menu.png"));
        } else if (index == ESprites.BUTTON_PLAY_SOLO) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/Home" + "/buttonPlaySolo.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/Home" + "/buttonPlaySoloLight.png"));
        } else if (index == ESprites.BUTTON_PLAY_VERSUS) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/Home" + "/buttonPlayVersus.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/Home" + "/buttonPlayVersusLight.png"));
        } else if (index == ESprites.BUTTON_PLAY_MULTI) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/Home" + "/buttonPlayMulti.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/Home" + "/buttonPlayMultiLight.png"));
        } else if (index == ESprites.BUTTON_SETTING) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/Home" + "/buttonSetting.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/Home" + "/buttonSettingLight.png"));
        }
        return animatorController;
    }

    public AnimatorController getGameGuiAnimator(ESprites index) {
        AnimatorController animatorController = new AnimatorController();

        return animatorController;
    }

    public AnimatorController getSelectGuiAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.LOADING) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.guiIMG + "/Select" + "/loading.png", 400, 95), EAnimation.IDLE.isLoop(), 0, 2, 0, 1, new int[]{200, 200}));
        }
        return animatorController;
    }
}

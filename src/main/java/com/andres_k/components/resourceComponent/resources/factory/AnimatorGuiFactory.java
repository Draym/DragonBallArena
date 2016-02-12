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
        if (index.getIndex() == ESprites.BUTTON.getIndex()) {
            return this.getGuiButtonAnimator(index);
        } else if (index.getIndex() == ESprites.COMPONENT.getIndex()) {
            return this.getGuiComponentAnimator(index);
        } else if (index.getIndex() == ESprites.CONTAINER.getIndex()) {
            return this.getGuiContainerAnimator(index);
        }
        return null;
    }

    public AnimatorController getGuiButtonAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.BUTTON_PLAY_SOLO) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonPlaySolo.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonPlaySoloLight.png"));
        } else if (index == ESprites.BUTTON_PLAY_VERSUS) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonPlayVersus.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonPlayVersusLight.png"));
        } else if (index == ESprites.BUTTON_PLAY_MULTI) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonPlayMulti.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonPlayMultiLight.png"));
        } else if (index == ESprites.BUTTON_SETTING) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonSetting.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonSettingLight.png"));
        } else if (index == ESprites.BUTTON_CONTROLS) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonControls.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonControlsLight.png"));
        } else if (index == ESprites.BUTTON_SLIDER) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonSlider.png"));
        } else if (index == ESprites.BUTTON_CLOSE) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonClose.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonCloseLight.png"));
        }

        return animatorController;
    }

    public AnimatorController getGuiComponentAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.LOAD_BAR) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/loadingBar.png"));
        } else if (index == ESprites.LOADING_ANIM) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_gui + "/component" + "/loading.png", 400, 95), EAnimation.IDLE.isLoop(), 0, 2, 0, 1, new int[]{200, 200}));
        } else if (index == ESprites.SLIDER) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/slider.png"));
        } else if (index == ESprites.SLIDER_VALUE) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/sliderValue.png"));
        }
        return animatorController;
    }


    public AnimatorController getGuiContainerAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.PANEL1) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/container" + "/panel1.png"));
        } else if (index == ESprites.PANEL2) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/container" + "/panel2.png"));
        } else if (index == ESprites.PANEL3) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/container" + "/panel3.png"));
        } else if (index == ESprites.PANEL4) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/container" + "/panel4.png"));
        }
        return animatorController;
    }
}

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
        } else if (index.getIndex() == ESprites.AVATAR.getIndex()) {
            return this.getGuiAvatarAnimator(index);
        } else if (index.getIndex() == ESprites.CARDS.getIndex()) {
            return this.getGuiCardsAnimator(index);
        } else if (index.getIndex() == ESprites.ICON.getIndex()) {
            return this.getGuiIconAnimator(index);
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
        } else if (index == ESprites.BUTTON_EXIT) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonExit.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonExitLight.png"));
        } else if (index == ESprites.BUTTON_RESUME) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonResume.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonResumeLight.png"));
        } else if (index == ESprites.BUTTON_COMBO) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonCombo.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonComboLight.png"));
        } else if (index == ESprites.BUTTON_LOCK) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonLock.png"));
            animatorController.addAnimation(EAnimation.ON_CLICK, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/button" + "/buttonLocked.png"));
        }

        return animatorController;
    }

    public AnimatorController getGuiComponentAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.LOAD_BAR) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/loadingBar.png"));
        } else if (index == ESprites.LOADING_ANIM) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_gui + "/component" + "/loading.png", 588, 142), EAnimation.IDLE.isLoop(), 0, 3, 0, 1, new int[]{200, 200, 200}));
        } else if (index == ESprites.SLIDER) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/slider.png"));
        } else if (index == ESprites.SLIDER_VALUE) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/sliderValue.png"));
        } else if (index == ESprites.TAB_STATUS) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/tabOFF.png"));
            animatorController.addAnimation(EAnimation.ON_CLICK, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/tabON.png"));
        } else if (index == ESprites.DISABLED) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/disabled.png"));
        } else if (index == ESprites.HEALTH_BAR) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/healthBar.png"));
        } else if (index == ESprites.KI_BAR) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/kiBar.png"));
        } else if (index == ESprites.ENERGY_BAR) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/energyBar.png"));
        } else if (index == ESprites.STATE_PLAYER) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_gui + "/component" + "/statePlayer.png"));
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

    public AnimatorController getGuiAvatarAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.AVATAR_GOKU) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_avatar + "/gokuAvatar2.png"));
        } else if (index == ESprites.AVATAR_GOHAN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_avatar + "/gohanAvatar.png"));
        } else if (index == ESprites.AVATAR_VEGETA) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_avatar + "/vegetaAvatar.png"));
        } else if (index == ESprites.AVATAR_PICOLO) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_avatar + "/picoloAvatar.png"));
        } else if (index == ESprites.AVATAR_KAME_SENNIN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_avatar + "/kameSenninAvatar.png"));
        } else if (index == ESprites.AVATAR_CELL) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_avatar + "/cellAvatar.png"));
        } else if (index == ESprites.AVATAR_BUU) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_avatar + "/buuAvatar.png"));
        } else if (index == ESprites.AVATAR_FRIEEZA) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_avatar + "/frieezaAvatar.png"));
        } else if (index == ESprites.AVATAR_BORDER) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_avatar + "/avatarBorder.png"));
            animatorController.addAnimation(EAnimation.ON_FOCUS, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_avatar + "/avatarBorderLight.png"));
        }
        return animatorController;
    }

    public AnimatorController getGuiCardsAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.CARDS_ALL) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_card + "/gokuCard.jpg"));
            animatorController.addAnimation(EAnimation.IDLE, 7, AnimationFactory.createUniqueFrame(ConfigPath.image_card + "/gohanCard.jpg"));
            animatorController.addAnimation(EAnimation.IDLE, 2, AnimationFactory.createUniqueFrame(ConfigPath.image_card + "/vegetaCard.jpg"));
            animatorController.addAnimation(EAnimation.IDLE, 1, AnimationFactory.createUniqueFrame(ConfigPath.image_card + "/picoloCard.jpg"));
            animatorController.addAnimation(EAnimation.IDLE, 3, AnimationFactory.createUniqueFrame(ConfigPath.image_card + "/kameSenninCard.jpg"));
            animatorController.addAnimation(EAnimation.IDLE, 6, AnimationFactory.createUniqueFrame(ConfigPath.image_card + "/cellCard.jpg"));
            animatorController.addAnimation(EAnimation.IDLE, 5, AnimationFactory.createUniqueFrame(ConfigPath.image_card + "/buuCard.jpg"));
            animatorController.addAnimation(EAnimation.IDLE, 4, AnimationFactory.createUniqueFrame(ConfigPath.image_card + "/frieezaCard.jpg"));
        }
        return animatorController;
    }

    public AnimatorController getGuiIconAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.ICON_GOKU) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_icon + "/gokuIcon.png"));
        } else if (index == ESprites.ICON_GOHAN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_icon + "/gohanIcon.png"));
        } else if (index == ESprites.ICON_VEGETA) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_icon + "/vegetaIcon.png"));
        } else if (index == ESprites.ICON_PICOLO) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_icon + "/picoloIcon.png"));
        } else if (index == ESprites.ICON_KAME_SENNIN) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_icon + "/kameSenninIcon.png"));
        } else if (index == ESprites.ICON_CELL) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_icon + "/cellIcon.png"));
        } else if (index == ESprites.ICON_BUU) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_icon + "/buuIcon.png"));
        } else if (index == ESprites.ICON_FRIEEZA) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.image_icon + "/frieezaIcon.png"));
        }
        return animatorController;
    }
}

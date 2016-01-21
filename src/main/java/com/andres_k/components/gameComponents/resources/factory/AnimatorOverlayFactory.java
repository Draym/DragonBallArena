package com.andres_k.components.gameComponents.resources.factory;

import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.resources.EnumSprites;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorOverlayFactory extends AnimatorFactory {

    @Override
    public AnimatorController getAnimator(EnumSprites index) throws SlickException {
        if (index.getIndex() == EnumSprites.ROUND.getIndex()) {
            return this.getRoundAnimator(index);
        } else if (index.getIndex() == EnumSprites.MENU_ITEMS.getIndex()) {
            return this.getMenuAnimator(index);
        }
        return null;
    }

    public AnimatorController getRoundAnimator(EnumSprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == EnumSprites.NEW_GAME) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/newGame.png");
            animation.addFrame(img, 150);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.END_GAME) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/victory.png");
            animation.addFrame(img, 150);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);

            Animation animation2 = new Animation();
            Image img2 = new Image("images/overlay/defeat.png");
            animation2.addFrame(img2, 150);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation2);
        } else if (index == EnumSprites.TIMER) {
            Animation animation = new Animation();
            for (int i = 3; i > 0; --i) {
                Image img = new Image("images/overlay/roundCounter" + String.valueOf(i) + ".png");
                animation.addFrame(img, 600);
            }
            Image img = new Image("images/overlay/roundGo.png");
            animation.addFrame(img, 1000);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.NEW_ROUND) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/newRound.png");
            animation.addFrame(img, 150);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        }
        return animatorController;
    }

    public AnimatorController getMenuAnimator(EnumSprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == EnumSprites.EXIT) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/exit.png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.SETTINGS) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/settings.png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.CONTROLS) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/controls.png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.SCREEN) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/screen.png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.NEW) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/new.png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.GO) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/go.png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.NEXT) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/next.png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.SAVE) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/save.png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.HIGHSCORE) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/highScore.png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.TOPSCORE) {
            Animation animation = new Animation();
            Image img = new Image("images/overlay/topScore.png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        } else if (index == EnumSprites.ALPHABET) {
            animatorController = this.loadAlphabet();
        }
        return animatorController;
    }

    public AnimatorController loadAlphabet() throws SlickException {
        AnimatorController animatorController = new AnimatorController();
        String alphabet = "abcdefghijklmnopqrstuvwxyz-";

        for (int i = 0; i < 10; ++i) {
            Animation animation = new Animation();
            Image img = new Image("images/characters/numeric/" + String.valueOf(i) + ".png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        }
        for (int i = 0; i < alphabet.length(); ++i) {
            Animation animation = new Animation();
            Image img = new Image("images/characters/alphabet/" + alphabet.charAt(i) + ".png");
            animation.addFrame(img, 150);
            animation.setLooping(false);
            animatorController.addAnimation(EnumAnimation.IDLE, 0, animation);
        }
        return animatorController;
    }
}

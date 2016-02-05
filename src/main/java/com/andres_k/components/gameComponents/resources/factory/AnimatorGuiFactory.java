package com.andres_k.components.gameComponents.resources.factory;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.resources.ESprites;
import com.andres_k.utils.configs.ConfigPath;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

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
        }
        return null;
    }

    public AnimatorController getLoadGuiAnimator(ESprites index) throws SlickException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.LOAD_BAR) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/loadingBar.png"));
        } else if (index == ESprites.LOADING_EMPTY) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createUniqueFrame(ConfigPath.guiIMG + "/loadingEmpty.png"));
        }
        return animatorController;
    }

    public AnimatorController getHomeGuiAnimator(ESprites index) {
        AnimatorController animatorController = new AnimatorController();

        return animatorController;
    }

    public AnimatorController getGameGuiAnimator(ESprites index) {
        AnimatorController animatorController = new AnimatorController();

        return animatorController;
    }
}

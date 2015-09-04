package com.andres_k.components.gameComponents.animations;

import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameFactory extends AnimatorFactory {
    public Animator getAnimator(EnumSprites index) throws SlickException, JSONException {
        if (index.getIndex() == EnumSprites.ITEM.getIndex()) {
            return this.getItemAnimator(index);
        }
        return null;
    }

    public Animator getItemAnimator(EnumSprites index) throws SlickException, JSONException {
        Animator animator = new Animator();
        /*if (index == EnumSprites.ASTEROID) {
            animator.addAnimation(EnumAnimation.BASIC, this.loadAnimation(new SpriteSheet("image/game/asteroid.png", 101, 105), true, 0, 3, 0, 1, 400));
            animator.addCollision(EnumAnimation.BASIC, StringTools.readInput(getClass().getClassLoader().getResourceAsStream("json/asteroid.json")));
        }*/
        return animator;
    }

}

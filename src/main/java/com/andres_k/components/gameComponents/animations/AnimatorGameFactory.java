package com.andres_k.components.gameComponents.animations;

import com.andres_k.utils.tools.StringTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

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
        if (index == EnumSprites.GOKU) {
            animator.addAnimation(EnumAnimation.BASIC, this.loadAnimation(new SpriteSheet("image/game/gokuRun.png", 42, 54), true, 0, 2, 0, 1, 400));
            animator.addCollision(EnumAnimation.BASIC, StringTools.readInput(getClass().getClassLoader().getResourceAsStream("json/gokuRun.json")));
        }
        return animator;
    }
}

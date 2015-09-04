package com.andres_k.components.gameComponents.animations;

import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by andres_k on 13/03/2015.
 */
public abstract class AnimatorFactory {
    public abstract Animator getAnimator(EnumSprites index) throws SlickException, JSONException;

    protected Animation loadAnimation(SpriteSheet spriteSheet, boolean lopping, int startX, int endX, int startY, int endY, int speed) {
        Animation animation = new Animation();
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                animation.addFrame(spriteSheet.getSprite(x, y), speed);
            }

        }
        animation.setLooping(lopping);
        return animation;
    }
}

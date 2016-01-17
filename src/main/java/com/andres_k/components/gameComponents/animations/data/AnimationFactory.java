package com.andres_k.components.gameComponents.animations.data;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by andres_k on 09/12/2015.
 */
public class AnimationFactory {

    public static Animation loadAnimation(String id, String extension, int start, int end, boolean looping, int interval) throws SlickException {
        Animation animation = new Animation();
        String zero;

        for (int i = start; i <= end; ++i) {
            if (i < 100) {
                zero = "0";
            } else if (i < 10) {
                zero = "00";
            } else {
                zero = "";
            }
            animation.addFrame(new Image(id + zero + i + extension), interval);
        }
        animation.setLooping(looping);
        return animation;
    }

    public static Animation createAnimation(SpriteSheet spriteSheet, boolean looping, int startX, int endX, int startY, int endY, int speed[]) {
        Animation animation = new Animation();
        int i = 0;

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                animation.addFrame(spriteSheet.getSprite(x, y), speed[i]);
                ++i;
            }

        }
        animation.setLooping(looping);
        return animation;
    }
}

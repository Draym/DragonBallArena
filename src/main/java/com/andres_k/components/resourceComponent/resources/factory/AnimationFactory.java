package com.andres_k.components.resourceComponent.resources.factory;

import com.andres_k.utils.tools.MathTools;
import com.andres_k.utils.tools.StringTools;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by andres_k on 09/12/2015.
 */
public class AnimationFactory {

    public static Animation loadAnimation(String id, String extension, int start, int end, boolean looping, int interval) throws SlickException {
        return loadAnimation(id, extension, start, end, looping, interval, 0);
    }

    public static Animation loadAnimation(String id, String extension, int start, int end, boolean looping, int interval, int addZero) throws SlickException {
        Animation animation = new Animation();
        String zero;

        for (int i = start; i <= end; ++i) {
            zero = StringTools.duplicateString("0", MathTools.numberLevel(i, end) + addZero);
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

    public static Animation createStaticUniqueFrame(String file) throws SlickException {
        Animation animation = new Animation();
        Image img = new Image(file);
        animation.addFrame(img, 100);
        animation.setLooping(false);
        return animation;
    }

    public static Animation createUniqueFrame(String file, int time, boolean looping) throws SlickException {
        Animation animation = new Animation();
        Image img = new Image(file);
        animation.addFrame(img, time);
        animation.setLooping(looping);
        return animation;
    }
}

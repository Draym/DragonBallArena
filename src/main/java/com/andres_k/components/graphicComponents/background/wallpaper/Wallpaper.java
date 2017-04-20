package com.andres_k.components.graphicComponents.background.wallpaper;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.graphicComponents.background.BackgroundComponent;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 07/10/2015.
 */
public class Wallpaper extends BackgroundComponent {

    public Wallpaper(AnimatorController animator) throws SlickException {
        super(animator);
        this.ready = true;
    }

    public Wallpaper(AnimatorController animator, float x, float y) throws SlickException {
        super(animator, x, y);
    }
}

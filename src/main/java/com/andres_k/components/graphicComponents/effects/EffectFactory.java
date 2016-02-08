package com.andres_k.components.graphicComponents.effects;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.flash.FlashImage;
import com.andres_k.components.graphicComponents.effects.effect.flash.FlashShape;
import com.andres_k.components.graphicComponents.effects.effect.hide.HideIt;
import com.andres_k.components.graphicComponents.effects.effect.shake.ShakeScreen;
import com.andres_k.components.graphicComponents.effects.effect.zoom.ZoomIt;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorShape;

import java.util.UUID;

/**
 * Created by andres_k on 25/01/2016.
 */
public class EffectFactory {

    public static Effect createShakeScreen(long duration, int power, int invSpeed) {
        return new ShakeScreen(UUID.randomUUID().toString(), duration, power, invSpeed);
    }

    public static Effect createFlashEffect(long speed, ColorShape shape) {
        return new FlashShape(UUID.randomUUID().toString(), speed, shape);
    }

    public static Effect createFlashEffect(long speed) {
        return new FlashImage(UUID.randomUUID().toString(), speed);
    }

    public static Effect hideIt(long duration) {
        return new HideIt(UUID.randomUUID().toString(), duration);
    }

    public static Effect zoomIt(long duration, float start, float max) {
        return new ZoomIt(UUID.randomUUID().toString(), duration, start, max);
    }
}

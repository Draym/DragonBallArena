package com.andres_k.gameToolsLib.components.graphicComponents.effects;

import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.Effect;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.flash.FlashImage;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.flash.FlashShape;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.hide.HideIt;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.image.SingleAnimationEffect;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.shake.ShakeScreen;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.sound.SoundEffect;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.zoom.ZoomIt;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import org.newdawn.slick.Animation;

import java.util.UUID;

/**
 * Created by com.andres_k on 25/01/2016.
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

    public static Effect createSoundEffect(ESound sound) {
        return new SoundEffect(UUID.randomUUID().toString(), sound);
    }

    public static Effect createAnimationEffect(Animation animation, float decalX, float decalY, boolean center) {
        return new SingleAnimationEffect(UUID.randomUUID().toString(), animation, decalX, decalY, center);
    }

    public static Effect hideIt(long duration) {
        return new HideIt(UUID.randomUUID().toString(), duration);
    }

    public static Effect zoomIt(long duration, float start, float max) {
        return new ZoomIt(UUID.randomUUID().toString(), duration, start, max);
    }
}

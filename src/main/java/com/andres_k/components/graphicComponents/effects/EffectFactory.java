package com.andres_k.components.graphicComponents.effects;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.flash.FlashScreen;
import com.andres_k.components.graphicComponents.effects.effect.shake.ShakeScreen;

import java.util.UUID;

/**
 * Created by andres_k on 25/01/2016.
 */
public class EffectFactory {

    public static Effect createShakeScreen(long duration, int power, int speed) {
        return new ShakeScreen(UUID.randomUUID().toString(), duration, power, speed);
    }

    public static Effect createFlashScreen(long duration) {
        return new FlashScreen(UUID.randomUUID().toString(), duration);
    }
}

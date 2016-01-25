package com.andres_k.components.graphicComponents.effects;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.flash.FlashScreen;

import java.util.UUID;

/**
 * Created by andres_k on 25/01/2016.
 */
public class EffectFactory {

    public static Effect createFlashScreen() {
        return new FlashScreen(UUID.randomUUID().toString());
    }
}

package com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.shake;

import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.Effect;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.EffectType;

/**
 * Created by com.andres_k on 07/02/2016.
 */
public class ShakeImage extends ShakeIt {
    protected ShakeImage(String id, long duration, int power, int speedInterval) {
        super(id, EffectType.SHAKE_IMAGE, duration, power, speedInterval);
    }

    @Override
    public Effect copy() {
        return new ShakeImage(this.getId(), this.duration, this.power, this.interval);
    }

}

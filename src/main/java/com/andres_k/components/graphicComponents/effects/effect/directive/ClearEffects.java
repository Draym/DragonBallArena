package com.andres_k.components.graphicComponents.effects.effect.directive;

import com.andres_k.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 17/03/2016.
 */
public class ClearEffects extends Effect {
    public ClearEffects() {
        super("none", EffectType.CLEAR);
    }

    @Override
    public void draw(Graphics g) {

    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        return false;
    }
}

package com.andres_k.components.graphicComponents.effects.effect.shake;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EnumEffect;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 25/01/2016.
 */
public class ShakeScreen extends Effect {
    public ShakeScreen(String id, long duration) {
        super(id, EnumEffect.SCREEN_SHAKE, duration);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {

    }
}

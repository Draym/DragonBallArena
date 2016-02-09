package com.andres_k.components.graphicComponents.effects.effect.hide;

import com.andres_k.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 08/02/2016.
 */
public class HideIt extends Effect {
    protected long timer;

    public HideIt(String id, long duration) {
        super(id, EffectType.HIDE, duration);
        this.timer = duration;
    }

    @Override
    public void draw(Graphics g) {
    }

    @Override
    public boolean update() {
        super.update();
        return false;
    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        this.timer -= this.delta;
        conf.drawable = false;
        if (this.timer <= 0) {
            this.running = false;
        }
        this.delta = 0;
        return false;
    }
}

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
    protected long current;

    public HideIt(String id, long duration) {
        super(id, EffectType.HIDE);
        this.timer = duration;
        this.current = 0;
    }

    @Override
    public void restart() {
        super.restart();
        this.current = 0;
    }

    @Override
    public void draw(Graphics g, float scale) {
        // do nothing
    }

    @Override
    public Effect copy() {
        return new HideIt(this.getId(), this.timer);
    }

    @Override
    public boolean update() {
        super.update();
        return false;
    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        this.current += this.delta;
        conf.drawable = false;
        if (this.current >= this.timer) {
            this.stop();
        }
        this.delta = 0;
        return false;
    }
}

package com.andres_k.components.graphicComponents.effects.effect.shake;

import com.andres_k.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 07/02/2016.
 */
public abstract class ShakeIt extends Effect {
    protected long duration;
    protected long timer;
    protected int interval;
    protected int current;
    protected int power;
    protected List<Pair<Integer, Integer>> pattern;

    protected ShakeIt(String id, EffectType type, long duration, int power, int interval) {
        super(id, type);
        this.duration = duration;
        this.timer = this.duration;
        this.current = 0;
        this.interval = interval;
        this.power = power;
        this.pattern = new ArrayList<>();
        this.pattern.add(new Pair<>(-power, -power));
        this.pattern.add(new Pair<>(power, 0));
        this.pattern.add(new Pair<>(-power, power));
        this.pattern.add(new Pair<>(power, 0));
    }

    @Override
    public void restart() {
        super.restart();
        this.current = 0;
        this.timer = this.duration;
    }

    @Override
    public void draw(Graphics g) {
        // do nothing
    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        if (this.delta >= this.interval) {
            this.current = (this.current + 1 < this.pattern.size() ? this.current + 1 : 0);

            conf.x += this.pattern.get(this.current).getV1();
            conf.y += this.pattern.get(this.current).getV2();

            this.timer -= this.delta;
            if (this.timer <= 0) {
                this.stop();
            }
            this.delta = 0;
        }
        return true;
    }
}

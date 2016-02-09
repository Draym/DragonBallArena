package com.andres_k.components.graphicComponents.effects.effect.zoom;

import com.andres_k.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.utils.tools.MathTools;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 07/02/2016.
 */
public class ZoomIt extends Effect {
    protected float start;
    protected float end;
    protected float max;
    protected float current;
    protected float interval;
    protected boolean toEnd;

    public ZoomIt(String id, long duration, float start, float max) {
        super(id, EffectType.ZOOM, duration);
        this.start = start;
        this.end = 1f;
        this.max = max;
        this.current = start;
        this.interval = (MathTools.abs(this.max - this.end) + MathTools.abs(this.max - this.start)) / this.getDuration();
        if (start > max) {
            this.interval *= (-1);
        }
        this.toEnd = false;
    }

    @Override
    public boolean update() {
        if (!this.toEnd && this.current < this.max) {
            this.current += this.interval;
            this.current = (this.current > this.max ? this.max : this.current);
        }
        else if (!this.toEnd && this.current >= this.max) {
            if ((this.max < this.end && this.interval < 0) || (this.max > this.end && this.interval >= 0)) {
                this.interval *= (-1);
            }
            this.toEnd = true;
        }
        if (this.toEnd && this.current > this.end) {
            this.current += this.interval;
            this.current = (this.current < this.end ? this.end : this.current);
        }
        else if (this.toEnd && this.current <= this.end) {
            this.running = false;
        }
        return true;
    }

    @Override
    public void draw(Graphics g) {
    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        conf.x -= (((conf.imageSizeX * this.current) - conf.imageSizeX) / 2);
        conf.y -= (((conf.imageSizeY * this.current) - conf.imageSizeY) / 2);
        conf.scale = this.current;
     //   Console.write("conf: " + conf);
        return true;
    }
}

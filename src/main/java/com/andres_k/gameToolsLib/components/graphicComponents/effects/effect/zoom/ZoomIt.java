package com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.zoom;

import com.andres_k.gameToolsLib.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.Effect;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.gameToolsLib.utils.tools.Console;
import com.andres_k.gameToolsLib.utils.tools.MathTools;
import org.newdawn.slick.Graphics;

/**
 * Created by com.andres_k on 07/02/2016.
 */
public class ZoomIt extends Effect {
    protected final float start;
    protected final float end;
    protected final long duration;
    protected float max;
    protected float current;
    protected float interval;
    protected boolean toEnd;

    public ZoomIt(String id, long duration, float start, float max) {
        super(id, EffectType.ZOOM);
        this.start = start;
        this.end = 1f;
        this.max = max;
        this.current = start;
        this.duration = duration;
        this.interval = (MathTools.abs(this.max - this.end) + MathTools.abs(this.max - this.start)) / this.duration;
        if (start > max) {
            this.interval *= (-1);
        }
        this.toEnd = false;
    }

    @Override
    public void restart() {
        super.restart();
        Console.write("RESTART");
        this.toEnd = false;
        this.current = this.start;
    }

    @Override
    public Effect copy() {
        return new ZoomIt(this.getId(), this.duration, this.start, this.max);
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
            this.stop();
        }
        return true;
    }

    @Override
    public void draw(Graphics g, float scale) {
        // do nothing
    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        conf.x -= (((conf.imageSizeX * this.current) - conf.imageSizeX) / 2);
        conf.y -= (((conf.imageSizeY * this.current) - conf.imageSizeY) / 2);
        conf.scale = this.current;
        return true;
    }
}

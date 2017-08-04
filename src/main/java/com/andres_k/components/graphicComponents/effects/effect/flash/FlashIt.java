package com.andres_k.components.graphicComponents.effects.effect.flash;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.utils.tools.Console;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 07/02/2016.
 */
public abstract class FlashIt extends Effect {
    protected ColorShape flash;
    protected float save_light;
    protected float save_constant;
    protected float light;
    protected float constant;
    protected float duration;
    protected boolean upLight;
    protected long speed;

    public FlashIt(String id, EffectType type, long speed, ColorShape shape) {
        super(id, type);
        float timeLoop = 30; //GameConfig.currentTimeLoop;
        this.speed = speed;
        this.duration = (510 / (speed / timeLoop)) * timeLoop;
        this.flash = shape;
        this.light = shape.getColor().getAlpha();
        this.upLight = true;
        this.constant = (255 / (speed / timeLoop));
        this.save_light = this.light;
        this.save_constant = this.constant;
    }

    @Override
    public void restart() {
        super.restart();
        this.upLight = true;
        this.light = this.save_light;
        this.constant = this.save_constant;
    }

    @Override
    public void draw(Graphics g, float scale) {
        this.flash.draw(g);
    }

    @Override
    public boolean update() {
        if (this.upLight) {
            this.light = (this.light + this.constant < 255 ? this.light + this.constant : 255);
        } else {
            this.light = (this.light - this.constant > 0 ? this.light - this.constant : 0);
        }
        this.flash.changeColor(255, 255, 255, this.light);

        if (this.light == 255) {
            this.upLight = false;
            this.constant = this.constant - (this.constant / 3);
        }
        else if (this.light == 0) {
            this.stop();
        }
        return true;
    }
}

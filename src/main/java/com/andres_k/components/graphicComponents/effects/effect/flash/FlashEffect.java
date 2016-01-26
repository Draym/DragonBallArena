package com.andres_k.components.graphicComponents.effects.effect.flash;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EnumEffect;
import com.andres_k.components.graphicComponents.userInterface.items.tools.ColorShape;
import com.andres_k.utils.configs.GlobalVariable;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 26/01/2016.
 */
public class FlashEffect extends Effect {
    protected ColorShape flash;
    protected float light;
    protected boolean upLight;
    protected float constant;

    protected FlashEffect(String id, EnumEffect type, long duration, ColorShape shape) {
        super(id, type, duration);
        this.flash = shape;
        this.light = shape.getColor().getAlpha();
        this.upLight = true;
        this.constant = (255 / (duration / GlobalVariable.currentTimeLoop));
    }

    @Override
    public void update() {
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
            this.running = false;
        }
    }

    @Override
    public void draw(Graphics g) {
        this.flash.draw(g);
    }

}

package com.andres_k.components.graphicComponents.effects.effect.flash;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorShape;
import com.andres_k.utils.configs.GameConfig;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 07/02/2016.
 */
public abstract class FlashIt extends Effect {
    protected ColorShape flash;
    protected float light;
    protected boolean upLight;
    protected float constant;

    public FlashIt(String id, EffectType type, long speed, ColorShape shape) {
        super(id, type, (510 / (speed / GameConfig.currentTimeLoop)) * GameConfig.currentTimeLoop);
        this.flash = shape;
        this.light = shape.getColor().getAlpha();
        this.upLight = true;
        this.constant = (255 / (speed / GameConfig.currentTimeLoop));
    }

    @Override
    public void draw(Graphics g) {
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
            this.running = false;
        }
        return true;
    }
}

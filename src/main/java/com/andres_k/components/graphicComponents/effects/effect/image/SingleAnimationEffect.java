package com.andres_k.components.graphicComponents.effects.effect.image;

import com.andres_k.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 21/02/2016.
 */
public class SingleAnimationEffect extends Effect {
    protected Animation animation;
    protected float decalX;
    protected float decalY;
    protected float x;
    protected float y;
    protected boolean center;

    public SingleAnimationEffect(String id, Animation animation, float decalX, float decalY, boolean center) {
        super(id, EffectType.ANIMATION);
        this.animation = animation;
        this.decalX = decalX;
        this.decalY = decalY;
        this.center = center;
        this.x = 0;
        this.y = 0;
    }

    @Override
    public void restart() {
        super.restart();
        this.animation.restart();
    }

    @Override
    public boolean update() {
        if (this.animation.isStopped()) {
            this.stop();
        }
        return super.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawAnimation(this.animation, this.x, this.y);
    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        this.x = conf.x + this.decalX;
        this.y = conf.y + this.decalY;
        if (this.center) {
            this.x -= this.animation.getWidth() / 2;
            this.y -= this.animation.getHeight() / 2;
        }
        return true;
    }
}

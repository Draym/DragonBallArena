package com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.image;

import com.andres_k.gameToolsLib.components.camera.CameraController;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.Effect;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

/**
 * Created by com.andres_k on 21/02/2016.
 */
public class SingleAnimationEffect extends Effect {
    protected Animation animation;
    protected float decalX;
    protected float decalY;
    protected float x;
    protected float y;
    protected boolean center;
    protected boolean flipX;
    protected boolean flipY;

    public SingleAnimationEffect(String id, Animation animation, float decalX, float decalY, boolean center) {
        super(id, EffectType.ANIMATION);
        this.animation = animation;
        this.decalX = decalX;
        this.decalY = decalY;
        this.center = center;
        this.x = 0;
        this.y = 0;
        this.flipX = false;
        this.flipY = false;
    }

    @Override
    public void restart() {
        super.restart();
        this.animation.restart();
    }

    @Override
    public Effect copy() {
        return new SingleAnimationEffect(this.getId(), this.animation.copy(), this.decalX, this.decalY, this.center);
    }

    @Override
    public boolean update() {
        this.animation.update(GameConfig.currentTimeLoop);
        if (this.animation.isStopped()) {
            this.stop();
        }
        return super.update();
    }

    @Override
    public void draw(Graphics g, float scale) {
        CameraController.get().draw(this.animation.getCurrentFrame().getFlippedCopy(this.flipX, this.flipY), this.x, this.y, scale, true);
    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        if (conf.flipX) {
            this.x = conf.x + (this.decalX < 0 ? -this.decalX : 2 * this.decalX) * conf.scale;
        } else {
            this.x = conf.x + this.decalX * conf.scale;
        }
        if (conf.flipY) {
            this.y = conf.y + (this.decalY < 0 ? -this.decalY : 2 * this.decalY) * conf.scale;
        } else {
            this.y = conf.y + this.decalY * conf.scale;
        }
        this.flipX = conf.flipX;
        this.flipY = conf.flipY;
        if (this.center) {
            this.x -= this.animation.getWidth() / 2;
            this.y -= this.animation.getHeight() / 2;
        }
        return true;
    }
}

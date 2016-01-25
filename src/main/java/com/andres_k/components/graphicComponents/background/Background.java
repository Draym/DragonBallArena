package com.andres_k.components.graphicComponents.background;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.graphicComponents.effects.EffectManager;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EnumEffect;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 07/10/2015.
 */
public class Background {
    protected AnimatorController animator;
    protected EffectManager effectManager;
    protected float x;
    protected float y;

    protected boolean ready;
    protected boolean running;

    public Background(AnimatorController animator) throws SlickException {
        this(animator, 0, 0);
    }

    public Background(AnimatorController animator, float x, float y) throws SlickException {
        this.ready = false;
        this.running = false;

        this.x = x;
        this.y = y;

        this.animator = animator;
        this.effectManager = new EffectManager();
        this.instanceCurrentBackground();
        this.ready = true;
    }

    // FUNCTIONS
    public void draw(Graphics g) {
        g.drawAnimation(this.animator.currentAnimation(), this.x, this.y);
        this.effectManager.render(g);
    }


    public void update(){
        this.effectManager.update();
    }

    public void instanceCurrentBackground() throws SlickException {
    }

    public boolean isReady() {
        return this.ready;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void playEffect(Effect effect) {
        this.effectManager.playEffect(effect);
    }

    public void stopEffect(String id) {
        this.effectManager.stopEffect(id);
    }

    public boolean hasActivity() {
        return this.effectManager.hasActivity();
    }

    public boolean effectIsRunning(String id) {
        return this.effectManager.effectIsRunning(id);
    }

    public boolean effectIsActive(EnumEffect type) {
        return this.effectManager.effectIsActive(type);
    }
}

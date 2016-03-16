package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.gameComponents.animations.details.AnimationConfigItem;
import com.andres_k.components.gameComponents.animations.details.AnimationRepercussionItem;
import com.andres_k.components.gameComponents.bodies.BodyAnimation;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.graphicComponents.effects.EffectManager;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Created by andres_k on 18/11/2015.
 */
public class Animator {
    protected EffectManager effectManager;
    protected Animation animation;
    protected BodyAnimation body;
    protected AnimationConfigItem config;
    protected AnimationRepercussionItem repercussion;
    protected Color filter;
    private int currentFrame;
    private boolean canDoAction;

    public Animator() {
        this.animation = null;
        this.config = null;
        this.body = null;
        this.repercussion = null;
        this.filter = new Color(1f, 1f, 1f);
        this.currentFrame = -1;
        this.canDoAction = true;
        this.effectManager = new EffectManager();
    }

    public Animator(Animator animator) {
        if (animator.animation != null) {
            this.animation = animator.animation.copy();
        }
        else {
            this.animation = null;
        }
        this.body = animator.body;
        this.config = animator.config;
        this.repercussion = animator.repercussion;
        this.filter = animator.filter;
        this.currentFrame = animator.currentFrame;
        this.canDoAction = animator.canDoAction;
        this.effectManager = new EffectManager(animator.effectManager);
    }

    // METHODS

    public void doAction(GameObject object, int frame) {
        if (this.config != null && (this.canDoAction || this.config.canExecuteEveryTime())) {
            this.canDoAction = false;
            this.config.doAction(object, frame);
        }
    }

    public void draw(Graphics g, float x, float y, boolean flipX, boolean flipY) {
        if (this.animation != null) {
            Image image = this.getAnimation().getCurrentFrame().getFlippedCopy(flipX, flipY);
            if (this.effectManager.hasActivity()) {
                this.effectManager.draw(g, image, x, y, flipX, flipY);
            } else {
                g.drawImage(image, x, y);
            }
        }
    }

    public void update(long delta) {
        this.effectManager.update();
        if (this.animation != null) {
            this.animation.update(delta);
            if (this.currentFrame != this.animation.getFrame()) {
                this.currentFrame = this.animation.getFrame();
                this.canDoAction = true;
                this.effectManager.activateEffects(this.currentFrame);
            }
        }
    }

    public void restart() {
        this.currentFrame = -1;
        if (this.animation != null) {
            this.animation.restart();
        }
        if (this.config != null) {
            this.config.restart();
        }
        if (this.repercussion != null) {
            this.repercussion.restart();
        }
        if (this.body != null) {
            this.body.restart();
        }
        this.effectManager.restart();
    }

    public void nextFrame() {
        if (!this.animation.isStopped()) {
            int frame = this.animation.getFrame() + 1;
            while (this.animation.getFrame() != frame) {
                this.animation.update(10);
            }
        }
    }

    // EFFECTS
    public void addEffect(int index, Effect effect, int priority) {
        this.effectManager.addEffect(index, effect, priority);
    }

    public void playEffect(int priority, Effect effect) {
        this.effectManager.playEffect(priority, effect);
    }

    public boolean hasEffectActivity() {
        return this.effectManager.hasActivity();
    }

    public boolean effectIsRunning(String id) {
        return this.effectManager.effectIsRunning(id);
    }

    public boolean effectIsActive(EffectType type) {
        return this.effectManager.effectIsActive(type);
    }

    // ADD FUNCTIONS
    public void addAnimation(Animation animation) {
        this.animation = animation.copy();
    }

    public void addCollision(BodyAnimation body) {
        this.body = body;
    }

    public void addConfig(AnimationConfigItem config) {
        this.config = config;
    }

    public void addRepercussion(AnimationRepercussionItem repercussion) {
        this.repercussion = repercussion;
    }

    // GETTERS

    public Animation getAnimation() {
        return this.animation;
    }

    public AnimationConfigItem getConfig() {
        return this.config;
    }

    public AnimationRepercussionItem getRepercussion() {
        return this.repercussion;
    }

    public BodyAnimation getBodyAnimation() {
        return this.body;
    }

    public Pair<EAnimation, Integer> getNext() {
        try {
            return this.config.getNext();
        } catch (Exception e) {
            return null;
        }
    }

    public Color getFilter() {
        return this.filter;
    }

}

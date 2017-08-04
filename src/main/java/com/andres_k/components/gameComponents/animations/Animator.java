package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.camera.CameraController;
import com.andres_k.components.gameComponents.animations.details.AnimationConfigItem;
import com.andres_k.components.gameComponents.animations.details.AnimationRepercussionItem;
import com.andres_k.components.gameComponents.bodies.BodyAnimation;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.graphicComponents.effects.EffectManager;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
import org.codehaus.jettison.json.JSONException;
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
    private float scale;

    public Animator() {
        this.animation = null;
        this.config = null;
        this.body = null;
        this.repercussion = null;
        this.filter = new Color(1f, 1f, 1f);
        this.currentFrame = -1;
        this.canDoAction = true;
        this.effectManager = new EffectManager();
        this.scale = 1.0f;
    }

    public Animator(Animator animator) {
        if (animator.animation != null) {
            this.animation = animator.animation.copy();
        } else {
            this.animation = null;
        }
        this.body = animator.body;
        this.config = animator.config;
        this.repercussion = animator.repercussion;
        this.filter = animator.filter;
        this.currentFrame = animator.currentFrame;
        this.canDoAction = animator.canDoAction;
        this.effectManager = new EffectManager(animator.effectManager);
        this.scale = animator.scale;
    }

    // METHODS

    public void doAction(GameObject object, int frame) {
        if (this.config != null && (this.canDoAction || this.config.canExecuteEveryTime())) {
            this.canDoAction = false;
            this.config.doAction(object, frame);
        }
    }

    public void draw(Graphics g, float drawX, float drawY, float rotateAngle, boolean flipX, boolean flipY, boolean useCameraMove) {
            if (this.animation != null) {
            Image image = this.getAnimation().getCurrentFrame().getFlippedCopy(flipX, flipY);
            if (rotateAngle != 0) {
                 image.setRotation(rotateAngle);
            }
            if (this.effectManager.hasActivity()) {
                this.effectManager.draw(g, image, drawX, drawY, this.scale, flipX, flipY);
            } else {
                CameraController.get().draw(image, drawX, drawY, this.scale, useCameraMove);
            }
        }
    }

    public void drawSubImage(Graphics g, float drawX, float drawY, int posX, int posY, int width, int height, float rotateAngle, boolean flipX, boolean flipY, boolean useCameraMove) {
        if (this.animation != null) {
            Image image = this.getAnimation().getCurrentFrame().getSubImage(posX, posY, width, height).getFlippedCopy(flipX, flipY);
            if (rotateAngle != 0) {
               image.setRotation(rotateAngle);
            }
            if (this.effectManager.hasActivity()) {
                this.effectManager.draw(g, image, drawX, drawY, 1.0f, flipX, flipY);
            } else {
                CameraController.get().draw(image, drawX, drawY, useCameraMove);
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
            while (this.animation.getFrame() != frame && !this.animation.isStopped()) {
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
    public void addAnimation(Animation animation, float scale) {
        this.animation = animation.copy();
        this.scale = scale;
    }

    public void addCollision(String jsonValue) throws JSONException {
        this.body = new BodyAnimation(jsonValue, this.scale);
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

    public float getScale() {
        return this.scale;
    }
}

package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.gameComponents.bodies.BodyAnimation;
import com.andres_k.components.gameComponents.bodies.BodySprite;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.collisions.EnumDirection;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ActivatedTimer;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.ConsoleWrite;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorController implements Observer {

    // animators
    private HashMap<EnumAnimation, Animator> animators;
    private ActivatedTimer activatedTimer;
    private EnumDirection direction;

    private EnumAnimation current;
    private int index;

    private boolean printable;
    private boolean deleted;
    private boolean needUpdate;

    public AnimatorController() {
        this.animators = new HashMap<>();
        this.current = EnumAnimation.IDLE;
        this.index = 0;

        this.printable = true;
        this.deleted = false;
        this.needUpdate = false;
        this.activatedTimer = new ActivatedTimer(true);
        this.direction = EnumDirection.NONE;
    }

    public AnimatorController(AnimatorController animatorController) throws SlickException {
        if (animatorController == null) {
            throw new SlickException("image not loaded");
        }

        this.animators = new HashMap<>();
        for (Map.Entry<EnumAnimation, Animator> entry : animatorController.animators.entrySet()) {
            this.animators.put(entry.getKey(), new Animator(entry.getValue()));
        }

        this.current = animatorController.current;
        this.index = animatorController.index;

        this.printable = animatorController.printable;
        this.deleted = animatorController.deleted;
        this.needUpdate = animatorController.needUpdate;
        this.activatedTimer = new ActivatedTimer(animatorController.activatedTimer);
        this.direction = animatorController.direction;
    }

    // UPDATE
    public void update() {
    }

    public void toCurrentNextAnimation() {
        Pair<EnumAnimation, Integer> next = this.getCurrent().getConfig().getNext();
        this.setCurrent(next.getV1());
        this.setIndex(next.getV2());
    }

    public void doCurrentAction(GameObject object) {
        this.getCurrent().doAction(object, this.currentFrame());
        if (this.currentAnimation().isStopped()) {
            this.toCurrentNextAnimation();
            if (this.current == EnumAnimation.FALL) {
                object.getMovement().resetGravity();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof EnumAnimation) {
            this.setCurrent((EnumAnimation) arg);
        }
    }

    // ADD FUNCTIONS
    public void addCopyAnimator(EnumAnimation type, Animator animator) {
        this.animators.put(type, new Animator(animator));
    }

    public void addAnimator(EnumAnimation type, Animator animator) {
        this.animators.put(type, animator);
    }

    public void addAnimation(EnumAnimation type, Animation animation) {
        if (!this.animators.containsKey(type)) {
            this.animators.put(type, new Animator());
        }
        this.animators.get(type).addAnimation(animation);
    }

    public void addCollision(EnumAnimation type, String jsonValue) throws JSONException {
        if (!this.animators.containsKey(type)) {
            this.animators.put(type, new Animator());
        }
        this.animators.get(type).addCollision(new BodyAnimation(jsonValue));
    }

    public void addConfig(EnumAnimation type, AnimatorConfig config) {
        if (!this.animators.containsKey(type)) {
            this.animators.put(type, new Animator());
        }
        this.animators.get(type).addConfig(config);
    }

    // METHODS
    public void restart() {
        for (Map.Entry<EnumAnimation, Animator> entry : this.animators.entrySet()) {
            entry.getValue().restart();
        }

        this.setCurrent(EnumAnimation.IDLE);
        this.printable = true;
        this.deleted = false;
    }

    public void restartAnimation(EnumAnimation animation) {
        if (this.animators.containsKey(animation)) {
            this.animators.get(animation).restart();
        }
    }

    public void startTimer(long delay) {
        this.activatedTimer.startTimer(delay);
        this.needUpdate = true;
    }

    public void updateAnimator(boolean setPrint, boolean setActivate) {
        this.setPrintable(setPrint);
        this.activatedTimer.setActivated(setActivate);
    }

    // GETTERS

    public Animator getCurrent() {
        return this.animators.get(this.current);
    }

    public int currentFrame() {
        try {
            return this.currentAnimation().getFrame();
        } catch (Exception e) {
            return 0;
        }
    }

    public Animation currentAnimation() {
        try {
            return this.getCurrent().getAnimation(this.index);
        } catch (Exception e) {
            return null;
        }
    }

    public BodyAnimation currentBodyAnimation() {
        try {
            return this.getCurrent().getBodyAnimation(this.index);
        } catch (Exception e) {
            return null;
        }
    }

    public BodySprite currentBodySprite() {
        try {
            return this.currentBodyAnimation().getCurrentBody(this.currentFrame());
        } catch (Exception e) {
            return null;
        }
    }

    public Pair<Float, Float> currentSizeAnimation() {
        return new Pair<>((float) this.currentAnimation().getWidth(), (float) this.currentAnimation().getHeight());
    }

    public boolean isPrintable() {
        return this.printable;
    }

    public boolean isDeleted() {
        if (this.current == EnumAnimation.EXPLODE && this.currentAnimation().isStopped()) {
            this.deleted = true;
        }
        return this.deleted;
    }

    public int getCurrentFrame() {
        try {
            return this.currentAnimation().getFrame();
        } catch (Exception e) {
            return -1;
        }
    }

    public EnumAnimation getCurrentAnimation() {
        return this.current;
    }

    public int getIndex() {
        return this.index;
    }

    public Color getFilter() {
        return this.getCurrent().getFilter();
    }

    public Animation getAnimation(EnumAnimation type, int index) {
        if (this.animators.containsKey(type)) {
            return this.animators.get(type).getAnimation(index);
        }
        return null;
    }

    public boolean needUpdate() {
        return this.needUpdate;
    }

    public boolean isActivated() {
        return this.activatedTimer.isActivated();
    }

    public EnumDirection getDirection() {
        return this.direction;
    }

    public boolean canSwitchCurrent() {
        return this.currentAnimation().isStopped() || EnumAnimation.checkLoop(this.current);
    }

    // SETTERS

    public void setIndex(int value) {
        if (this.getCurrent().canSetIndex(value)) {
            this.index = value;
        }
    }

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }

    public void setCurrent(EnumAnimation current) {
        if (this.animators.containsKey(current)) {
            if (this.currentAnimation().isStopped() || EnumAnimation.checkLoop(this.current)) {
                this.current = current;
                this.setIndex(0);
                this.getCurrent().restart();
            }
        } else if (current == EnumAnimation.EXPLODE) {
            this.setDeleted(true);
        }
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setNeedUpdate(boolean value) {
        this.needUpdate = value;
    }

    public void setDirection(EnumDirection direction) {
        this.direction = direction;
    }
}

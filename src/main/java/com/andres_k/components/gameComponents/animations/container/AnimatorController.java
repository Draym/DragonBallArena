package com.andres_k.components.gameComponents.animations.container;

import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.bodies.BodyAnimation;
import com.andres_k.components.gameComponents.bodies.BodySprite;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EnumDirection;
import com.andres_k.components.graphicComponents.userInterface.items.tools.ActivatedTimer;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
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
    private HashMap<EnumAnimation, AnimatorContainer> animators;
    private ActivatedTimer activatedTimer;
    private EnumDirection eyesDirection;

    private EnumAnimation current;
    private Pair<EnumAnimation, Integer> nextRequiredAnimation;

    private boolean printable;
    private boolean deleted;
    private boolean needUpdate;

    public AnimatorController() {
        this.animators = new HashMap<>();
        this.current = EnumAnimation.IDLE;

        this.printable = true;
        this.deleted = false;
        this.needUpdate = false;
        this.activatedTimer = new ActivatedTimer(true);
        this.eyesDirection = EnumDirection.RIGHT;
        this.nextRequiredAnimation = new Pair<>(EnumAnimation.NULL, 0);
    }

    public AnimatorController(AnimatorController animatorController) throws SlickException {
        if (animatorController == null) {
            throw new SlickException("image not loaded");
        }
        this.animators = new HashMap<>();
        for (Map.Entry<EnumAnimation, AnimatorContainer> entry : animatorController.animators.entrySet()) {
            this.animators.put(entry.getKey(), new AnimatorContainer(entry.getValue()));
        }
        this.current = animatorController.current;
        this.printable = animatorController.printable;
        this.deleted = animatorController.deleted;
        this.needUpdate = animatorController.needUpdate;
        this.activatedTimer = new ActivatedTimer(animatorController.activatedTimer);
        this.eyesDirection = animatorController.eyesDirection;
        this.nextRequiredAnimation = new Pair<>(animatorController.nextRequiredAnimation);
    }

    // UPDATE
    public void update() {
    }

    private void resetNextRequiredAnimation() {
        this.nextRequiredAnimation.setV1(EnumAnimation.NULL);
        this.nextRequiredAnimation.setV2(0);
    }

    private void toNextCurrentAnimation() {
        try {
            Pair<EnumAnimation, Integer> next = this.getCurrentContainer()
                    .getConfig()
                    .getNext();
            this.setCurrentAnimationType(next.getV1());
            this.setCurrentAnimationIndex(next.getV2());
        } catch (Exception e) {
            Console.err("AnimatorController", "toNextCurrentAnimation", e.getMessage());
        }
    }

    private void toNextRequiredAnimation() {
        try {
            this.setCurrentAnimationType(this.nextRequiredAnimation.getV1());
            this.setCurrentAnimationIndex(this.nextRequiredAnimation.getV2());
            this.resetNextRequiredAnimation();
        } catch (Exception e) {
            Console.err("AnimatorController", "toNextRequiredAnimation", e.getMessage());
        }
    }

    public void toNextAnimation() {
        if (this.nextRequiredAnimation.getV1() != EnumAnimation.NULL) {
            this.toNextRequiredAnimation();
        } else {
            this.toNextCurrentAnimation();
        }
    }

    public void doCurrentAction(GameObject object) {
        this.getCurrentContainer().doAction(object, this.currentFrame());
        if (this.currentAnimation().isStopped()) {
            this.toNextAnimation();
            if (this.current == EnumAnimation.FALL) {
                object.getMovement().resetGravity();
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof EnumAnimation) {
            this.setCurrentAnimationType((EnumAnimation) arg);
        } else if (arg instanceof Integer) {
            this.setCurrentAnimationIndex((Integer) arg);
        }
    }

    // ADD FUNCTIONS
    public void addCopyAnimator(EnumAnimation type, AnimatorContainer animatorContainer) {
        this.animators.put(type, new AnimatorContainer(animatorContainer));
    }

    public void addAnimator(EnumAnimation type, AnimatorContainer animatorContainer) {
        this.animators.put(type, animatorContainer);
    }

    public void addAnimation(EnumAnimation type, int index, Animation animation) {
        if (!this.animators.containsKey(type)) {
            this.animators.put(type, new AnimatorContainer());
        }
        this.animators.get(type).addAnimation(animation, index);
    }

    public void addCollision(EnumAnimation type, int index, String jsonValue) throws JSONException {
        if (!this.animators.containsKey(type)) {
            this.animators.put(type, new AnimatorContainer());
        }
        this.animators.get(type).addCollision(new BodyAnimation(jsonValue), index);
    }

    public void addConfig(EnumAnimation type, int index, AnimatorConfig config) {
        if (!this.animators.containsKey(type)) {
            this.animators.put(type, new AnimatorContainer());
        }
        this.animators.get(type).addConfig(config, index);
    }

    // METHODS
    public void restart() {
        for (Map.Entry<EnumAnimation, AnimatorContainer> entry : this.animators.entrySet()) {
            entry.getValue().restart();
        }

        this.setCurrentAnimationType(EnumAnimation.IDLE);
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

    public AnimatorContainer getCurrentContainer() {
        return this.animators.get(this.current);
    }

    public int currentFrame() {
        try {
            return this.currentAnimation().getFrame();
        } catch (Exception e) {
            return 0;
        }
    }

    public Animator currentAnimator() {
        try {
            return this.getCurrentContainer().getCurrentAnimator();
        } catch (Exception e) {
            return null;
        }
    }

    public Animation currentAnimation() {
        try {
            return this.getCurrentContainer().getCurrentAnimation();
        } catch (Exception e) {
            return null;
        }
    }

    public BodyAnimation currentBodyAnimation() {
        try {
            return this.getCurrentContainer().getBodyAnimation();
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

    public EnumAnimation currentAnimationType() {
        return this.current;
    }

    public int getIndex() {
        return this.getCurrentContainer().getIndex();
    }

    public Color getFilter() {
        return this.getCurrentContainer().getFilter();
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

    public EnumDirection getEyesDirection() {
        return this.eyesDirection;
    }

    public boolean canSwitchCurrent() {
        return this.currentAnimation().isStopped() || EnumAnimation.checkLoop(this.current);
    }

    // SETTERS

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }

    public void setCurrentAnimationIndex(int value) {
        Console.write("index -> " + value);
        this.animators.get(this.current).setIndex(value);
    }

    public void setCurrentAnimationType(EnumAnimation current) {
        if (this.animators.containsKey(current)) {
            Console.write("anim -> " + current);
            this.current = current;
            this.getCurrentContainer().restart();
        } else if (current == EnumAnimation.EXPLODE) {
            this.setDeleted(true);
        }
    }

    private void setNextRequiredAnimation(EnumAnimation type, int index) {
        this.nextRequiredAnimation.setV1(type);
        this.nextRequiredAnimation.setV2(index);
    }

    private void setCurrentAnimation(EnumAnimation type, int index) {
        this.setCurrentAnimationType(type);
        this.setCurrentAnimationIndex(index);
    }

    public void changeAnimation(EnumAnimation type) {
        if (this.canSwitchCurrent()) {
            this.setCurrentAnimation(type, this.getIndex());
        } else {
            this.setNextRequiredAnimation(type, this.getIndex());
        }
    }

    public void changeAnimation(EnumAnimation type, int index) {
        if (!(type == EnumAnimation.NULL || index < 0)) {
            if (this.canSwitchCurrent()) {
                this.setCurrentAnimation(type, index);
            } else {
                this.setNextRequiredAnimation(type, index);
            }
        }
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setNeedUpdate(boolean value) {
        this.needUpdate = value;
    }

    public void setEyesDirection(EnumDirection direction) {
        this.eyesDirection = direction;
    }
}

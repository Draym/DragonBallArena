package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.gameComponents.animations.details.AnimationConfigItem;
import com.andres_k.components.gameComponents.animations.details.AnimationRepercussionItem;
import com.andres_k.components.gameComponents.bodies.BodyAnimation;
import com.andres_k.components.gameComponents.bodies.BodySprite;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ActivatedTimer;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
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
    private HashMap<EAnimation, AnimatorContainer> animators;
    private ActivatedTimer activatedTimer;
    private EDirection eyesDirection;
    private float rotateAngle;

    private EAnimation current;
    private Pair<EAnimation, Integer> nextRequiredAnimation;

    private boolean printable;
    private boolean deleted;
    private boolean needUpdate;

    private boolean useCameraMove;

    private String ownerId;

    public AnimatorController() {
        this.animators = new HashMap<>();
        this.current = EAnimation.IDLE;

        this.printable = true;
        this.deleted = false;
        this.needUpdate = false;
        this.activatedTimer = new ActivatedTimer(true);
        this.eyesDirection = EDirection.RIGHT;
        this.nextRequiredAnimation = new Pair<>(EAnimation.NULL, 0);
        this.ownerId = "unknown";
        this.rotateAngle = 0;
        this.useCameraMove = true;
    }

    public AnimatorController(AnimatorController animatorController) throws SlickException {
        if (animatorController == null) {
            throw new SlickException("image not loaded");
        }
        this.animators = new HashMap<>();
        for (Map.Entry<EAnimation, AnimatorContainer> entry : animatorController.animators.entrySet()) {
            this.animators.put(entry.getKey(), new AnimatorContainer(entry.getValue()));
        }
        this.current = animatorController.current;
        this.printable = animatorController.printable;
        this.deleted = animatorController.deleted;
        this.needUpdate = animatorController.needUpdate;
        this.activatedTimer = new ActivatedTimer(animatorController.activatedTimer);
        this.eyesDirection = animatorController.eyesDirection;
        this.nextRequiredAnimation = new Pair<>(animatorController.nextRequiredAnimation);
        this.ownerId = animatorController.ownerId;
        this.rotateAngle = animatorController.rotateAngle;
        this.useCameraMove = animatorController.useCameraMove;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof EAnimation) {
            this.forceCurrentAnimationType((EAnimation) arg);
        } else if (arg instanceof Integer) {
            this.forceCurrentAnimationIndex((Integer) arg);
        }
    }

    // DRAW
    public void drawSubImage(Graphics g, float drawX, float drawY, int posX, int posY, int width, int height) throws SlickException {
        if (this.isPrintable() && !this.isDeleted()) {
            this.currentAnimator().drawSubImage(g, drawX, drawY, posX, posY, width, height, this.rotateAngle, this.getEyesDirection().isHorizontalFlip(), false, this.useCameraMove);
        }
    }

    public void draw(Graphics g, float x, float y) throws SlickException {
        if (this.isPrintable() && !this.isDeleted()) {
            this.currentAnimator().draw(g, x, y, this.rotateAngle, this.getEyesDirection().isHorizontalFlip(), false, this.useCameraMove);
        }
    }

    // UPDATE
    public void update() {
        if (this.animators.size() != 0) {
            try {
                this.currentAnimator().update(GameConfig.currentTimeLoop);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateAnimation(GameObject object) throws SlickException {
        this.getCurrentContainer().doAction(object, this.currentFrame());
        if (updateAnimation()) {
            if (this.current == EAnimation.FALL) {
                object.getMovement().resetGravity();
            }
        }
    }

    public boolean updateAnimation() {
        try {
            if (this.currentAnimation().isStopped()) {
                this.toNextAnimation();
                return true;
            }
        } catch (SlickException ignored) {
        }
        return false;
    }

    // TOOLS
    private void resetNextRequiredAnimation() {
        this.nextRequiredAnimation.setV1(EAnimation.NULL);
        this.nextRequiredAnimation.setV2(0);
    }

    private void toNextCurrentAnimation() {
        if (this.getCurrentContainer().getConfig() != null) {
            Pair<EAnimation, Integer> next = this.getCurrentContainer().getConfig().getNext();
            if ((this.currentAnimationType() == EAnimation.EXPLODE && next.getV1() == EAnimation.EXPLODE)
                    || this.currentAnimationType() != EAnimation.EXPLODE) {
                this.forceCurrentAnimationType(next.getV1());
                this.forceCurrentAnimationIndex(next.getV2());
            }
        }
    }

    private void toNextRequiredAnimation() {
        if (this.currentAnimationType() != EAnimation.EXPLODE) {
            this.forceCurrentAnimationType(this.nextRequiredAnimation.getV1());
            this.forceCurrentAnimationIndex(this.nextRequiredAnimation.getV2());
            this.resetNextRequiredAnimation();
        }
    }

    public void toNextAnimation() {
        if (this.nextRequiredAnimation.getV1() != EAnimation.NULL) {
            this.toNextRequiredAnimation();
        } else {
            toNextCurrentAnimation();
        }
    }

    // ADD FUNCTIONS
    public void addCopyAnimator(EAnimation type, AnimatorContainer animatorContainer) {
        this.animators.put(type, new AnimatorContainer(animatorContainer));
    }

    public void addAnimator(EAnimation type, AnimatorContainer animatorContainer) {
        this.animators.put(type, animatorContainer);
    }

    public void addAnimation(EAnimation type, int index, Animation animation) {
        if (!this.animators.containsKey(type)) {
            this.animators.put(type, new AnimatorContainer());
        }
        this.animators.get(type).addAnimation(animation, index);
    }

    public void addCollision(EAnimation type, int index, String jsonValue) throws JSONException {
        if (!this.animators.containsKey(type)) {
            this.animators.put(type, new AnimatorContainer());
        }
        this.animators.get(type).addCollision(new BodyAnimation(jsonValue), index);
    }

    public void addConfig(EAnimation type, int index, AnimationConfigItem config) {
        if (!this.animators.containsKey(type)) {
            this.animators.put(type, new AnimatorContainer());
        }
        this.animators.get(type).addConfig(config, index);
    }

    public void addRepercussion(EAnimation type, int index, AnimationRepercussionItem config) {
        if (!this.animators.containsKey(type)) {
            this.animators.put(type, new AnimatorContainer());
        }
        this.animators.get(type).addRepercussion(config, index);
    }

    // METHODS

    public void restart() {
        for (Map.Entry<EAnimation, AnimatorContainer> entry : this.animators.entrySet()) {
            entry.getValue().restart();
        }

        this.forceCurrentAnimationType(EAnimation.IDLE);
        this.printable = true;
        this.deleted = false;
    }

    public void restartAnimation(EAnimation animation) {
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
    public void addEffect(EAnimation type, int index, int frame, Effect effect) {
        this.addEffect(type, index, frame, effect, 0);
    }

    public void addEffect(EAnimation type, int index, int frame, Effect effect, int priority) {
        try {
            this.getAnimator(type, index).addEffect(frame, effect, priority);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playEffect(Effect effect, int priority) {
        try {
            this.currentAnimator().playEffect(priority, effect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasEffectActivity() {
        try {
            return this.currentAnimator().hasEffectActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean effectIsRunning(String id) {
        try {
            return this.currentAnimator().effectIsRunning(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean effectIsActive(EffectType type) {
        try {
            return this.currentAnimator().effectIsActive(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    public Animator currentAnimator() throws SlickException {
        try {
            return this.getCurrentContainer().getCurrentAnimator();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public Animation currentAnimation() throws SlickException {
        try {
            return this.getCurrentContainer().getCurrentAnimation();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    public BodyAnimation currentBodyAnimation() throws SlickException {
        try {
            return this.getCurrentContainer().getBodyAnimation();
        } catch (Exception e) {
            throw new NullPointerException(e.getMessage());
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
        try {
            return new Pair<>((float) this.currentAnimation().getWidth(), (float) this.currentAnimation().getHeight());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Pair<>(0f, 0f);
    }

    public boolean hasAnimation(EAnimation animation) {
        return this.animators.containsKey(animation);
    }

    public boolean hasNextRequired() {
        return this.nextRequiredAnimation.getV1() != EAnimation.NULL;
    }

    public boolean isPrintable() {
        return this.printable;
    }

    public boolean isDeleted() {
        try {
            if (this.current == EAnimation.EXPLODE && this.currentAnimation().isStopped()) {
                if (this.getCurrentContainer().getConfig() != null) {
                    if (this.getCurrentContainer().getConfig().getNext().getV1() == EAnimation.NULL) {
                        this.deleted = true;
                    }
                } else {
                    this.deleted = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.deleted;
    }

    public EAnimation currentAnimationType() {
        return this.current;
    }

    public int getIndex() {
        return this.getCurrentContainer().getIndex();
    }

    public Color getFilter() {
        return this.getCurrentContainer().getFilter();
    }

    public float getRotateAngle() {
        return this.rotateAngle;
    }

    public boolean isUseCameraMove() {
        return this.useCameraMove;
    }

    public Animation getAnimation(EAnimation type, int index) {
        if (this.animators.containsKey(type)) {
            try {
                return this.animators.get(type).getAnimation(index);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Animator getAnimator(EAnimation type, int index) {
        if (this.animators.containsKey(type)) {
            try {
                return this.animators.get(type).getAnimator(index);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean needUpdate() {
        return this.needUpdate;
    }

    public boolean timerIsActivated() {
        return this.activatedTimer.isActivated();
    }

    public boolean timerIsRunning() {
        return this.activatedTimer.isRunning();
    }

    public EDirection getEyesDirection() {
        return this.eyesDirection;
    }

    public boolean canSwitchCurrent() {
        try {
            return (this.currentAnimation().isStopped() || EAnimation.checkLoop(this.current));
        } catch (SlickException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean animationHasNextAnimation() {
        Pair<EAnimation, Integer> next = this.getCurrentContainer().getNext();
        return next != null && next.getV1() != EAnimation.IDLE;
    }

    // SETTERS

    public void setOwnerId(String id) {
        this.ownerId = id;
    }

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }

    public boolean forceCurrentAnimation(EAnimation type, int index) {
        return this.forceCurrentAnimationType(type) && this.forceCurrentAnimationIndex(index);
    }

    public boolean forceCurrentAnimationIndex(int value) {
        // Console.write("index -> " + value);
        return this.animators.get(this.current).setIndex(value);
    }

    public boolean forceCurrentAnimationType(EAnimation current) {
        if (this.currentAnimationType() != EAnimation.EXPLODE) {
            if (this.animators.containsKey(current)) {
                this.current = current;
                this.getCurrentContainer().restart();
                return true;
            } else if (current == EAnimation.EXPLODE) {
                this.setDeleted(true);
                return true;
            }
        }
        return false;
    }

    public boolean forceNextFrame() {
        try {
            this.currentAnimator().nextFrame();
            return true;
        } catch (SlickException e) {
            return false;
        }
    }

    private void setNextRequiredAnimation(EAnimation type, int index) {
        this.nextRequiredAnimation.setV1(type);
        this.nextRequiredAnimation.setV2(index);
    }

    public void changeAnimation(EAnimation type) throws SlickException {
        if (type != EAnimation.NULL) {
            if (this.canSwitchCurrent()) {
                this.forceCurrentAnimation(type, 0);
            } else {
                this.setNextRequiredAnimation(type, 0);
            }
        }
    }

    public void changeAnimation(EAnimation type, int index) throws SlickException {
        if (!(type == EAnimation.NULL || index < 0)) {
            if (this.canSwitchCurrent()) {
                this.forceCurrentAnimation(type, index);
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

    public void setEyesDirection(EDirection direction) {
        this.eyesDirection = direction;
    }

    public void setRotateAngle(float value) {
        this.rotateAngle = value;
    }

    public void setUseCameraMove(boolean value) {
        this.useCameraMove = value;
    }
}

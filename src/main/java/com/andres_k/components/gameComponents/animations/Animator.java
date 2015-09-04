package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.gameComponents.collisions.BodyAnimation;
import com.andres_k.components.gameComponents.collisions.BodyRect;
import com.andres_k.components.gameComponents.collisions.BodySprite;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ActivatedTimer;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.util.*;

/**
 * Created by andres_k on 13/03/2015.
 */
public class Animator implements Observer {

    // collisions
    private HashMap<EnumAnimation, List<BodyAnimation>> collisions;

    // animations
    private HashMap<EnumAnimation, List<Animation>> animations;
    private ActivatedTimer activatedTimer;
    private EnumAnimation current;
    private Color filter;

    private int index;
    private boolean printable;
    private boolean deleted;
    private boolean needUpdate;

    public Animator() {
        this.animations = new HashMap<>();
        this.collisions = new HashMap<>();
        this.current = EnumAnimation.BASIC;
        this.printable = true;
        this.deleted = false;
        this.needUpdate = false;
        this.index = 0;
        this.filter = new Color(1f, 1f, 1f);
        this.activatedTimer = new ActivatedTimer(true);
    }

    public Animator(Animator animator) throws SlickException {
        this.animations = new HashMap<>();
        if (animator == null){
            throw new SlickException("image not loaded");
        }
        for (Map.Entry<EnumAnimation, List<Animation>> entry : animator.animations.entrySet()) {
            EnumAnimation type = entry.getKey();
            List<Animation> values = entry.getValue();
            List<Animation> newValues = new ArrayList<>();
            for (Animation value : values) {
                newValues.add(value.copy());
            }
            this.addListAnimation(type, newValues);
        }

        this.collisions = new HashMap<>();
        this.collisions.putAll(animator.collisions);

        this.current = animator.current;
        this.index = animator.index;
        this.printable = animator.printable;
        this.deleted = animator.deleted;
        this.needUpdate = animator.needUpdate;
        this.filter = animator.filter;
        this.activatedTimer = new ActivatedTimer(animator.activatedTimer);
    }

    // FUNCTIONS
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof EnumAnimation) {
            this.setCurrent((EnumAnimation) arg);
        }
    }

    public void addAnimation(EnumAnimation type, Animation animation) {
        if (animation.getFrameCount() == 1) {
            animation.setAutoUpdate(false);
            animation.setLooping(false);
        }

        if (this.animations.containsKey(type)) {
            this.animations.get(type).add(animation.copy());
        } else {
            List<Animation> values = new ArrayList<>();
            values.add(animation.copy());
            this.animations.put(type, values);
        }
    }

    public void addListAnimation(EnumAnimation type, List<Animation> animation) {
        for (Animation anAnimation : animation) {
            this.addAnimation(type, anAnimation);
        }
    }

    public void addCollision(EnumAnimation type, String jsonValue) throws JSONException {
        if (this.collisions.containsKey(type)) {
            this.collisions.get(type).add(new BodyAnimation(jsonValue));
        } else {
            List<BodyAnimation> values = new ArrayList<>();
            values.add(new BodyAnimation(jsonValue));
            this.collisions.put(type, values);
        }
    }

    public void nextCurrentIndex() {
        if (this.canSetIndex(this.index + 1)){
            this.setIndex(this.index + 1);
        }
    }

    public void restart() {
        for (Map.Entry<EnumAnimation, List<Animation>> entry : this.animations.entrySet()) {
            entry.getValue().forEach(org.newdawn.slick.Animation::restart);
        }
        this.setCurrent(EnumAnimation.BASIC);
        this.printable = true;
        this.deleted = false;
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

    public int currentFrame(){
        return this.currentAnimation().getFrame();
    }

    public Animation currentAnimation() {
        return this.animations.get(this.current).get(this.index);
    }

    public List<BodyRect> currentCollisions() {
        int currentFrame = this.currentFrame();
        if (this.collisions.containsKey(this.current)) {
            return this.collisions.get(this.current).get(this.index).getCurrentCollisions(currentFrame);
        }
        return null;
    }

    public BodyAnimation currentBodyAnimation() {
        if (this.collisions.containsKey(this.current)) {
            return this.collisions.get(this.current).get(this.index);
        }
        return null;
    }

    public BodySprite currentBodySprite() {
        if (this.collisions.containsKey(this.current)) {
            return currentBodyAnimation().getCurrentBody(this.currentFrame());
        }
        return null;
    }

    public Pair<Float, Float> currentSizeAnimation() {
        return new Pair<>((float) this.animations.get(this.current).get(this.index).getWidth(), (float) this.animations.get(this.current).get(this.index).getHeight());
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

    public EnumAnimation getCurrentAnimation() {
        return this.current;
    }

    public int getIndex() {
        return this.index;
    }

    public Color getFilter() {
        return this.filter;
    }

    public Animation getAnimation(EnumAnimation type, int index) {
        if (this.animations.containsKey(type)) {
            if (index >= 0 && index < this.animations.get(type).size()) {
                return this.animations.get(type).get(index);
            }
        }
        return null;
    }

    public boolean canSetIndex(int index){
        if (index < this.animations.get(this.current).size()) {
            return true;
        }
        return false;
    }

    public boolean needUpdate() {
        return this.needUpdate;
    }

    public boolean isActivated() {
        return this.activatedTimer.isActivated();
    }

    // SETTERS

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }

    public void setCurrent(EnumAnimation current) {
        if (this.animations.containsKey(current)) {
            this.current = current;
            this.index = 0;
        } else if (current == EnumAnimation.EXPLODE) {
            this.setDeleted(true);
        }
    }

    public void setFilter(Color filter) {
        this.filter = filter;
    }

    public void setIndex(int index) {
        if (index < this.animations.get(this.current).size()) {
            this.index = index;
        }
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setNeedUpdate(boolean value) {
        this.needUpdate = value;
    }
}

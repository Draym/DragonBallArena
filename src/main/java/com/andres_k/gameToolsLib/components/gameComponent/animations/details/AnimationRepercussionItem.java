package com.andres_k.gameToolsLib.components.gameComponent.animations.details;

import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.stockage.Tuple;

/**
 * Created by com.andres_k on 10/03/2016.
 */
public class AnimationRepercussionItem {
    private final EAnimation targetType;
    private final int targetIndex;
    private final Pair<Float, Float> recoil;
    private final boolean itsAdditionalMove;
    private final float damageToTheTarget;
    private final float gainLife;
    private final float lostKi;
    private final float lostEnergy;
    private final float gainKi;
    private final float gainEnergy;

    public AnimationRepercussionItem(float damageToTheTarget) {
        this(EAnimation.NULL, 0, damageToTheTarget, new Pair<>(0f, 0f), false);
    }

    public AnimationRepercussionItem(EAnimation targetType, float damageToTheTarget, Pair<Float, Float> recoil) {
        this(targetType, 0, damageToTheTarget, recoil, false);
    }

    public AnimationRepercussionItem(EAnimation targetType, float damageToTheTarget, Pair<Float, Float> recoil, boolean itsAdditionalMove) {
        this(targetType, 0, damageToTheTarget, recoil, itsAdditionalMove);
    }

    public AnimationRepercussionItem(EAnimation targetType, int targetIndex, float damageToTheTarget, Pair<Float, Float> recoil, boolean itsAdditionalMove) {
        this(targetType, targetIndex, damageToTheTarget, 0, 0, 0, 0, 0, recoil, itsAdditionalMove);
    }

    public AnimationRepercussionItem(EAnimation targetType, int targetIndex, float damageToTheTarget, float lostKi, float lostEnergy, Pair<Float, Float> recoil, boolean itsAdditionalMove) {
        this(targetType, targetIndex, damageToTheTarget, 0, lostKi, 0, lostEnergy, 0, recoil, itsAdditionalMove);
    }

    public AnimationRepercussionItem(EAnimation targetType, int targetIndex, float damageToTheTarget, float gainLife, float lostKi, float gainKi, float lostEnergy, float gainEnergy, Pair<Float, Float> recoil, boolean itsAdditionalMove) {
        this.targetType = targetType;
        this.targetIndex = targetIndex;
        this.recoil = recoil;
        this.itsAdditionalMove = itsAdditionalMove;
        this.damageToTheTarget = damageToTheTarget;
        this.gainLife = gainLife;
        this.lostKi = lostKi;
        this.gainKi = gainKi;
        this.lostEnergy = lostEnergy;
        this.gainEnergy = gainEnergy;
    }

    public void restart() {
    }

    public void applyMoveRepercussion(GameObject target) {
        target.getMovement().setMoveDirection(target.getLastAttacker().getAnimatorController().getEyesDirection());
        target.getMovement().setPushX(this.getRecoil().getV1());
        target.getMovement().setPushY(this.getRecoil().getV2());
        target.getMovement().addPushX(this.getAdditionalMove().getV1());
        target.getMovement().addPushY(this.getAdditionalMove().getV2());
    }

    public void applyEffectRepercussion(GameObject me, GameObject target) {
        me.incrementCurrentLife(this.gainLife);
        me.doTask(new Tuple<>(ETaskType.ADD, "ki", this.gainKi));
        me.doTask(new Tuple<>(ETaskType.ADD, "energy", this.gainEnergy));
        target.doTask(new Tuple<>(ETaskType.ADD, "ki", -this.lostKi));
        target.doTask(new Tuple<>(ETaskType.ADD, "energy", -this.lostEnergy));
    }

    public void applyAnimationRepercussion(GameObject target) {
        if (target.getAnimatorController().currentAnimationType() != this.getTargetType()) {
            target.getAnimatorController().forceCurrentAnimation(this.getTargetType(), this.getTargetIndex());
        }
    }

    // GETTERS
    public EAnimation getTargetType() {
        return this.targetType;
    }

    public int getTargetIndex() {
        return this.targetIndex;
    }

    public float getDamageToTheTarget() {
        return this.damageToTheTarget;
    }

    public Pair<Float, Float> getRecoil() {
        return (this.itsAdditionalMove ? new Pair<>(0f, 0f) : this.recoil);
    }

    public Pair<Float, Float> getAdditionalMove() {
        return (this.itsAdditionalMove ? this.recoil : new Pair<>(0f, 0f));
    }
}

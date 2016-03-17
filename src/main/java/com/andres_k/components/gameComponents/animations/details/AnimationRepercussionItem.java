package com.andres_k.components.gameComponents.animations.details;

import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;

/**
 * Created by andres_k on 10/03/2016.
 */
public class AnimationRepercussionItem {
    private final EAnimation targetType;
    private final int targetIndex;
    private final Pair<Float, Float> recoil;
    private final boolean itsAdditionalMove;
    private final float damageToTheTarget;
    private final int gainLife;
    private final int lostKi;
    private final int lostEnergy;
    private final int gainKi;
    private final int gainEnergy;

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

    public AnimationRepercussionItem(EAnimation targetType, int targetIndex, float damageToTheTarget, int lostKi, int lostEnergy, Pair<Float, Float> recoil, boolean itsAdditionalMove) {
        this(targetType, targetIndex, damageToTheTarget, 0, lostKi, 0, lostEnergy, 0, recoil, itsAdditionalMove);
    }

    public AnimationRepercussionItem(EAnimation targetType, int targetIndex, float damageToTheTarget, int gainLife, int lostKi, int gainKi, int lostEnergy, int gainEnergy, Pair<Float, Float> recoil, boolean itsAdditionalMove) {
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
        target.getMovement().setMoveDirection((target.getDirectionOfMyAttacker() == EDirection.RIGHT ? EDirection.LEFT : EDirection.RIGHT));
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
            target.getAnimatorController().forceCurrentAnimationType(this.getTargetType());
            target.getAnimatorController().forceCurrentAnimationIndex(this.getTargetIndex());
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

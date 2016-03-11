package com.andres_k.components.gameComponents.animations.details;

import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.utils.stockage.Pair;

/**
 * Created by andres_k on 10/03/2016.
 */
public class AnimationRepercussionItem {
    private final EAnimation targetType;
    private final int targetIndex;
    private final float damageToTheTarget;
    private final Pair<Float, Float> recoil;
    private boolean itsAdditionalMove;

    public AnimationRepercussionItem(EAnimation targetType, float damageToTheTarget, Pair<Float, Float> recoil) {
        this(targetType, 0, damageToTheTarget, recoil, false);
    }

    public AnimationRepercussionItem(EAnimation targetType, float damageToTheTarget, Pair<Float, Float> recoil, boolean itsAdditionalMove) {
        this(targetType, 0, damageToTheTarget, recoil, itsAdditionalMove);
    }

    public AnimationRepercussionItem(EAnimation targetType, int targetIndex, float damageToTheTarget, Pair<Float, Float> recoil, boolean itsAdditionalMove) {
        this.targetType = targetType;
        this.targetIndex = targetIndex;
        this.damageToTheTarget = damageToTheTarget;
        this.recoil = recoil;
        this.itsAdditionalMove = itsAdditionalMove;
    }

    public void restart() {
    }

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

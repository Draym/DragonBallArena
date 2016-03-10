package com.andres_k.components.gameComponents.animations.details;

import com.andres_k.components.gameComponents.animations.EAnimation;

/**
 * Created by andres_k on 10/03/2016.
 */
public class AnimationRepercussionItem {
    private final EAnimation targetType;
    private final int targetIndex;
    private final float damageToTheTarget;


    public AnimationRepercussionItem(EAnimation targetType, float damageToTheTarget) {
        this(targetType, 0, damageToTheTarget);
    }

    public AnimationRepercussionItem(EAnimation targetType, int targetIndex, float damageToTheTarget) {
        this.targetType = targetType;
        this.targetIndex = targetIndex;
        this.damageToTheTarget = damageToTheTarget;
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
}

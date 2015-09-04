package com.andres_k.components.gameComponents.animations;

/**
 * Created by andres_k on 13/07/2015.
 */
public abstract class AnimatorData {
    protected boolean needInit = true;
    protected AnimatorFactory animatorFactory;

    public boolean isInit(){
        return this.needInit;
    }
}

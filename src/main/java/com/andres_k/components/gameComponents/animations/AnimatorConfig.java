package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.utils.stockage.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by andres_k on 17/11/2015.
 */
public class AnimatorConfig {
    private int start;

    private Method action;
    private EAnimation nextType;

    private int nextIndex;
    private int changedNextIndex;
    private boolean executeEveryTime;

    public AnimatorConfig(Method action, boolean executeEveryTime) {
        this(action, executeEveryTime, -1, EAnimation.IDLE, 0);
    }

    public AnimatorConfig(Method action, boolean executeEveryTime, EAnimation type) {
        this(action, executeEveryTime, -1, type, 0);
    }

    public AnimatorConfig(EAnimation type, int index) {
        this(null, false, -1, type, index);
    }

    public AnimatorConfig(Method action, boolean executeEveryTime, EAnimation type, int index) {
        this(action, executeEveryTime, -1, type, index);
    }

    public AnimatorConfig(Method action, boolean executeEveryTime, int start, EAnimation type, int index) {
        this.action = action;
        this.start = start;
        this.nextType = type;
        this.nextIndex = index;
        this.executeEveryTime = executeEveryTime;
        this.changedNextIndex = this.nextIndex;
    }

    // METHODS

    public void doAction(GameObject object, int index) {
        if ((this.start == -1 || this.start == index) && this.action != null) {
            try {
                this.action.invoke(this.action.getDeclaringClass(), object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void restart() {
        this.changedNextIndex = this.nextIndex;
    }

    // GETTERS

    public Pair<EAnimation, Integer> getNext() {
        return new Pair<>(this.nextType, this.changedNextIndex);
    }

    public int getStart() {
        return this.start;
    }

    public boolean canExecuteEveryTime() {
        return this.executeEveryTime;
    }

    // SETTERS

    public void setNextType(EAnimation type) {
        this.nextType = type;
    }

    public void setNextIndex(int index) {
        this.changedNextIndex = index;
    }
}

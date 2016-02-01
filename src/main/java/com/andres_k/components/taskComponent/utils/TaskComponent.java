package com.andres_k.components.taskComponent.utils;

import com.andres_k.components.taskComponent.EnumLocation;

/**
 * Created by andres_k on 01/02/2016.
 */
public class TaskComponent {
    private EnumLocation sender;
    private EnumLocation target;
    private Object task;

    public TaskComponent(EnumLocation sender, EnumLocation target, Object task) {
        this.sender = sender;
        this.target = target;
        this.task = task;
    }

    // GETTERS
    public EnumLocation getSender() {
        return this.sender;
    }

    public EnumLocation getTarget() {
        return this.target;
    }

    public Object getTask() {
        return this.task;
    }

    // SETTERS
    public void setSender(EnumLocation sender) {
        this.sender = sender;
    }

    public void setTarget(EnumLocation target) {
        this.target = target;
    }
}

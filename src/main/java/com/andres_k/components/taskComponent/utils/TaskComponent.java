package com.andres_k.components.taskComponent.utils;

import com.andres_k.components.taskComponent.ELocation;

/**
 * Created by andres_k on 01/02/2016.
 */
public class TaskComponent {
    private ELocation sender;
    private ELocation target;
    private Object task;

    public TaskComponent(ELocation sender, ELocation target, Object task) {
        this.sender = sender;
        this.target = target;
        this.task = task;
    }

    // GETTERS
    public ELocation getSender() {
        return this.sender;
    }

    public ELocation getTarget() {
        return this.target;
    }

    public Object getTask() {
        return this.task;
    }

    // SETTERS
    public void setSender(ELocation sender) {
        this.sender = sender;
    }

    public void setTarget(ELocation target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "{" + this.sender + "} -> {" + target + "} : " + task;
    }
}

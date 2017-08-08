package com.andres_k.gameToolsLib.components.taskComponent.utils;

import com.andres_k.custom.component.taskComponents.ELocation;

/**
 * Created by com.andres_k on 01/02/2016.
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
        return "{" + this.sender + "} -> {" + this.target + "} : " + this.task;
    }
}

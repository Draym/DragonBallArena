package com.andres_k.components.taskComponent;

import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.components.taskComponent.utils.TaskObservable;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

/**
 * Created by andres_k on 01/02/2016.
 */
public abstract class TaskManager {
    protected Map<String, TaskObservable> targets;
    protected EnumLocation location;

    protected TaskManager(EnumLocation location) {
        this.targets = new HashMap<>();
        this.location = location;
    }

    public EnumLocation getLocation() {
        return this.location;
    }

    public abstract void sendRequest(TaskComponent task);

    public abstract void register(String target, Observer observer);
}

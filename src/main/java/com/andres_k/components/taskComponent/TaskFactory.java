package com.andres_k.components.taskComponent;

import com.andres_k.components.taskComponent.utils.TaskComponent;

/**
 * Created by andres_k on 30/05/2015.
 */
public class TaskFactory {
    public static TaskComponent changeSender(EnumLocation sender, TaskComponent task){
        task.setSender(sender);
        return task;
    }

    public static TaskComponent changeTarget(EnumLocation target, TaskComponent task){
        task.setTarget(target);
        return task;
    }

    public static TaskComponent createTask(EnumLocation sender, EnumLocation target, Object task){
        return new TaskComponent(sender, target, task);
    }
}

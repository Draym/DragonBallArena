package com.andres_k.gameToolsLib.components.taskComponent;

import com.andres_k.gameToolsLib.components.taskComponent.utils.TaskComponent;
import com.andres_k.custom.component.taskComponents.ELocation;

/**
 * Created by com.andres_k on 30/05/2015.
 */
public class TaskFactory {
    public static TaskComponent changeSender(ELocation sender, TaskComponent task){
        task.setSender(sender);
        return task;
    }

    public static TaskComponent changeTarget(ELocation target, TaskComponent task){
        task.setTarget(target);
        return task;
    }

    public static TaskComponent createTask(ELocation sender, ELocation target, Object task){
        return new TaskComponent(sender, target, task);
    }

    public static TaskComponent createTask(String sender, String target, Object task){
        return new TaskComponent(ELocation.getEnumByLocation(sender), ELocation.getEnumByLocation(target), task);
    }
}

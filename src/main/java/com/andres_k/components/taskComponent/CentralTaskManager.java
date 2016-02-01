package com.andres_k.components.taskComponent;

import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.components.taskComponent.utils.TaskObservable;

import java.util.Observer;

/**
 * Created by andres_k on 01/02/2016.
 */
public class CentralTaskManager extends TaskManager {

    private CentralTaskManager() {
        super(EnumLocation.MASTER);
    }

    private static class SingletonHolder {
        private static CentralTaskManager instance = new CentralTaskManager();
    }

    public static CentralTaskManager get() {
        return SingletonHolder.instance;
    }

    public void register(String target, Observer observer) {
        if (!this.targets.containsKey(target)) {
            TaskObservable obs = new TaskObservable();
            obs.addObserver(observer);
            this.targets.put(target, obs);
        }
    }

    public void sendRequest(TaskComponent task) {
        if (this.targets.containsKey(task.getTarget().getLocation())) {
            this.targets.get(task.getTarget().getLocation()).notify(TaskFactory.changeSender(this.location, task));
        }
    }
}

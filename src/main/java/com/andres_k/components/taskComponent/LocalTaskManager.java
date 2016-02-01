package com.andres_k.components.taskComponent;

import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.components.taskComponent.utils.TaskObservable;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 01/02/2016.
 */
public class LocalTaskManager extends TaskManager implements Observer {

    public LocalTaskManager(EnumLocation location) {
        super(location);
        CentralTaskManager.get().register(this.location.getLocation(), this);
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
        } else {
            CentralTaskManager.get().sendRequest(task);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof TaskComponent) {
            this.sendRequest((TaskComponent) arg);
        }
    }
}

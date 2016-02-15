package com.andres_k.components.taskComponent;

import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.components.taskComponent.utils.TaskObservable;
import com.andres_k.utils.tools.Console;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 01/02/2016.
 */
public class LocalTaskManager implements Observer {

    protected Map<String, TaskObservable> targets;
    protected ELocation location;

    public LocalTaskManager(ELocation location) {
        this.targets = new HashMap<>();
        this.location = location;
    }

    public void register(String location, Observer observer) {
        if (!this.targets.containsKey(location)) {
            TaskObservable obs = new TaskObservable();
            obs.addObserver(observer);
            this.targets.put(location, obs);
        }
    }

    public void sendRequest(TaskComponent task) {
        final boolean[] send = {false};

        if (this.targets.containsKey(task.getTarget().getId())) {
            this.targets.get(task.getTarget().getId()).notify(TaskFactory.changeSender(this.location, task));
            send[0] = true;
        } else {
            this.targets.entrySet().forEach(entry -> {
                if (task.getTarget().getId().indexOf(entry.getKey()) == 0) {
                    entry.getValue().notify(TaskFactory.changeSender(this.location, task));
                    send[0] = true;
                }
            });
        }
        if (!send[0]) {
            CentralTaskManager.get().sendRequest(task);
        }
    }

    protected void relayRequest(TaskComponent task) {
        Console.debug("LocalRelay: " + task);
        if (this.targets.containsKey(task.getTarget().getId())) {
            this.targets.get(task.getTarget().getId()).notify(TaskFactory.changeSender(this.location, task));
        } else {
            this.targets.entrySet().forEach(entry -> {
                if (task.getTarget().getId().indexOf(entry.getKey()) == 0) {
                    entry.getValue().notify(TaskFactory.changeSender(this.location, task));
                }
            });
        }
    }

    public ELocation getLocation() {
        return this.location;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof TaskComponent) {
            this.relayRequest((TaskComponent) arg);
        }
    }
}

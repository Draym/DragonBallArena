package com.andres_k.gameToolsLib.components.taskComponent;

import com.andres_k.gameToolsLib.components.taskComponent.utils.TaskComponent;
import com.andres_k.gameToolsLib.components.taskComponent.utils.TaskObservable;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.gameToolsLib.utils.tools.Console;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;

/**
 * Created by com.andres_k on 01/02/2016.
 */
public class CentralTaskManager {

    protected Map<String, TaskObservable> targets;
    protected ELocation location;

    private CentralTaskManager() {
        this.targets = new HashMap<>();
        this.location = ELocation.MASTER;
    }

    private static class SingletonHolder {
        private static CentralTaskManager instance = new CentralTaskManager();
    }

    public static CentralTaskManager get() {
        return SingletonHolder.instance;
    }

    public void register(String location, Observer observer) {
        if (!this.targets.containsKey(location)) {
            TaskObservable obs = new TaskObservable();
            obs.addObserver(observer);
            this.targets.put(location, obs);
        }
    }

    public void sendRequest(TaskComponent task) {
        Console.write("CentralSend: " + task);
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
}

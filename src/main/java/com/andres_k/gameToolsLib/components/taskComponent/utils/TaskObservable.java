package com.andres_k.gameToolsLib.components.taskComponent.utils;

import java.util.Observable;

/**
 * Created by com.andres_k on 01/02/2016.
 */
public class TaskObservable extends Observable {

    public void notify(Object obj) {
        this.setChanged();
        this.notifyObservers(obj);
    }
}

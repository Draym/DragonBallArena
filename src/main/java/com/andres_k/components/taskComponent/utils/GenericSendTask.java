package com.andres_k.components.taskComponent.utils;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 30/05/2015.
 */
@Deprecated
public class GenericSendTask extends Observable implements Observer{

    public GenericSendTask(){

    }

    public void sendTask(Object task){
        this.setChanged();
        this.notifyObservers(task);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.setChanged();
        this.notifyObservers(arg);
    }
}

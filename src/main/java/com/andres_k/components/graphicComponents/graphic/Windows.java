package com.andres_k.components.graphicComponents.graphic;


import com.andres_k.components.graphicComponents.graphic.windowGame.WindowGame;
import com.andres_k.components.graphicComponents.graphic.windowInterface.WindowInterface;
import com.andres_k.components.taskComponent.EnumTargetTask;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.stockage.Tuple;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 17/03/2015.
 */
public class Windows extends StateBasedGame implements Observer {

    private GenericSendTask gameTask;
    private GenericSendTask interfaceTask;
    private GenericSendTask masterTask;

    private WindowBasedGame windowInterface;
    private WindowBasedGame windowGame;

    public Windows(String name, GenericSendTask masterTask) throws JSONException, SlickException {
        super(name);
        this.masterTask = masterTask;

        this.gameTask = new GenericSendTask();
        this.gameTask.addObserver(this);

        this.interfaceTask = new GenericSendTask();
        this.interfaceTask.addObserver(this);


        this.windowInterface = new WindowInterface(EnumWindow.INTERFACE.getValue(), this.interfaceTask);
        this.windowGame = new WindowGame(EnumWindow.GAME.getValue(), this.gameTask);
    }


    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(this.windowInterface);
        this.addState(this.windowGame);

        this.enterState(EnumWindow.INTERFACE.getValue());
    }

    public void update(Observable o, Object arg) {
        if (arg instanceof Tuple) {
            Tuple<EnumTargetTask, EnumTargetTask, Object> task = (Tuple<EnumTargetTask, EnumTargetTask, Object>) arg;

            if (!(task.getV1().equals(EnumTargetTask.WINDOWS))) {
                if (task.getV2().isIn(EnumTargetTask.WINDOWS)) {
                    this.doTask(o, task);
                } else {
                    this.masterTask.sendTask(task);
                }
            }
        }
    }

    public void doTask(Observable o, Object arg) {
        Tuple<EnumTargetTask, EnumTargetTask, Object> task = (Tuple<EnumTargetTask, EnumTargetTask, Object>) arg;

        if (task.getV2().isIn(EnumTargetTask.GAME)) {
            this.redirectGame(task);
            this.gameTask.sendTask(TaskFactory.createTask(EnumTargetTask.WINDOWS, task));
        } else if (task.getV2().isIn(EnumTargetTask.INTERFACE)) {
            this.interfaceTask.sendTask(TaskFactory.createTask(EnumTargetTask.WINDOWS, task));
        }
    }

    private void redirectGame(Tuple<EnumTargetTask, EnumTargetTask, Object> task) {
    }
}

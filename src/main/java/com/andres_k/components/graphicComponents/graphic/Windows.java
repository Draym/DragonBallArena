package com.andres_k.components.graphicComponents.graphic;


import com.andres_k.components.graphicComponents.graphic.windows.WindowGame;
import com.andres_k.components.graphicComponents.graphic.windows.WindowHome;
import com.andres_k.components.graphicComponents.graphic.windows.WindowLoad;
import com.andres_k.components.taskComponent.EnumTargetTask;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.stockage.Tuple;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 17/03/2015.
 */
public class Windows extends StateBasedGame implements Observer {

    private Map<EnumWindow, GenericSendTask> tasks;
    private GenericSendTask masterTask;

    private WindowBasedGame windowLoad;
    private WindowBasedGame windowHome;
    private WindowBasedGame windowGame;

    public Windows(String name, GenericSendTask masterTask) throws JSONException, SlickException {
        super(name);
        this.masterTask = masterTask;

        this.tasks = new HashMap<>();
        this.tasks.put(EnumWindow.GAME, new GenericSendTask());
        this.tasks.put(EnumWindow.HOME, new GenericSendTask());
        this.tasks.put(EnumWindow.LOAD, new GenericSendTask());

        this.tasks.get(EnumWindow.GAME).addObserver(this);
        this.tasks.get(EnumWindow.HOME).addObserver(this);
        this.tasks.get(EnumWindow.LOAD).addObserver(this);

        this.windowLoad = new WindowLoad(EnumWindow.LOAD.getValue(), this.tasks.get(EnumWindow.LOAD));
        this.windowHome = new WindowHome(EnumWindow.HOME.getValue(), this.tasks.get(EnumWindow.HOME));
        this.windowGame = new WindowGame(EnumWindow.GAME.getValue(), this.tasks.get(EnumWindow.GAME));
    }


    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(this.windowLoad);
        this.addState(this.windowHome);
        this.addState(this.windowGame);

        this.enterState(EnumWindow.LOAD.getValue());
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
            this.tasks.get(EnumWindow.GAME).sendTask(TaskFactory.createTask(EnumTargetTask.WINDOWS, task));
        } else if (task.getV2().isIn(EnumTargetTask.INTERFACE)) {
            this.tasks.get(EnumWindow.HOME).sendTask(TaskFactory.createTask(EnumTargetTask.WINDOWS, task));
        }
    }

    private void redirectGame(Tuple<EnumTargetTask, EnumTargetTask, Object> task) {
    }
}

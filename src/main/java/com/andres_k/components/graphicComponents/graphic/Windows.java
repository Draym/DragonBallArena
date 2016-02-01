package com.andres_k.components.graphicComponents.graphic;


import com.andres_k.components.graphicComponents.graphic.windows.WindowGame;
import com.andres_k.components.graphicComponents.graphic.windows.WindowHome;
import com.andres_k.components.graphicComponents.graphic.windows.WindowLoad;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.EnumLocation;
import com.andres_k.components.taskComponent.LocalTaskManager;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by andres_k on 17/03/2015.
 */
public class Windows extends StateBasedGame {

    private LocalTaskManager windowsTask;

    private WindowBasedGame windowLoad;
    private WindowBasedGame windowHome;
    private WindowBasedGame windowGame;

    public Windows(String name) throws JSONException, SlickException {
        super(name);

        this.windowsTask = new LocalTaskManager(EnumLocation.WINDOWS);
        CentralTaskManager.get().register(EnumLocation.WINDOWS.getLocation(), this.windowsTask);

        this.windowLoad = new WindowLoad(EnumWindow.LOAD.getValue(), this.windowsTask);
        this.windowHome = new WindowHome(EnumWindow.HOME.getValue(), this.windowsTask);
        this.windowGame = new WindowGame(EnumWindow.GAME.getValue(), this.windowsTask);
    }


    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(this.windowLoad);
        this.addState(this.windowHome);
        this.addState(this.windowGame);

        this.enterState(EnumWindow.LOAD.getValue());
    }
/*
    public void update(Observable o, Object arg) {
        if (arg instanceof TaskComponent) {
            TaskComponent task = (TaskComponent) arg;

            if (!(task.getSender().equals(EnumTargetTask.WINDOWS))) {
                if (task.getTarget().isIn(EnumTargetTask.WINDOWS)) {
                    this.doTask(o, task);
                } else {
                    CentralTaskManager.get().sendRequest(task);
                }
            }
        }
    }

    public void doTask(Observable o, Object arg) {
        if (arg instanceof TaskComponent) {
            TaskComponent task = (TaskComponent) arg;
            if (task.getTarget().isIn(EnumTargetTask.GAME_CONTROLLER)) {
                this.redirectGame(task);
                this.windowsTask.sendTask(TaskFactory.createTask(EnumTargetTask.WINDOWS, task));
            } else if (task.getTarget().isIn(EnumTargetTask.HOME_CONTROLLER)) {
                this.tasks.get(EnumWindow.HOME_CONTROLLER).sendTask(TaskFactory.createTask(EnumTargetTask.WINDOWS, task));
            } else if (task.getTarget().isIn(EnumTargetTask.LOAD_CONTROLLER)) {
                this.tasks.get(EnumWindow.LOAD_CONTROLLER).sendTask(TaskFactory.createTask(EnumTargetTask.WINDOWS, task));
            }
        }
    }

    private void redirectGame(TaskComponent task) {
    }
    */
}

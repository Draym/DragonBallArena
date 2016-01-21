package com.andres_k.master;


import com.andres_k.components.controllers.ScoreData;
import com.andres_k.components.graphicComponents.graphic.Windows;
import com.andres_k.components.eventComponent.input.InputData;
import com.andres_k.components.networkComponents.networkGame.NetworkController;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.soundComponents.SoundController;
import com.andres_k.components.taskComponent.EnumTargetTask;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.utils.configs.ConfigPath;
import com.andres_k.utils.configs.CurrentUser;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Tuple;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 10/03/2015.
 */
public class MasterGame implements Observer {
    private GenericSendTask masterTask;
    private NetworkController networkController;
    private Windows windows;

    public MasterGame() throws SlickException, JSONException {
        SoundController.init();
        MusicController.init();

        InputData.init(ConfigPath.input);
        ScoreData.init(ConfigPath.score);
        CurrentUser.init("player", "player", "ally");
        this.masterTask = new GenericSendTask();
        this.masterTask.addObserver(this);
        this.windows = new Windows("DragonBallArena", this.masterTask);
        this.networkController = new NetworkController(this.masterTask);
    }

    public void start() {
        try {
            this.startGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startGame() throws SlickException, JSONException {

        AppGameContainer appGame = new AppGameContainer(this.windows);
        appGame.setDisplayMode(WindowConfig.getWLowSizeX(), WindowConfig.getWLowSizeY(), false);
        GlobalVariable.appGameContainer = appGame;
        appGame.start();
    }


    @Override
    public void update(Observable o, Object arg) {
        Tuple<EnumTargetTask, EnumTargetTask, Object> task = (Tuple<EnumTargetTask, EnumTargetTask, Object>) arg;

        //Debug.debug("masterTask " + task);
        if (task.getV2().isIn(EnumTargetTask.WINDOWS)) {
            this.windows.doTask(o, task);
        } else if (task.getV2().isIn(EnumTargetTask.SERVER)) {
            this.networkController.doTask(o, task);
        }
    }
}

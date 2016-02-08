package com.andres_k.master;


import com.andres_k.components.controllers.ScoreData;
import com.andres_k.components.eventComponent.input.InputData;
import com.andres_k.components.graphicComponents.graphic.Windows;
import com.andres_k.components.networkComponents.networkGame.NetworkController;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.soundComponents.SoundController;
import com.andres_k.utils.configs.ConfigPath;
import com.andres_k.utils.configs.CurrentUser;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 10/03/2015.
 */
public class MasterGame {
    private NetworkController networkController;
    private Windows windows;

    public MasterGame() throws SlickException, JSONException {
        SoundController.init();
        MusicController.init();

        InputData.init(ConfigPath.input);
        ScoreData.init(ConfigPath.score);
        CurrentUser.init("player", "player", "ally");

        this.windows = new Windows("DragonBallArena");
        this.networkController = new NetworkController();
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
        appGame.setDisplayMode(WindowConfig.getWMediumSizeX(), WindowConfig.getWMediumSizeY(), false);
        GlobalVariable.appGameContainer = appGame;
        appGame.start();
    }
}

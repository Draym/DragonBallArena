package com.andres_k.master;


import com.andres_k.components.controllers.ScoreData;
import com.andres_k.components.eventComponent.input.InputData;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.graphic.Windows;
import com.andres_k.components.networkComponents.networkGame.NetworkController;
import com.andres_k.components.resourceComponent.sounds.MusicController;
import com.andres_k.components.resourceComponent.sounds.SoundController;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.utils.configs.ConfigPath;
import com.andres_k.utils.configs.CurrentUser;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.tools.ColorTools;
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
        ColorTools.init();

        InputData.init(ConfigPath.config_input);
        ScoreData.init(ConfigPath.config_score);
        CurrentUser.init("player", "player", "ally");

        CentralTaskManager.get().register(ELocation.MUSIC_CONTROLLER.getId(), MusicController.get());
        CentralTaskManager.get().register(ELocation.SOUND_CONTROLLER.getId(), SoundController.get());
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
        GlobalVariable.appGameContainer = new AppGameContainer(this.windows, WindowConfig.get().getWindowSizes(EnumWindow.LOAD).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.LOAD).getV2(), false);
        WindowConfig.get().setCurrent(EnumWindow.LOAD);
        GlobalVariable.appGameContainer.start();
    }
}

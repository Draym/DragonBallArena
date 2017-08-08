package com.andres_k.gameToolsLib.master;


import com.andres_k.custom.component.graphicComponents.graphic.MyWindows;
import com.andres_k.gameToolsLib.components.data.ScoreData;
import com.andres_k.gameToolsLib.components.eventComponents.input.InputData;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.gameToolsLib.components.graphicComponents.graphic.Windows;
import com.andres_k.gameToolsLib.components.resourceComponents.sounds.MusicController;
import com.andres_k.gameToolsLib.components.resourceComponents.sounds.SoundController;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.gameToolsLib.utils.configs.*;
import com.andres_k.gameToolsLib.utils.tools.ColorTools;
import com.andres_k.gameToolsLib.utils.tools.DLLTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.util.UUID;

/**
 * Created by com.andres_k on 10/03/2015.
 */
public class MasterGame {
    private Windows windows;
    private boolean isInit = false;

    public MasterGame() {
        this.init();
    }

    public void start() {
        try {
            this.startGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        if (!this.isInit) {
            DLLTools.init();
            ColorTools.init();
            CurrentUser.init("player 1", UUID.randomUUID().toString(), "ally");
            CentralTaskManager.get().register(ELocation.MUSIC_CONTROLLER.getId(), MusicController.get());
            CentralTaskManager.get().register(ELocation.SOUND_CONTROLLER.getId(), SoundController.get());

            try {
                InputData.init(ConfigPath.config_input);
                ScoreData.init(ConfigPath.config_score);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                this.windows = new MyWindows(GameInfo.get().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.isInit = true;
        }
    }

    private void startGame() throws SlickException, JSONException {
        GlobalVariable.appGameContainer = new AppGameContainer(this.windows, WindowConfig.get().getWindowSizes(EnumWindow.LOAD).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.LOAD).getV2(), false);
        WindowConfig.get().setCurrent(EnumWindow.LOAD);
        GlobalVariable.appGameContainer.start();
    }
}

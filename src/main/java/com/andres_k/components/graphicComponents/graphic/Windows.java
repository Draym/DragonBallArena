package com.andres_k.components.graphicComponents.graphic;


import com.andres_k.components.graphicComponents.graphic.windows.*;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.LocalTaskManager;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 17/03/2015.
 */
public class Windows extends StateBasedGame {

    private LocalTaskManager windowsTask;

    private List<WindowBasedGame> windows;

    public Windows(String name) throws JSONException, SlickException {
        super(name);

        this.windows = new ArrayList<>();
        this.windowsTask = new LocalTaskManager(ELocation.WINDOWS);
        CentralTaskManager.get().register(ELocation.WINDOWS.getId(), this.windowsTask);

        this.windows.add(new WindowLoad(EnumWindow.LOAD.getValue(), this.windowsTask));
        this.windows.add(new WindowHome(EnumWindow.HOME.getValue(), this.windowsTask));
        this.windows.add(new WindowGame(EnumWindow.GAME.getValue(), this.windowsTask));
        this.windows.add(new WindowSelectSolo(EnumWindow.SELECT_SOLO.getValue(), this.windowsTask));
        this.windows.add(new WindowSelectVersus(EnumWindow.SELECT_VERSUS.getValue(), this.windowsTask));
        this.windows.add(new WindowSelectMulti(EnumWindow.SELECT_MULTI.getValue(), this.windowsTask));
    }


    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.windows.forEach(this::addState);
        this.enterState(EnumWindow.LOAD.getValue());
    }
}

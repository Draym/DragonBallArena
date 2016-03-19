package com.andres_k.components.graphicComponents.graphic;


import com.andres_k.components.graphicComponents.graphic.windows.game.WindowGame;
import com.andres_k.components.graphicComponents.graphic.windows.home.WindowHome;
import com.andres_k.components.graphicComponents.graphic.windows.waiting.WindowLoad;
import com.andres_k.components.graphicComponents.graphic.windows.select.WindowSelectMulti;
import com.andres_k.components.graphicComponents.graphic.windows.select.WindowSelectSolo;
import com.andres_k.components.graphicComponents.graphic.windows.select.WindowSelectVersus;
import com.andres_k.components.graphicComponents.graphic.windows.waiting.WindowBattleConnection;
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

        this.windows.add(new WindowLoad(this.windowsTask));
        this.windows.add(new WindowHome(this.windowsTask));
        this.windows.add(new WindowGame(this.windowsTask));
        this.windows.add(new WindowSelectSolo(this.windowsTask));
        this.windows.add(new WindowSelectVersus(this.windowsTask));
        this.windows.add(new WindowSelectMulti(this.windowsTask));
        this.windows.add(new WindowBattleConnection(this.windowsTask));
    }


    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.windows.forEach(this::addState);
        this.enterState(EnumWindow.LOAD.getId());
    }
}

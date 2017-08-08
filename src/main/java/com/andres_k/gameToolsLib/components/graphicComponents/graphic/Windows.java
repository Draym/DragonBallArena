package com.andres_k.gameToolsLib.components.graphicComponents.graphic;


import com.andres_k.gameToolsLib.components.graphicComponents.graphic.windows.game.WindowGame;
import com.andres_k.gameToolsLib.components.graphicComponents.graphic.windows.home.WindowHome;
import com.andres_k.gameToolsLib.components.graphicComponents.graphic.windows.waiting.WindowLoad;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.gameToolsLib.components.taskComponent.LocalTaskManager;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by com.andres_k on 17/03/2015.
 */
public abstract class Windows extends StateBasedGame {

    protected LocalTaskManager windowsTask;

    protected List<WindowBasedGame> windows;

    public Windows(String name) throws JSONException, SlickException {
        super(name);

        this.windows = new ArrayList<>();
        this.windowsTask = new LocalTaskManager(ELocation.WINDOWS);
        CentralTaskManager.get().register(ELocation.WINDOWS.getId(), this.windowsTask);

        this.windows.add(new WindowLoad(this.windowsTask));
        this.windows.add(new WindowHome(this.windowsTask));
        this.windows.add(new WindowGame(this.windowsTask));
        this.addWindows();
    }


    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.windows.forEach(this::addState);
        this.enterState(EnumWindow.LOAD.getId());
    }

    protected abstract void addWindows() throws SlickException, JSONException;
}

package com.andres_k.gameToolsLib.components.controllers.waiting;

import com.andres_k.gameToolsLib.components.controllers.WindowController;
import com.andres_k.custom.component.graphicComponents.background.EBackground;
import com.andres_k.gameToolsLib.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.taskComponent.TaskFactory;
import com.andres_k.gameToolsLib.utils.stockage.Tuple;
import com.andres_k.gameToolsLib.utils.tools.Console;
import org.codehaus.jettison.json.JSONException;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import java.util.Observable;

/**
 * Created by com.andres_k on 08/07/2015.
 */
public abstract class LoadController extends WindowController {
    private int index;
    private boolean loadCompleted;

    public LoadController(int idWindow) throws JSONException, SlickException {
        super(ELocation.LOAD_CONTROLLER, idWindow);
        this.index = 1;
        this.loadCompleted = false;
    }

    @Override
    public void enter() throws SlickException {
        this.backgroundManager.run();
    }

    @Override
    public void leave() {
    }

    @Override
    public void init() throws SlickException {
        this.backgroundManager.addComponent(EBackground.LOAD_SCREEN, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.LOAD_SCREEN)));
    }

    @Override
    public void update(GameContainer gameContainer) throws SlickException {
        this.backgroundManager.update();

        if (!this.loadCompleted && !this.backgroundManager.hasActivity()) {
            try {
                this.loadCompleted = ResourceManager.get().initialise(index);
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.LOAD_GUI_LoadingBar_value, new Tuple<>(ETaskType.CUT, "body_X", ResourceManager.get().getPercentInitialised())));
                Console.write("index {" + this.index + "} -> " + (this.loadCompleted ? "loadCompleted" : "running") + " " + (ResourceManager.get().getPercentInitialised() * 100f)+ "%\n");
                if (ResourceManager.get().getPercentInitialised() == 1.0f) {
                    CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.LOAD_GUI_LoadingBar_title, new Tuple<>(ETaskType.SETTER, "value", "Press ENTER")));
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SlickException(e.getMessage());
            }
            ++this.index;
        }

    }

    @Override
    public void keyPressed(int key, char c) {

    }

    @Override
    public void keyReleased(int key, char c) {
        if (this.loadCompleted && key == Keyboard.KEY_RETURN) {
            this.stateWindow.enterState(EnumWindow.HOME.getId());
        }
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
    }

    @Override
    public void mousePressed(int button, int x, int y) {

    }

    @Override
    public void update(Observable o, Object arg) {
    }
}

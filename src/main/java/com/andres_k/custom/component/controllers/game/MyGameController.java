package com.andres_k.custom.component.controllers.game;

import com.andres_k.custom.component.graphicComponents.background.EBackground;
import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.elements.MyElementFactory;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObjectController;
import com.andres_k.gameToolsLib.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.gameToolsLib.components.resourceComponents.fonts.EFont;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.components.resourceComponents.sounds.SoundController;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.taskComponent.TaskFactory;
import com.andres_k.gameToolsLib.components.controllers.game.GameController;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.tools.ColorTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kevin on 03/05/2017.
 */
public class MyGameController extends GameController {

    public MyGameController(int idWindow) throws JSONException {
        super(idWindow, true);
    }

    @Override
    public void init() throws SlickException {
        super.init();
        this.backgroundManager.addComponent(EBackground.VALLEY_MAP, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.VALLEY_MAP)));
    }

    @Override
    protected void applyWinOnGui() {
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, MyElementFactory.createText(GameObjectController.get().getGameDesign().getWinnersNumber() + " slimes survived", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 15, 100, 30))));
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, MyElementFactory.createText("you get " + GameObjectController.get().getGameDesign().getBonusPoint() + " super coin!", ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 15, 100, 45))));
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, MyElementFactory.createText("Total score : " + GameObjectController.get().getGameDesign().getTotalScore(), ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 20, 110, 70))));
    }

    @Override
    protected void applyLooseOnGui() {
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, MyElementFactory.createText("none of your slimes survived", ColorTools.get(ColorTools.Colors.GUI_ORANGE), EFont.MODERN, 15, 100, 30))));
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, MyElementFactory.createText("you get " + GameObjectController.get().getGameDesign().getBonusPoint() + " super coin!", ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 15, 100, 45))));
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, MyElementFactory.createText("Total score : " + GameObjectController.get().getGameDesign().getTotalScore(), ColorTools.get(ColorTools.Colors.GUI_YELLOW_BLAND), EFont.MODERN, 20, 110, 70))));
    }

    @Override
    protected void launchGamePlay() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SoundController.get().play(ESound.EFFECT_FIGHT);
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(location, ELocation.GAME_GUI_AnimStart, new Pair<>(ETaskType.START_TIMER, 2000)));
                backgroundManager.run();
            }
        }, 2000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                gameStarted = true;
            }
        }, 3500);
    }

    @Override
    protected void leaveGamePlay() {
    }

    @Override
    protected void addPlayerForGame() {
    }
}

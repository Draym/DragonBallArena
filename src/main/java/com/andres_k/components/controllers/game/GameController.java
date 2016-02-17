package com.andres_k.components.controllers.game;

import com.andres_k.components.controllers.WindowController;
import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.eventComponent.input.InputGame;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObjectController;
import com.andres_k.components.graphicComponents.background.EBackground;
import com.andres_k.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.networkComponents.networkSend.messageInterface.MessageOverlayMenu;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by andres_k on 08/07/2015.
 */
public class GameController extends WindowController {
    private GameObjectController gameObjectController;
    private InputGame inputGame;
    private List<String> playerNames;

    private boolean running;

    public GameController() throws JSONException {
        super(ELocation.GAME_CONTROLLER);
        this.inputGame = new InputGame();
        this.gameObjectController = new GameObjectController();

        this.playerNames = new ArrayList<>();
    }

    @Override
    public void enter() throws SlickException {
        this.gameObjectController.enter();

        this.createPlayerForGame();
        //CentralTaskManager.get().sendRequest(TaskFactory.createTask(EnumLocation.GAME_CONTROLLER, EnumLocation.GAME_GUI, new Pair<>(EnumOverlayElement.TABLE_ROUND, new MessageRoundStart("admin", "admin", true))));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //      CentralTaskManager.get().sendRequest(TaskFactory.createTask(EnumLocation.GAME_CONTROLLER, EnumLocation.GAME_GUI, new Pair<>(EnumOverlayElement.TABLE_ROUND, new MessageRoundStart("admin", "admin", false))));
                running = true;
            }
        }, 3000);
    }

    @Override
    public void leave() {
        this.running = false;
        this.gameObjectController.leave();
        GameConfig.typePlayer.clear();
    }

    @Override
    public void init() throws SlickException, JSONException, NoSuchMethodException {
        this.gameObjectController.init();

        this.backgroundManager.addComponent(EBackground.VALLEY_MAP, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.VALLEY_MAP)));
    }

    public void restart() throws SlickException {
        this.leave();
        try {
            this.enter();
        } catch (SlickException e) {
            e.printStackTrace();
            this.window.quit();
        }
    }

    @Override
    public void renderWindow(Graphics g) {
        this.backgroundManager.draw(g);
        this.gameObjectController.draw(g);
    }

    @Override
    public void update(GameContainer gameContainer) throws SlickException {
        if (this.running || this.gameObjectController.isTheEndOfTheGame()) {
            this.backgroundManager.update();
            this.gameObjectController.update(this.running);
        }
        if (this.running) {
            if (this.gameObjectController.isTheEndOfTheGame()) {
                //    CentralTaskManager.get().sendRequest(TaskFactory.createTask(EnumLocation.GAME_CONTROLLER, EnumLocation.GAME_GUI, new Pair<>(EnumOverlayElement.TABLE_ROUND, new MessageRoundEnd("admin", "admin", "enemy"))));
                this.running = false;
            }
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        if (this.running) {
            EInput result = this.inputGame.checkInput(key);
            this.gameObjectController.event(EInput.KEY_PRESSED, result);
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        if ((!this.running && this.gameObjectController.isTheEndOfTheGame()) && key == Input.KEY_ENTER) {
            try {
                this.restart();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }

        if (this.running) {
            EInput result = this.inputGame.checkInput(key);
            this.gameObjectController.event(EInput.KEY_RELEASED, result);
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof TaskComponent) {
            TaskComponent received = (TaskComponent) arg;

            if (received.getTarget().equals(ELocation.GAME_CONTROLLER)) {
                if (received.getTask() instanceof EnumWindow && !received.getTask().equals(EnumWindow.EXIT)) {
                    this.stateWindow.enterState(((EnumWindow) received.getTask()).getId());
                } else if (received.getTask() instanceof ETaskType) {
                    if (received.getTask() == ETaskType.EXIT) {
                        this.window.quit();
                    } else if (received.getTask() == ETaskType.START) {
                        this.playerNames.clear();
                        this.playerNames.addAll(GameConfig.typePlayer.stream().map(EGameObject::getValue).collect(Collectors.toList()));
                        this.stateWindow.enterState(EnumWindow.GAME.getId());
                    }
                } else if (received.getTask() instanceof MessageOverlayMenu) {
                    this.running = !((MessageOverlayMenu) received.getTask()).isActivated();
                    this.gameObjectController.changeGameState(this.running);
                }
            }
        } else if (arg instanceof Pair) {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.GAME_CONTROLLER, (ELocation) ((Pair) arg).getV1(), ((Pair) arg).getV2()));
        }
    }

    public void createPlayerForGame() throws SlickException {
        this.gameObjectController.createPlayers(this.playerNames);
    }
}

package com.andres_k.components.controllers;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.eventComponent.input.InputGame;
import com.andres_k.components.gameComponents.gameObject.GameObjectController;
import com.andres_k.components.gameComponents.resources.ResourceManager;
import com.andres_k.components.graphicComponents.background.Background;
import com.andres_k.components.graphicComponents.background.EnumBackground;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.userInterfaceDeprecated.types.EnumOverlayElement;
import com.andres_k.components.networkComponents.networkSend.messageInterface.MessageGameNew;
import com.andres_k.components.networkComponents.networkSend.messageInterface.MessageOverlayMenu;
import com.andres_k.components.networkComponents.networkSend.messageInterface.MessageRoundEnd;
import com.andres_k.components.networkComponents.networkSend.messageInterface.MessageRoundStart;
import com.andres_k.components.soundComponents.EnumSound;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.EnumLocation;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.*;

/**
 * Created by andres_k on 08/07/2015.
 */
public class GameController extends WindowController {
    private GameObjectController gameObjectController;
    private InputGame inputGame;
    private List<String> playerNames;

    private boolean running;

    public GameController() throws JSONException {
        this.inputGame = new InputGame();
        this.gameObjectController = new GameObjectController();

        this.playerNames = new ArrayList<>();
    }

    @Override
    public void enter() throws SlickException {
        this.gameObjectController.enter();

        this.createPlayerForGame();
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(EnumLocation.GAME_CONTROLLER, EnumLocation.GAME_GUI, new Pair<>(EnumOverlayElement.TABLE_ROUND, new MessageRoundStart("admin", "admin", true))));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(EnumLocation.GAME_CONTROLLER, EnumLocation.GAME_GUI, new Pair<>(EnumOverlayElement.TABLE_ROUND, new MessageRoundStart("admin", "admin", false))));
                running = true;
            }
        }, 3000);
    }

    @Override
    public void leave() {
        this.running = false;
        this.gameObjectController.leave();
        MusicController.resume(EnumSound.BACKGROUND_GAME);
    }

    @Override
    public void init() throws SlickException, JSONException, NoSuchMethodException {
        this.gameObjectController.init();

        this.background = new Background(ResourceManager.get().getBackgroundAnimator(EnumBackground.VALLEY_MAP));
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
        this.background.draw(g);
        this.gameObjectController.draw(g);
    }

    @Override
    public void update(GameContainer gameContainer) throws SlickException {
        if (this.running || this.gameObjectController.isTheEndOfTheGame()) {
            this.background.update();
            this.gameObjectController.update(this.running);
        }
        if (this.running) {
            if (this.gameObjectController.isTheEndOfTheGame()) {
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(EnumLocation.GAME_CONTROLLER, EnumLocation.GAME_GUI, new Pair<>(EnumOverlayElement.TABLE_ROUND, new MessageRoundEnd("admin", "admin", "enemy"))));
                this.running = false;
            }
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        if (this.running) {
            EnumInput result = this.inputGame.checkInput(key);
            this.gameObjectController.event(EnumInput.KEY_PRESSED, result);
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
            EnumInput result = this.inputGame.checkInput(key);
            this.gameObjectController.event(EnumInput.KEY_RELEASED, result);
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

            if (received.getTarget().equals(EnumLocation.GAME_CONTROLLER)) {
                if (received.getTask() instanceof EnumWindow) {
                    this.stateWindow.enterState(((EnumWindow) received.getTask()).getValue());
                } else if (received.getTask() instanceof EnumOverlayElement) {
                    if (received.getTask() == EnumOverlayElement.EXIT) {
                        this.window.quit();
                    }
                } else if (received.getTask() instanceof MessageGameNew) {
                    List<String> values = ((MessageGameNew) received.getTask()).getValues();

                    this.playerNames.clear();
                    this.playerNames.addAll(values);

                    this.stateWindow.enterState(EnumWindow.GAME.getValue());
                } else if (received.getTask() instanceof MessageOverlayMenu) {
                    this.running = !((MessageOverlayMenu) received.getTask()).isActivated();
                    this.gameObjectController.changeGameState(this.running);
                }
            }
        } else if (arg instanceof Pair) {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(EnumLocation.GAME_CONTROLLER, (EnumLocation) ((Pair) arg).getV1(), ((Pair) arg).getV2()));
        }
    }

    public void createPlayerForGame() throws SlickException {
        this.gameObjectController.createPlayers(this.playerNames);
    }
}

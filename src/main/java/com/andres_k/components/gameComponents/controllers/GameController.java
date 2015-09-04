package com.andres_k.components.gameComponents.controllers;

import com.andres_k.components.gameComponents.animations.AnimatorGameData;
import com.andres_k.components.gameComponents.gameObject.GameObjectController;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.graphic.windowGame.BackgroundSliding;
import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.components.graphicComponents.input.InputGame;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.networkComponents.messages.MessageGameNew;
import com.andres_k.components.networkComponents.messages.MessageOverlayMenu;
import com.andres_k.components.networkComponents.messages.MessageRoundEnd;
import com.andres_k.components.networkComponents.messages.MessageRoundStart;
import com.andres_k.components.soundComponents.EnumSound;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.taskComponent.EnumTargetTask;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
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
    private AnimatorGameData animatorGameData;
    private GameObjectController gameObjectController;
    private BackgroundSliding background;
    private InputGame inputGame;
    private List<String> playerNames;

    private boolean running;
    private float roundSpeed;

    public GameController() throws JSONException {
        this.animatorGameData = new AnimatorGameData();

        this.inputGame = new InputGame();
        this.gameObjectController = new GameObjectController();
        this.gameObjectController.addObserver(this);
        this.roundSpeed = GlobalVariable.defaultSpeed;

        this.playerNames = new ArrayList<>();
    }

    @Override
    public void enter() throws SlickException {
        this.gameObjectController.enter();

        GlobalVariable.currentSpeed = this.roundSpeed;
        this.createPlayerForGame();
        this.setChanged();
        this.notifyObservers(TaskFactory.createTask(EnumTargetTask.GAME, EnumTargetTask.GAME_OVERLAY, new Pair<>(EnumOverlayElement.TABLE_ROUND, new MessageRoundStart("admin", "admin", true))));

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setChanged();
                notifyObservers(TaskFactory.createTask(EnumTargetTask.GAME, EnumTargetTask.GAME_OVERLAY, new Pair<>(EnumOverlayElement.TABLE_ROUND, new MessageRoundStart("admin", "admin", false))));
                running = true;
            }
        }, 3000);
    }

    @Override
    public void leave() {
        this.running = false;
        GlobalVariable.currentSpeed = this.roundSpeed;
        this.gameObjectController.leave();
        MusicController.resume(EnumSound.BACKGROUND_GAME);
    }

    @Override
    public void init() throws SlickException, JSONException {
        this.animatorGameData.init();
        this.gameObjectController.init(this.animatorGameData);

        this.background = new BackgroundSliding("image/background/backgroundGame.png");
    }

    @Override
    public void renderWindow(Graphics g) {
        this.background.draw(g);
        this.gameObjectController.draw(g);
    }

    @Override
    public void updateWindow(GameContainer gameContainer) throws SlickException {
        if (this.running || this.gameObjectController.getNumberPlayers() == 0) {
            this.background.update();
            this.gameObjectController.update(this.running);
        }
        if (this.running) {
            if (this.gameObjectController.getNumberPlayers() == 0) {
                this.setChanged();
                this.notifyObservers(TaskFactory.createTask(EnumTargetTask.GAME, EnumTargetTask.GAME_OVERLAY, new Pair<>(EnumOverlayElement.TABLE_ROUND, new MessageRoundEnd("admin", "admin", "enemy"))));
                this.running = false;
            }
            GlobalVariable.currentSpeed += 0.001;
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        if (this.running) {
            EnumInput result = this.inputGame.checkInput(key, EnumInput.PRESSED);
            this.gameObjectController.event(EnumInput.KEY_PRESSED, result);
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        if ((!this.running && this.gameObjectController.getNumberPlayers() == 0) && key == Input.KEY_ENTER) {
            this.leave();
            try {
                this.enter();
            } catch (SlickException e) {
                e.printStackTrace();
                this.window.quit();
            }
        }

        if (this.running) {
            EnumInput result = this.inputGame.checkInput(key, EnumInput.RELEASED);
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
        if (arg instanceof Tuple) {
            Tuple<EnumTargetTask, EnumTargetTask, Object> received = (Tuple<EnumTargetTask, EnumTargetTask, Object>) arg;
            if (received.getV1().equals(EnumTargetTask.WINDOWS) && received.getV2().equals(EnumTargetTask.GAME)) {
                if (received.getV3() instanceof EnumWindow) {
                    this.stateWindow.enterState(((EnumWindow) received.getV3()).getValue());
                } else if (received.getV3() instanceof EnumOverlayElement) {
                    if (received.getV3() == EnumOverlayElement.EXIT) {
                        this.window.quit();
                    }
                } else if (received.getV3() instanceof MessageGameNew) {
                    List<String> values = ((MessageGameNew) received.getV3()).getValues();
                    float newSpeed = Float.valueOf(values.get(0));

                    this.playerNames.clear();
                    for (int i = 1; i < values.size(); ++i) {
                        this.playerNames.add(values.get(i));
                    }

                    if (newSpeed >= 1 && newSpeed < 100) {
                        this.roundSpeed = newSpeed;
                    } else {
                        this.roundSpeed = GlobalVariable.defaultSpeed;
                    }
                    this.stateWindow.enterState(EnumWindow.GAME.getValue());
                } else if (received.getV3() instanceof MessageOverlayMenu) {
                    this.running = !((MessageOverlayMenu) received.getV3()).isActivated();
                    this.gameObjectController.changeGameState(this.running);
                }
            }
        } else if (arg instanceof Pair){
            this.setChanged();
            this.notifyObservers(TaskFactory.createTask(EnumTargetTask.GAME, (EnumTargetTask) ((Pair) arg).getV1(), ((Pair) arg).getV2()));
        }
    }

    public void createPlayerForGame() throws SlickException {
        this.gameObjectController.createPlayers(this.playerNames, this.animatorGameData);
    }
}

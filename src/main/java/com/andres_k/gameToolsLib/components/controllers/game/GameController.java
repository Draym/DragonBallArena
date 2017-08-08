package com.andres_k.gameToolsLib.components.controllers.game;

import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.elements.MyElementFactory;
import com.andres_k.gameToolsLib.components.controllers.EMode;
import com.andres_k.gameToolsLib.components.data.ScoreData;
import com.andres_k.gameToolsLib.components.controllers.WindowController;
import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.gameToolsLib.components.eventComponents.input.InputGame;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObjectController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Character;
import com.andres_k.gameToolsLib.components.networkComponents.networkGame.NetworkController;
import com.andres_k.gameToolsLib.components.networkComponents.networkGame.NetworkProfile;
import com.andres_k.gameToolsLib.components.networkComponents.networkSend.MessageModel;
import com.andres_k.gameToolsLib.components.networkComponents.networkSend.messageServer.*;
import com.andres_k.gameToolsLib.components.resourceComponents.fonts.EFont;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.gameToolsLib.components.resourceComponents.sounds.MusicController;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.taskComponent.TaskFactory;
import com.andres_k.gameToolsLib.components.taskComponent.utils.TaskComponent;
import com.andres_k.gameToolsLib.utils.configs.CurrentUser;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.tools.ColorTools;
import com.andres_k.gameToolsLib.utils.tools.Console;
import com.andres_k.gameToolsLib.utils.tools.DateTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.*;

/**
 * Created by com.andres_k on 08/07/2015.
 */
public abstract class GameController extends WindowController {
    protected InputGame inputGame;
    protected List<EGameObject> playerTypes;
    protected boolean pause;
    protected boolean technicalPause;
    protected boolean gameStarted;
    protected boolean availablePause;
    protected boolean gameFinish;
    protected boolean saveScore;

    public GameController(int idWindow, boolean saveScore) throws JSONException {
        super(ELocation.GAME_CONTROLLER, idWindow);
        this.inputGame = new InputGame();
        this.playerTypes = new ArrayList<>();
        this.saveScore = saveScore;
    }

    private void initialiseStatus() {
        this.gameFinish = false;
        this.gameStarted = false;
        this.pause = false;
        this.technicalPause = false;
        this.availablePause = true;
    }

    @Override
    public void enter() throws SlickException {
        this.initialiseStatus();
        GameObjectController.get().enter();
        this.initPlayerForGame();
        this.launchGamePlay();
    }

    @Override
    public void leave() {
        this.initialiseStatus();
        MusicController.get().stop(ESound.BACKGROUND_GAME);
        GameObjectController.get().leave();
        if (NetworkController.get().isConnected()) {
            NetworkController.get().disconnect();
        }
    }

    @Override
    public void init() throws SlickException {
        GameObjectController.get().init();
    }

    @Override
    public void renderWindow(Graphics g) throws SlickException {
        this.backgroundManager.draw(g);

        if (this.gameIsDrawable()) {
            GameObjectController.get().draw(g);
        }
    }

    @Override
    public void update(GameContainer gameContainer) throws SlickException {
        if (!this.pause && !this.technicalPause && this.gameStarted) {
            this.backgroundManager.update();
        }
        if (this.gameIsRunning()) {
            GameObjectController.get().update(!this.pause);
        }
        if (GameObjectController.get().getGameDesign().isTheEndOfTheGame()) {
            this.endOfTheGame();
        }
    }

    @Override
    public void beforeEnter() {
        this.playerTypes.clear();
        this.playerTypes.addAll(GameConfig.typePlayer);
    }

    @Override
    public void keyPressed(int key, char c) {
        if (this.gameIsPlayable()) {
            EInput result = this.inputGame.checkInput(key);
            if (this.gameIsRunning()) {
                GameObjectController.get().event(EInput.KEY_PRESSED, result);
            }
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        if (key == Input.KEY_ESCAPE && this.gameStarted && this.gameCanBePaused()) {
            this.pause = !this.pause;
        }
        if (this.gameIsPlayable()) {
            EInput result = this.inputGame.checkInput(key);
            if (result == EInput.PAUSE && this.gameCanBePaused()) {
                this.technicalPause = !this.technicalPause;
            } else if (this.gameIsRunning()) {
                GameObjectController.get().event(EInput.KEY_RELEASED, result);
            }
        }
    }

    @Override
    public void eventControllerReceived(EInput event, EInput input) {
        GameObjectController.get().event(event, input);
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
            Console.write("GAME CONTROLLER  -> " + arg);
            TaskComponent received = (TaskComponent) arg;
            if (received.getTarget().equals(this.location)) {
                if (received.getTask() instanceof MessageModel) {
                    try {
                        this.resolveNetworkTask((MessageModel) received.getTask());
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }
                } else if (received.getTask() instanceof Pair) {
                    if (((Pair) received.getTask()).getV1() == ETaskType.CREATE && ((Pair) received.getTask()).getV2() instanceof GameObject) {
                        GameObjectController.get().addEntity((GameObject) ((Pair) received.getTask()).getV2());
                        return;
                    } else if (((Pair) received.getTask()).getV1() instanceof String) {
                        GameObjectController.get().taskForPlayer((String) ((Pair) received.getTask()).getV1(), ((Pair) received.getTask()).getV2());
                        return;
                    }
                } else if (received.getTask() instanceof ETaskType) {
                    if (received.getTask() == ETaskType.START_ACTIVITY) {
                        this.pause = false;
                    }
                }
            }
        }
        super.update(o, arg);
    }

    protected void endOfTheGame() {
        if (!this.gameFinish) {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit, ETaskType.START_ACTIVITY));

            this.gameFinish = true;
            if (this.saveScore) {
                int totalScore = GameObjectController.get().getGameDesign().getTotalScore();
                ScoreData.setAvailableScore(CurrentUser.getPseudo(), Integer.toString(totalScore));
            }

            if (GameObjectController.get().getGameDesign().didIWin()) {
                NetworkController.get().sendMessage(CurrentUser.getPseudo(), new MessageGameEnd("", ""));
                if (GameConfig.mode == EMode.ONLINE) {
                    this.endOfTheGameForcedByNetwork(CurrentUser.getPseudo(), "unknown", "");
                } else {
                    CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, MyElementFactory.createText("Good Job ! ", ColorTools.get(ColorTools.Colors.GUI_GREEN), EFont.MODERN, 50, 70, 5))));
                    this.applyWinOnGui();
                }
            } else {
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, MyElementFactory.createText("Game Over !", ColorTools.get(ColorTools.Colors.GUI_RED), EFont.MODERN, 50, 60, 5))));
                this.applyLooseOnGui();
            }
        }
    }

    protected void endOfTheGameForcedByNetwork(String id, String pseudo, String type) {
        if (!this.gameFinish) {
            this.gameFinish = true;
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit, ETaskType.START_ACTIVITY));
            if (NetworkProfile.get().itsMyNetworkProfile(id) || NetworkProfile.get().itsMyGameProfile(id)) {
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, MyElementFactory.createText("YOU WIN !", ColorTools.get(ColorTools.Colors.GUI_GREEN), EFont.MODERN, 30, 30, 40))));
            } else {
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, MyElementFactory.createText("YOU LOOSE !", ColorTools.get(ColorTools.Colors.GUI_RED), EFont.MODERN, 30, 30, 40))));
            }
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_GUI_PanelQuit_Details, new Pair<>(ETaskType.ADD, MyElementFactory.createText("Winner : " + pseudo + " with " + type, ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 20, 20, 80))));
        }
    }

    public void resolveNetworkTask(MessageModel task) throws SlickException {
        Console.write("Task: " + task + "\n");
        if (task instanceof MessageNewPlayer) {
            if (NetworkProfile.get().itsMyNetworkProfile(task.getId())) {
                GameObjectController.get().createPlayer(EGameObject.getEnumByValue(((MessageNewPlayer) task).getPlayerType()), ((MessageNewPlayer) task).getGameId(), 0, (int) ((MessageNewPlayer) task).getX(), 0, (int) ((MessageNewPlayer) task).getY(), ((MessageNewPlayer) task).getPlayerTeam());
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.SERVER, ELocation.BATTLE_CONNECTION_GUI_StatusList, new Pair<>(ETaskType.ADD, MyElementFactory.createText(DateTools.getCurrentDate("HH:mm:ss") + " : you are connected as " + ((MessageNewPlayer) task).getPlayerType(), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.BASIC, 20, 5, 0))));
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.SERVER, ELocation.BATTLE_CONNECTION_GUI_StatusList, new Pair<>(ETaskType.ADD, MyElementFactory.createText(DateTools.getCurrentDate("HH:mm:ss") + " : waiting " + (4 - GameObjectController.get().getNumberPlayers()) + " players(s)", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.BASIC, 20, 5, 0))));
            } else {
                GameObjectController.get().createEntity(EGameObject.getEnumByValue(((MessageNewPlayer) task).getPlayerType()), task.getId(), 0, (int) ((MessageNewPlayer) task).getX(), 0, (int) ((MessageNewPlayer) task).getY(), ((MessageNewPlayer) task).getPlayerTeam());
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.SERVER, ELocation.BATTLE_CONNECTION_GUI_StatusList, new Pair<>(ETaskType.ADD, MyElementFactory.createText(DateTools.getCurrentDate("HH:mm:ss") + " : another players is connected as " + ((MessageNewPlayer) task).getPlayerType(), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.BASIC, 20, 5, 0))));
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.SERVER, ELocation.BATTLE_CONNECTION_GUI_StatusList, new Pair<>(ETaskType.ADD, MyElementFactory.createText(DateTools.getCurrentDate("HH:mm:ss") + " : waiting " + (4 - GameObjectController.get().getNumberPlayers()) + " players(s)", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.BASIC, 20, 5, 0))));
            }
        } else if (task instanceof MessageDeletePlayer) {
            GameObjectController.get().deleteEntity(task.getId());
        } else if (task instanceof MessageActionPlayer) {
            GameObject player = GameObjectController.get().getObjectById(task.getId());
            if (player != null && player.getAnimatorController() != null) {
                player.getAnimatorController().forceCurrentAnimationType(((MessageActionPlayer) task).getAction());
                player.getAnimatorController().forceCurrentAnimationIndex(((MessageActionPlayer) task).getIndex());
            }
        } else if (task instanceof MessageStatePlayer) {
            GameObject object = GameObjectController.get().getObjectById(task.getId());
            if (object != null && object instanceof Character) {
                Character character = (Character) object;
                MessageStatePlayer state = (MessageStatePlayer) task;
                character.setCurrentLife(state.getLife());
                character.getMovement().setPositions(state.getX(), state.getY());
            }
        } else if (task instanceof MessageInputPlayer) {
            GameObject object = GameObjectController.get().getObjectById(task.getId());

            if (object != null) {
                if (((MessageInputPlayer) task).getEvent() == EInput.KEY_RELEASED) {
                    object.eventReleased(((MessageInputPlayer) task).getInput());
                } else if (((MessageInputPlayer) task).getEvent() == EInput.KEY_PRESSED) {
                    object.eventPressed(((MessageInputPlayer) task).getInput());
                }
            }
        } else if (task instanceof MessageGameEnd) {
            this.endOfTheGameForcedByNetwork(task.getId(), ((MessageGameEnd) task).getWinner(), ((MessageGameEnd) task).getWinnerType());
        }
    }


    protected abstract void applyWinOnGui();

    protected abstract void applyLooseOnGui();

    protected abstract void launchGamePlay();

    protected abstract void leaveGamePlay();

    public void initPlayerForGame() throws SlickException {
        this.addPlayerForGame();
        this.createPlayerForGame();
        this.playerTypes.clear();
    }

    protected abstract void addPlayerForGame();

    private void createPlayerForGame() throws SlickException {
        Console.write("PLAYER TYPES: " + this.playerTypes);
        GameObjectController.get().createPlayers(this.playerTypes);
    }

    public boolean gameIsRunning() {
        return !this.pause && !this.technicalPause && this.gameStarted && !this.gameFinish;
    }

    public boolean gameIsPlayable() {
        return !this.pause && this.gameStarted && !this.gameFinish;
    }

    public boolean gameIsDrawable() {
        return !this.gameFinish;
    }

    public boolean gameCanBePaused() {
        return this.availablePause;
    }
}

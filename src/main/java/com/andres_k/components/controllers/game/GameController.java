package com.andres_k.components.controllers.game;

import com.andres_k.components.controllers.WindowController;
import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.eventComponent.input.InputGame;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.GameObjectController;
import com.andres_k.components.graphicComponents.background.EBackground;
import com.andres_k.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.networkComponents.networkSend.MessageModel;
import com.andres_k.components.networkComponents.networkSend.messageServer.MessageActionPlayer;
import com.andres_k.components.networkComponents.networkSend.messageServer.MessageDeletePlayer;
import com.andres_k.components.networkComponents.networkSend.messageServer.MessageNewPlayer;
import com.andres_k.components.networkComponents.networkSend.messageServer.MessageStatePlayer;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.configs.WindowConfig;
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
    private InputGame inputGame;
    private List<EGameObject> playerTypes;

    private boolean running;

    public GameController(int idWindow) throws JSONException {
        super(ELocation.GAME_CONTROLLER, idWindow);
        this.inputGame = new InputGame();
        this.playerTypes = new ArrayList<>();
    }

    @Override
    public void enter() throws SlickException {
        GameObjectController.get().enter();

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
        GameObjectController.get().leave();
        GameConfig.typePlayer.clear();
    }

    @Override
    public void init() throws SlickException {
        GameObjectController.get().init();

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
    public void renderWindow(Graphics g) throws SlickException {
        this.backgroundManager.draw(g);
        GameObjectController.get().draw(g);
    }

    @Override
    public void update(GameContainer gameContainer) throws SlickException {
        if (this.running || GameObjectController.get().isTheEndOfTheGame()) {
            this.backgroundManager.update();
            GameObjectController.get().update(this.running);
        }
        if (this.running) {
            if (GameObjectController.get().isTheEndOfTheGame()) {
                //    CentralTaskManager.get().sendRequest(TaskFactory.createTask(EnumLocation.GAME_CONTROLLER, EnumLocation.GAME_GUI, new Pair<>(EnumOverlayElement.TABLE_ROUND, new MessageRoundEnd("admin", "admin", "enemy"))));
                this.running = false;
            }
        }
    }

    @Override
    public void beforeEnter() {
        this.playerTypes.clear();
        this.playerTypes.addAll(GameConfig.typePlayer);
    }

    @Override
    public void keyPressed(int key, char c) {
        if (this.running) {
            EInput result = this.inputGame.checkInput(key);
            GameObjectController.get().event(EInput.KEY_PRESSED, result);
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        if ((!this.running && GameObjectController.get().isTheEndOfTheGame()) && key == Input.KEY_ENTER) {
            try {
                this.restart();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }

        if (this.running) {
            EInput result = this.inputGame.checkInput(key);
            GameObjectController.get().event(EInput.KEY_RELEASED, result);
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
                }
            }
        }/* else if (arg instanceof Pair) {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.GAME_CONTROLLER, (ELocation) ((Pair) arg).getV1(), ((Pair) arg).getV2()));
            return;
        }*/
        super.update(o, arg);
    }

    public void resolveNetworkTask(MessageModel task) throws SlickException {
        if (task instanceof MessageNewPlayer) {
            GameObjectController.get().createEntity(EGameObject.getEnumByValue(((MessageNewPlayer) task).getPlayerType()), task.getId(), (WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1() - 600), 300, 0, 700);
        } else if (task instanceof MessageDeletePlayer) {
            GameObjectController.get().deleteEntity(task.getId());
        } else if (task instanceof MessageActionPlayer) {
            GameObject player = GameObjectController.get().getObjectById(task.getId());
            if (player != null && player.getAnimatorController() != null) {
                player.getAnimatorController().forceCurrentAnimationType(((MessageActionPlayer) task).getAction());
                player.getAnimatorController().forceCurrentAnimationIndex(((MessageActionPlayer) task).getIndex());
            }
        } else if (task instanceof MessageStatePlayer) {
            GameObject player = GameObjectController.get().getObjectById(task.getId());
            if (player != null) {
                
            }
        }
    }

    public void createPlayerForGame() throws SlickException {
        GameObjectController.get().createPlayers(this.playerTypes);
    }
}

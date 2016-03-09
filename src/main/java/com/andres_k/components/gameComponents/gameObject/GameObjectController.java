package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.controllers.ScoreData;
import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.collisions.CollisionResult;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
import com.andres_k.utils.tools.RandomTools;
import com.andres_k.utils.tools.StringTools;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andres_k on 10/07/2015.
 */
public class GameObjectController {
    private List<GameObject> entities;
    private List<GameObject> players;

    public GameObjectController() {
        this.entities = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    // INIT
    public void init() {
    }

    public void initWorld() throws SlickException {
        this.entities.add(GameObjectFactory.create(EGameObject.PLATFORM, ResourceManager.get().getGameAnimator(EGameObject.GROUND), "item1:ground", 950, 900));
        this.entities.add(GameObjectFactory.create(EGameObject.PLATFORM, ResourceManager.get().getGameAnimator(EGameObject.GROUND), "item2:sky", 950, 0));
        this.entities.add(GameObjectFactory.create(EGameObject.BORDER, ResourceManager.get().getGameAnimator(EGameObject.WALL), "item3:leftWall", 0, 475));
        this.entities.add(GameObjectFactory.create(EGameObject.BORDER, ResourceManager.get().getGameAnimator(EGameObject.WALL), "item4:rightWall", 1900, 475));
    }

    // FUNCTIONS

    public void enter() throws SlickException {
        this.initWorld();
    }

    public void leave() {
        this.players.forEach(GameObject::clear);
        this.players.clear();
        this.entities.forEach(GameObject::clear);
        this.entities.clear();
    }

    public void draw(Graphics g) throws SlickException {
        for (GameObject player : this.players) {
            player.draw(g);
        }
        for (GameObject object : this.entities) {
            object.draw(g);
        }
    }

    private void doMovement(GameObject item) {
        if (item.getType().isIn(EGameObject.ANIMATED))
            item.doMovement(this.checkCollision(item, ETaskType.MOVE));
    }

    public void update(boolean running) throws SlickException {
        for (int i = 0; i < this.players.size(); ++i) {
            this.players.get(i).update();
            if (this.players.get(i).isNeedDelete()) {
                this.thisPlayerIsDead((Player) this.players.get(i));
                this.players.remove(i);
                --i;
            } else {
                this.doMovement(this.players.get(i));
            }
        }
        for (int i = 0; i < this.entities.size(); ++i) {
            entities.get(i).update();
            if (this.entities.get(i).isNeedDelete()) {
                this.entities.remove(i);
                --i;
            } else {
                this.doMovement(this.entities.get(i));
            }
        }
    }

    // TASK

    public void taskForPlayer(String id, Object task) {
        GameObject player = this.getPlayerById(id);

        if (player != null) {
            player.doTask(task);
        }
    }

    // EVENTS
    public void event(EInput event, EInput input) {
        if (event == EInput.KEY_RELEASED) {
            if (input.getIndex() >= 0 && input.getIndex() < this.players.size()) {
                GameObject player = this.getPlayerById("player" + String.valueOf(input.getIndex()));
                if (player != null) {
                    player.eventReleased(input);
                }
            }
        } else if (event == EInput.KEY_PRESSED) {
            if (input.getIndex() >= 0 && input.getIndex() < this.players.size()) {
                GameObject player = this.getPlayerById("player" + String.valueOf(input.getIndex()));
                if (player != null) {
                    player.eventPressed(input);
                }
            }
        }
    }

    public void thisPlayerIsDead(Player player) {
        String score = String.valueOf(player.getScore());

        ScoreData.setAvailableScore(player.getPseudo(), score);
        score = StringTools.addCharacterEach(score, " ", 3);
        //todo SCORE ?
        //Pair task = new Pair<>(EnumOverlayElement.SCORE.getValue() + player.getIdIndex(), new Tuple<>(EnumTask.SETTER, "value", player.getPseudo() + " - " + score));
        // CentralTaskManager.get().sendRequest(TaskFactory.createTask(EnumLocation.GAME_OBJECT_CONTROLLER, EnumLocation.GAME_GUI, new Pair<>(EnumOverlayElement.TABLE_ROUND_END, task)));
        Console.write("\n" + player.getPseudo() + " : '" + score + "' pts.");
    }

    public void changeGameState(boolean running) throws SlickException {
        for (GameObject object : this.players) {
            object.getAnimatorController().currentAnimation().setAutoUpdate(running);
            object.resetActions();
        }
        for (GameObject object : this.entities) {
            object.getAnimatorController().currentAnimation().setAutoUpdate(running);
            object.resetActions();
        }
    }

    // ADD

    public void createPlayers(List<EGameObject> playerNames) throws SlickException {
        Integer count = 0;

        for (EGameObject type : playerNames) {
            GameObject player = null;
            while (player == null || this.checkCollision(player, ETaskType.STATIC).hasCollision()) {
                int randomX = RandomTools.getInt(WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1() - 600) + 300;
                Console.write("Create player: " + type);
                player = GameObjectFactory.create(type, ResourceManager.get().getGameAnimator(type),
                        "player" + count + GlobalVariable.id_delimiter + type.getValue(), randomX, WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV2() - 200);
                if (count == 0) {
                    CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, ELocation.GAME_GUI_State_AlliedPlayers, new Pair<>(ETaskType.ADD, ElementFactory.createStateBarPlayer(player.id, 475, 85, EGuiElement.getEnum(player.getType().getValue(), EGuiElement.ICON.getValue()), false))));
                } else {
                    CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, ELocation.GAME_GUI_State_EnemyPlayers, new Pair<>(ETaskType.ADD, ElementFactory.createStateBarPlayer(player.id, 475, 85, EGuiElement.getEnum(player.getType().getValue(), EGuiElement.ICON.getValue()), true))));
                }
            }
            ++count;
            this.players.add(player);
        }
    }

    public void addEntity(GameObject entity) {
        this.entities.add(entity);
    }

    // COLLISION

    public CollisionResult checkCollision(GameObject current, ETaskType type) {
        CollisionResult result = new CollisionResult();

        if (current != null) {
            List<GameObject> items = this.getAllExpectHim(current.getId());
            Pair<Float, Float> newPos;

            if (type == ETaskType.STATIC) {
                newPos = new Pair<>(current.getPosX(), current.getPosY());
            } else {
                newPos = current.predictNextPosition();
            }
            result = current.checkCollision(items, newPos);
        }
        return result;
    }

    // GETTERS

    public List<GameObject> getAllExpectHim(String id) {
        List<GameObject> items = new ArrayList<>();
        items.addAll(this.players.stream().filter(object -> !id.contains(object.getId())).collect(Collectors.toList()));
        items.addAll(this.entities.stream().filter(object -> !id.contains(object.getId())).collect(Collectors.toList()));
        return items;
    }

    public GameObject getPlayerById(String id) {
        for (GameObject player : this.players) {
            if (player.getId().contains(id)) {
                return player;
            }
        }
        return null;
    }

    public boolean isTheEndOfTheGame() {
        return (this.getNumberPlayers() == 0);
    }

    public int getNumberPlayers() {
        return this.players.size();
    }
}

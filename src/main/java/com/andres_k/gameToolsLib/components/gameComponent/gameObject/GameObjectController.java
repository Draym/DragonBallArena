package com.andres_k.gameToolsLib.components.gameComponent.gameObject;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.gameComponent.gameObject.GameObjectFactory;
import com.andres_k.custom.component.gameComponent.gameObject.MyGameDesign;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.elements.MyElementFactory;
import com.andres_k.gameToolsLib.components.camera.CameraController;
import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.gameToolsLib.components.gameComponent.collisions.CollisionResult;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Character;
import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.gameToolsLib.components.networkComponents.networkGame.NetworkController;
import com.andres_k.gameToolsLib.components.networkComponents.networkSend.messageServer.MessageInputPlayer;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.taskComponent.TaskFactory;
import com.andres_k.gameToolsLib.utils.configs.GlobalVariable;
import com.andres_k.gameToolsLib.utils.configs.WindowConfig;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.stockage.Tuple;
import com.andres_k.gameToolsLib.utils.tools.Console;
import com.andres_k.gameToolsLib.utils.tools.RandomTools;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by com.andres_k on 10/07/2015.
 */
public final class GameObjectController {
    private List<GameObject> entities;
    private List<GameObject> players;
    private GameDesign gameDesign;

    private GameObjectController() {
        this.entities = new ArrayList<>();
        this.players = new ArrayList<>();
        this.gameDesign = new MyGameDesign();
    }

    private static class SingletonHolder {
        private final static GameObjectController instance = new GameObjectController();
    }

    public static GameObjectController get() {
        return SingletonHolder.instance;
    }


    // INIT
    public void init() {
    }

    // FUNCTIONS

    public void enter() throws SlickException {
        this.gameDesign.enter();
    }

    public void leave() {
        this.gameDesign.leave();
        this.players.forEach(GameObject::clear);
        this.players.clear();
        this.entities.forEach(GameObject::clear);
        this.entities.clear();
    }

    public void draw(Graphics g) throws SlickException {
        for (GameObject player : this.players) {
            if (CameraController.get().isVisible(player))
                player.draw(g);
        }
        for (GameObject object : this.entities) {
            if (CameraController.get().isVisible(object))
                object.draw(g);
        }
    }

    private void doMovement(GameObject item) {
        if (item.getType().isIn(EGameObject.ANIMATED)) {
            item.doMovement(this.checkCollision(item, ETaskType.MOVE));
        }
    }

    public void update(boolean running) throws SlickException {
        if (running) {
            for (int i = 0; i < this.players.size(); ++i) {
                this.players.get(i).update();
                if (this.players.get(i).isNeedDelete() || !this.players.get(i).isVisibleInScreen()) {
                    this.gameDesign.thisPlayerIsDead((Character) this.players.get(i));
                    this.players.remove(i);
                    --i;
                } else {
                    this.doMovement(this.players.get(i));
                }
            }
            for (int i = 0; i < this.entities.size(); ++i) {
                this.entities.get(i).update();
                if (this.entities.get(i).isNeedDelete()) {
                    this.entities.remove(i);
                    --i;
                } else {
                    this.doMovement(this.entities.get(i));
                }
            }

            this.updateOwnerCameraPlayer();
            CameraController.get().followOwner(this.getPlayerById(CameraController.get().getIdOwner()));
        }
    }

    private void updateOwnerCameraPlayer() {
        float maxX = 0;
        int saveI = 0;

        for (int i = 0; i < this.players.size(); ++i) {
            if ((maxX <= this.players.get(i).getPosX() || maxX == 0) && !this.players.get(i).getAnimatorController().isDeleted()) {
                maxX = this.players.get(i).getPosX();
                saveI = i;
            }
        }
        Console.debug("(" + this.players.size() + ") -> " + saveI);
        if (this.players.size() > 0) {
            CameraController.get().setIdOwner(this.players.get(saveI).getId());
        }
    }

    // TASK

    public void taskForPlayer(String id, Object task) {
        GameObject player = this.getObjectById(id);

        if (player != null) {
            player.doTask(task);
        }
    }

    // EVENTS
    public void event(EInput event, EInput input) {
        if (input.getIndex() >= 0) {
            GameObject player = this.getPlayerById("player" + GlobalVariable.id_delimiter + String.valueOf(input.getIndex()));

            if (player != null) {
                NetworkController.get().sendMessage(player.getId(), new MessageInputPlayer(event, input));
                if (event == EInput.KEY_RELEASED) {
                    player.eventReleased(input);
                } else if (event == EInput.KEY_PRESSED) {
                    player.eventPressed(input);
                }
            }
        }
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
        Integer count;
        int sizeX;
        int startX;

        if (!playerNames.isEmpty()) {
            count = 1;
            sizeX = WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1() / playerNames.size();
            startX = 0;

            for (EGameObject type : playerNames) {
                this.createPlayer(type, "player" + GlobalVariable.id_delimiter + count + GlobalVariable.id_delimiter + type.getValue(), sizeX - 100, startX + 50, 0, 300, count);
                startX += sizeX;
                ++count;
            }
        }
        /*
        this.updateOwnerCameraPlayer();
        CameraController.get().followOwner(this.getPlayerById(CameraController.get().getIdOwner()));*/
    }

    public void createPlayer(EGameObject type, String id, float startX, int boundX, float startY, int boundY, int team) throws SlickException {
        GameObject player = null;
        float randomX = startX;
        float randomY = startY;
        boolean checked = false;

        Console.write("CREATE PLAYER : " + id);
        while (player == null || this.checkCollision(player, ETaskType.STATIC).hasCollision()) {
            if (checked) {
                randomX = (float) RandomTools.getInt(boundX) + startX;
                randomY = (float) RandomTools.getInt(boundY) + startY;
            }
            player = GameObjectFactory.create(type, ResourceManager.get().getGameAnimator(type), id, randomX, randomY);
            player.doTask(new Tuple<>(ETaskType.SETTER, "team", team));
            checked = true;
        }
        Console.write("Create Character: " + player);
        if (team == 1) {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, ELocation.GAME_GUI_State_AlliedPlayers, new Pair<>(ETaskType.ADD, ElementFactory.createStateBarPlayer(player.id, 475, 85, EGuiElement.getEnum(player.getType().getValue(), EGuiElement.ICON.getValue()), false))));
        } else {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, ELocation.GAME_GUI_State_EnemyPlayers, new Pair<>(ETaskType.ADD, ElementFactory.createStateBarPlayer(player.id, 475, 85, EGuiElement.getEnum(player.getType().getValue(), EGuiElement.ICON.getValue()), true))));
        }
        this.players.add(player);
    }

    public void createEntity(EGameObject type, String id, int boundX, int startX, int boundY, int startY, int team) throws SlickException {
        GameObject object = null;

        while (object == null || this.checkCollision(object, ETaskType.STATIC).hasCollision()) {
            int randomX = RandomTools.getInt(boundX) + startX;
            int randomY = RandomTools.getInt(boundY) + startY;
            object = GameObjectFactory.create(type, ResourceManager.get().getGameAnimator(type), id, randomX, randomY);
            object.doTask(new Tuple<>(ETaskType.SETTER, "team", team));
        }
        Console.write("Create Entity: " + object);
        if (object instanceof Character) {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, ELocation.GAME_GUI_State_EnemyPlayers, new Pair<>(ETaskType.ADD, MyElementFactory.createStateBarPlayer(object.getId(), 475, 85, EGuiElement.getEnum(object.getType().getValue(), EGuiElement.ICON.getValue()), true))));
        }
        this.entities.add(object);
    }

    public void addEntity(GameObject entity) {
        this.entities.add(entity);
    }

    public void deleteEntity(String id) {
        Console.write("delete : " + id);
        for (int i = 0; i < this.entities.size(); ++i) {
            if (this.entities.get(i).getId().equals(id)) {
                Console.write("REMOVE");
                this.entities.remove(i);
                --i;
            }
        }
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
            if (!result.hasCollision()) {
                result = current.checkCollision(items, newPos);
            }
        }
        return result;
    }

    // GETTERS

    public List<GameObject> getPlayers() {
        return this.players;
    }

    public List<GameObject> getEntities() {
        return this.entities;
    }

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

    public GameObject getObjectById(String id) {
        for (GameObject player : this.players) {
            if (player.getId().contains(id)) {
                return player;
            }
        }
        for (GameObject entity : this.entities) {
            if (entity.getId().contains(id)) {
                return entity;
            }
        }
        return null;
    }

    public int getNumberPlayers() {
        int nbPlayer = this.players.size();

        for (GameObject object : this.entities) {
            if (object instanceof Character) {
                nbPlayer += 1;
            }
        }
        return nbPlayer;
    }

    public GameDesign getGameDesign() {
        return this.gameDesign;
    }
}

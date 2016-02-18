package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.controllers.ScoreData;
import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.collisions.CollisionResult;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
import com.andres_k.utils.tools.RandomTools;
import com.andres_k.utils.tools.StringTools;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 10/07/2015.
 */
public class GameObjectController {
    private List<GameObject> obstacles;
    private List<GameObject> players;

    public GameObjectController() {
        this.obstacles = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    // INIT
    public void init() {
    }

    public void initWorld() throws SlickException {
        this.obstacles.add(GameObjectFactory.create(EGameObject.PLATFORM, ResourceManager.get().getGameAnimator(EGameObject.GROUND), "item1:ground", 950, 900));
        this.obstacles.add(GameObjectFactory.create(EGameObject.PLATFORM, ResourceManager.get().getGameAnimator(EGameObject.GROUND), "item2:sky", 950, 0));
        this.obstacles.add(GameObjectFactory.create(EGameObject.BORDER, ResourceManager.get().getGameAnimator(EGameObject.WALL), "item3:leftWall", 0, 475));
        this.obstacles.add(GameObjectFactory.create(EGameObject.BORDER, ResourceManager.get().getGameAnimator(EGameObject.WALL), "item4:rightWall", 1900, 475));
    }

    // FUNCTIONS

    public void enter() throws SlickException {
        this.initWorld();
    }

    public void leave() {
        this.players.forEach(GameObject::clear);
        this.players.clear();
        this.obstacles.forEach(GameObject::clear);
        this.obstacles.clear();
    }

    public void draw(Graphics g) {
        for (GameObject player : this.players) {
            player.draw(g);
        }
        for (GameObject object : this.obstacles) {
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
        for (int i = 0; i < this.obstacles.size(); ++i) {
            obstacles.get(i).update();
            if (this.obstacles.get(i).isNeedDelete()) {
                this.obstacles.remove(i);
                --i;
            } else {
                this.doMovement(this.obstacles.get(i));
            }
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

    public void changeGameState(boolean running) {
        for (GameObject object : this.players) {
            if (object.getAnimatorController() != null && object.getAnimatorController().currentAnimation() != null)
                object.getAnimatorController().currentAnimation().setAutoUpdate(running);
            object.resetActions();
        }
        for (GameObject object : this.obstacles) {
            if (object.getAnimatorController() != null && object.getAnimatorController().currentAnimation() != null)
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
                        "player" + count + ":" + type.getValue(), randomX, WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV2() - 200);
            }
            ++count;
            this.players.add(player);
        }
    }

    // COLLISION

    public CollisionResult checkCollision(GameObject current, ETaskType type) {
        CollisionResult result = new CollisionResult();

        if (current != null) {
            List<GameObject> items = this.getAllExpectHim(current.getId());
            Pair<Float, Float> newPos;

            if (type == ETaskType.STATIC)
                newPos = new Pair<>(current.getPosX(), current.getPosY());
            else
                newPos = current.predictNextPosition();
            result = current.checkCollision(items, newPos);
        }
        return result;
    }

    // GETTERS

    public List<GameObject> getAllExpectHim(String id) {
        List<GameObject> items = new ArrayList<>();

        for (GameObject object : this.players) {
            if (!object.getId().equals(id)) {
                items.add(object);
            }
        }
        for (GameObject object : this.obstacles) {
            if (!object.getId().equals(id)) {
                items.add(object);
            }
        }
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

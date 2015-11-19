package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.gameComponents.animations.data.AnimatorGameData;
import com.andres_k.components.controllers.ScoreData;
import com.andres_k.components.gameComponents.collisions.CollisionResult;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.taskComponent.EnumTargetTask;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import com.andres_k.utils.tools.ConsoleWrite;
import com.andres_k.utils.tools.RandomTools;
import com.andres_k.utils.tools.StringTools;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Created by andres_k on 10/07/2015.
 */
public class GameObjectController extends Observable {
    private List<GameObject> obstacles;
    private List<GameObject> players;

    private AnimatorGameData animatorGameData;

    public GameObjectController() {
        this.obstacles = new ArrayList<>();
        this.players = new ArrayList<>();
        this.animatorGameData = null;
    }

    // INIT
    public void init(AnimatorGameData animatorGameData) {
        this.animatorGameData = animatorGameData;
    }

    public void initWorld() throws SlickException {
        this.obstacles.add(GameObjectFactory.create(EnumGameObject.PLATFORM, this.animatorGameData.getAnimator(EnumGameObject.GROUND), "item1:ground", 640, 640));
       // this.obstacles.add(GameObjectFactory.create(EnumGameObject.PLATFORM, this.animatorGameData.getAnimator(EnumGameObject.GROUND), "item5:ground", 300, 300));
        this.obstacles.add(GameObjectFactory.create(EnumGameObject.PLATFORM, this.animatorGameData.getAnimator(EnumGameObject.GROUND), "item2:sky", 640, -1));
        this.obstacles.add(GameObjectFactory.create(EnumGameObject.BORDER, this.animatorGameData.getAnimator(EnumGameObject.WALL), "item3:leftWall", 0, 340));
        this.obstacles.add(GameObjectFactory.create(EnumGameObject.BORDER, this.animatorGameData.getAnimator(EnumGameObject.WALL), "item4:rightWall", 1280, 340));
    }

    // FUNCTIONS

    public void enter() throws SlickException {
        this.initWorld();
    }

    public void leave() {
        for (GameObject player : this.players) {
            player.clear();
        }
        this.players.clear();
        for (GameObject object : this.obstacles) {
            object.clear();
        }
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
        if (item.getType().isIn(EnumGameObject.ANIMATED))
            item.doMovement(this.checkCollision(item, EnumTask.MOVE));
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
    public void event(EnumInput event, EnumInput input) {
        if (event == EnumInput.KEY_RELEASED) {
            if (input.getIndex() >= 0 && input.getIndex() < this.players.size()) {
                GameObject player = this.getPlayerById("player" + String.valueOf(input.getIndex()));
                if (player != null) {
                    player.eventReleased(input);
                }
            }
        } else if (event == EnumInput.KEY_PRESSED) {
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
        Pair task = new Pair<>(EnumOverlayElement.SCORE.getValue() + player.getIdIndex(), new Tuple<>(EnumTask.SETTER, "value", player.getPseudo() + " - " + score));

        this.setChanged();
        this.notifyObservers(new Pair<>(EnumTargetTask.GAME_OVERLAY, new Pair<>(EnumOverlayElement.TABLE_ROUND_END, task)));
        ConsoleWrite.write("\n" + player.getPseudo() + " : '" + score + "' pts.");
    }

    public void changeGameState(boolean running) {
        for (GameObject object : this.players) {
            if (object.getAnimatorController() != null && object.getAnimatorController().currentAnimation() != null)
                object.getAnimatorController().currentAnimation().setAutoUpdate(running);
        }
        for (GameObject object : this.obstacles) {
            if (object.getAnimatorController() != null && object.getAnimatorController().currentAnimation() != null)
                object.getAnimatorController().currentAnimation().setAutoUpdate(running);
        }
    }

    // ADD

    public void createPlayers(List<String> playerNames) throws SlickException {
        for (int i = 0; i < playerNames.size(); ++i) {
            GameObject player = null;
            while (player == null || this.checkCollision(player, EnumTask.STATIC).hasCollision()) {
                int randomX = RandomTools.getInt(WindowConfig.getW2SizeX() - 200) + 100;
                player = GameObjectFactory.create(EnumGameObject.GOKU, this.animatorGameData.getAnimator(EnumGameObject.GOKU), "player" + String.valueOf(i) + ":" + playerNames.get(i), randomX, WindowConfig.w2_sY - 100);
            }
            this.players.add(player);
        }
    }

    // COLLISION

    public CollisionResult checkCollision(GameObject current, EnumTask type) {
        CollisionResult result = new CollisionResult();

        if (current != null) {
            List<GameObject> items = this.getAllExpectHim(current.getId());
            Pair<Float, Float> newPos;

            if (type == EnumTask.STATIC)
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

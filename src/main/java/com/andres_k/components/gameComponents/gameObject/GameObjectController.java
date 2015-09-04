package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.gameComponents.animations.AnimatorGameData;
import com.andres_k.components.gameComponents.controllers.ScoreData;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.graphicComponents.input.EnumInput;
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

    private long updateIncrement;
    private long objectiveIncrement;

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
    }

    // FUNCTIONS

    public void enter() throws SlickException {
        this.initWorld();
        this.updateIncrement = 0;
        this.objectiveIncrement = 20;
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

    public void update(boolean running) throws SlickException {
        for (int i = 0; i < this.players.size(); ++i) {
            this.players.get(i).update();
            if (this.players.get(i).isNeedDelete()) {
                this.thisPlayerIsDead((Player) this.players.get(i));
                this.players.remove(i);
                --i;
            } else {
                this.checkCollision(this.players.get(i));
                this.players.get(i).move();
            }
        }
        for (int i = 0; i < this.obstacles.size(); ++i) {
            obstacles.get(i).update();
            if (this.obstacles.get(i).isNeedDelete()) {
                this.obstacles.remove(i);
                --i;
            } else {
                this.checkCollision(this.obstacles.get(i));
                this.obstacles.get(i).move();
            }
        }
        if (running) {
            ++this.updateIncrement;
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
            GameObject player = this.getPlayerById("player" + String.valueOf(input.getIndex()));
            if (player != null) {
                player.eventPressed(input);
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

    public void changeGameState(boolean running){
        for (GameObject object : this.players){
            object.getAnimator().currentAnimation().setAutoUpdate(running);
        }
        for (GameObject object : this.obstacles){
            object.getAnimator().currentAnimation().setAutoUpdate(running);
        }
    }

    // ADD

    public void createPlayers(List<String> playerNames, AnimatorGameData animatorGameData) throws SlickException {
        for (int i = 0; i < playerNames.size(); ++i) {
            Player player = null;
            while (player == null || this.checkCollision(player)) {
                int randomX = RandomTools.getInt(WindowConfig.getW2SizeX() - 200) + 100;
                player = new Player(animatorGameData.getAnimator(EnumGameObject.PLAYER), "player" + String.valueOf(i) + ":" + playerNames.get(i), randomX, WindowConfig.w2_sY - 100);
            }
            this.players.add(player);
        }
    }

    // COLLISION

    public boolean checkCollision(GameObject current) {
        boolean collision = false;
        if (current != null) {
            List<GameObject> items = this.getAllExpectHim(current.getId());

            for (GameObject item : items) {
                if (current.checkCollisionWith(item)){
                    collision = true;
                }
            }
        }
        return collision;
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

    public int getNumberPlayers() {
        return this.players.size();
    }
}

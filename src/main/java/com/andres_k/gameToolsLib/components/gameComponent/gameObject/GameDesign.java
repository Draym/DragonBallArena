package com.andres_k.gameToolsLib.components.gameComponent.gameObject;

import com.andres_k.gameToolsLib.components.controllers.EMode;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Character;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import org.newdawn.slick.SlickException;

/**
 * Created by kevin on 03/05/2017.
 */
public abstract class GameDesign {
    protected int bonusPoint;
    protected int point;

    protected GameDesign() {
    }

    // INIT
    protected abstract void initWorld() throws SlickException;

    // FUNCTIONS

    public void enter() throws SlickException {
        this.bonusPoint = 0;
        this.point = 0;
        this.initWorld();
    }

    public void leave() {
    }

    public abstract void addWinner(String id);

    public abstract void thisPlayerIsDead(Character character);

    public boolean isTheEndOfTheGame() {
        return (GameConfig.mode != EMode.SOLO && GameObjectController.get().getNumberPlayers() == 0);
    }

    public void addBonusPoint(int value) {
        this.bonusPoint += value;
    }

    public void addPoint(int value) {
        this.point += value;
    }

    // GETTERS
    public abstract int getWinnersNumber();

    public int getBonusPoint() {
        return this.bonusPoint;
    }

    public int getPoint() {
        return this.point;
    }

    public abstract int getTotalScore();

    public abstract boolean didIWin();
}

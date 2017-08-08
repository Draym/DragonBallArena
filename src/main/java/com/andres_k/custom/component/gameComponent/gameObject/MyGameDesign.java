package com.andres_k.custom.component.gameComponent.gameObject;

import com.andres_k.gameToolsLib.components.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.components.controllers.EMode;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameDesign;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObjectController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Character;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import org.newdawn.slick.SlickException;

/**
 * Created by com.andres_k on 10/07/2015.
 */
public final class MyGameDesign extends GameDesign {

    public MyGameDesign() {
    }


    // INIT
    @Override
    public void initWorld() throws SlickException {
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.PLATFORM, ResourceManager.get().getGameAnimator(EGameObject.GROUND), "item1:ground", 950, 900));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.PLATFORM, ResourceManager.get().getGameAnimator(EGameObject.GROUND), "item2:sky", 950, 0));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.BORDER, ResourceManager.get().getGameAnimator(EGameObject.WALL), "item3:leftWall", 0, 475));
        GameObjectController.get().addEntity(GameObjectFactory.create(EGameObject.BORDER, ResourceManager.get().getGameAnimator(EGameObject.WALL), "item4:rightWall", 1900, 475));
    }

    // FUNCTIONS

    @Override
    public void enter() throws SlickException {
        this.initWorld();
    }

    @Override
    public void addWinner(String id) {
        this.point += 1;
    }

    @Override
    public void thisPlayerIsDead(Character character) {
        /*
        if (character.getId().equals(CameraController.get().getIdOwner())) {
        }*/
    }

    @Override
    public boolean isTheEndOfTheGame() {
        return (GameConfig.mode != EMode.SOLO && GameObjectController.get().getNumberPlayers() == 1);
    }

    // GETTERS
    @Override
    public int getWinnersNumber() {
        return this.point;
    }

    @Override
    public int getTotalScore() {
        int winners = this.point;

        int totalScore = (winners * 1000) + this.bonusPoint * 10;

        if (this.bonusPoint > 0) {
            totalScore *= this.bonusPoint;
        }
        return totalScore;
    }

    @Override
    public boolean didIWin() {
        return this.point > 0;
    }
}

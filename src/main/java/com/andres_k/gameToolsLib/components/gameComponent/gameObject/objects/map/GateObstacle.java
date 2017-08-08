package com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.map;

import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObjectController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Obstacle;

/**
 * Created by kevin on 25/04/2017.
 */
public class GateObstacle extends Obstacle {
    public GateObstacle(AnimatorController animatorController, String id, float posX, float posY) {
        super(animatorController, EGameObject.GATE, id, posX, posY, 0, 0, 0, 0);
    }


    @Override
    public void manageDoHit(GameObject enemy) {
        enemy.getAnimatorController().setDeleted(true);
        GameObjectController.get().getGameDesign().addWinner(enemy.getId());
    }
}

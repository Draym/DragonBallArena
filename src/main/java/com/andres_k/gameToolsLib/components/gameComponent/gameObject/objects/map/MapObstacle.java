package com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.map;

import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Obstacle;

/**
 * Created by kevin on 23/04/2017.
 */
public class MapObstacle extends Obstacle {
    public MapObstacle(AnimatorController animatorController, String id, float posX, float posY) {
        super(animatorController, EGameObject.MAP, id, posX, posY, 1, 1, 0, 0);
    }
}

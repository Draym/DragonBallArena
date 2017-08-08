package com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.obstacles;

import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Obstacle;

/**
 * Created by com.andres_k on 13/10/2015.
 */
public class Platform extends Obstacle {
    public Platform(AnimatorController animatorController, String id, float posX, float posY) {
        super(animatorController, EGameObject.PLATFORM, id, posX, posY, 30, 0, 0, 0);
    }
}

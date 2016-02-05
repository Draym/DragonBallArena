package com.andres_k.components.gameComponents.gameObject.objects.obstacles;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.objects.Obstacle;

/**
 * Created by andres_k on 21/10/2015.
 */

public class Border extends Obstacle {
    public Border(AnimatorController animatorController, String id, float posX, float posY) {
        super(animatorController, EGameObject.BORDER, id, posX, posY, 30, 0, 0, 0);
    }
}

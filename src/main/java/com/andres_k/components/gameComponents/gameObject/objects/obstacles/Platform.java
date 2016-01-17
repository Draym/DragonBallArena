package com.andres_k.components.gameComponents.gameObject.objects.obstacles;

import com.andres_k.components.gameComponents.animations.container.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.objects.Obstacle;

/**
 * Created by andres_k on 13/10/2015.
 */
public class Platform extends Obstacle {
    public Platform(AnimatorController animatorController, String id, float posX, float posY) {
        super(animatorController, EnumGameObject.PLATFORM, id, posX, posY, 30, 0, 0, 0);
    }
}

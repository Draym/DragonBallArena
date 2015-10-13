package com.andres_k.components.gameComponents.gameObject.objects.obstacles;

import com.andres_k.components.gameComponents.animations.Animator;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.objects.Obstacle;

/**
 * Created by andres_k on 13/10/2015.
 */
public class Platform extends Obstacle {
    public Platform(Animator animator, String id, float posX, float posY) {
        super(animator, id, EnumGameObject.PLATFORM, posX, posY, 30, 0, 0);
    }
}

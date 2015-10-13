package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.gameComponents.animations.Animator;
import com.andres_k.components.gameComponents.gameObject.objects.obstacles.Platform;
import com.andres_k.components.gameComponents.gameObject.objects.players.Goku;

/**
 * Created by andres_k on 13/10/2015.
 */
public class GameObjectFactory {

    public static GameObject create(EnumGameObject type, Animator animator, String id, float x, float y) {
        GameObject object = null;

        if (type == EnumGameObject.GOKU) {
            object = new Goku(animator, id, x, y);
        } else if (type == EnumGameObject.PLATFORM) {
            object = new Platform(animator, id, x, y);
        }
        return object;
    }
}

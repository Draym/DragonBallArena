package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.gameComponents.animations.container.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.objects.obstacles.Border;
import com.andres_k.components.gameComponents.gameObject.objects.obstacles.Platform;
import com.andres_k.components.gameComponents.gameObject.objects.players.Goku;

/**
 * Created by andres_k on 13/10/2015.
 */
public class GameObjectFactory {

    public static GameObject create(EnumGameObject type, AnimatorController animatorController, String id, float x, float y) {
        GameObject object = null;

        if (type == EnumGameObject.GOKU) {
            object = new Goku(animatorController, id, x, y);
        } else if (type == EnumGameObject.PLATFORM) {
            object = new Platform(animatorController, id, x, y);
        } else if (type == EnumGameObject.BORDER) {
            object = new Border(animatorController, id, x, y);
        }
        return object;
    }
}

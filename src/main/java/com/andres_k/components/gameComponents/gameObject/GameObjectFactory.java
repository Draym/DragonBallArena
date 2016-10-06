package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.obstacles.Border;
import com.andres_k.components.gameComponents.gameObject.objects.obstacles.Platform;
import com.andres_k.components.gameComponents.gameObject.objects.players.Goku;
import com.andres_k.components.gameComponents.gameObject.objects.players.Vegeta;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.utils.configs.WindowConfig;

/**
 * Created by andres_k on 13/10/2015.
 */
public class GameObjectFactory {

    public static GameObject create(EGameObject type, AnimatorController animatorController, String id, float x, float y) {
        GameObject object = null;

        if (x > WindowConfig.get().centerPosX(EnumWindow.GAME, 0)) {
            animatorController.setEyesDirection(EDirection.LEFT);
        }
        if (type == EGameObject.GOKU) {
            object = new Goku(animatorController, id, x, y);
        } else if (type == EGameObject.VEGETA) {
                object = new Vegeta(animatorController, id, x, y);
        } else if (type == EGameObject.PLATFORM) {
            object = new Platform(animatorController, id, x, y);
        } else if (type == EGameObject.BORDER) {
            object = new Border(animatorController, id, x, y);
        }
        return object;
    }
}

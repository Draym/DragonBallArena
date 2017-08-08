package com.andres_k.custom.component.gameComponent.gameObject;

import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.custom.component.gameComponent.gameObject.objects.characters.players.Goku;
import com.andres_k.custom.component.gameComponent.gameObject.objects.characters.players.Vegeta;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.obstacles.Border;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.obstacles.Platform;
import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.gameToolsLib.utils.configs.WindowConfig;

/**
 * Created by com.andres_k on 13/10/2015.
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

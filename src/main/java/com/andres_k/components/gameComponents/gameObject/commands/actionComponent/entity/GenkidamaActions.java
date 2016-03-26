package com.andres_k.components.gameComponents.gameObject.commands.actionComponent.entity;

import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.utils.configs.GameConfig;

/**
 * Created by andres_k on 17/03/2016.
 */
public class GenkidamaActions {

    public static void stopOnExplode(GameObject object) {
        object.getMovement().setPushX(GameConfig.speedTravel / 3f);
    }
}

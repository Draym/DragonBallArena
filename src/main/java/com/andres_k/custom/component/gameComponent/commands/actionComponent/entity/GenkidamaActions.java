package com.andres_k.custom.component.gameComponent.commands.actionComponent.entity;


import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;

/**
 * Created by com.andres_k on 17/03/2016.
 */
public class GenkidamaActions {

    public static void stopOnExplode(GameObject object) {
        object.getMovement().setPushX(GameConfig.speedTravel / 3f);
    }
}

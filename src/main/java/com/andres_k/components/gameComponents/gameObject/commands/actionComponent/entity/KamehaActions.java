package com.andres_k.components.gameComponents.gameObject.commands.actionComponent.entity;

import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.utils.configs.GameConfig;

/**
 * Created by andres_k on 23/02/2016.
 */
public class KamehaActions {
    public static void speAttack(GameObject object) {
        object.getMovement().setPushX(GameConfig.speedTravel * 2);
    }
}

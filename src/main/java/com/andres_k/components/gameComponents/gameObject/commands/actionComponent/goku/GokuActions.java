package com.andres_k.components.gameComponents.gameObject.commands.actionComponent.goku;

import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.BasicActions;
import com.andres_k.utils.configs.GameConfig;

/**
 * Created by andres_k on 30/11/2015.
 */
public class GokuActions extends BasicActions {

    // ATTACKS

    public static void handAttack(GameObject object) {
        if (object.getAnimatorController().getIndex() == 1 && object.getAnimatorController().currentAnimation().getFrame() == 0) {
            object.getMovement().setUseGravity(true);
            object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
            object.getMovement().addPushX(GameConfig.speedTravel * 2);
        } else {
            object.getMovement().setPushX(0);
        }
    }

    public static void handFlyPropels(GameObject object) {
        object.getMovement().addPushY(-0.1f);
        object.getMovement().setUseGravity(false);
        if (object.getAnimatorController().currentAnimation().getFrame() >= 3) {
            object.getMovement().setPushX(GameConfig.speedTravel / 2);
        } else {
            object.getMovement().setPushX(GameConfig.speedTravel * 2);
        }
    }

    // KAMEHAMEHA
    public static void speAttack1(GameObject object) {

    }
}

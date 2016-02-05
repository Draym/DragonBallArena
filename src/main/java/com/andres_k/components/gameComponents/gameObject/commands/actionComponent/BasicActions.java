package com.andres_k.components.gameComponents.gameObject.commands.actionComponent;

import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.utils.configs.GameConfig;

/**
 * Created by andres_k on 17/11/2015.
 */
public class BasicActions {

    // ACTIONS
    public static void idle(GameObject object) {
        object.getMovement().setUseGravity(true);
        object.getMovement().stopMovement();
    }

    public static void defense(GameObject object) {
        object.getMovement().setPushX(0f);
        object.getMovement().setPushY(0f);
    }

    public static void block(GameObject object) {
        object.getMovement().setPushX(GameConfig.speedTravel / 5);
        object.getMovement().setPushY(0f);
    }

    public static void receipt(GameObject object) {
        object.getMovement().setUseGravity(true);
        object.getMovement().setPushX(0f);
    }

    public static void rush(GameObject object) {
        object.getMovement().setUseGravity(false);
        if (object.isOnEarth()) {
            object.getMovement().addPushY(10);
        }

        if (object.getMovement().getMoveDirection() != EDirection.NONE) {
            object.getMovement().setPushX(GameConfig.speedTravel * 3);
        }
        object.getMovement().setPushY(0);
    }

    // MOVEMENT
    public static void fall(GameObject object) {
        object.getMovement().setPushY(0f);

        if (object.getMovement().getMoveDirection() != EDirection.NONE) {
            object.getMovement().setPushX(GameConfig.speedTravel);
        }
        if (!object.isOnEarth()) {
            if (object.getMovement().getGravity() > 8) {
                object.getAnimatorController().getCurrentContainer().getConfig().setNextIndex(1);
            }
        }
        if (object.isOnEarth()) {
            object.getAnimatorController().toNextAnimation();
        }
    }

    public static void run(GameObject object) {
        object.getMovement().setUseGravity(true);
        object.getMovement().addPushY(-0.2f);
        object.getMovement().setPushY(0);
        if (object.getMovement().getMoveDirection() != EDirection.NONE) {
            object.getMovement().setPushX(GameConfig.speedTravel);
        }
    }

    public static void jump(GameObject object) {
        if (object.getMovement().getMoveDirection() != EDirection.NONE) {
            object.getMovement().setPushX(GameConfig.speedTravel);
        }
        object.getMovement().setPushY(-GameConfig.speedJump);
    }
}

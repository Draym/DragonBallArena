package com.andres_k.components.gameComponents.gameObject.commands.actions;

import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.collisions.EnumDirection;
import com.andres_k.utils.configs.GlobalVariable;

/**
 * Created by andres_k on 17/11/2015.
 */
public class Actions {

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
        if (object.getAnimatorController().getDirection() == EnumDirection.LEFT)
            object.getMovement().setPushX(-(GlobalVariable.speedTravel / 5));
        else if (object.getAnimatorController().getDirection() == EnumDirection.RIGHT)
            object.getMovement().setPushX((GlobalVariable.speedTravel / 5));
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
        if (object.getAnimatorController().getDirection() == EnumDirection.LEFT)
            object.getMovement().setPushX(-GlobalVariable.speedTravel * 3);
        else
            object.getMovement().setPushX(GlobalVariable.speedTravel * 3);
        object.getMovement().setPushY(0);
    }

    // MOVEMENT
    public static void fall(GameObject object) {
        object.getMovement().setPushY(0f);

        if (object.getAnimatorController().getDirection() == EnumDirection.LEFT)
            object.getMovement().setPushX(-GlobalVariable.speedTravel);
        else if (object.getAnimatorController().getDirection() == EnumDirection.RIGHT)
            object.getMovement().setPushX(GlobalVariable.speedTravel);

        if (!object.isOnEarth()) {
            if (object.getMovement().getGravity() > 8) {
                object.getAnimatorController().getCurrent().getConfig().setNextIndex(1);
            }
        }
        if (object.isOnEarth()) {
            object.getAnimatorController().toCurrentNextAnimation();
        }
    }

    public static void run(GameObject object) {
        object.getMovement().setUseGravity(false);
        if (object.isOnEarth()) {
            object.getMovement().addPushY(10);
        }
        if (object.getAnimatorController().getDirection() == EnumDirection.LEFT)
            object.getMovement().setPushX(-GlobalVariable.speedTravel);
        else
            object.getMovement().setPushX(GlobalVariable.speedTravel);
        object.getMovement().setPushY(0);
    }

    public static void jump(GameObject object) {
        if (object.getAnimatorController().getDirection() == EnumDirection.LEFT)
            object.getMovement().setPushX(-GlobalVariable.speedTravel);
        else if (object.getAnimatorController().getDirection() == EnumDirection.RIGHT)
            object.getMovement().setPushX(GlobalVariable.speedTravel);
        object.getMovement().setPushY(-GlobalVariable.speedJump);
    }
}

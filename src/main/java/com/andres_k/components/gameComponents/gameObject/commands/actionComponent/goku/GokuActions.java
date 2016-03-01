package com.andres_k.components.gameComponents.gameObject.commands.actionComponent.goku;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.BasicActions;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 30/11/2015.
 */
public class GokuActions extends BasicActions {

    // ATTACKS

    public static void handAttack(GameObject object) {
        try {
            if (object.getAnimatorController().getIndex() == 1 && object.getAnimatorController().currentAnimation().getFrame() == 0) {
                object.getMovement().setUseGravity(true);
                object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
                object.getMovement().addPushX(GameConfig.speedTravel * 2);
            } else {
                object.getMovement().setPushX(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rushAttack(GameObject object) {
        object.getMovement().setPushY(0);
        object.getMovement().addPushY(-0.25f);
        object.getMovement().setUseGravity(true);
        object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
        if (object.getAnimatorController().getIndex() == 1) {
            object.getMovement().setPushX(GameConfig.speedTravel * 2);
        } else {
            object.getMovement().setPushX(0);
        }
    }

    public static void handFlyPropels(GameObject object) {
        try {
            object.getMovement().addPushY(-0.1f);
            object.getMovement().setUseGravity(false);
            if (object.getAnimatorController().currentAnimation().getFrame() >= 3) {
                object.getMovement().setPushX(GameConfig.speedTravel / 2);
            } else {
                object.getMovement().setPushX(GameConfig.speedTravel * 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void kiBasicAttack(GameObject object) {
        try {
            if (object.getAnimatorController().currentAnimation().getFrame() == object.getAnimatorController().currentAnimation().getFrameCount() - 1) {
                object.doTask(new Pair<>(ETaskType.CREATE, EGameObject.KI_BLAST.toString()));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    // KAMEHAMEHA
    public static void kiSpeAttack(GameObject object) {
        try {
            object.getMovement().setPushY(GameConfig.speedJump / 4);
            if (object.getAnimatorController().currentAnimation().getFrame() == 5 && object.isOnEarth()) {
                object.getMovement().stopMovement();
                object.getMovement().setUseGravity(false);
                object.doTask(new Pair<>(ETaskType.CREATE, EGameObject.KAMEHA.toString()));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

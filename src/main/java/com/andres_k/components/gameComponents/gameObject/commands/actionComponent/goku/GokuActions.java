package com.andres_k.components.gameComponents.gameObject.commands.actionComponent.goku;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.BasicActions;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
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
                object.getMovement().addPushX(GameConfig.speedTravel * 3f);
            } else {
                object.getMovement().setPushX(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void rushAttack(GameObject object) {
        object.getMovement().addPushY(-0.25f);
        object.getMovement().setPushY(0);
        object.getMovement().setUseGravity(true);
        try {
            if (object.getAnimatorController().currentAnimation().getFrame() == 0) {
                object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
        if (object.getAnimatorController().getIndex() == 1) {
            object.getMovement().setPushX(GameConfig.speedTravel * 2f);
        } else {
            object.getMovement().setPushX(0);
        }
    }

    public static void handFlyPropels(GameObject object) {
        try {
            object.getMovement().addPushY(-0.1f);
            object.getMovement().setUseGravity(false);
            if (object.getAnimatorController().currentAnimation().getFrame() >= 3) {
                object.getMovement().setPushX(GameConfig.speedTravel / 2f);
            } else {
                object.getMovement().setPushX(GameConfig.speedTravel * 2f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void jumpKickAttack(GameObject object) {
        try {
            object.getMovement().setPushY(0);
            object.getMovement().setUseGravity(false);
            if (object.getAnimatorController().getIndex() == 0 && object.getAnimatorController().currentAnimation().getFrame() == 0) {
                object.getMovement().setPushX(0);
            } else if (object.getAnimatorController().getIndex() == 0 && object.getAnimatorController().currentAnimation().getFrame() == 2) {
                object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
                object.getMovement().setPushX(GameConfig.speedTravel * 2f);
                object.getMovement().setPushY(-GameConfig.speedTravel);
            } else if (object.getAnimatorController().getIndex() == 2 && object.getAnimatorController().currentAnimation().getFrame() == 1) {
                object.getMovement().setPushX(0);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void spiralKickAttack(GameObject object) {
        try {
            object.getMovement().setPushY(0);
            object.getMovement().setUseGravity(false);
            if (object.getAnimatorController().getIndex() == 0 && object.getAnimatorController().currentAnimation().getFrame() == 0) {
                object.getMovement().setPushX(0);
            } else if (object.getAnimatorController().getIndex() == 0 && object.getAnimatorController().currentAnimation().getFrame() == 2) {
                object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
                object.getMovement().setPushX(GameConfig.speedTravel * 2f);
                object.getMovement().setPushY(-GameConfig.speedTravel);
            } else if (object.getAnimatorController().getIndex() == 2) {
                object.getMovement().setPushX(0);
                object.getMovement().setPushY(-GameConfig.speedTravel / 5f);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void kickPropelsAttack(GameObject object) {
            object.getMovement().setPushY(-GameConfig.speedTravel / 4f);
    }

    public static void kiChargeAction(GameObject object) {
        try {
            if (object.getAnimatorController().currentAnimation().getFrame() == 0) {
                object.doTask(new Tuple<>(ETaskType.ADD, "ki", 50));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void kiBasicAttack(GameObject object) {
        try {
            object.getMovement().setUseGravity(false);
            object.getMovement().setPushY(GameConfig.speedTravel / 3f);
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
            if (object.getAnimatorController().currentAnimation().getFrame() == 5) {
                object.getMovement().stopMovement();
                object.getMovement().setUseGravity(false);
                object.doTask(new Pair<>(ETaskType.CREATE, EGameObject.KAMEHA.toString()));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

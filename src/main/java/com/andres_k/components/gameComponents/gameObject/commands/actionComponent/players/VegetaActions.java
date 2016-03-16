package com.andres_k.components.gameComponents.gameObject.commands.actionComponent.players;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.BasicActions;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 14/03/2016.
 */
public class VegetaActions extends BasicActions {

    public static void handAttack(GameObject object) {
        try {
            if (object.getAnimatorController().currentAnimation().getFrame() == 0) {
                object.getMovement().setUseGravity(true);
                object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
                object.getMovement().addPushX(GameConfig.speedTravel * 4f);
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
        object.getMovement().setPushX(GameConfig.speedTravel * 2f);
        try {
            if (object.getAnimatorController().currentAnimation().getFrame() == 0) {
                object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
            }
        } catch (SlickException e) {
            e.printStackTrace();
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

    public static void kickPropelsAttack(GameObject object) {
        try {
            if (object.getAnimatorController().currentAnimation().getFrame() == object.getAnimatorController().currentAnimation().getFrameCount() - 1) {
                object.getMovement().stopMovement();
            } else {
                object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
                object.getMovement().setPushX(GameConfig.speedTravel);
                object.getMovement().setPushY(-GameConfig.speedTravel / 3f);
                object.getMovement().setUseGravity(false);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
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
            object.getMovement().setPushX(0f);
            object.getMovement().setPushY(GameConfig.speedTravel / 3f);
            if (object.getAnimatorController().currentAnimation().getFrame() == 1 || object.getAnimatorController().currentAnimation().getFrame() == 3) {
                object.doTask(new Pair<>(ETaskType.CREATE, EGameObject.KI_BLAST.toString()));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void kiSpeAttack(GameObject object) {
        try {
            object.getMovement().stopMovement();
            object.getMovement().setUseGravity(false);
            if (object.getAnimatorController().currentAnimation().getFrame() == 3) {
                object.doTask(new Pair<>(ETaskType.CREATE, EGameObject.KI_BURST.toString()));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void kiFinalAttack(GameObject object) {
        try {
            object.getMovement().stopMovement();
            object.getMovement().setUseGravity(false);
            if (object.getAnimatorController().currentAnimation().getFrame() == 6) {
                object.doTask(new Pair<>(ETaskType.CREATE, EGameObject.KI_FINAL.toString()));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

package com.andres_k.components.gameComponents.gameObject.commands.actionComponent.players;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 20/03/2016.
 */
public class GokuS1Actions extends GokuActions {

    public static void kiChargeAction(GameObject object) {
        try {
            if (object.getAnimatorController().currentAnimation().getFrame() == 0) {
                object.doTask(new Tuple<>(ETaskType.ADD, "ki", 100f));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void kiFinalAttack(GameObject object) {
        try {
            object.getMovement().setPushY(0f);
            object.getMovement().setPushX(0f);
            object.getMovement().setUseGravity(false);
            if (object.getAnimatorController().currentAnimation().getFrame() == 6 ||
                    object.getAnimatorController().currentAnimation().getFrame() == 8) {
                object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
                object.getMovement().addPushX(GameConfig.speedTravel * 5f);
            }
            if (object.getAnimatorController().currentAnimation().getFrame() == 9) {
                object.doTask(new Pair<>(ETaskType.CREATE, EGameObject.MEGA_KAMEHA.toString()));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

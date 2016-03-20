package com.andres_k.components.gameComponents.gameObject.commands.actionComponent.players;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 20/03/2016.
 */
public class VegetaS1Actions extends VegetaActions {

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
            object.getMovement().stopMovement();
            object.getMovement().setUseGravity(false);
            if (object.getAnimatorController().currentAnimation().getFrame() == 3) {
                object.doTask(new Pair<>(ETaskType.CREATE, EGameObject.KI_FINAL.toString()));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}

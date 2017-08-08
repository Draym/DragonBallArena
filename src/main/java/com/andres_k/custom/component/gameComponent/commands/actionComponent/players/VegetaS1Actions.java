package com.andres_k.custom.component.gameComponent.commands.actionComponent.players;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.stockage.Tuple;
import org.newdawn.slick.SlickException;

/**
 * Created by com.andres_k on 20/03/2016.
 */
public class VegetaS1Actions extends VegetaActions {

    public static void kiChargeAction(GameObject object) {
        try {
            object.getMovement().setPushX(0f);
            object.getMovement().setPushY(0f);
            if (object.getAnimatorController().currentAnimation().getFrame() == 0) {
                object.doTask(new Tuple<>(ETaskType.ADD, "ki", 200f));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void kiFinalAttack(GameObject object) {
        try {
            object.getMovement().stopMovement();
            object.getMovement().setUseGravity(false);
            if (object.getAnimatorController().currentAnimation().getFrame() == 2) {
                object.doTask(new Pair<>(ETaskType.CREATE, EGameObject.BIG_BANG.toString()));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}

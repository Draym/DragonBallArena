package com.andres_k.components.gameComponents.gameObject.objects.entities.ki.vulnerable;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.KiVulnerableAttack;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;

/**
 * Created by andres_k on 17/03/2016.
 */
public class Genkidama extends KiVulnerableAttack {

    public Genkidama(AnimatorController animatorController, String parent, float x, float y, EDirection direction, float damage, float speed, float weight) {
        super(animatorController, EGameObject.GENKIDAMA, parent, x, y, direction, damage, speed, weight);
    }

    @Override
    public boolean die() {
        if (super.die()) {
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(this.parent, new Pair<>(ETaskType.NEXT, "frame"))));
            return true;
        }
        return false;
    }
}

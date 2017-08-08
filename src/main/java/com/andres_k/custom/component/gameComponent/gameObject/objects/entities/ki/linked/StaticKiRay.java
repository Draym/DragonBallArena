package com.andres_k.custom.component.gameComponent.gameObject.objects.entities.ki.linked;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.gameComponent.gameObject.objects.entities.ki.KiLinkedAttack;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.gameToolsLib.components.taskComponent.utils.TaskComponent;
import com.andres_k.gameToolsLib.utils.stockage.Pair;

/**
 * Created by com.andres_k on 01/03/2016.
 */
public class StaticKiRay extends KiLinkedAttack {

    public StaticKiRay(AnimatorController head, AnimatorController body, AnimatorController back, EGameObject type, String parent, float x, float y, EDirection direction, float damage, float speed, float weight, float sizeBody) {
        super(head, body, back, type, parent, x, y, direction, damage, speed, weight, sizeBody);
        this.saveX = x;
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

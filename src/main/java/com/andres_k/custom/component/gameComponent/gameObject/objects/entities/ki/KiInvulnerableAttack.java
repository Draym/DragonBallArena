package com.andres_k.custom.component.gameComponent.gameObject.objects.entities.ki;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.gameComponent.gameObject.objects.entities.KiEntity;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;

/**
 * Created by com.andres_k on 15/03/2016.
 */
public class KiInvulnerableAttack extends KiEntity {
    public KiInvulnerableAttack(AnimatorController animatorController, EGameObject type, String parent, float x, float y, EDirection direction, float damage, float speed, float weight) {
        super(animatorController, type, parent, x, y, direction, damage, speed, weight);
    }
}

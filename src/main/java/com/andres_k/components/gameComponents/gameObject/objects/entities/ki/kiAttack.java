package com.andres_k.components.gameComponents.gameObject.objects.entities.ki;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.entities.KiEntity;

/**
 * Created by andres_k on 15/03/2016.
 */
public class KiAttack extends KiEntity {
    public KiAttack(AnimatorController animatorController, EGameObject type, String parent, float x, float y, EDirection direction, float damage, float speed, float weight) {
        super(animatorController, type, parent, x, y, direction, damage, speed, weight);
    }
}

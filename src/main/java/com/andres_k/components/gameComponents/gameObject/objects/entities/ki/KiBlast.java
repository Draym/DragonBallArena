package com.andres_k.components.gameComponents.gameObject.objects.entities.ki;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.entities.KiEntity;

/**
 * Created by andres_k on 01/03/2016.
 */
public class KiBlast extends KiEntity {
    public KiBlast(AnimatorController animatorController, String parent, float x, float y, EDirection direction, float damage, float speed) {
        super(animatorController, EGameObject.KI_BLAST, parent, (direction == EDirection.RIGHT ? x : x - 300), y - 50, direction, damage, damage, speed, 0);
    }

    @Override
    public void powerAfterDoDamage(float power) {
        this.damage = power;
        this.currentLife = power;
        if (this.currentLife <= 0) {
            this.die();
        }
    }
}

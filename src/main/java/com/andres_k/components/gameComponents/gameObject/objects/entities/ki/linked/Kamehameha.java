package com.andres_k.components.gameComponents.gameObject.objects.entities.ki.linked;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.KiLinkedAttack;

/**
 * Created by andres_k on 01/03/2016.
 */
public class Kamehameha extends KiLinkedAttack {
    public Kamehameha(AnimatorController head, AnimatorController body, AnimatorController back, String parent, float x, float y, EDirection direction, float damage, float speed) {
        super(head, body, back, EGameObject.KAMEHA, parent, (direction == EDirection.RIGHT ? x : x - 220), y, direction, damage, damage, speed, 0);
        if (direction == EDirection.RIGHT) {
            this.saveX = x - 140;
        } else {

            this.saveX = x - 470;
        }
    }
}

package com.andres_k.components.gameComponents.gameObject.objects.entities.ki.linked;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;

/**
 * Created by andres_k on 16/03/2016.
 */
public class Kameha extends StaticKiRay {
    public Kameha(AnimatorController head, AnimatorController body, AnimatorController back, String parent, float headX, float headY, float backX, EDirection direction, float damage, float speed) {
        super(head, body, back, EGameObject.FINAL_FLASH, parent, headX, headY, direction, damage, speed, 0);
        this.saveX = backX;
    }

    public Kameha(AnimatorController head, AnimatorController body, AnimatorController back, String parent, float x, float y, EDirection direction, float damage, float speed) {
        super(head, body, back, EGameObject.KAMEHA, parent, (direction == EDirection.RIGHT ? x : x - 220), y, direction, damage, speed, 0);
        if (direction == EDirection.RIGHT) {
            this.saveX = x - 140;
        } else {

            this.saveX = x - 470;
        }
    }
}

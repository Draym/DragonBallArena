package com.andres_k.components.gameComponents.gameObject.objects.entities.ki.linked;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;

/**
 * Created by andres_k on 16/03/2016.
 */
public class FinalFlash extends StaticKiRay {
    public FinalFlash(AnimatorController head, AnimatorController body, AnimatorController back, String parent, float headX, float headY, float backX, EDirection direction, float damage, float speed) {
        super(head, body, back, EGameObject.FINAL_FLASH, parent, headX, headY, direction, damage, speed, 0);
        this.saveX = backX;
    }

    protected float getMaxBodyX() {
        if (this.bodiesAnim.isEmpty())
            return (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.saveX + 130 : this.saveX - 130);
        else {
            return super.getMaxBodyX();
        }
    }
}

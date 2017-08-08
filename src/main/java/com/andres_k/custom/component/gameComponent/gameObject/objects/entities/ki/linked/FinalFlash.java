package com.andres_k.custom.component.gameComponent.gameObject.objects.entities.ki.linked;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;

/**
 * Created by com.andres_k on 16/03/2016.
 */
public class FinalFlash extends StaticKiRay {
    public FinalFlash(AnimatorController head, AnimatorController body, AnimatorController back, String parent, float headX, float headY, float backX, EDirection direction, float damage, float speed) {
        super(head, body, back, EGameObject.FINAL_FLASH, parent, headX, headY, direction, damage, speed, 0, 60 * GameConfig.scaleGameSprite);
        this.saveX = backX;
    }

    protected float getMaxBodyX() {
        if (this.bodiesAnim.isEmpty())
            return (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.saveX + 130 * GameConfig.scaleGameSprite : this.saveX - 130 * GameConfig.scaleGameSprite);
        else {
            return super.getMaxBodyX();
        }
    }
}

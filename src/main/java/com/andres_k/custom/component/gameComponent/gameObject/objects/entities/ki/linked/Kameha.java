package com.andres_k.custom.component.gameComponent.gameObject.objects.entities.ki.linked;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;

/**
 * Created by com.andres_k on 16/03/2016.
 */
public class Kameha extends StaticKiRay {
    public Kameha(AnimatorController head, AnimatorController body, AnimatorController back, String parent, float headX, float headY, float backX, EDirection direction, float damage, float speed) {
        super(head, body, back, EGameObject.FINAL_FLASH, parent, headX, headY, direction, damage, speed, 0, 60 * GameConfig.scaleGameSprite);
        this.saveX = backX;
    }

    public Kameha(AnimatorController head, AnimatorController body, AnimatorController back, String parent, float x, float y, EDirection direction, float damage, float speed) {
        super(head, body, back, EGameObject.KAMEHA, parent, (direction == EDirection.RIGHT ? x : x - 200), y, direction, damage, speed, 0, 60 * GameConfig.scaleGameSprite);
        this.saveX = (direction == EDirection.RIGHT ? x - 140 : x - 470);
    }
}

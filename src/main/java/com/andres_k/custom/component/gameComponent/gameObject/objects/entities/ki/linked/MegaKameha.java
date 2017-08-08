package com.andres_k.custom.component.gameComponent.gameObject.objects.entities.ki.linked;


import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;

/**
 * Created by com.andres_k on 16/03/2016.
 */
public class MegaKameha extends StaticKiRay {
    public MegaKameha(AnimatorController head, AnimatorController body, AnimatorController back, String parent, float headX, float headY, float backX, EDirection direction, float damage, float speed) {
        super(head, body, back, EGameObject.MEGA_KAMEHA, parent, headX, headY, direction, damage, speed, 0, 60 * GameConfig.scaleGameSprite);
        this.saveX = backX;
    }

}

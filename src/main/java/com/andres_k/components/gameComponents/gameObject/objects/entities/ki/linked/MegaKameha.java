package com.andres_k.components.gameComponents.gameObject.objects.entities.ki.linked;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.utils.configs.GameConfig;

/**
 * Created by andres_k on 16/03/2016.
 */
public class MegaKameha extends StaticKiRay {
    public MegaKameha(AnimatorController head, AnimatorController body, AnimatorController back, String parent, float headX, float headY, float backX, EDirection direction, float damage, float speed) {
        super(head, body, back, EGameObject.MEGA_KAMEHA, parent, headX, headY, direction, damage, speed, 0, 60 * GameConfig.scaleGameSprite);
        this.saveX = backX;
    }

}

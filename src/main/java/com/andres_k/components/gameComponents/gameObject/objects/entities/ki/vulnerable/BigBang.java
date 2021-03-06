package com.andres_k.components.gameComponents.gameObject.objects.entities.ki.vulnerable;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.KiVulnerableAttack;
import com.andres_k.utils.configs.GameConfig;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 17/03/2016.
 */
public class BigBang extends KiVulnerableAttack {

    public BigBang(AnimatorController animatorController, String parent, float x, float y, EDirection direction, float damage, float speed, float weight) {
        super(animatorController, EGameObject.BIG_BANG, parent, x, y, direction, damage, speed, weight);
        this.movement.setPushX(0f);
    }

    @Override
    public void update() throws SlickException {
        super.update();
        if (this.movement.getPushX() == 0f && this.animatorController.currentAnimationType() == EAnimation.RUN) {
            this.movement.setPushX(GameConfig.speedTravel);
        }
        if (this.animatorController.currentAnimationType() == EAnimation.EXPLODE) {
            this.movement.setPushX(GameConfig.speedTravel / 4);
            this.movement.setMoveSpeed(this.movement.getMoveSpeed() / 2);
        }
    }
}

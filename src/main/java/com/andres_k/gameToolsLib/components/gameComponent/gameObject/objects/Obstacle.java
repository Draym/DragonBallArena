package com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.gameToolsLib.components.gameComponent.collisions.PhysicalObject;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import org.newdawn.slick.SlickException;

/**
 * Created by com.andres_k on 10/07/2015.
 */
public class Obstacle extends PhysicalObject {

    public Obstacle(AnimatorController animatorController, EGameObject type, String id, float posX, float posY, float life, float damage, float speed, float weight) {
        super(animatorController, type, id, posX, posY, life, damage, speed, speed, weight);
    }

    @Override
    public void clear() {
    }

    @Override
    public void update() throws SlickException {
        super.update();
        this.animatorController.update();
    }

    @Override
    public void eventPressed(EInput input) {
    }

    @Override
    public void eventReleased(EInput input) {
    }

    @Override
    public Object doTask(Object task) {
        return null;
    }

    @Override
    public void manageDoHit(GameObject enemy) {
        if (enemy.getAnimatorController().currentAnimationType() == EAnimation.JUMP && !enemy.isOnEarth()) {
            enemy.getAnimatorController().toNextAnimation();
        }
    }
}

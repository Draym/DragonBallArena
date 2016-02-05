package com.andres_k.components.gameComponents.gameObject.objects;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.collisions.PhysicalObject;
import com.andres_k.components.gameComponents.gameObject.EGameObject;

/**
 * Created by andres_k on 10/07/2015.
 */
public class Obstacle extends PhysicalObject {

    public Obstacle(AnimatorController animatorController, EGameObject type, String id, float posX, float posY, float life, float damage, float speed, float weight) {
        super(animatorController, type, id, posX, posY, life, damage, speed, weight);
    }

    @Override
    public void clear() {
    }

    @Override
    public void update() {
        this.updateAnimation();
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
}

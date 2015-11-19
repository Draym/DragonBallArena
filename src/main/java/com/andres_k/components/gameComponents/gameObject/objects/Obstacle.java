package com.andres_k.components.gameComponents.gameObject.objects;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.gameComponents.animations.container.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.utils.stockage.Pair;

/**
 * Created by andres_k on 10/07/2015.
 */
public class Obstacle extends GameObject {

    public Obstacle(AnimatorController animatorController, String id, EnumGameObject type, float posX, float posY, float life, float damage, float speed, float weight) {
        super(animatorController, id, type,new Pair<>(posX, posY), life, damage, speed, weight);
    }

    @Override
    public void clear() {
    }

    @Override
    public void update() {
        this.updateAnimation();
    }

    @Override
    public void eventPressed(EnumInput input) {
    }

    @Override
    public void eventReleased(EnumInput input) {
    }

    @Override
    public Object doTask(Object task) {
        return null;
    }
}

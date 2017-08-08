package com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.collisions.PhysicalObject;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import org.newdawn.slick.SlickException;

/**
 * Created by kevin on 16/04/2017.
 */
public class Character extends PhysicalObject {

    protected Character(AnimatorController animatorController, EGameObject type, String id, float x, float y, float life, float damage, float moveSpeed, float gravitySpeed, float weight) {
        super(animatorController, type, id, x, y, life, damage, moveSpeed, gravitySpeed, weight);
    }

    @Override
    public void clear() {

    }

    @Override
    public void update() throws SlickException {
        this.animatorController.update();
        this.animatorController.updateAnimation(this);
        this.movement.update();
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

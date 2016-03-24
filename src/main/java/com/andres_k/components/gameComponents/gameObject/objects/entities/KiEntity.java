package com.andres_k.components.gameComponents.gameObject.objects.entities;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.collisions.PhysicalObject;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.tools.Console;
import org.newdawn.slick.SlickException;

import java.util.UUID;

/**
 * Created by andres_k on 01/03/2016.
 */
public abstract class KiEntity extends PhysicalObject {
    protected String parent;

    protected KiEntity(AnimatorController animatorController, EGameObject type, String parent, float x, float y, EDirection direction, float damage, float speed, float weight) {
        super(animatorController, type, parent + GlobalVariable.id_delimiter + UUID.randomUUID().toString(), x, y, damage, damage, speed, speed, weight);
        this.movement.setMoveDirection(direction);
        this.movement.setPushX(GameConfig.speedTravel);
        this.animatorController.setEyesDirection(direction);
        this.parent = parent;
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

    @Override
    public void manageEachCollisionExceptValidHit(EGameObject mine, GameObject enemy, EGameObject him) {
        if (him == EGameObject.BLOCK_BODY && enemy.getType().isIn(EGameObject.DEADPAN)) {
            Console.write(this.type + " die");
            this.die();
        }
    }

    @Override
    public void manageMutualHit(GameObject enemy) {
        if (!this.wasHit) {
            float saveDamage = this.damage;
            super.manageGetHit(enemy);
            this.damage = saveDamage;
            enemy.manageGetHit(this);
            this.damage = this.currentLife;
            this.wasHit = true;
            this.resetHitStatus();
        }
    }

    @Override
    public void manageGetHit(GameObject enemy) {
        super.manageGetHit(enemy);
        this.damage = this.currentLife;
    }
}

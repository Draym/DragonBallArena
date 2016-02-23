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
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;

import java.util.UUID;

/**
 * Created by andres_k on 23/02/2016.
 */
public class Kamehameha extends PhysicalObject {
    private Animation body;
    private Animation back;
    private String parent;

    public Kamehameha(AnimatorController head, String parent, float x, float y, EDirection direction, float damage, float speed) {
        super(head, EGameObject.KAMEHA, parent + GlobalVariable.id_delimiter + UUID.randomUUID().toString(), x, y, damage, damage, speed, 0);
        this.movement.setMoveDirection(direction);
        this.animatorController.setEyesDirection(direction);
        this.parent = parent;
        this.movement.setPushX(GameConfig.speedTravel);
    }

    @Override
    public void clear() {

    }

    @Override
    public void update() throws SlickException {
        this.animatorController.update();
        this.animatorController.doCurrentAction(this);
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
    public void manageEachCollisionExceptHit(EGameObject mine, GameObject enemy, EGameObject him) {
        if (him == EGameObject.BLOCK_BODY && enemy.getType().isIn(EGameObject.DEADPAN)) {
            Console.write("KAMEHA DIE");
            this.die();
        }
    }

}

package com.andres_k.components.gameComponents.gameObject.objects.entities;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.collisions.PhysicalObject;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
import com.andres_k.utils.tools.MathTools;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by andres_k on 23/02/2016.
 */
public class Kamehameha extends PhysicalObject {
    private List<Pair<Float, AnimatorController>> bodiesAnim;
    private AnimatorController body;
    private AnimatorController back;
    private boolean canCreateBodies;
    private float saveX;

    public Kamehameha(AnimatorController head, AnimatorController body, AnimatorController back, String parent, float x, float y, EDirection direction, float damage, float speed) {
        super(head, EGameObject.KAMEHA, parent + GlobalVariable.id_delimiter + UUID.randomUUID().toString(), (direction == EDirection.RIGHT ? x : x - 300), y + 10, damage, damage, speed, 0);
        this.movement.setMoveDirection(direction);
        this.animatorController.setEyesDirection(direction);
        this.body = body;
        this.back = back;
        this.back.setPrintable(false);
        this.back.setEyesDirection(direction);
        this.body.setPrintable(false);
        this.body.setEyesDirection(direction);
        this.bodiesAnim = new ArrayList<>();
        this.canCreateBodies = false;
        if (direction == EDirection.RIGHT) {
            this.saveX = x - 140;
        } else {

            this.saveX = x - 470;
        }
    }

    @Override
    public void clear() {
        this.bodiesAnim.clear();
    }

    @Override
    public void draw(Graphics g) throws SlickException {
        for (Pair<Float, AnimatorController> item : this.bodiesAnim) {
            if (item.getV1() != this.saveX) {
                item.getV2().draw(g, item.getV1(), this.graphicalY());
            }
        }
        if (this.body != null) {
            this.body.draw(g, this.getMaxBodyX(), this.graphicalY());
        }
        if (this.back != null) {
            this.back.draw(g, this.saveX, this.graphicalY());
        }
        super.draw(g);
    }

    @Override
    public void update() throws SlickException {
        this.animatorController.update();
        this.animatorController.doCurrentAction(this);
        this.movement.update();
        this.back.update();
        this.body.update();
        this.bodiesAnim.stream().forEach(item -> item.getV2().update());
        if (this.animatorController.currentAnimationType() == EAnimation.SPE_ATTACK_1) {
            this.body.setPrintable(true);
            this.back.setPrintable(true);
            this.canCreateBodies = true;
        }
        this.createNewBodySegment();
    }

    @Override
    public boolean isNeedDelete() {
        return (this.animatorController == null || this.back.isDeleted());
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
    public void die() {
        super.die();
        this.back.setCurrentAnimationType(EAnimation.EXPLODE);
        this.body.setCurrentAnimationType(EAnimation.EXPLODE);
        for (Pair<Float, AnimatorController> item : this.bodiesAnim) {
            item.getV2().setCurrentAnimationType(EAnimation.EXPLODE);
        }
    }

    @Override
    public void manageEachCollisionExceptHit(EGameObject mine, GameObject enemy, EGameObject him) {
        if (him == EGameObject.BLOCK_BODY && enemy.getType().isIn(EGameObject.DEADPAN)) {
            Console.write("KAMEHA DIE");
            this.die();
        }
    }

    private void createNewBodySegment() throws SlickException {
        if (this.body != null && this.canCreateBodies) {
            if (MathTools.abs(this.graphicalX() - this.getMaxBodyX()) >= 60) {
                this.bodiesAnim.add(new Pair<>(this.getMaxBodyX(), new AnimatorController(this.body)));
            }
        }
    }

    private float getMaxBodyX() {
        if (this.bodiesAnim.isEmpty())
            return this.saveX;
        if (this.animatorController.getEyesDirection() == EDirection.RIGHT) {
            return this.bodiesAnim.get(this.bodiesAnim.size() - 1).getV1() + 60;
        } else {
            return this.bodiesAnim.get(this.bodiesAnim.size() - 1).getV1() - 60;
        }
    }

}

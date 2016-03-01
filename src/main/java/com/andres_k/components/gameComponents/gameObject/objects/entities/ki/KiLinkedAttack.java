package com.andres_k.components.gameComponents.gameObject.objects.entities.ki;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.entities.KiEntity;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.MathTools;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 23/02/2016.
 */
public abstract class KiLinkedAttack extends KiEntity {
    protected List<Pair<Float, AnimatorController>> bodiesAnim;
    protected AnimatorController body;
    protected AnimatorController back;
    protected boolean canCreateBodies;
    protected float saveX;

    protected KiLinkedAttack(AnimatorController head, AnimatorController body, AnimatorController back, EGameObject type, String parent, float x, float y, EDirection direction, float life, float damage, float speed, float weight) {
        super(head, type, parent, x, y, direction, life, damage, speed, weight);
        this.body = body;
        this.back = back;
        this.back.setPrintable(false);
        this.back.setEyesDirection(direction);
        this.body.setPrintable(false);
        this.body.setEyesDirection(direction);
        this.bodiesAnim = new ArrayList<>();
        this.canCreateBodies = false;
        this.parent = parent;
        this.movement.setPushX(0f);
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
        super.update();
        this.back.update();
        this.body.update();
        this.bodiesAnim.stream().forEach(item -> item.getV2().update());
        if (this.animatorController.currentAnimationType() == EAnimation.KI_SPE_ATTACK) {
            this.movement.setPushX(GameConfig.speedTravel);
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
    public void die() {
        super.die();
        CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(this.parent, new Pair<>(ETaskType.NEXT, "frame"))));
        this.back.forceCurrentAnimationType(EAnimation.EXPLODE);
        this.body.forceCurrentAnimationType(EAnimation.EXPLODE);
        for (Pair<Float, AnimatorController> item : this.bodiesAnim) {
            item.getV2().forceCurrentAnimationType(EAnimation.EXPLODE);
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

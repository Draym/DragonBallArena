package com.andres_k.components.gameComponents.gameObject.objects.entities.ki;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.entities.KiEntity;
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

    public KiLinkedAttack(AnimatorController head, AnimatorController body, AnimatorController back, EGameObject type, String parent, float x, float y, EDirection direction, float damage, float speed, float weight) {
        super(head, type, parent, x, y, direction, damage, speed, weight);
        this.body = body;
        this.back = back;
        if (this.back != null) {
            this.back.setPrintable(false);
            this.back.setEyesDirection(direction);
        }
        if (this.body != null) {
            this.body.setPrintable(false);
            this.body.setEyesDirection(direction);
        }
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
        super.draw(g);
        if (this.back != null) {
            this.back.draw(g, this.saveX, this.graphicalY());
        }
    }

    @Override
    public void update() throws SlickException {
        super.update();
        if (this.back != null) {
            this.back.update();
            this.back.updateAnimation(this);
        }
        if (this.body != null) {
            this.body.update();
            this.body.updateAnimation(this);
        }
        this.bodiesAnim.stream().forEach(item -> item.getV2().update());
        if (this.animatorController.currentAnimationType() == EAnimation.RUN) {
            this.movement.setPushX(GameConfig.speedTravel);
            if (this.body != null) {
                this.body.setPrintable(true);
            }
            if (this.back != null) {
                this.back.setPrintable(true);
            }
            this.canCreateBodies = true;
        }
        this.createNewBodySegment();
        this.deleteKilledElement();
    }

    @Override
    public boolean isNeedDelete() {
        return (this.animatorController == null || (this.back != null && this.back.isDeleted()) || (this.back == null && this.animatorController.isDeleted()));
    }

    @Override
    public boolean die() {
        if (super.die()) {
            if (this.back != null) {
                this.back.forceCurrentAnimationType(EAnimation.EXPLODE);
            }
            if (this.body != null) {
                this.body.forceCurrentAnimationType(EAnimation.EXPLODE);
            }
            for (Pair<Float, AnimatorController> item : this.bodiesAnim) {
                item.getV2().forceCurrentAnimationType(EAnimation.EXPLODE);
            }
            return true;
        }
        return false;
    }

    private void createNewBodySegment() throws SlickException {
        if (this.body != null && this.canCreateBodies) {
            float distance = this.graphicalX() - this.getMaxBodyX();
            if (this.animatorController.getEyesDirection() == EDirection.LEFT) {
                distance = MathTools.abs(distance);
            }
            while (distance >= 60) {
                this.bodiesAnim.add(new Pair<>(this.getMaxBodyX(), new AnimatorController(this.body)));
                distance -= 60;
            }
        }
    }

    private void deleteKilledElement() {
        if (this.body != null && this.body.isDeleted()) {
            this.body = null;
        }
        if (this.back != null && this.back.isDeleted()) {
            this.back = null;
        }
    }

    protected float getMaxBodyX() {
        if (this.bodiesAnim.isEmpty())
            return this.saveX;
        if (this.animatorController.getEyesDirection() == EDirection.RIGHT) {
            return this.bodiesAnim.get(this.bodiesAnim.size() - 1).getV1() + 60;
        } else {
            return this.bodiesAnim.get(this.bodiesAnim.size() - 1).getV1() - 60;
        }
    }

}

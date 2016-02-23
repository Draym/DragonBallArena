package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.bodies.BodySprite;
import com.andres_k.components.gameComponents.collisions.CollisionResult;
import com.andres_k.components.gameComponents.gameObject.commands.movement.MovementController;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.List;

/**
 * Created by andres_k on 10/07/2015.
 */
public abstract class GameObject {
    protected AnimatorController animatorController;
    protected MovementController movement;
    protected String id;
    protected EGameObject type;

    protected boolean alive;

    protected float maxLife;
    protected float currentLife;
    protected float damage;

    protected GameObject(AnimatorController animatorController, EGameObject type, String id, Pair<Float, Float> pos, float life, float damage, float speed, float weight) {
        this.movement = new MovementController(pos, 9.8f, speed, weight, false);

        this.alive = true;
        this.type = type;
        this.id = id;
        this.animatorController = animatorController;
        this.maxLife = life;
        this.currentLife = life;
        this.damage = damage;
    }

    public void revive() {
        this.alive = true;
        this.currentLife = this.maxLife;
    }

    public void die() {
        this.currentLife = 0;
        this.animatorController.setCurrentAnimationType(EAnimation.EXPLODE);
        this.alive = false;
    }

    public void resetActions() {
    }

    public abstract void clear();

    public void draw(Graphics g) throws SlickException {
        if (this.animatorController != null) {
            this.animatorController.draw(g, this.graphicalX(), this.graphicalY());
            if (GlobalVariable.drawCollision && this.animatorController.currentBodyAnimation() != null) {
                this.animatorController.currentBodyAnimation().draw(g, this.animatorController.currentFrame(), this.animatorController.getEyesDirection().isHorizontalFlip(), this.getPosX(), this.getPosY());
                g.fillRect(this.getPosX(), this.getPosY(), 5, 5);
            }
        }
    }

    public abstract void update() throws SlickException;

    public abstract void eventPressed(EInput input);

    public abstract void eventReleased(EInput input);

    public abstract Object doTask(Object task);

    // MOVEMENT

    public void doMovement(CollisionResult collisionResult) {
        if (!this.isNeedDelete()) {
            this.movement.nextPosition(collisionResult);
        }
    }

    public Pair<Float, Float> predictNextPosition() {
        return this.movement.predictNextPosition();
    }

    // CHECK COLLISION

    public abstract CollisionResult checkCollision(List<GameObject> items, Pair<Float, Float> newPos);

    // TOOLS

    public void getHit(GameObject enemy) {
//        Console.debug("\nCURRENT LIFE [" + this.type + "] vs [" + enemy.type + "]: " + this.currentLife + " - " + enemy.getDamage() + " = " + (this.currentLife - enemy.getDamage()));
        this.currentLife -= enemy.getDamage();
        if (this.currentLife <= 0) {
            this.die();
        }
        // changer l'anim pour recevoir degat
    }

    public void manageEachCollisionExceptHit(EGameObject mine, GameObject enemy, EGameObject him) {
    }

    // GETTERS

    public float getMaxLife() {
        return this.maxLife;
    }

    public float getCurrentLife() {
        return this.currentLife;
    }

    public float getDamage() {
        return this.damage;
    }

    public boolean isOnEarth() {
        return this.movement.isOnEarth();
    }

    public BodySprite getBody() {
        return this.animatorController.currentBodySprite();
    }

    public float graphicalX() {
        if (this.getBody() == null) {
            return this.getPosX();
        }
        return this.getBody().getFlippedSprite(this.animatorController.getEyesDirection().isHorizontalFlip(), this.getPosX(), this.getPosY()).getMinX();
    }

    public float graphicalY() {
        if (this.getBody() == null) {
            return this.getPosY();
        }
        return this.getBody().getFlippedSprite(this.animatorController.getEyesDirection().isHorizontalFlip(), this.getPosX(), this.getPosY()).getMinY();
    }

    public float getPosX() {
        return this.movement.getX();
    }

    public float getPosY() {
        return this.movement.getY();
    }

    public boolean isNeedDelete() {
        return (this.animatorController == null || this.animatorController.isDeleted());
    }

    public String getId() {
        return this.id;
    }

    public EGameObject getType() {
        return this.type;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public AnimatorController getAnimatorController() {
        return this.animatorController;
    }

    public MovementController getMovement() {
        return this.movement;
    }

    // SETTERS

    public void setOnEarth(boolean value) {
        this.movement.setOnEarth(value);
    }
}

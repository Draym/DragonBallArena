package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.gameComponents.animations.container.AnimatorController;
import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.bodies.BodySprite;
import com.andres_k.components.gameComponents.gameObject.commands.MovementController;
import com.andres_k.components.gameComponents.gameObject.collisions.CollisionController;
import com.andres_k.components.gameComponents.gameObject.collisions.CollisionResult;
import com.andres_k.components.gameComponents.gameObject.events.EventController;
import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;

import java.util.List;

/**
 * Created by andres_k on 10/07/2015.
 */
public abstract class GameObject {
    protected AnimatorController animatorController;
    protected CollisionController collision;
    protected EventController event;
    protected MovementController movement;
    protected String id;
    protected EnumGameObject type;

    protected boolean alive;

    protected float maxLife;
    protected float currentLife;
    protected float damage;

    protected GameObject(AnimatorController animatorController, String id, EnumGameObject type, Pair<Float, Float> pos, float life, float damage, float speed, float weight) {
        this.movement = new MovementController(pos, 8, speed, weight, false);
        this.collision = new CollisionController(this);
        this.event = new EventController();

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

    public abstract void clear();

    public abstract void draw(Graphics g);

    public abstract void update();

    public abstract void eventPressed(EnumInput input);

    public abstract void eventReleased(EnumInput input);

    public abstract Object doTask(Object task);

    // MOVEMENT

    public void doMovement(CollisionResult collisionResult) {
        if (!this.isNeedDelete())
            this.movement.nextPosition(collisionResult);
    }

    public Pair<Float, Float> predictNextPosition() {
        return this.movement.predictNextPosition();
    }

    // CHECK COLLISION

    public CollisionResult checkCollision(List<GameObject> items, Pair<Float, Float> newPos) {
        return this.collision.checkItemCollision(items, this.movement.getPositions(), newPos);
    }

    // TOOLS

    public void getHit(GameObject enemy) {
//        ConsoleWrite.debug("\nCURRENT LIFE [" + this.type + "] vs [" + enemy.type + "]: " + this.currentLife + " - " + enemy.getDamage() + " = " + (this.currentLife - enemy.getDamage()));
        this.currentLife -= enemy.getDamage();
        if (this.currentLife <= 0) {
            this.animatorController.setCurrent(EnumAnimation.EXPLODE);
            this.alive = false;
        }
        // changer l'anim pour recevoir degat
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
        return this.movement.getX() - (this.animatorController.currentAnimation().getWidth() / 2);
    }

    public float graphicalY() {
        return this.movement.getY() - (this.animatorController.currentAnimation().getHeight() / 2);
    }

    public float getPosX() {
        return this.movement.getX();
    }

    public float getPosY() {
        return this.movement.getY();
    }

    public boolean isNeedDelete() {
        if (this.animatorController == null)
            return true;
        return this.animatorController.isDeleted();
    }

    public String getId() {
        return this.id;
    }

    public EnumGameObject getType() {
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

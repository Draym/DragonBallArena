package com.andres_k.components.gameComponents.gameObject.collisions;

import com.andres_k.utils.stockage.Pair;

/**
 * Created by andres_k on 21/10/2015.
 */
public class CollisionResult {
    private Pair<Boolean, EnumDirection> collisionX;
    private Pair<Boolean, EnumDirection> collisionY;

    public CollisionResult() {
        this.collisionX = new Pair<>(false, EnumDirection.NONE);
        this.collisionY = new Pair<>(false, EnumDirection.NONE);
    }

    // FUNCTIONS

    public boolean hasCollision() {
        if (this.collisionX.getV1() || this.collisionY.getV1())
            return true;
        return false;
    }

    public void copy(CollisionResult collision) {
        this.collisionX.copy(collision.getCollisionX());
        this.collisionY.copy(collision.getCollisionY());
    }

    // GETTERS
    public Pair<Boolean, EnumDirection> getCollisionX() {
        return this.collisionX;
    }

    public Pair<Boolean, EnumDirection> getCollisionY() {
        return this.collisionY;
    }

    // SETTERS
    public void setCollisionX(boolean collision, EnumDirection direction) {
        this.collisionX.setV1(collision);
        this.collisionX.setV2(direction);
    }

    public void setCollisionX(Pair<Boolean, EnumDirection> collision) {
        this.collisionX.copy(collision);
    }

    public void setCollisionY(boolean collision, EnumDirection direction) {
        this.collisionY.setV1(collision);
        this.collisionY.setV2(direction);
    }

    public void setCollisionY(Pair<Boolean, EnumDirection> collision) {
        this.collisionY.copy(collision);
    }
}

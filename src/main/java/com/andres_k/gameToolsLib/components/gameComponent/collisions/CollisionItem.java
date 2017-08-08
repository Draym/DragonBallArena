package com.andres_k.gameToolsLib.components.gameComponent.collisions;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;

/**
 * Created by com.andres_k on 29/10/2015.
 */
public class CollisionItem {
    private boolean collision;
    private EGameObject collisionType;
    private EDirection collisionDirection;
    private float collisionDistance;

    CollisionItem(boolean collision, EGameObject type, EDirection direction, float distance) {
        this.collision = collision;
        this.collisionType = type;
        this.collisionDirection = direction;
        this.collisionDistance = distance;
    }

    @Override
    public String toString() {
        return "[" + this.collision + "] [" + this.collisionType + "] [" + this.collisionDirection + "] [" + this.collisionDistance + "]";
    }

    // SETTERS
    public void copy(CollisionItem item) {
        this.collision = item.collision;
        this.collisionType = item.collisionType;
        this.collisionDirection = item.collisionDirection;
        this.collisionDistance = item.collisionDistance;
    }

    public void init(boolean collision, EGameObject type, EDirection direction, float distance) {
        this.collision = collision;
        this.collisionType = type;
        this.collisionDirection = direction;
        this.collisionDistance = distance;
    }

    // GETTERS
    public boolean isCollision() {
        return this.collision;
    }

    public EGameObject getCollisionType() {
        return this.collisionType;
    }

    public EDirection getCollisionDirection() {
        return this.collisionDirection;
    }

    public float getCollisionDistance() {
        return this.collisionDistance;
    }
}

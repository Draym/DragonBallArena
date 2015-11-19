package com.andres_k.components.gameComponents.collisions;

import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EnumDirection;

/**
 * Created by andres_k on 29/10/2015.
 */
public class CollisionItem {
    private boolean collision;
    private EnumGameObject collisionType;
    private EnumDirection collisionDirection;
    private float collisionDistance;

    CollisionItem(boolean collision, EnumGameObject type, EnumDirection direction, float distance) {
        this.collision = collision;
        this.collisionType = type;
        this.collisionDirection = direction;
        this.collisionDistance = distance;
    }

    @Override
    public String toString() {
        return "[" + collision + "] [" + collisionType + "] [" + collisionDirection + "] [" + collisionDistance + "]";
    }

    // SETTERS
    public void copy(CollisionItem item) {
        this.collision = item.collision;
        this.collisionType = item.collisionType;
        this.collisionDirection = item.collisionDirection;
        this.collisionDistance = item.collisionDistance;
    }

    public void init(boolean collision, EnumGameObject type, EnumDirection direction, float distance) {
        this.collision = collision;
        this.collisionType = type;
        this.collisionDirection = direction;
        this.collisionDistance = distance;
    }

    // GETTERS
    public boolean isCollision() {
        return this.collision;
    }

    public EnumGameObject getCollisionType() {
        return this.collisionType;
    }

    public EnumDirection getCollisionDirection() {
        return this.collisionDirection;
    }

    public float getCollisionDistance() {
        return this.collisionDistance;
    }
}

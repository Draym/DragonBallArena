package com.andres_k.gameToolsLib.components.gameComponent.collisions;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.utils.tools.ContainerTools;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by com.andres_k on 21/10/2015.
 */
public class CollisionResult {
    private List<CollisionItem> collisionsX;
    private List<CollisionItem> collisionsY;
    private boolean onEarth;

    public CollisionResult() {
        this.collisionsX = new ArrayList<>();
        this.collisionsY = new ArrayList<>();
        this.onEarth = false;
    }

    public void compileWith(CollisionResult item) {
        this.addCollisionsX(item.getCollisionsX());
        this.addCollisionsY(item.getCollisionsY());
        if (item.isOnEarth()) {
            this.setOnEarth(true);
        }
    }

    // GETTERS
    public List<CollisionItem> getCollisionsX() {
        return this.collisionsX;
    }

    public List<CollisionItem> getCollisionsY() {
        return this.collisionsY;
    }

    public boolean hasCollision() {
        return (hasCollisionX() || hasCollisionY());
    }

    public boolean hasCollisionXWith(EGameObject type) {
        for (CollisionItem item : this.collisionsX) {
            if (item.isCollision() && item.getCollisionType() == type)
                return true;
        }
        return false;
    }

    public boolean hasCollisionYWith(EGameObject type) {
        for (CollisionItem item : this.collisionsY) {
            if (item.isCollision() && item.getCollisionType() == type)
                return true;
        }
        return false;
    }

    public boolean hasCollisionX() {
        for (CollisionItem item : this.collisionsX) {
            if (item.isCollision())
                return true;
        }
        return false;
    }

    public boolean hasCollisionY() {
        for (CollisionItem item : this.collisionsY) {
            if (item.isCollision())
                return true;
        }
        return false;
    }

    public CollisionItem getLowCollisionX() {
        CollisionItem result = null;

        for (CollisionItem tmp : this.collisionsX) {
            if (result == null)
                result = tmp;
            else if (result.getCollisionDistance() > tmp.getCollisionDistance())
                result = tmp;
        }
        return result;
    }

    public CollisionItem getLowCollisionX(Object except[]) {
        CollisionItem result = null;

        for (CollisionItem tmp : this.collisionsX) {
            if (!ContainerTools.arrayContains(except, tmp.getCollisionType())) {
                if (result == null)
                    result = tmp;
                else if (result.getCollisionDistance() > tmp.getCollisionDistance())
                    result = tmp;
            }
        }
        return result;
    }

    public CollisionItem getLowCollisionY() {
        CollisionItem result = null;

        for (CollisionItem tmp : this.collisionsY) {
            if (result == null)
                result = tmp;
            else if (result.getCollisionDistance() > tmp.getCollisionDistance())
                result = tmp;
        }
        return result;
    }

    public CollisionItem getLowCollisionY(Object except[]) {
        CollisionItem result = null;

        for (CollisionItem tmp : this.collisionsY) {
            if (!ContainerTools.arrayContains(except, tmp.getCollisionType())) {
                if (result == null)
                    result = tmp;
                else if (result.getCollisionDistance() > tmp.getCollisionDistance())
                    result = tmp;
            }
        }
        return result;
    }

    public boolean isOnEarth() {
        return this.onEarth;
    }

    // SETTERS
    public void setOnEarth(boolean value) {
        this.onEarth = value;
    }

    public void addCollisionX(boolean collision, EGameObject type, EDirection direction, float distance) {
        this.collisionsX.add(new CollisionItem(collision, type, direction, distance));
    }

    public void addCollisionX(CollisionItem collision) {
        this.collisionsX.add(collision);
    }

    public void addCollisionsX(List<CollisionItem> collisions) {
        this.collisionsX.addAll(collisions.stream().filter(CollisionItem::isCollision).collect(Collectors.toList()));
    }

    public void addCollisionY(boolean collision, EGameObject type, EDirection direction, float distance) {
        this.collisionsY.add(new CollisionItem(collision, type, direction, distance));
    }

    public void addCollisionY(CollisionItem collision) {
        this.collisionsY.add(collision);
    }

    public void addCollisionsY(List<CollisionItem> collisions) {
        this.collisionsY.addAll(collisions.stream().filter(CollisionItem::isCollision).collect(Collectors.toList()));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("CollisionResult: [");
        this.collisionsX.forEach(item -> result.append(item.toString()).append(", "));
        result.append("] [");
        this.collisionsY.forEach(item -> result.append(item.toString()).append(", "));
        result.append("]");
        return result.toString();
    }
}

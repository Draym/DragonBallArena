package com.andres_k.components.gameComponents.collisions;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.bodies.BodyRect;
import com.andres_k.components.gameComponents.bodies.BodySprite;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
import com.andres_k.utils.tools.MathTools;
import org.newdawn.slick.geom.Shape;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by andres_k on 21/10/2015.
 */
public abstract class PhysicalObject extends GameObject {
    private Map<UUID, UUID> saveCollisions;

    protected PhysicalObject(AnimatorController animatorController, EGameObject type, String id, float x, float y, float life, float damage, float moveSpeed, float gravitySpeed, float weight) {
        super(animatorController, type, id, new Pair<>(x, y), life, damage, moveSpeed, gravitySpeed, weight);
        this.saveCollisions = new HashMap<>();
    }

    // FUNCTIONS
    @Override
    public CollisionResult checkCollision(List<GameObject> items, Pair<Float, Float> newPos) {
        return this.checkItemCollision(items, this.movement.getPositions(), newPos);
    }

    public CollisionResult checkItemCollision(List<GameObject> items, Pair<Float, Float> pos, Pair<Float, Float> newPos) {
        CollisionResult result = new CollisionResult();

        this.saveCollisions.clear();

        List<GameObject> potential = items.stream().filter(item -> checkBorderCollision(item, newPos)).collect(Collectors.toList());

        potential.forEach(item -> result.compileWith(checkBodyCollision(item, pos, newPos)));

        if (!result.isOnEarth())
            this.setOnEarth(result.isOnEarth());
        Console.debug(result.toString());
        return result;
    }

    private boolean checkBorderCollision(GameObject enemy, Pair<Float, Float> pos) {
        if (!enemy.getId().contains(this.getId())) {
            BodySprite myBody = this.getBody();
            BodySprite hisBody = enemy.getBody();

            if (myBody == null || hisBody == null
                    || this.saveCollisions.get(myBody.getId()) == hisBody.getId()
                    || this.saveCollisions.get(hisBody.getId()) == myBody.getId())
                return false;
            if (myBody.getFlippedBody(this.getAnimatorController().getEyesDirection().isHorizontalFlip(),
                    pos.getV1(), pos.getV2(), this.getAnimatorController().getRotateAngle())
                    .intersects(hisBody.getFlippedBody(enemy.getAnimatorController().getEyesDirection().isHorizontalFlip(),
                            enemy.getPosX(), enemy.getPosY(), enemy.getAnimatorController().getRotateAngle()))) {
                this.saveCollisions.put(myBody.getId(), hisBody.getId());
                return true;
            }
        }
        return false;
    }

    private CollisionResult checkBodyCollision(GameObject enemy, Pair<Float, Float> pos, Pair<Float, Float> newPos) {
        CollisionResult result = new CollisionResult();

        List<BodyRect> enemyBodies = enemy.getBody().getBodies();
        List<BodyRect> myBodies = this.getBody().getBodies();

        for (BodyRect myRect : myBodies) {
            for (BodyRect hisRect : enemyBodies) {
                if (checkCollisionInSpecialWay(enemy, myRect, hisRect, pos, new Pair<>(pos.getV1(), newPos.getV2()), result, 0))
                    result.setOnEarth(true);
                checkCollisionInSpecialWay(enemy, myRect, hisRect, pos, newPos, result, 1);
            }

        }
        return result;
    }

    private boolean checkCollisionInSpecialWay(GameObject enemy, BodyRect myRect, BodyRect hisRect, Pair<Float, Float> pos, Pair<Float, Float> newPos, CollisionResult result, int mode) {

        boolean myEyesDir = this.getAnimatorController().getEyesDirection().isHorizontalFlip();
        float myRotateAngle = this.getAnimatorController().getRotateAngle();
        boolean hisEyesDir = enemy.getAnimatorController().getEyesDirection().isHorizontalFlip();
        float hisRotateAngle = this.getAnimatorController().getRotateAngle();

        Shape myShape = myRect.getFlippedRect(myEyesDir,
                this.getBody().getFlippedBody(myEyesDir, newPos.getV1(), newPos.getV2()),
                newPos.getV1(), newPos.getV2(), myRotateAngle);

        Shape hisShape = hisRect.getFlippedRect(hisEyesDir,
                enemy.getBody().getFlippedBody(hisEyesDir, enemy.getPosX(), enemy.getPosY()),
                enemy.getPosX(), enemy.getPosY(), hisRotateAngle);

        if (myShape.intersects(hisShape) || myShape.contains(hisShape) || hisShape.contains(myShape)) {
            this.doCollision(enemy, myRect, hisRect, myShape, hisShape, result, pos, mode);
            myRect.addCollision(hisRect.getId());
            hisRect.addCollision(myRect.getId());
            if (enemy.getType() == EGameObject.PLATFORM || (enemy.getType() == EGameObject.MAP && hisShape.getWidth() >= hisShape.getHeight()))
                return true;
        } else {
            myRect.deleteCollision(hisRect.getId());
            hisRect.deleteCollision(myRect.getId());
        }
        return false;
    }

    private void doCollision(GameObject enemy, BodyRect myRect, BodyRect hisRect, Shape myShape, Shape hisShape, CollisionResult result, Pair<Float, Float> pos, int mode) {
        if (!myRect.containsCollision(hisRect.getId()) && !hisRect.containsCollision(myRect.getId())) {
            if (myRect.getType() == EGameObject.ATTACK_BODY && hisRect.getType() == EGameObject.DEFENSE_BODY) {
                enemy.manageGetHit(this);
                this.manageDoHit(enemy);
            } else if (myRect.getType() == EGameObject.DEFENSE_BODY && hisRect.getType() == EGameObject.ATTACK_BODY) {
                this.manageGetHit(enemy);
                enemy.manageDoHit(this);
            } else if (myRect.getType() == EGameObject.ATTACK_BODY && hisRect.getType() == EGameObject.ATTACK_BODY) {
                this.manageMutualHit(enemy);
            } else {
                this.manageEachCollisionExceptValidHit(myRect.getType(), enemy, hisRect.getType());
                enemy.manageEachCollisionExceptValidHit(hisRect.getType(), this, myRect.getType());
            }
        }
        if (myRect.getType() != EGameObject.ATTACK_BODY && hisRect.getType() != EGameObject.ATTACK_BODY && !enemy.getType().isIn(EGameObject.ANIMATED)) {
            float diffPos;
            float diffNewPos;
            float diffAbsPos;
            float diffAbsNewPos;
            float distance;

            boolean myEyesDir = this.getAnimatorController().getEyesDirection().isHorizontalFlip();
            float myRotateAngle = this.getAnimatorController().getRotateAngle();

            Shape myPreviousShape = myRect.getFlippedRect(myEyesDir,
                    this.getBody().getFlippedBody(myEyesDir, pos.getV1(), pos.getV2()),
                    pos.getV1(), pos.getV2(), myRotateAngle);

            EGameObject enemyType = enemy.getType();

            enemy.manageDoHit(this);
            if (enemyType == EGameObject.MAP) {
                if (hisShape.getWidth() >= hisShape.getHeight()) {
                    enemyType = EGameObject.PLATFORM;
                } else {
                    enemyType = EGameObject.BORDER;
                }
            }
            if (mode == 1) {
                diffPos = MathTools.getDistance(myPreviousShape.getCenterX(), hisShape.getCenterX());
                diffNewPos = MathTools.getDistance(myShape.getCenterX(), hisShape.getCenterX());
                diffAbsNewPos = MathTools.abs(diffNewPos);
                diffAbsPos = MathTools.abs(diffPos);

                distance = diffAbsPos - (myShape.getWidth() / 2 + hisShape.getWidth() / 2);
                result.addCollisionX((diffAbsPos >= diffAbsNewPos), enemyType, (diffPos > 0 ? EDirection.RIGHT : EDirection.LEFT), distance);
            } else {
                diffPos = MathTools.getDistance(myPreviousShape.getCenterY(), hisShape.getCenterY());
                diffNewPos = myShape.getCenterY() - hisShape.getCenterY();
                diffAbsNewPos = MathTools.abs(diffNewPos);
                diffAbsPos = MathTools.abs(diffPos);

                distance = diffAbsPos - (myShape.getHeight() / 2 + hisShape.getHeight() / 2);
                result.addCollisionY((diffAbsPos >= diffAbsNewPos), enemyType, (diffPos <= 0 ? EDirection.UP : EDirection.DOWN), distance);
            }
        }
    }
}

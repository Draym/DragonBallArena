package com.andres_k.components.gameComponents.collisions;

import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.bodies.BodyRect;
import com.andres_k.components.gameComponents.bodies.BodySprite;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EnumDirection;
import com.andres_k.utils.stockage.Pair;
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
public class CollisionController {
    private GameObject mine;
    private Map<UUID, UUID> saveCollisions;

    public CollisionController(GameObject mine) {
        this.mine = mine;
        this.saveCollisions = new HashMap<>();
    }

    // FUNCTIONS
    public CollisionResult checkItemCollision(List<GameObject> items, Pair<Float, Float> pos, Pair<Float, Float> newPos) {
        CollisionResult result = new CollisionResult();

        this.saveCollisions.clear();
        List<GameObject> potential = items.stream().filter(item -> checkBorderCollision(item, newPos)).collect(Collectors.toList());

        for (GameObject item : potential) {
            CollisionResult tmp = checkBodyCollision(item, pos, newPos);
            result.addCollisionX(tmp.getCollisionsX());
            result.addCollisionY(tmp.getCollisionsY());
            if (tmp.isOnEarth()) {
                result.setOnEarth(true);
            }
        }
        if (!result.isOnEarth())
            this.mine.setOnEarth(result.isOnEarth());
        return result;
    }

    public boolean checkBorderCollision(GameObject object, Pair<Float, Float> pos) {
        if (this.mine.getAnimatorController().getCurrentAnimation() != EnumAnimation.EXPLODE &&
                object.getAnimatorController().getCurrentAnimation() != EnumAnimation.EXPLODE) {
            BodySprite myBody = this.mine.getBody();
            BodySprite hisBody = object.getBody();

            if (myBody == null || hisBody == null
                    || this.saveCollisions.get(myBody.getId()) == hisBody.getId()
                    || this.saveCollisions.get(hisBody.getId()) == myBody.getId())
                return false;
            if (myBody.getBody(pos.getV1(), pos.getV2()).intersects(hisBody.getBody(object.getPosX(), object.getPosY()))) {
                this.saveCollisions.put(myBody.getId(), hisBody.getId());
                return true;
            }
        }
        return false;
    }

    private CollisionResult checkBodyCollision(GameObject enemy, Pair<Float, Float> pos, Pair<Float, Float> newPos) {
        CollisionResult result = new CollisionResult();

        List<BodyRect> enemyBodies = enemy.getBody().getBodies();
        List<BodyRect> myBodies = this.mine.getBody().getBodies();

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
        Shape myShape = myRect.getBody(newPos.getV1(), newPos.getV2());
        Shape hisShape = hisRect.getBody(enemy.getPosX(), enemy.getPosY());
        if (myShape.intersects(hisShape) || myShape.contains(hisShape) || hisShape.contains(myShape)) {
            myRect.addCollision(hisRect.getId());
            hisRect.addCollision(myRect.getId());
            this.doCollision(enemy, myRect, hisRect, myShape, hisShape, result, pos, mode);
            if (enemy.getType() == EnumGameObject.PLATFORM)
                return true;
        } else {
            myRect.deleteCollision(hisRect.getId());
            hisRect.deleteCollision(myRect.getId());
        }
        return false;
    }

    private void doCollision(GameObject enemy, BodyRect myRect, BodyRect hisRect, Shape myShape, Shape hisShape, CollisionResult result, Pair<Float, Float> pos, int mode) {
        if (!myRect.containsCollision(hisRect.getId()) && !hisRect.containsCollision(myRect.getId())) {
            if (myRect.getType() == EnumGameObject.ATTACK_BODY && hisRect.getType() == EnumGameObject.DEFENSE_BODY) {
                enemy.getHit(this.mine);
            } else if (myRect.getType() == EnumGameObject.DEFENSE_BODY && hisRect.getType() == EnumGameObject.ATTACK_BODY) {
                this.mine.getHit(enemy);
            }
        }
        if (myRect.getType() != EnumGameObject.ATTACK_BODY && hisRect.getType() != EnumGameObject.ATTACK_BODY) {
            float diffPos;
            float diffNewPos;
            float diffAbsPos;
            float diffAbsNewPos;
            float distance;

            if (mode == 1) {
                diffPos = MathTools.getDistance(myRect.getBody(pos.getV1(), pos.getV2()).getCenterX(), hisShape.getCenterX());
                diffNewPos = MathTools.getDistance(myShape.getCenterX(), hisShape.getCenterX());
                diffAbsNewPos = MathTools.abs(diffNewPos);
                diffAbsPos = MathTools.abs(diffPos);

                distance = diffAbsPos - (myShape.getWidth() / 2 + hisShape.getWidth() / 2);
                result.addCollisionX((diffAbsPos >= diffAbsNewPos), enemy.getType(), (diffPos > 0 ? EnumDirection.RIGHT : EnumDirection.LEFT), distance);
            } else {
                diffPos = MathTools.getDistance(myRect.getBody(pos.getV1(), pos.getV2()).getCenterY(), hisShape.getCenterY());
                diffNewPos = myShape.getCenterY() - hisShape.getCenterY();
                diffAbsNewPos = MathTools.abs(diffNewPos);
                diffAbsPos = MathTools.abs(diffPos);

                distance = diffAbsPos - (myShape.getHeight() / 2 + hisShape.getHeight() / 2);
                result.addCollisionY((diffAbsPos >= diffAbsNewPos), enemy.getType(), (diffPos <= 0 ? EnumDirection.TOP : EnumDirection.DOWN), distance);
            }
        }
    }
}

package com.andres_k.components.gameComponents.gameObject.collisions;

import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.bodies.BodyRect;
import com.andres_k.components.gameComponents.bodies.BodySprite;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.MathTools;
import org.newdawn.slick.geom.Shape;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andres_k on 21/10/2015.
 */
public class CollisionController {
    private GameObject mine;

    public CollisionController(GameObject mine) {
        this.mine = mine;
    }

    // FUNCTIONS
    public CollisionResult checkCollision(List<GameObject> items, Pair<Float, Float> pos, Pair<Float, Float> newPos) {
        CollisionResult result = new CollisionResult();
        List<GameObject> potential = items.stream().filter(item -> checkBorderCollision(item, newPos)).collect(Collectors.toList());

        for (GameObject item : potential) {
            CollisionResult tmp1 = doCollision(item, pos, new Pair<>(newPos.getV1(), pos.getV2()), 1);
            if (tmp1.getCollisionX().getV1())
                result.setCollisionX(tmp1.getCollisionX());
            CollisionResult tmp2 = doCollision(item, pos, new Pair<>(pos.getV1(), newPos.getV2()), 2);
            if (tmp2.getCollisionY().getV1())
                result.setCollisionY(tmp2.getCollisionY());
        }
        return result;
    }

    public boolean checkBorderCollision(GameObject object, Pair<Float, Float> pos) {
        if (this.mine.getAnimator().getCurrentAnimation() != EnumAnimation.EXPLODE &&
                object.getAnimator().getCurrentAnimation() != EnumAnimation.EXPLODE) {
            BodySprite enemyBody = object.getBody();
            BodySprite myBody = this.mine.getBody();

            if (myBody == null || enemyBody == null)
                return false;
            if (myBody.getBody(pos.getV1(), pos.getV2()).intersects(enemyBody.getBody(object.getPosX(), object.getPosY()))) {
                return true;
            }
        }
        return false;
    }

    private CollisionResult doCollision(GameObject enemy, Pair<Float, Float> pos, Pair<Float, Float> newPos, int mode) {
        CollisionResult result = new CollisionResult();

        List<BodyRect> enemyBodies = enemy.getBody().getBodies();
        List<BodyRect> myBodies = this.mine.getBody().getBodies();

        for (BodyRect mine : myBodies) {
            for (BodyRect his : enemyBodies) {
                Shape mineShape = mine.getBody(newPos.getV1(), newPos.getV2());
                Shape hisShape = his.getBody(enemy.getPosX(), enemy.getPosY());
                if (mineShape.intersects(hisShape) || mineShape.contains(hisShape) || hisShape.contains(mineShape)) {
                    if (mine.getType() == EnumGameObject.ATTACK_BODY && his.getType() == EnumGameObject.DEFENSE_BODY) {
                        enemy.getHit(this.mine);
                    } else if (mine.getType() == EnumGameObject.DEFENSE_BODY && his.getType() == EnumGameObject.ATTACK_BODY) {
                        this.mine.getHit(enemy);
                    } else if (mine.getType() != EnumGameObject.ATTACK_BODY && his.getType() != EnumGameObject.ATTACK_BODY) {
                        float diffNewPos;
                        float diffAbsNewPos;
                        float diffAbsPos;

                        if (mode == 1) {
                            diffNewPos = mineShape.getCenterX() - hisShape.getCenterX();
                            diffAbsNewPos = MathTools.getDistance(mineShape.getCenterX(), hisShape.getCenterX());
                            diffAbsPos = MathTools.getDistance(mine.getBody(pos.getV1(), pos.getV2()).getCenterX(), hisShape.getCenterX());
                            result.setCollisionX((diffAbsPos >= diffAbsNewPos), (diffNewPos > 0 ? EnumDirection.RIGHT : EnumDirection.LEFT));
                        } else {
                            diffNewPos = mineShape.getCenterY() - hisShape.getCenterY();
                            diffAbsNewPos = MathTools.getDistance(mineShape.getCenterY(), hisShape.getCenterY());
                            diffAbsPos = MathTools.getDistance(mine.getBody(pos.getV1(), pos.getV2()).getCenterY(), hisShape.getCenterY());
                            result.setCollisionY((diffAbsPos >= diffAbsNewPos), (diffNewPos >= 0 ? EnumDirection.TOP : EnumDirection.DOWN));
                        }

                        if (enemy.getType() == EnumGameObject.PLATFORM) {
                            this.mine.setOnEarth(true);
                            this.mine.getMovement().setPushY(0);
                        }
                    }
                }
            }
        }
        return result;
    }
}

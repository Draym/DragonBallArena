package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.gameComponents.animations.Animator;
import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.collisions.BodyRect;
import com.andres_k.components.gameComponents.collisions.BodySprite;
import com.andres_k.components.gameComponents.collisions.EnumDirection;
import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import java.util.List;

/**
 * Created by andres_k on 10/07/2015.
 */
public abstract class GameObject {
    protected Animator animator;
    protected MovementController movement;
    protected String id;
    protected EnumGameObject type;

    protected boolean alive;

    protected float maxLife;
    protected float currentLife;
    protected float damage;

    protected GameObject(Animator animator, String id, EnumGameObject type, Pair<Float, Float> pos, float life, float damage, float speed, float weight) {
        this.movement = new MovementController(pos, 1, speed, weight, false);
        this.alive = true;

        this.type = type;
        this.id = id;
        this.animator = animator;
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

    public void forceMove(EnumDirection direction) {
        if (direction != EnumDirection.NULL) {
//            if (this.inTheMapAfterMove(new Pair<>(newX, newY))) {
            this.movement.rollBack();
            //         }
        }
    }

    public void updatePos() {
        if (!this.isNeedDelete()) {
            if (this.inTheMapAfterMove(this.predictNextPosition())) {
                this.movement.nextPosition();
            } else {
                this.movement.stopMovement();
            }
        }
    }

    public Pair<Float, Float> predictNextPosition() {
        return this.movement.predictNextPosition();
    }

    // CHECK COLLISION

    public boolean inTheMapAfterMove(Pair<Float, Float> pos) {
        if (this.animator.currentAnimation() != null && this.checkBorderMap(pos.getV1(), this.animator.currentAnimation().getWidth() / 2, pos.getV2(), this.animator.currentAnimation().getHeight() / 2))
            return true;
        else if (this.animator.currentAnimation() == null && this.checkBorderMap(pos.getV1(), 0, pos.getV2(), 0))
            return true;
        return false;
    }

    public boolean checkBorderMap(float x, float decalX, float y, float decalY) {
        if (x - decalX > 0 && x + decalX < WindowConfig.w2_sX && y - decalY > 0 && y + decalY < WindowConfig.w2_sY)
            return true;
        else
            return false;
    }

    public Pair<Boolean, EnumDirection> checkCollisionWith(GameObject enemy, Pair<Float, Float> pos) {
        boolean collision = false;
        EnumDirection direction = EnumDirection.NULL;

        if (this.animator.getCurrentAnimation() != EnumAnimation.EXPLODE && enemy.animator.getCurrentAnimation() != EnumAnimation.EXPLODE) {
            BodySprite enemyBody = enemy.getBody();
            BodySprite myBody = this.getBody();

            if (myBody == null || enemyBody == null)
                return new Pair<>(collision, direction);
            if (myBody.getBody(pos.getV1(), pos.getV2()).intersects(enemyBody.getBody(enemy.getPosX(), enemy.getPosY()))) {
                List<BodyRect> enemyBodies = enemyBody.getBodies();
                List<BodyRect> myBodies = myBody.getBodies();

                for (BodyRect mine : myBodies) {
                    for (BodyRect his : enemyBodies) {
                        Shape mineShape = mine.getBody(pos.getV1(), pos.getV2());
                        Shape hisShape = his.getBody(enemy.getPosX(), enemy.getPosY());
                        if (mineShape.intersects(hisShape) || mineShape.contains(hisShape)) {
                            collision = true;
                            if (mine.getType() == EnumGameObject.ATTACK_BODY && his.getType() == EnumGameObject.DEFENSE_BODY) {
                                enemy.getHit(this);
                            } else if (mine.getType() == EnumGameObject.DEFENSE_BODY && his.getType() == EnumGameObject.ATTACK_BODY) {
                                this.getHit(enemy);
                            } else if (mine.getType() != EnumGameObject.ATTACK_BODY && his.getType() != EnumGameObject.ATTACK_BODY) {
                                // this.move = false;
                                float diffX = mineShape.getCenterX() - hisShape.getCenterX();
                                float diffY = mineShape.getCenterY() - hisShape.getCenterY();

                               // ConsoleWrite.write("diffX= " + diffX + "  [" + mineShape.getCenterX() + " - " + hisShape.getCenterX() + "]");
/*
                                if ((diffX < 0 ? diffX * -1 : diffX) >= (diffY < 0 ? diffY * -1 : diffY))
                                    direction = (diffX > 0 ? EnumDirection.RIGHT : EnumDirection.LEFT);
                                else
                                    direction = (diffY >= 0 ? EnumDirection.TOP : EnumDirection.DOWN);
*/
                                direction = EnumDirection.TOP;
                                if (enemy.getType() == EnumGameObject.PLATFORM) {
                                    this.setOnEarth(true);
                                    this.movement.setPushY(0);
                                }
                            }
                        }
                    }
                }
                // do something with collision
            }
        }
        return new Pair<>(collision, direction);
    }

    // TOOLS

    public void getHit(GameObject enemy) {
//        ConsoleWrite.debug("\nCURRENT LIFE [" + this.type + "] vs [" + enemy.type + "]: " + this.currentLife + " - " + enemy.getDamage() + " = " + (this.currentLife - enemy.getDamage()));
        this.currentLife -= enemy.getDamage();
        if (this.currentLife <= 0) {
            this.animator.setCurrent(EnumAnimation.EXPLODE);
            this.alive = false;
        } else {
            this.animator.nextCurrentIndex();
        }
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
        return this.animator.currentBodySprite();
    }

    public float graphicalX() {
        return this.movement.getX() - (this.animator.currentAnimation().getWidth() / 2);
    }

    public float graphicalY() {
        return this.movement.getY() - (this.animator.currentAnimation().getHeight() / 2);
    }

    public float getPosX() {
        return this.movement.getX();
    }

    public float getPosY() {
        return this.movement.getY();
    }

    public boolean isNeedDelete() {
        if (this.animator == null)
            return true;
        return this.animator.isDeleted();
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

    public Animator getAnimator() {
        return this.animator;
    }

    // SETTERS

    public void setOnEarth(boolean value) {
        this.movement.setOnEarth(value);
    }
}

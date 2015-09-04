package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.gameComponents.animations.Animator;
import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.collisions.BodyRect;
import com.andres_k.components.gameComponents.collisions.BodySprite;
import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;

import java.util.List;

/**
 * Created by andres_k on 10/07/2015.
 */
public abstract class GameObject {
    protected Animator animator;
    protected Pair<Float, Float> positions;
    protected Pair<Float, Float> moveTo;
    protected String id;
    protected EnumGameObject type;

    protected boolean move;
    protected boolean alive;
    protected float maxLife;
    protected float currentLife;
    protected float damage;
    protected float speed;

    protected GameObject(Animator animator, String id, EnumGameObject type, Pair<Float, Float> pos, float life, float damage, float speed) {
        this.positions = pos;
        this.moveTo = new Pair<>(0f, 0f);
        this.move = false;
        this.alive = true;

        this.type = type;
        this.id = id;
        this.animator = animator;
        this.maxLife = life;
        this.currentLife = life;
        this.damage = damage;
        this.speed = speed;
    }

    public void revive(){
        this.alive = true;
        this.currentLife = this.maxLife;
    }

    public abstract void clear();

    public abstract void draw(Graphics g);

    public abstract void update();

    public abstract void eventPressed(EnumInput input);

    public abstract void eventReleased(EnumInput input);

    public abstract Object doTask(Object task);

    public void move() {
        if (this.move) {
            this.positions.setV1(this.positions.getV1() + this.moveTo.getV1());
            this.positions.setV2(this.positions.getV2() + this.moveTo.getV2());
        }
    }

    public Pair<Float, Float> predictMove() {
        if (this.move) {
            return new Pair<>(this.positions.getV1() + this.moveTo.getV1(), this.positions.getV2() + this.moveTo.getV2());
        } else {
            return new Pair<>(this.positions.getV1(), this.positions.getV2());
        }
    }

    public boolean inTheMapAfterMove() {
        Pair<Float, Float> pos = this.predictMove();
        if (pos.getV1() > 0 && pos.getV1() < WindowConfig.w2_sX && pos.getV2() > 0 && pos.getV2() < WindowConfig.w2_sY) {
            return true;
        }
        return false;
    }

    public boolean checkCollisionWith(GameObject enemy) {
        Pair<Float, Float> tmpPos = predictMove();
        boolean collision = false;

        if (this.animator.getCurrentAnimation() != EnumAnimation.EXPLODE && enemy.animator.getCurrentAnimation() != EnumAnimation.EXPLODE) {
            BodySprite enemyBody = enemy.getBody();
            BodySprite myBody = this.getBody();

            if (myBody.getBody(tmpPos.getV1(), tmpPos.getV2()).intersects(enemyBody.getBody(enemy.getPosX(), enemy.getPosY()))) {
                List<BodyRect> enemyBodies = enemyBody.getBodies();
                List<BodyRect> myBodies = myBody.getBodies();

                for (BodyRect mine : myBodies) {
                    for (BodyRect his : enemyBodies) {
                        if (mine.getBody(tmpPos.getV1(), tmpPos.getV2()).intersects(his.getBody(enemy.getPosX(), enemy.getPosY()))) {
                            collision = true;
                            if (mine.getType() == EnumGameObject.ATTACK_BODY && his.getType() == EnumGameObject.DEFENSE_BODY) {
                                enemy.getHit(this);
                            } else if (mine.getType() == EnumGameObject.DEFENSE_BODY && his.getType() == EnumGameObject.ATTACK_BODY) {
                                this.getHit(enemy);
                            } else if (mine.getType() != EnumGameObject.ATTACK_BODY && his.getType() != EnumGameObject.ATTACK_BODY) {
                                this.move = false;
                            }
                        }
                    }
                }
            // do something with collision
            }
        }
        return collision;
    }

    public void getHit(GameObject enemy) {
//        ConsoleWrite.debug("\nCURRENT LIFE [" + this.type + "] vs [" + enemy.type + "]: " + this.currentLife + " - " + enemy.getDamage() + " = " + (this.currentLife - enemy.getDamage()));
        this.currentLife -= enemy.getDamage();
        if (this.currentLife <= 0) {
            this.animator.setCurrent(EnumAnimation.EXPLODE);
            this.move = false;
            this.alive = false;
        } else {
            this.animator.nextCurrentIndex();
        }
    }

    public float calculateWithSpeed() {
        return this.speed + (GlobalVariable.currentSpeed * 0.7f);
    }

    // GETTERS

    public boolean isMove() {
        return this.move;
    }

    public float getMaxLife() {
        return this.maxLife;
    }

    public float getCurrentLife() {
        return this.currentLife;
    }

    public float getDamage() {
        return this.damage;
    }

    public BodySprite getBody() {
        return this.animator.currentBodySprite();
    }

    public float graphicalX() {
        return this.positions.getV1() - (this.animator.currentAnimation().getWidth() / 2);
    }

    public float graphicalY() {
        return this.positions.getV2() - (this.animator.currentAnimation().getHeight() / 2);
    }

    public float getPosX() {
        return this.positions.getV1();
    }

    public float getPosY() {
        return this.positions.getV2();
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

    public Animator getAnimator(){
        return this.animator;
    }

    // SETTERS

    public void setMove(boolean value) {
        this.move = value;
    }

}

package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.animations.details.AnimationRepercussionItem;
import com.andres_k.components.gameComponents.bodies.BodySprite;
import com.andres_k.components.gameComponents.collisions.CollisionResult;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.commands.movement.MovementController;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.StringTools;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andres_k on 10/07/2015.
 */
public abstract class GameObject {
    private GameObject attackerOwner;
    private GameObject lastAttacker;
    private long resetAttackerTimer;
    private boolean useAttackerTimer;

    protected AnimatorController animatorController;
    protected MovementController movement;
    protected String id;
    protected EGameObject type;

    protected boolean alive;
    protected boolean wasHit;
    protected float maxLife;
    protected float currentLife;
    protected float damage;

    protected GameObject(AnimatorController animatorController, EGameObject type, String id, Pair<Float, Float> pos, float life, float damage, float moveSpeed, float gravitySpeed, float weight) {
        this.movement = new MovementController(pos, 9.8f, moveSpeed, gravitySpeed, weight, false);

        this.alive = true;
        this.type = type;
        this.id = id;
        this.animatorController = animatorController;
        this.maxLife = life;
        this.currentLife = life;
        this.damage = damage;
        this.wasHit = false;
        this.lastAttacker = null;
        this.initResetAttackerTimer(false);
    }

    public void revive() {
        this.alive = true;
        this.currentLife = this.maxLife;
    }

    public boolean die() {
        if (this.alive) {
            this.currentLife = 0;
            this.animatorController.forceCurrentAnimationType(EAnimation.EXPLODE);
            this.alive = false;
            return true;
        }
        return false;
    }

    public void resetActions() {
    }

    public abstract void clear();

    public void draw(Graphics g) throws SlickException {
        if (this.animatorController != null) {
            this.animatorController.draw(g, this.graphicalX(), this.graphicalY());
            if (GlobalVariable.drawCollision && this.animatorController.currentBodyAnimation() != null) {
                this.animatorController.currentBodyAnimation().draw(g, this.animatorController.currentFrame(), this.animatorController.getEyesDirection().isHorizontalFlip(), this.getPosX(), this.getPosY());
                g.fillRect(this.getPosX(), this.getPosY(), 5, 5);
            }
        }
    }

    public void update() throws SlickException {
        if (this.useAttackerTimer) {
            this.resetAttackerTimer -= GameConfig.currentTimeLoop;
            if (this.resetAttackerTimer <= 0) {
                this.lastAttacker = null;
                this.attackerOwner = null;
                this.initResetAttackerTimer(false);
            }
        }
    }

    public abstract void eventPressed(EInput input);

    public abstract void eventReleased(EInput input);

    public abstract Object doTask(Object task);

    // MOVEMENT

    public void doMovement(CollisionResult collisionResult) {
        if (!this.isNeedDelete()) {
            this.movement.nextPosition(collisionResult);
        }
    }

    public Pair<Float, Float> predictNextPosition() {
        return this.movement.predictNextPosition();
    }

    // CHECK COLLISION

    public abstract CollisionResult checkCollision(List<GameObject> items, Pair<Float, Float> newPos);

    // TOOLS

    public void teleportBehindMyAttacker() {
        if (this.attackerOwner != null) {
            this.movement.setPositions(this.attackerOwner.getPosX() + (this.attackerOwner.getAnimatorController().getEyesDirection() == EDirection.RIGHT ? - 70 : 70), this.attackerOwner.getPosY() - 70);
            this.animatorController.setEyesDirection(this.attackerOwner.getAnimatorController().getEyesDirection());
            this.useAttackerTimer = true;
        }
    }

    public void powerAfterDoDamage(float power) {
    }

    public void manageEachCollisionExceptValidHit(EGameObject mine, GameObject enemy, EGameObject him) {
    }

    public void manageMutualHit(GameObject enemy) {
    }

    public void manageGetHit(GameObject enemy) {
        if (!this.wasHit) {
            float enemyDamage = enemy.getDamage();
            enemy.powerAfterDoDamage((enemyDamage - this.currentLife < 0 ? 0 : enemyDamage - this.currentLife));
            this.incrementCurrentLife(-enemyDamage);

            AnimationRepercussionItem repercussionItem = enemy.getAnimatorController().getCurrentContainer().getRepercussion();
            if (repercussionItem != null) {
                repercussionItem.applyAnimationRepercussion(this);
                repercussionItem.applyEffectRepercussion(enemy, this);
            }
            this.setLastAttacker(enemy);
            this.wasHit = true;
            this.resetHitStatus();
        }
    }

    protected final void resetHitStatus() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                wasHit = false;
            }
        }, 100);
    }

    private void initResetAttackerTimer(boolean useTimer) {
        this.resetAttackerTimer = 400;
        this.useAttackerTimer = useTimer;
    }

    // GETTERS

    public float getCurrentPercentLife() {
        return this.currentLife * 100 / this.maxLife;
    }

    public float getMaxLife() {
        return this.maxLife;
    }

    public float getCurrentLife() {
        return this.currentLife;
    }

    public float getDamage() {
        float power = 1;

        AnimationRepercussionItem repercussionItem = this.animatorController.getCurrentContainer().getRepercussion();
        if (repercussionItem != null) {
            power = repercussionItem.getDamageToTheTarget();
        }
        return this.damage * power;
    }

    public boolean isOnEarth() {
        return this.movement.isOnEarth();
    }

    public BodySprite getBody() {
        return this.animatorController.currentBodySprite();
    }

    public float graphicalX() {
        if (this.getBody() == null) {
            return this.getPosX();
        }
        return this.getBody().getFlippedSprite(this.animatorController.getEyesDirection().isHorizontalFlip(), this.getPosX(), this.getPosY()).getMinX();
    }

    public float graphicalY() {
        if (this.getBody() == null) {
            return this.getPosY();
        }
        return this.getBody().getFlippedSprite(this.animatorController.getEyesDirection().isHorizontalFlip(), this.getPosX(), this.getPosY()).getMinY();
    }

    public float getPosX() {
        return this.movement.getX();
    }

    public float getPosY() {
        return this.movement.getY();
    }

    public boolean isNeedDelete() {
        return (this.animatorController == null || this.animatorController.isDeleted());
    }

    public String getId() {
        return this.id;
    }

    public EGameObject getType() {
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

    public GameObject getLastAttacker() {
        this.initResetAttackerTimer(true);
        return this.lastAttacker;
    }

    public GameObject getAttackerOwner() {
        this.initResetAttackerTimer(true);
        return this.attackerOwner;
    }

    public EDirection getDirectionOfMyAttacker() {
        if (this.lastAttacker != null) {
            float attackerPosX = this.lastAttacker.getPosX();
            attackerPosX = (this.lastAttacker.getAnimatorController().getEyesDirection() == EDirection.RIGHT ? attackerPosX : attackerPosX + 50);
            if (this.getPosX() > attackerPosX) {
                return EDirection.LEFT;
            } else {
                return EDirection.RIGHT;
            }
        }
        return EDirection.NONE;
    }

    // SETTERS

    public boolean setCurrentLife(float value) {
        this.currentLife = (value > this.maxLife ? this.maxLife : value);
        if (this.currentLife <= 0) {
            this.die();
            return false;
        }
        return true;
    }

    public void incrementCurrentLife(float value) {
        if ((value < 0 && this.currentLife != 0) || (value > 0 && this.currentLife != this.maxLife)) {
            this.setCurrentLife(this.currentLife + value);
        }
    }

    public void setOnEarth(boolean value) {
        this.movement.setOnEarth(value);
    }

    public void setLastAttacker(GameObject attacker) {
        this.lastAttacker = attacker;
        this.attackerOwner = GameObjectController.get().getObjectById(StringTools.getWord(attacker.getId(), "", ".", 0, 2));
        if (this.attackerOwner != null) {
            this.initResetAttackerTimer(true);
        }
    }

    public void setUseAttackerTimer(boolean value) {
        this.useAttackerTimer = value;
    }

    @Override
    public String toString() {
        return "(" + this.id + ") (" + this.type + ") [" + this.currentLife + "pv, " + this.damage + "dg]";
    }
}

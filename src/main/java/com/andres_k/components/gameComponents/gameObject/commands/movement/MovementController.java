package com.andres_k.components.gameComponents.gameObject.commands.movement;

import com.andres_k.components.gameComponents.collisions.CollisionItem;
import com.andres_k.components.gameComponents.collisions.CollisionResult;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.MathTools;

/**
 * Created by andres_k on 20/10/2015.
 */
public class MovementController {
    private Pair<Float, Float> positions;

    private EDirection moveDirection;

    private float gravity;
    private boolean onEarth;
    private boolean useGravity;

    private float moveSpeed;
    private float gravitySpeed;
    private float weight;
    private float pushX;
    private float pushY;
    private float pushGravity;
    private float exponential;

    private float coeffX;
    private float coeffY;

    public MovementController(Pair<Float, Float> positions, float gravity, float moveSpeed, float gravitySpeed, float weight, boolean onEarth) {
        this.positions = new Pair<>(positions.getV1(), positions.getV2());
        this.onEarth = onEarth;
        this.gravity = gravity;
        this.moveSpeed = moveSpeed;
        this.gravitySpeed = gravitySpeed;
        this.weight = weight;
        this.pushX = 0;
        this.pushY = 0;
        this.coeffX = 1;
        this.coeffY = 1;
        this.useGravity = true;
        this.moveDirection = EDirection.NONE;
        this.resetGravity();
    }

    public MovementController(MovementController movement) {
        this.positions = new Pair<>(movement.positions.getV1(), movement.positions.getV2());
        this.onEarth = movement.onEarth;
        this.gravity = movement.gravity;
        this.moveSpeed = movement.moveSpeed;
        this.gravitySpeed = movement.gravitySpeed;
        this.weight = movement.weight;
        this.pushX = movement.pushX;
        this.pushY = movement.pushY;
        this.pushGravity = movement.pushGravity;
        this.useGravity = movement.useGravity;
        this.exponential = movement.exponential;
        this.moveDirection = movement.moveDirection;
    }

    // FUNCTIONS

    public void update() {
        if (!this.onEarth && this.useGravity) {
            this.pushGravity += this.calculateGravity();
            if (this.exponential < 1) {
                this.exponential = this.exponential + this.exponential / 2;
                this.exponential = (this.exponential > 1 ? 1 : this.exponential);
            }
        }
    }

    public void addPushX(float value) {
        if (this.moveDirection == EDirection.RIGHT)
            this.positions.setV1(this.positions.getV1() + value);
        else if (this.moveDirection == EDirection.LEFT)
            this.positions.setV1(this.positions.getV1() - value);
    }

    public void addPushY(float value) {
        this.positions.setV2(this.positions.getV2() + value);
    }

    public void stopMovement() {
        this.pushX = 0;
        this.pushY = 0;
        this.moveDirection = EDirection.NONE;
        this.resetGravity();
    }

    public void resetGravity() {
        this.pushGravity = 0;
        this.exponential = 0.3f;
    }

    public Pair<Float, Float> predictNextPosition() {
        float nx = this.getNextX();
        float ny = this.getNextY();
        return new Pair<>(nx, ny);
    }

    public void nextPosition(CollisionResult collisionResult) {
        this.nextX(collisionResult);
        this.nextY(collisionResult);
    }

    private void nextX(CollisionResult collisionResult) {
        if (!collisionResult.hasCollisionX()) {
            this.positions.setV1(this.getNextX());
        } else if (this.moveSpeed != 0) {
            CollisionItem item = collisionResult.getLowCollisionX(new EGameObject[]{EGameObject.PLATFORM});

            if (item != null) {
                int mult = (item.getCollisionDirection() == EDirection.RIGHT ? 1 : -1);
                this.positions.setV1(this.getX() + ((MathTools.abs(item.getCollisionDistance() - 1)) * mult));
                this.setPushX(0);
            }
        }
    }

    private void nextY(CollisionResult collisionResult) {
        if (!collisionResult.hasCollisionY()) {
            this.positions.setV2(this.getNextY());
        } else if (this.moveSpeed != 0) {
            CollisionItem item = collisionResult.getLowCollisionY(new EGameObject[]{EGameObject.BORDER});

            if (item != null) {
                if (item.getCollisionDirection() == EDirection.UP) {
                    if (item.getCollisionDistance() > 0)
                        this.positions.setV2(this.getY() + item.getCollisionDistance());
                    this.onEarth = true;
                } else if (item.getCollisionDirection() == EDirection.DOWN) {
                    this.positions.setV2(this.getY() + MathTools.abs(item.getCollisionDistance()));
                }
                this.setPushY(0);
                this.resetGravity();
            }
        }
    }

    public void nextPosition() {
        this.positions.setV1(this.getNextX());
        this.positions.setV2(this.getNextY());
    }

    public float calculateDistance(float msec) {
        return this.moveSpeed * (msec / 1000);
    }

    public float calculateGravity() {
        if (this.gravitySpeed == 0) {
            return 0;
        }
        return (((this.weight * this.gravity)) / this.gravitySpeed) * this.exponential;
    }

    public float calculatePushX() {
        return this.calculateDistance(GameConfig.currentTimeLoop) * this.getPushX();
    }

    public float calculatePushY() {
        return this.calculateDistance(GameConfig.currentTimeLoop) * this.getPushY();
    }

    // GETTERS

    public float getX() {
        return this.positions.getV1();
    }

    public float getY() {
        return this.positions.getV2();
    }

    public float getCoeffX() {
        return this.coeffX;
    }

    public float getCoeffY() {
        return this.coeffY;
    }

    public Pair<Float, Float> getPositions() {
        return this.positions;
    }

    public boolean isOnEarth() {
        return this.onEarth;
    }

    public float getPushX() {
        if (this.moveDirection == EDirection.RIGHT)
            return this.pushX;
        else if (this.moveDirection == EDirection.LEFT)
            return -this.pushX;
        return 0;
    }

    public float getPushY() {
        return this.pushY;
    }

    private float getNextX() {
        return this.getX() + this.calculatePushX();
    }

    private float getNextY() {
        return this.getY() + this.calculatePushY() + this.getPushGravity();
    }

    private float getPushGravity() {
        if (this.useGravity)
            return this.pushGravity;
        else
            return 0f;
    }

    public boolean isUseGravity() {
        return this.useGravity;
    }

    public float getGravity() {
        return this.pushGravity / this.calculateGravity();
    }

    public float getMoveSpeed() {
        return this.moveSpeed;
    }

    public float getGravitySpeed() {
        return this.gravitySpeed;
    }

    public float getWeight() {
        return this.weight;
    }

    public EDirection getMoveDirection() {
        return this.moveDirection;
    }

    // SETTERS

    public void setPushX(float value) {
        this.pushX = value;
    }

    public void setPushY(float value) {
        this.pushY = value;
    }

    public void setCoeffX(float value) {
        this.coeffX = value;
    }

    public void setCoeffY(float value) {
        this.coeffY = value;
    }

    public void setOnEarth(boolean value) {
        this.onEarth = value;
        this.useGravity = !value;
    }

    public void setUseGravity(boolean value) {
        this.useGravity = value;
        this.resetGravity();
    }

    public void setGravitySpeed(float value) {
        this.gravitySpeed = value;
    }

    public void setMoveSpeed(float value) {
        this.moveSpeed = value;
    }

    public void setWeight(float value) {
        this.weight = value;
    }

    public void setMoveDirection(EDirection direction) {
        this.moveDirection = direction;
    }

    public void setPositions(float x, float y) {
        this.positions.setV1(x);
        this.positions.setV2(y);
    }
}

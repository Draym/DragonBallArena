package com.andres_k.components.gameComponents.gameObject;

import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.stockage.Pair;

/**
 * Created by andres_k on 20/10/2015.
 */
public class MovementController {
    private Pair<Float, Float> positions;
    private Pair<Float, Float> last;

    private float gravity;
    private boolean onEarth;

    private float speed;
    private float weight;
    private float pushX;
    private float pushY;

    public MovementController(Pair<Float, Float> positions, float gravity, float speed, float weight, boolean onEarth) {
        this.positions = new Pair<>(positions.getV1(), positions.getV2());
        this.last = new Pair<>(this.positions.getV1(), this.positions.getV2());
        this.onEarth = onEarth;
        this.gravity = gravity;
        this.speed = speed;
        this.weight = weight;
        this.pushX = 0;
        this.pushY = 0;
    }

    public MovementController(MovementController movement) {
        this.positions = new Pair<>(movement.positions.getV1(), movement.positions.getV2());
        this.last = new Pair<>(this.positions.getV1(), this.positions.getV2());
        this.onEarth = movement.onEarth;
        this.gravity = movement.gravity;
        this.speed = movement.speed;
        this.weight = movement.weight;
        this.pushX = movement.pushX;
        this.pushY = movement.pushY;
    }

    // FUNCTIONS

    public void stopMovement() {
        this.pushX = 0;
        this.pushY = 0;
    }

    public Pair<Float, Float> predictNextPosition() {
        float nx = this.positions.getV1() + this.pushX;
        float ny = this.positions.getV2() + this.pushY;
        return new Pair<>(nx, ny);
    }

    public void nextPosition() {
        this.last.setPair(this.positions);
        this.positions.setV1(this.positions.getV1() + this.pushX);
        this.positions.setV2(this.positions.getV2() + this.pushY);
    }

    public void rollBack() {
        this.positions.setPair(this.last);
    }

    public float calculateDistance(float msec){
        return this.speed * (msec / 1000);
    }

    // GETTERS

    public float getX() {
        return this.positions.getV1();
    }

    public float getY() {
        return this.positions.getV2();
    }

    public boolean isOnEarth() {
        return this.onEarth;
    }

    // SETTERS

    public void setPushX(float value) {
        this.pushX = this.calculateDistance(GlobalVariable.timeLoop) * value;
    }

    public void setPushY(float value) {
        this.pushY = this.calculateDistance(GlobalVariable.timeLoop) * value;
    }

    public void setOnEarth(boolean value) {
        this.onEarth = value;
    }
}

package com.andres_k.components.gameComponents.collisions;

/**
 * Created by andres_k on 14/10/2015.
 */
public enum EnumDirection {
    NULL(0, 0),
    TOP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private int coeffX;
    private int coeffY;

    EnumDirection(int coeffX, int coeffY){
        this.coeffX = coeffX;
        this.coeffY = coeffY;
    }

    public int  getCoeffX(){
        return this.coeffX;
    }

    public int  getCoeffY(){
        return this.coeffY;
    }
}

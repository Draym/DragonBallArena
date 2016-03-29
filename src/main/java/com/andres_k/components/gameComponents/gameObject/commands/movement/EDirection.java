package com.andres_k.components.gameComponents.gameObject.commands.movement;

/**
 * Created by andres_k on 14/10/2015.
 */
public enum EDirection {
    NONE(0, 0, false),
    UP(0, -1, false),
    DOWN(0, 1, false),
    LEFT(-1, 0, true),
    RIGHT(1, 0, false);

    private int coeffX;
    private int coeffY;
    private boolean horizontalFlip;

    EDirection(int coeffX, int coeffY, boolean horizontalFlip){
        this.coeffX = coeffX;
        this.coeffY = coeffY;
        this.horizontalFlip = horizontalFlip;
    }

    public int  getCoeffX(){
        return this.coeffX;
    }

    public int  getCoeffY(){
        return this.coeffY;
    }

    public boolean isHorizontalFlip() {
        return this.horizontalFlip;
    }
}

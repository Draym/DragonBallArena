package com.andres_k.components.eventComponent.events;

/**
 * Created by kevin on 25/04/2017.
 */
public class MouseController {

    private int mouseX;
    private int mouseY;

    private MouseController() {
        this.mouseX = 0;
        this.mouseY = 0;
    }

    private static class SingletonHolder {
        private final static MouseController instance = new MouseController();
    }

    public static MouseController get() {
        return MouseController.SingletonHolder.instance;
    }

    public void updatePosition(int x, int y) {
        this.mouseX = x;
        this.mouseY = y;
    }

    // GETTERS

    public int getMouseX() {
        return this.mouseX;
    }

    public int getMouseY() {
        return this.mouseY;
    }

    // SETTERS

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }
}

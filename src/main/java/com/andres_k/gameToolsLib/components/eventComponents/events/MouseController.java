package com.andres_k.gameToolsLib.components.eventComponents.events;

import com.andres_k.gameToolsLib.components.camera.CameraController;

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
        return SingletonHolder.instance;
    }

    public void updatePosition(int x, int y) {
        this.mouseX = x + (int)CameraController.get().getCamX();
        this.mouseY = y + (int) CameraController.get().getCamY();
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

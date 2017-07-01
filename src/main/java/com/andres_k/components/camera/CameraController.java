package com.andres_k.components.camera;

import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

/**
 * Created by kevin on 25/04/2017.
 */
public class CameraController {

    private float camX;
    private float camY;
    private String idOwner;

    private CameraController() {
        this.camX = 0;
        this.camY = 0;
        this.idOwner = "";
    }

    private static class SingletonHolder {
        private final static CameraController instance = new CameraController();
    }

    public static CameraController get() {
        return CameraController.SingletonHolder.instance;
    }

    public void draw(Graphics g, Shape shape, boolean useCameraMove) {
        if (useCameraMove) {
            shape.setX(this.getTransformPosX(shape.getX()));
            shape.setY(this.getTransformPosY(shape.getY()));
        }
        g.draw(shape);
    }

    public void draw(Image image, float drawX, float drawY, boolean useCameraMove) {
        if (useCameraMove)
            image.draw(this.getTransformPosX(drawX), this.getTransformPosY(drawY));
        else
            image.draw(drawX, drawY);
    }

    public void draw(Image image, float drawX, float drawY, float scale, boolean useCameraMove) {
        if (useCameraMove)
            image.draw(this.getTransformPosX(drawX), this.getTransformPosY(drawY), scale);
        else
            image.draw(drawX, drawY, scale);
    }

    public void draw(Image image, float drawX, float drawY, float scale, Color filter, boolean useCameraMove) {
        if (useCameraMove)
            image.draw(this.getTransformPosX(drawX), this.getTransformPosY(drawY), scale, filter);
        else
            image.draw(drawX, drawY, scale, filter);
    }

    public void followOwner(GameObject owner) {
        if (owner != null) {
            float halfVisibleMapX = WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1() / 2;
            float halfVisibleMapY = WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV2() / 2;

            if (owner.getPosX() > halfVisibleMapX && owner.getPosX() < (GameConfig.globalMapWidth - halfVisibleMapX)) {
                this.camX = owner.getPosX() - halfVisibleMapX;
            } else if (owner.getPosX() < halfVisibleMapX) {
                this.camX = 0;
            }
            if (owner.getPosY() > (GameConfig.globalMapHeight - halfVisibleMapY) && owner.getPosY() < halfVisibleMapY) {
                this.camY = owner.getPosY() - halfVisibleMapY;
            } else if (owner.getPosY() > halfVisibleMapY) {
                this.camY = 0;
            }
        }
    }

    // GETTERS
    public Pair<Float, Float> getTransformPos(Pair<Float, Float> pos) {
        return new Pair<>(this.getTransformPosX(pos.getV1()), this.getTransformPosY(pos.getV2()));
    }

    public Pair<Float, Float> getTransformPos(float x, float y) {
        return new Pair<>(this.getTransformPosX(x), this.getTransformPosY(y));
    }

    public float getTransformPosX(float x) {
        return x - this.camX;
    }

    public float getTransformPosY(float y) {
        return y - this.camY;
    }

    public float getCamX() {
        return this.camX;
    }

    public float getCamY() {
        return this.camY;
    }

    public String getIdOwner() {
        return this.idOwner;
    }

    // SETTERS

    public void setCamX(float camX) {
        this.camX = camX;
    }

    public void setCamY(float camY) {
        this.camY = camY;
    }

    public void setIdOwner(String idOwner) {
        this.idOwner = idOwner;
    }

}

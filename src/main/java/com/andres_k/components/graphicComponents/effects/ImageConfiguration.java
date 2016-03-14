package com.andres_k.components.graphicComponents.effects;

import org.newdawn.slick.Color;

/**
 * Created by andres_k on 08/02/2016.
 */
public class ImageConfiguration {
    public float x;
    public float y;
    public float imageSizeX;
    public float imageSizeY;
    public float scale;
    public float rotation;
    public Color color;
    public boolean drawable;
    public boolean flipX;
    public boolean flipY;

    public ImageConfiguration() {
        this.x = 0;
        this.y = 0;
        this.imageSizeX = 0;
        this.imageSizeY = 0;
        this.scale = 1;
        this.rotation = 0;
        this.color = null;
        this.drawable = true;
        this.flipX = false;
        this.flipY = false;
    }

    public ImageConfiguration(ImageConfiguration conf) {
        this.x = conf.x;
        this.y = conf.y;
        this.imageSizeX = conf.imageSizeX;
        this.imageSizeY = conf.imageSizeY;
        this.scale = conf.scale;
        this.rotation = conf.rotation;
        if (conf.color == null) {
            this.color = null;
        } else {
            this.color = new Color(conf.color);
        }
        this.drawable = conf.drawable;
        this.flipX = conf.flipX;
        this.flipY = conf.flipY;
    }

    public void reset() {
        this.drawable = true;
        this.scale = 1f;
        this.color = null;
        this.rotation = 0f;
        this.flipX = false;
        this.flipY = false;
    }

    public void changePosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void changeSizes(float sizeX, float sizeY) {
        this.imageSizeX = sizeX;
        this.imageSizeY = sizeY;
    }

    public void setFlip(boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + "] (" + this.imageSizeX + ", " + this.imageSizeY + ") {" + this.drawable + ", " + this.scale + ", " + this.rotation + "}";
    }
}

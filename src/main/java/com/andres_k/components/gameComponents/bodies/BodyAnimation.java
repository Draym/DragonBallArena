package com.andres_k.components.gameComponents.bodies;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 11/07/2015.
 */
public class BodyAnimation {
    private float sizeXSprite;
    private float sizeYSprite;
    private int spriteNumber;
    private List<BodySprite> bodies;

    public BodyAnimation(String jsonValue) throws JSONException {
        this.bodies = new ArrayList<>();

        JSONObject object = new JSONObject(jsonValue);
        this.sizeXSprite = (float) object.getDouble("spriteSizeX");
        this.sizeXSprite = (float) object.getDouble("spriteSizeY");
        this.spriteNumber = object.getInt("spriteNumber");
        JSONArray array = object.getJSONArray("sprites");

        for (int i = 0; i < array.length(); ++i) {
            this.bodies.add(new BodySprite(array.getJSONObject(i)));
        }
    }

    public void draw(Graphics g, int currentFrame, boolean haveToFlip, float posX, float posY, float rotateAngle, boolean useCameraMove) {
        if (currentFrame < this.bodies.size()) {
            this.bodies.get(currentFrame).draw(g, haveToFlip, posX, posY, rotateAngle, useCameraMove);
        }
    }

    public void restart() {
        this.bodies.forEach(BodySprite::restart);
    }

    // GETTERS

    public BodySprite getCurrentBody(int currentFrame) {
        if (this.bodies.size() > currentFrame) {
            return this.bodies.get(currentFrame);
        }
        return null;
    }
}

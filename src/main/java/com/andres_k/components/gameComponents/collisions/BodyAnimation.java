package com.andres_k.components.gameComponents.collisions;

import com.andres_k.utils.configs.GlobalVariable;
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

    public void draw(Graphics g, int currentFrame, float posX, float posY){
        if (GlobalVariable.drawCollision) {
            if (this.bodies.size() > currentFrame) {
                this.bodies.get(currentFrame).draw(g, posX, posY);
            }
        }
    }

    // GETTERS

    public List<BodyRect> getCurrentCollisions(int currentFrame){
        if (this.bodies.size() > currentFrame){
            return this.bodies.get(currentFrame).getBodies();
        }
        return null;
    }

    public BodySprite getCurrentBody(int currentFrame){
        if (this.bodies.size() > currentFrame){
            return this.bodies.get(currentFrame);
        }
        return null;
    }
}

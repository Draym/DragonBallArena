package com.andres_k.components.gameComponents.bodies;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by andres_k on 09/07/2015.
 */
public class BodySprite {
    private UUID id;
    private List<BodyRect> bodies;
    private Rectangle sprite;
    private Rectangle body;

    public BodySprite(JSONObject object) throws JSONException {
        this.bodies = new ArrayList<>();
        this.sprite = new Rectangle(0, 0, (float) object.getDouble("SpriteSizeX"), (float) object.getDouble("SpriteSizeY"));
        this.body = new Rectangle((float) object.getDouble("BodyPosX"), (float) object.getDouble("BodyPosY"), (float) object.getDouble("BodySizeX"), (float) object.getDouble("BodySizeY"));
        JSONArray array = object.getJSONArray("rectangles");

        for (int i = 0; i < array.length(); ++i) {
            this.bodies.add(new BodyRect(array.getJSONObject(i), this.body.getMinX(), this.body .getMinY()));
        }
        this.id = UUID.randomUUID();
    }

    public void draw(Graphics g, boolean haveToFlip, float posX, float posY) {
        g.setColor(Color.red);
        Rectangle mySprite = this.getFlippedSprite(haveToFlip, posX, posY);
        g.draw(mySprite);
        g.setColor(Color.green);
        Rectangle myBody = this.getFlippedBody(haveToFlip, posX, posY);
        g.draw(myBody);
        for (BodyRect rect : this.bodies) {
            rect.draw(g, haveToFlip, myBody, posX, posY);
        }
    }

    public void restart() {
        this.bodies.forEach(BodyRect::restart);
    }

    public void update() {
    }

    // GETTERS
    public List<BodyRect> getBodies() {
        return this.bodies;

    }

    public Rectangle getFlippedSprite(boolean haveToFlip, float posX, float posY) {
        float flipX = posX - this.sprite.getCenterX();

        if (haveToFlip) {
            flipX = posX - this.sprite.getWidth();
        }
        return new Rectangle(flipX, posY - this.sprite.getCenterY(), this.sprite.getWidth(), this.sprite.getHeight());
    }

    public Rectangle getFlippedBody(boolean haveToFlip, float posX, float posY) {
        float flipX = posX - this.sprite.getCenterX() + this.body.getMinX();

        if (haveToFlip) {
            Rectangle sprite = this.getFlippedSprite(true, posX, posY);

            flipX = sprite.getCenterX() + ((sprite.getWidth() / 2) - this.body.getMaxX());
        }
        return new Rectangle(flipX, posY - this.sprite.getCenterY() + this.body.getMinY(), this.body.getWidth(), this.body.getHeight());
    }

    public UUID getId() {
        return this.id;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();

        try {
            object.put("centerX", this.sprite.getCenterX());
            object.put("centerY", this.sprite.getCenterY());

            object.put("sizeX", this.sprite.getWidth());
            object.put("sizeY", this.sprite.getHeight());
            JSONArray arrayRect = new JSONArray();
            for (BodyRect body : this.bodies) {
                arrayRect.put(new JSONObject(body.toString()));
            }
            object.put("rectangles", arrayRect);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }
}

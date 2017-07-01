package com.andres_k.components.gameComponents.bodies;

import com.andres_k.components.camera.CameraController;
import com.andres_k.utils.tools.MathTools;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

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

    public void draw(Graphics g, boolean haveToFlip, float posX, float posY, float rotateAngle, boolean useCameraMove) {
        g.setColor(Color.red);
        Shape mySprite = this.getFlippedSprite(haveToFlip, posX, posY, rotateAngle);
        CameraController.get().draw(g, mySprite, useCameraMove);
        g.setColor(Color.green);
        Shape myBody = this.getFlippedBody(haveToFlip, posX, posY, rotateAngle);
        CameraController.get().draw(g, myBody, useCameraMove);
        for (BodyRect rect : this.bodies) {
            rect.draw(g, haveToFlip, this.getFlippedBody(haveToFlip, posX, posY), posX, posY, rotateAngle, useCameraMove);
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

    public Shape getFlippedSprite(boolean haveToFlip, float posX, float posY) {
        return this.getFlippedSprite(haveToFlip, posX, posY, 0);
    }

    public Shape getFlippedSprite(boolean haveToFlip, float posX, float posY, float rotateAngle) {
        float flipX = posX - this.sprite.getCenterX();

        if (haveToFlip) {
            flipX = posX - this.sprite.getWidth();
        }
        return MathTools.rotateShape(new Rectangle(flipX, posY - this.sprite.getCenterY(), this.sprite.getWidth(), this.sprite.getHeight()), rotateAngle);
    }

    public Shape getFlippedBody(boolean haveToFlip, float posX, float posY, float rotateAngle) {
        float flipX = posX - this.sprite.getCenterX() + this.body.getMinX();

        if (haveToFlip) {
            Shape sprite = this.getFlippedSprite(true, posX, posY, rotateAngle);

            flipX = sprite.getCenterX() + ((sprite.getWidth() / 2) - this.body.getMaxX());
        }
        return MathTools.rotateShape(new Rectangle(flipX, posY - this.sprite.getCenterY() + this.body.getMinY(), this.body.getWidth(), this.body.getHeight()), rotateAngle);
    }

    public Shape getFlippedBody(boolean haveToFlip, float posX, float posY) {
        return this.getFlippedBody(haveToFlip, posX, posY, 0);
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

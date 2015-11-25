package com.andres_k.components.gameComponents.bodies;

import com.andres_k.utils.stockage.Pair;
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
    private Pair<Float, Float> sizes;
    private Pair<Float, Float> center;

    public BodySprite(JSONObject object) throws JSONException {
        this.bodies = new ArrayList<>();
        this.center = new Pair<>((float) object.getDouble("centerX"), (float) object.getDouble("centerY"));
        this.sizes = new Pair<>((float) object.getDouble("sizeX"), (float) object.getDouble("sizeY"));
        JSONArray array = object.getJSONArray("rectangles");

        for (int i = 0; i < array.length(); ++i) {
            this.bodies.add(new BodyRect(array.getJSONObject(i), this.center.getV1(), this.center.getV2()));
        }
        this.id = UUID.randomUUID();
    }

    public void draw(Graphics g, boolean haveToFlip, float posX, float posY) {
        g.setColor(Color.red);
        Rectangle myBody = this.getBody(posX, posY);
        g.draw(myBody);
        for (BodyRect rect : this.bodies) {
            rect.draw(g, haveToFlip, myBody, posX, posY);
        }
    }

    public void update() {
    }

    // GETTERS
    public List<BodyRect> getBodies() {
        return this.bodies;

    }

    public Rectangle getBody(float posX, float posY) {
        return new Rectangle(posX - this.center.getV1(), posY - this.center.getV2(), this.sizes.getV1(), this.sizes.getV2());
    }

    public UUID getId() {
        return this.id;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();

        try {
            object.put("centerX", this.center.getV1());
            object.put("centerY", this.center.getV2());

            object.put("sizeX", this.sizes.getV1());
            object.put("sizeY", this.sizes.getV2());
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

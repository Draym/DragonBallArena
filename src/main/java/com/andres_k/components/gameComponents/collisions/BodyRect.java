package com.andres_k.components.gameComponents.collisions;

import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


/**
 * Created by andres_k on 09/07/2015.
 */
public class BodyRect {
    private EnumGameObject type;
    private Pair<Float, Float> positions;
    private Pair<Float, Float> sizes;

    public BodyRect(JSONObject object, Float decalX, Float decalY) throws JSONException {
        this.type = EnumGameObject.getEnumByValue(object.getString("type"));
        this.positions = new Pair<>((float) object.getDouble("posX") - decalX, (float) object.getDouble("posY") - decalY);
        this.sizes = new Pair<>((float) object.getDouble("sizeX"), (float) object.getDouble("sizeY"));
    }

    public void draw(Graphics g, float posX, float posY) {
        if (this.type == EnumGameObject.DEFENSE_BODY) {
            g.setColor(Color.cyan);
        } else if (this.type == EnumGameObject.ATTACK_BODY) {
            g.setColor(Color.orange);
        } else if (this.type == EnumGameObject.BLOCK_BODY) {
            g.setColor(Color.green);
        }
        g.draw(this.getBody(posX, posY));
    }


    // GETTERS

    public Shape getBody(float posX, float posY) {
        if (this.sizes.getV2() < 0) {
            return new Circle(posX + this.positions.getV1(), posY + this.positions.getV2(), this.sizes.getV1());
        } else {
            return new Rectangle(posX + this.positions.getV1(), posY + this.positions.getV2(), this.sizes.getV1(), this.sizes.getV2());
        }
    }

    public EnumGameObject getType() {
        return this.type;
    }

    // SETTERS
    public void setType(EnumGameObject type) {
        this.type = type;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();

        try {
            object.put("type", this.type.getValue());
            object.put("posX", this.positions.getV1());
            object.put("posY", this.positions.getV2());
            object.put("sizeX", this.sizes.getV1());
            object.put("sizeY", this.sizes.getV2());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object.toString();
    }
}

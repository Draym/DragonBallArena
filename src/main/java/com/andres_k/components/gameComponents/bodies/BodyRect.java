package com.andres_k.components.gameComponents.bodies;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.MathTools;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by andres_k on 09/07/2015.
 */
public class BodyRect {
    private UUID id;
    private List<UUID> lastCollisions;

    private EGameObject type;
    private Pair<Float, Float> positions;
    private Pair<Float, Float> sizes;

    public BodyRect(JSONObject object, Float decalX, Float decalY) throws JSONException {
        this.type = EGameObject.getEnumByValue(object.getString("type"));
        this.positions = new Pair<>((float) object.getDouble("posX") - decalX, (float) object.getDouble("posY") - decalY);
        this.sizes = new Pair<>((float) object.getDouble("sizeX"), (float) object.getDouble("sizeY"));
        this.id = UUID.randomUUID();
        this.lastCollisions = new ArrayList<>();
    }

    public void draw(Graphics g, boolean haveToFlip, Rectangle container, float posX, float posY) {
        if (this.type == EGameObject.DEFENSE_BODY) {
            g.setColor(Color.cyan);
        } else if (this.type == EGameObject.ATTACK_BODY) {
            g.setColor(Color.orange);
        } else if (this.type == EGameObject.BLOCK_BODY) {
            g.setColor(Color.black);
        }
        g.draw(this.getFlippedRect(haveToFlip, container, posX, posY));
    }

    public void restart() {
        this.lastCollisions.clear();
    }

    public void addCollision(UUID id) {
        if (!this.lastCollisions.contains(id))
            this.lastCollisions.add(id);
    }

    public void deleteCollision(UUID id) {
        if (this.lastCollisions.contains(id))
            this.lastCollisions.remove(id);
    }

    public boolean containsCollision(UUID id) {
        return this.lastCollisions.contains(id);
    }

    // GETTERS

    public UUID getId() {
        return this.id;
    }

    public Shape getFlippedRect(boolean haveToFlip, Rectangle container, float posX, float posY) {
        Pair<Float, Float> newPoint = new Pair<>(this.positions.getV1() + container.getMinX(), this.positions.getV2() + container.getMinY());

        if (haveToFlip)
            MathTools.flip(1, container, newPoint, this.sizes);

        if (this.sizes.getV2() < 0) {
            return new Circle(newPoint.getV1(), newPoint.getV2(), this.sizes.getV1());
        } else {
            return new Rectangle(newPoint.getV1(), newPoint.getV2(), this.sizes.getV1(), this.sizes.getV2());
        }
    }

    public EGameObject getType() {
        return this.type;
    }

    // SETTERS
    public void setType(EGameObject type) {
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

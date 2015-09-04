package com.andres_k.utils.tools;


import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.geom.Rectangle;

import java.awt.geom.AffineTransform;

/**
 * Created by andres_k on 17/03/2015.
 */
public class MathTools {

    public static float getAngle(double x1, double y1, double x2, double y2) {
        double angle;
        angle = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
        return (float) angle;
    }

    public static float toRadian(float angle){
        return (float)(angle * Math.PI / 180);
    }

    public static Pair<Float, Float> movePredict(float angle, float speed, float delta) {
        double addX = Math.cos(angle * Math.PI / 180);
        double addY = Math.sin(angle * Math.PI / 180);
        float x = ((float) addX * speed / 10);
        float y = ((float) addY * speed / 10);
        return new Pair<>(x * delta, y * delta);
    }

    public static void rotate(Pair<Float, Float> center, Pair<Float, Float> point, float angle){
        float[] newPoint = {point.getV1(), point.getV2()};
        AffineTransform.getRotateInstance(Math.toRadians(angle), center.getV1(), center.getV2()).transform(newPoint, 0, newPoint, 0, 1);
        point.setV1(newPoint[0]);
        point.setV2(newPoint[1]);
    }

    public static float getDistance(float p1, float p2){
        float distance = p1 - p2;
        distance = (distance < 0 ? distance * (-1) : distance);
        return distance;
    }

    public static Rectangle createRectangle(Pair<Float, Float> p1, Pair<Float, Float> p2){
        float x = p1.getV1();
        float y = p1.getV2();
        float sizeX = getDistance(p1.getV1(), p2.getV1());
        float sizeY = getDistance(p1.getV2(), p2.getV2());

        if (p2.getV1() - x < 0){
            x = p2.getV1();
        }
        if (p2.getV2() - y < 0){
            y = p2.getV2();
        }
        return new Rectangle(x, y, sizeX, sizeY);
    }
}

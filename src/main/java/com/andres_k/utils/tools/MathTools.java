package com.andres_k.utils.tools;


import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

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

    public static void flipRect(int mode, Shape container, Pair<Float, Float> point, Pair<Float, Float> sizes) {
        if (mode == 1) {
            point.setV1(container.getMaxX() - (point.getV1() - container.getMinX()) - sizes.getV1());
        } else {
            point.setV2(container.getMaxY() - (point.getV2() - container.getMinY()) - sizes.getV2());
        }
    }

    public static void flipCircle(int mode, Shape container, Pair<Float, Float> point, Pair<Float, Float> sizes) {
        if (mode == 1) {
            point.setV1(container.getMaxX() - (point.getV1() - container.getMinX()));
        } else {
            point.setV2(container.getMaxY() - (point.getV2() - container.getMinY()));
        }
    }


    public static Shape rotateShape(Shape shape, float angle) {
        if (angle != 0) {
            return shape.transform(Transform.createRotateTransform(MathTools.toRadian(angle), shape.getCenterX(), shape.getCenterY()));
        }
        return shape;
    }

    public static void rotate(Pair<Float, Float> center, Pair<Float, Float> point, float angle){
        float[] newPoint = {point.getV1(), point.getV2()};
        AffineTransform.getRotateInstance(Math.toRadians(angle), center.getV1(), center.getV2()).transform(newPoint, 0, newPoint, 0, 1);
        point.setV1(newPoint[0]);
        point.setV2(newPoint[1]);
    }

    public static float getAbsDistance(float p1, float p2){
        float distance = p1 - p2;
        return abs(distance);
    }

    public static float getDistance(float p1, float p2){
        return p1 - p2;
    }

    public static float abs(float value) {
        return (value > 0 ? value : value * -1);
    }

    public static Rectangle createRectangle(Pair<Float, Float> p1, Pair<Float, Float> p2){
        float x = p1.getV1();
        float y = p1.getV2();
        float sizeX = getAbsDistance(p1.getV1(), p2.getV1());
        float sizeY = getAbsDistance(p1.getV2(), p2.getV2());

        if (p2.getV1() - x < 0){
            x = p2.getV1();
        }
        if (p2.getV2() - y < 0){
            y = p2.getV2();
        }
        return new Rectangle(x, y, sizeX, sizeY);
    }

    public static int numberLevel(int number, int max) {
        int levelMax = 0;
        int levelNumber = 0;

        while (max >= 10) {
            max = max / 10;
            levelMax += 1;
        }
        while (number >= 10) {
            number = number / 10;
            levelNumber += 1;
        }
        return levelMax - levelNumber;
    }
}

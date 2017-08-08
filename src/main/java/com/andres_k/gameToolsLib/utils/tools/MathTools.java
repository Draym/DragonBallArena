package com.andres_k.gameToolsLib.utils.tools;


import com.andres_k.gameToolsLib.utils.stockage.Pair;
import org.newdawn.slick.geom.*;

import java.awt.geom.AffineTransform;

/**
 * Created by com.andres_k on 17/03/2015.
 */
public class MathTools {

    public static float getAngle(double x1, double y1, double x2, double y2) {
        double angle;
        angle = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
        return (float) angle;
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
            return shape.transform(Transform.createRotateTransform((float) Math.toRadians((double) angle), shape.getCenterX(), shape.getCenterY()));
        }
        return shape;
    }

    public static void rotate(Pair<Float, Float> center, Pair<Float, Float> point, float angle) {
        float[] newPoint = {point.getV1(), point.getV2()};
        AffineTransform.getRotateInstance(Math.toRadians(angle), center.getV1(), center.getV2()).transform(newPoint, 0, newPoint, 0, 1);
        point.setV1(newPoint[0]);
        point.setV2(newPoint[1]);
    }

    public static float getAbsDistance(float p1, float p2) {
        float distance = p1 - p2;
        return abs(distance);
    }

    public static float getDistance(float p1, float p2) {
        return p1 - p2;
    }

    public static float abs(float value) {
        return (value > 0.00f ? value : value * -1);
    }

    public static boolean isUpper(double[] d1, double[] d2, double[] p1) {
        return !(((d2[0] - d1[0]) * (p1[1] - d1[1]) - (d2[1] - d1[1]) * (p1[0] - d1[0])) > 0);
    }

    public static boolean isUpper(Shape s1, float angle, Shape s2) {
        float[] p1 = {s1.getCenterX() - (s1.getWidth() / 2), s1.getCenterY() - (s1.getHeight() / 2)};
        AffineTransform.getRotateInstance(Math.toRadians(angle), s1.getCenterX(), s1.getCenterY()).transform(p1, 0, p1, 0, 1);

        float[] p2 = {s1.getCenterX() + (s1.getWidth() / 2), s1.getCenterY() - (s1.getHeight() / 2)};
        AffineTransform.getRotateInstance(Math.toRadians(angle), s1.getCenterX(), s1.getCenterY()).transform(p2, 0, p2, 0, 1);

        float[] p3 = {s1.getCenterX() - (s1.getWidth() / 2), s1.getCenterY() + (s1.getHeight() / 2)};
        AffineTransform.getRotateInstance(Math.toRadians(angle), s1.getCenterX(), s1.getCenterY()).transform(p3, 0, p3, 0, 1);

        float[] p4 = {s1.getCenterX() + (s1.getWidth() / 2), s1.getCenterY() + (s1.getHeight() / 2)};
        AffineTransform.getRotateInstance(Math.toRadians(angle), s1.getCenterX(), s1.getCenterY()).transform(p4, 0, p4, 0, 1);

        Line l1 = new Line(p1[0], p1[1], p2[0], p2[1]);
        Line l2 = new Line(p3[0], p3[1], p4[0], p4[1]);
        Line l3 = new Line(s2.getCenterX(), s2.getCenterY(), s1.getCenterX(), s1.getCenterY());

        Vector2f r1 = l1.intersect(l3);
        Vector2f r2 = l2.intersect(l3);

        boolean result = r1.distance(new Vector2f(s2.getCenterX(), s2.getCenterY())) <= r2.distance(new Vector2f(s2.getCenterX(), s2.getCenterY()));
        if (angle <= 90 && angle >= -90) {
            return result;
        } else {
            return !result;
        }
    }

    public static Rectangle createRectangle(Pair<Float, Float> p1, Pair<Float, Float> p2) {
        float x = p1.getV1();
        float y = p1.getV2();
        float sizeX = getAbsDistance(p1.getV1(), p2.getV1());
        float sizeY = getAbsDistance(p1.getV2(), p2.getV2());

        if (p2.getV1() - x < 0) {
            x = p2.getV1();
        }
        if (p2.getV2() - y < 0) {
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

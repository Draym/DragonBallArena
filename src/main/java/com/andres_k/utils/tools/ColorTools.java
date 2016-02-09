package com.andres_k.utils.tools;

import org.newdawn.slick.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andres_k on 03/07/2015.
 */
public class ColorTools {
    private static Map<Colors, Color> colors;

    public enum Colors {
        TRANSPARENT_GREYBLUE,
        TRANSPARENT_GREYBLACK,
        TRANSPARENT_GREY,
        TRANSPARENT_BLACK,
        TRANSPARENT_YELLOW,
        TRANSPARENT_GREEN,
        TRANSPARENT_BLUE,
        TRANSPARENT_RED,

        SOFT_BLACK,

        GUI_GREY,
        GUI_RED,
        GUI_ORANGE,
        GUI_BLUE,
        GUI_BLUE_LIGHT,

        FULL_TRANSPARENT_WHITE
    }

    public static void init() {
        colors = new HashMap<>();
        colors.put(Colors.TRANSPARENT_GREYBLUE, new Color(0.1f, 0.2f, 0.3f, 0.5f));
        colors.put(Colors.TRANSPARENT_GREY, new Color(0.5f, 0.5f, 0.5f, 0.5f));
        colors.put(Colors.TRANSPARENT_GREYBLACK, new Color(0.2f, 0.2f, 0.2f, 0.6f));
        colors.put(Colors.TRANSPARENT_BLACK, new Color(0f, 0f, 0f, 0.7f));
        colors.put(Colors.FULL_TRANSPARENT_WHITE, new Color(1f, 1f, 1f, 0f));
        colors.put(Colors.SOFT_BLACK, new Color(0f, 0f, 0f, 0.95f));
        colors.put(Colors.TRANSPARENT_RED, new Color(1f, 0f, 0f, 0.5f));
        colors.put(Colors.TRANSPARENT_GREEN, new Color(0f, 1f, 0f, 0.5f));
        colors.put(Colors.TRANSPARENT_BLUE, new Color(0f, 0.8f, 1f, 0.5f));
        colors.put(Colors.TRANSPARENT_YELLOW, new Color(1f, 1f, 0f, 0.5f));

        colors.put(Colors.GUI_GREY, new Color(130, 130, 130));
        colors.put(Colors.GUI_RED, new Color(210, 55, 53));
        colors.put(Colors.GUI_ORANGE, new Color(255, 225, 125));
        colors.put(Colors.GUI_BLUE, new Color(102, 200, 255));
        colors.put(Colors.GUI_BLUE_LIGHT, new Color(204, 239, 255));
    }

    public static Color get(Colors color) {
        if (colors.containsKey(color)) {
            return colors.get(color);
        } else {
            return Color.black;
        }
    }

    public static boolean compareColor(Color color, Colors type) {
        Color compare = colors.get(type);

        if (compare.a == color.a &&
                compare.r == color.r &&
                compare.g == color.g &&
                compare.b == color.b) {
            return true;
        }
        return false;
    }

    public static Color getGreenOrRed(boolean choice) {
        if (choice) {
            return colors.get(Colors.TRANSPARENT_GREEN);
        } else {
            return colors.get(Colors.TRANSPARENT_RED);
        }
    }
}

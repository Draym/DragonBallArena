package com.andres_k.utils.configs;


/**
 * Created by andres_k on 11/03/2015.
 */
public class WindowConfig {
    public static float wLow_sX = 1280;
    public static float wLow_sY = 697;
    public static float wMedium_sX = 1300;
    public static float wMedium_sY = 732;
    public static float wBig_sX = 1900;
    public static float wBig_sY = 950;


    public static int getWMediumSizeX() {
        return (int) wLow_sX;
    }

    public static int getWMediumSizeY() {
        return (int) wLow_sY;
    }

    public static int getWLowSizeX() {
        return (int) wLow_sX;
    }

    public static int getWLowSizeY() {
        return (int) wLow_sY;
    }

    public static int getWBigSizeX() {
        return (int) wBig_sX;
    }

    public static int getWBigSizeY() {
        return (int) wBig_sY;
    }
}

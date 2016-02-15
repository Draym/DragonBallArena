package com.andres_k.utils.configs;


/**
 * Created by andres_k on 11/03/2015.
 */
public class WindowConfig {
    public static float wHome_sX = 1280;
    public static float wHome_sY = 697;
    public static float wLoad_sX = 1300;
    public static float wLoad_sY = 732;
    public static float wSelect_sX = 1600;
    public static float wSelect_sY = 900;
    public static float wBattle_sX = 1440;
    public static float wBattle_sY = 900;
    public static float wGame_sX = 1900;
    public static float wGame_sY = 950;


    public static int getWLoadSizeX() {
        return (int) wLoad_sX;
    }

    public static int getWLoadSizeY() {
        return (int) wLoad_sY;
    }

    public static int getWHomeSizeX() {
        return (int) wHome_sX;
    }

    public static int getWHomeSizeY() {
        return (int) wHome_sY;
    }

    public static int getWSelectSizeX() {
        return (int) wSelect_sX;
    }

    public static int getWSelectSizeY() {
        return (int) wSelect_sY;
    }

    public static int getWBattleSizeX() {
        return (int) wBattle_sX;
    }

    public static int getWBattleSizeY() {
        return (int) wBattle_sY;
    }

    public static int getWGameSizeX() {
        return (int) wGame_sX;
    }

    public static int getWGameSizeY() {
        return (int) wGame_sY;
    }
}

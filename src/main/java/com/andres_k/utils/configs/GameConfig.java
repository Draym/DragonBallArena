package com.andres_k.utils.configs;

import com.andres_k.components.gameComponents.gameObject.EGameObject;

import java.util.ArrayList;

/**
 * Created by andres_k on 05/02/2016.
 */
public class GameConfig {

    public static int maxFps = 60;
    public static long timeLoop = 30;
    public static long currentTimeLoop = 30;

    public static int maxPlayer = 2;
    public static int currentPlayer = 1;
    public static ArrayList<EGameObject> typePlayer;

    public static float speedTravel = 1f;
    public static float speedJump = 2f;
}

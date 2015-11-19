package com.andres_k.utils.configs;

import org.newdawn.slick.AppGameContainer;

/**
 * Created by andres_k on 09/07/2015.
 */
public class GlobalVariable {
    public static long timeLoop = 30;
    public static long currentTimeLoop = 30;
    public static int maxPlayer = 2;
    public static int currentPlayer = 1;

    public static boolean drawCollision = true;
    public static boolean showFps = true;
    public static boolean debug = false;

    public static float speedTravel = 1f;
    public static float speedJump = 2f;

    public static AppGameContainer appGameContainer;
}

package com.andres_k.utils.configs;

import org.newdawn.slick.AppGameContainer;

/**
 * Created by andres_k on 09/07/2015.
 */
public class GlobalVariable {
    public static float defaultSpeed = 1;
    public static float currentSpeed = defaultSpeed;
    public static int maxPlayer = 2;
    public static int currentPlayer = 1;

    public static boolean drawCollision = true;
    public static boolean showFps = false;
    public static boolean debug = false;

    public static AppGameContainer appGameContainer;
}

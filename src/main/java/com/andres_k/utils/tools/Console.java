package com.andres_k.utils.tools;

import com.andres_k.utils.configs.GlobalVariable;

/**
 * Created by andres_k on 13/03/2015.
 */
public class Console {
    public static void debug(String message){
        if (GlobalVariable.debug) {
            System.out.println(message);
        }
    }

    public static void write(String message){
        if (GlobalVariable.log) {
            System.out.println(message);
        }
    }

    public static void err(String locateClass, String locateMethod, String message){
        System.err.println("Locate: [" + locateClass + "." + locateMethod + "] -> " + message);
    }
}

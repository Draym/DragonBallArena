package com.andres_k.utils.tools;

import com.andres_k.utils.configs.GlobalVariable;

/**
 * Created by andres_k on 13/03/2015.
 */
public class ConsoleWrite {
    public static void debug(String message){
        if (GlobalVariable.debug == true) {
            System.out.println(message);
        }
    }

    public static void write(String message){
        System.out.println(message);
    }

    public static void err(String message){
        System.err.println(message);
    }
}

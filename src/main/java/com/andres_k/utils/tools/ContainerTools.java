package com.andres_k.utils.tools;

/**
 * Created by andres_k on 31/10/2015.
 */
public class ContainerTools {

    public static boolean arrayContains(Object array[], Object toFind) {
        for (Object item : array) {
            if (item.equals(toFind))
                return true;
        }
        return false;
    }
}

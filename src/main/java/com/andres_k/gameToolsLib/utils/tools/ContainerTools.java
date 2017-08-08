package com.andres_k.gameToolsLib.utils.tools;

import java.util.List;

/**
 * Created by com.andres_k on 31/10/2015.
 */
public class ContainerTools {

    public static boolean arrayContains(Object array[], Object toFind) {
        for (Object item : array) {
            if (item.equals(toFind))
                return true;
        }
        return false;
    }

    public static String listToString(List<Object> items) {
        StringBuilder string = new StringBuilder();

        for (Object item : items) {
            string.append(item.toString());
            string.append(", ");
        }
        return string.toString();
    }
}

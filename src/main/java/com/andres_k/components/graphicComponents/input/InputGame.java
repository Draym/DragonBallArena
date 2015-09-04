package com.andres_k.components.graphicComponents.input;


import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Input;

import java.util.Observable;

/**
 * Created by andres_k on 11/03/2015.
 */
public class InputGame extends Observable {

    public InputGame() throws JSONException {
    }

    public EnumInput checkInput(int key, EnumInput mode) {
        String keyName;

        if (key == -2) {
            keyName = "MOUSE_LEFT_BUTTON";
        } else if (key == -3) {
            keyName = "MOUSE_RIGHT_BUTTON";
        } else {
            keyName = Input.getKeyName(key);
        }
        return EnumInput.getEnumByValue(InputData.getInputByValue(keyName));
    }
}
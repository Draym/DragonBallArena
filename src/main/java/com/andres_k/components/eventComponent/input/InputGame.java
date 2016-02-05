package com.andres_k.components.eventComponent.input;


import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Input;

/**
 * Created by andres_k on 11/03/2015.
 */
public class InputGame {

    public InputGame() throws JSONException {
    }

    public EInput checkInput(int key) {
        String keyName;

        if (key == -2) {
            keyName = "MOUSE_LEFT_BUTTON";
        } else if (key == -3) {
            keyName = "MOUSE_RIGHT_BUTTON";
        } else {
            keyName = Input.getKeyName(key);
        }
        return EInput.getEnumByValue(InputData.getInputByValue(keyName));
    }
}
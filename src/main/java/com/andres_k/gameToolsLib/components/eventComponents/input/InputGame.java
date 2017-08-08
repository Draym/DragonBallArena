package com.andres_k.gameToolsLib.components.eventComponents.input;


import com.andres_k.custom.component.eventComponents.EInput;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Input;

/**
 * Created by com.andres_k on 11/03/2015.
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
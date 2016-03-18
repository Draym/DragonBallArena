package com.andres_k.components.eventComponent.input;

import com.andres_k.utils.tools.FilesTools;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by andres_k on 22/05/2015.
 */
public class InputData {
    private static Map<EInput, String> availableInput;
    private static JSONObject configs;
    private static String file;

    public static void init(String file) throws JSONException {
        availableInput = new LinkedHashMap<>();
        configs = new JSONObject(FilesTools.readFile(file));
        InputData.file = file;

        Iterator iterator = configs.keys();
        while (iterator.hasNext()) {
            String input = (String) iterator.next();
            String value = configs.getString(input);
            if (EInput.getEnumByValue(input) != EInput.NOTHING) {
                availableInput.put(EInput.getEnumByValue(input), value);
            }
        }
    }

    // GETTERS
    public static Map<EInput, String> getAvailableInput(){
        return availableInput;
    }

    public static String getInputValue(EInput input) {
        return availableInput.get(input);
    }

    public static String getInputByValue(String value){
        for (Map.Entry<EInput, String> entry : availableInput.entrySet()){
            if (entry.getValue().equals(value)){
                return entry.getKey().getValue();
            }
        }
        return "";
    }

    // SETTERS

    public static boolean setAvailableInput(EInput type, String value){
        if (availableInput.containsKey(type) && !availableInput.containsValue(value)){
            availableInput.replace(type, value);
            try {
                configs.put(type.getValue(), value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            FilesTools.writeInFile(file, configs.toString());
            return true;
        }
        return false;
    }
}

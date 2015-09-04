package com.andres_k.components.graphicComponents.input;

import com.andres_k.utils.tools.ConsoleWrite;
import com.andres_k.utils.tools.StringTools;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by andres_k on 22/05/2015.
 */
public class InputData {
    private static Map<EnumInput, String> availableInput;
    private static JSONObject configs;
    private static String file;

    public static void init(String file) throws JSONException {
        ConsoleWrite.debug("file: " + file);
        availableInput = new LinkedHashMap<>();
        configs = new JSONObject(StringTools.readFile(file));
        InputData.file = file;

        Iterator iterator = configs.keys();
        while (iterator.hasNext()) {
            String input = (String) iterator.next();
            String value = configs.getString(input);
            if (EnumInput.getEnumByValue(input) != EnumInput.NOTHING) {
                availableInput.put(EnumInput.getEnumByValue(input), value);
            }
        }
    }

    // GETTERS
    public static Map<EnumInput, String> getAvailableInput(){
        return availableInput;
    }

    public static String getInputValue(EnumInput input) {
        return availableInput.get(input);
    }

    public static String getInputByValue(String value){
        for (Map.Entry<EnumInput, String> entry : availableInput.entrySet()){
            if (entry.getValue().equals(value)){
                return entry.getKey().getValue();
            }
        }
        return "";
    }

    // SETTERS

    public static boolean setAvailableInput(EnumInput type, String value){
        if (availableInput.containsKey(type) && !availableInput.containsValue(value)){
            availableInput.replace(type, value);
            try {
                configs.put(type.getValue(), value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringTools.writeInFile(file, configs.toString());
            return true;
        }
        return false;
    }
}

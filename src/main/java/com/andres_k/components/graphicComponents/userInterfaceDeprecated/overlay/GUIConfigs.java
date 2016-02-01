package com.andres_k.components.graphicComponents.userInterfaceDeprecated.overlay;

import com.andres_k.components.graphicComponents.userInterfaceDeprecated.types.EnumOverlayElement;
import com.andres_k.utils.tools.StringTools;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by andres_k on 04/07/2015.
 */
public class GUIConfigs {
    private Map<EnumOverlayElement, boolean[]> availablePreference;
    private JSONObject configPreference;
    private String filePreference;

    private Map<EnumOverlayElement, String> data;
    private String fileData;

    public GUIConfigs(String filePreference, String fileData) throws JSONException {
        this.availablePreference = new LinkedHashMap<>();
        this.filePreference = filePreference;

        this.data = new LinkedHashMap<>();
        this.fileData = fileData;

        this.initPreference();
        this.initData();
    }

    private void initPreference() throws JSONException {
        this.configPreference = new JSONObject(StringTools.readFile(this.filePreference));
        Iterator iterator = this.configPreference.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            JSONArray array = this.configPreference.getJSONArray(key);
            boolean[] values = new boolean[array.length()];
            for (int i = 0; i < array.length(); ++i) {
                values[i] = array.getBoolean(i);
            }
            this.availablePreference.put(EnumOverlayElement.getEnumByValue(key), values);
        }
    }

    private void initData() throws JSONException {
        JSONObject configData = new JSONObject(StringTools.readFile(this.fileData));
        Iterator iterator = configData.keys();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = configData.getString(key);
            this.data.put(EnumOverlayElement.getEnumByValue(key), value);
        }
    }

    // GETTERS
    public Map<EnumOverlayElement, boolean[]> getAvailablePreference() {
        return this.availablePreference;
    }

    public boolean[] getPreferenceValues(EnumOverlayElement type) {
        return this.availablePreference.get(type);
    }

    public boolean getPreferenceValue(EnumOverlayElement type, int position){
        if (this.availablePreference.containsKey(type)){
            if (position < this.availablePreference.get(type).length){
                return this.availablePreference.get(type)[position];
            }
        }
        return true;
    }

    public String getData(EnumOverlayElement type){
        if (this.data.containsKey(type)){
            return this.data.get(type);
        }
        return "";
    }

    // SETTERS
    public boolean setAvailableInput(EnumOverlayElement type, boolean[] values) {
        if (this.availablePreference.containsKey(type)) {
            this.availablePreference.replace(type, values);
            JSONArray array = new JSONArray();
            for (boolean value : values) {
                array.put(value);
            }
            try {
                this.configPreference.put(type.getValue(), array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            StringTools.writeInInput(getClass().getClassLoader().getResourceAsStream(this.filePreference), this.configPreference.toString());
            return true;
        }
        return false;
    }
}

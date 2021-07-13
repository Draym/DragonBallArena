package com.andres_k.components.gameComponents.gameObject.commands.comboComponent.combo;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.utils.configs.ConfigPath;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.FilesTools;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.*;

/**
 * Created by andres_k on 17/03/2016.
 */
public final class ComboAvailableList {
    private Map<EGameObject, List<Pair<String, String>>> playerCombos;

    private ComboAvailableList() {
        this.playerCombos = new HashMap<>();
        try {
            JSONObject configs = new JSONObject(FilesTools.readTempFile(ConfigPath.comboAvailableList));

            Iterator iterator = configs.keys();
            while (iterator.hasNext()) {
                String name = (String) iterator.next();
                EGameObject player = EGameObject.getEnumByValue(name);
                if (player != EGameObject.NULL) {
                    this.playerCombos.put(player, new ArrayList<>());
                    JSONArray combos = configs.getJSONArray(name);
                    for (int i = 0; i < combos.length(); ++i) {
                        this.playerCombos.get(player).add(new Pair<>(combos.getJSONObject(i).getString("name"), combos.getJSONObject(i).getString("combo")));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static class SingletonHolder {
        private final static ComboAvailableList instance = new ComboAvailableList();
    }

    public static ComboAvailableList get() {
        return SingletonHolder.instance;
    }

    public List<Pair<String, String>> getPlayerCombos(EGameObject player) {
        if (this.playerCombos.containsKey(player)) {
            return this.playerCombos.get(player);
        }
        return new ArrayList<>();
    }
}

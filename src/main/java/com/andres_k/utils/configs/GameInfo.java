package com.andres_k.utils.configs;

import com.andres_k.utils.tools.FilesTools;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by kevin on 18/07/2017.
 */
public class GameInfo {
    private String name = "";
    private String version = "";
    private String gamePathTMP = "";

    private GameInfo() {
        try {
            JSONObject gameInfo = new JSONObject(FilesTools.readFile(ConfigPath.game_info));
            this.name = gameInfo.getString("name");
            this.version = gameInfo.getString("version");
            String path = System.getProperty("java.io.tmpdir");
            this.gamePathTMP = (path.endsWith("/") ? path : path + "/") + "TMP_DLL_" + this.name;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static class SingletonHolder {
        private final static GameInfo instance = new GameInfo();
    }

    public static GameInfo get() {
        return SingletonHolder.instance;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public String getGamePathTMP() {
        return this.gamePathTMP;
    }
}

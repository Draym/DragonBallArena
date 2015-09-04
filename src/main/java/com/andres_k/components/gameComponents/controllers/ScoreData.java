package com.andres_k.components.gameComponents.controllers;

import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.ConsoleWrite;
import com.andres_k.utils.tools.StringTools;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by andres_k on 22/05/2015.
 */
public class ScoreData {
    private static List<Pair<String, String>> dataScore;
    private static JSONObject configs;
    private static String file;

    public static void init(String file) throws JSONException {
        ConsoleWrite.debug("file: " + file);
        dataScore = new ArrayList<>();
        configs = new JSONObject(StringTools.readFile(file));
        ScoreData.file = file;

        JSONArray array = configs.getJSONArray("scores");

        for (int i = 0; i < array.length(); ++i) {
            Iterator iterator = array.getJSONObject(i).keys();
            while (iterator.hasNext()) {
                String pseudo = (String) iterator.next();
                String score = array.getJSONObject(i).getString(pseudo);
                dataScore.add(new Pair<>(pseudo, score));
            }
        }
        parseScore();
    }

    public static void parseScore() {
        while (!isParsed()){
            int compare = -1;
            for (int i = 0; i < dataScore.size(); ++i){
                if (compare != -1 && compare < Integer.valueOf(dataScore.get(i).getV2())) {
                    Pair save = new Pair<>(dataScore.get(i).getV1(), dataScore.get(i).getV2());
                    dataScore.remove(i);
                    dataScore.add(i - 1, save);
                }
                ConsoleWrite.debug("\n");
                for (int i2 = 0; i2 < dataScore.size(); ++i2){
                    ConsoleWrite.debug(dataScore.get(i2).toString());
                }
                compare = Integer.valueOf(dataScore.get(i).getV2());
            }
        }
    }

    public static boolean isParsed() {
        int compare = -1;

        for (int i = 0; i < dataScore.size(); ++i){
            if (compare != -1 && compare < Integer.valueOf(dataScore.get(i).getV2())) {
                return false;
            }
            compare = Integer.valueOf(dataScore.get(i).getV2());
        }
        return true;
    }

    // GETTERS
    public static List<Pair<String, String>> getDataScore() {
        return dataScore;
    }

    // SETTERS

    public static boolean setAvailableScore(String pseudo, String score) {
        dataScore.add(new Pair<>(pseudo, score));
        parseScore();
        try {
            JSONObject item = new JSONObject();
            item.put(pseudo, score);
            configs.getJSONArray("scores").put(item);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringTools.writeInFile(file, configs.toString());
        return true;
    }
}

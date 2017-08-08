package com.andres_k.custom.component.resourceComponents.resources.data;

import com.andres_k.gameToolsLib.components.resourceComponents.resources.data.DataManager;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by com.andres_k on 21/01/2016.
 */
public class SoundData extends DataManager {

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException {
        this.initialiseMethods();
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("init")));
    }

    @Override
    public String success() {
        return "> Sound complete";
    }

    public void init() throws NoSuchMethodException, SlickException, JSONException {
    }
}

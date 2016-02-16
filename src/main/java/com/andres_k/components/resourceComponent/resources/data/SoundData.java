package com.andres_k.components.resourceComponent.resources.data;

import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 21/01/2016.
 */
public class SoundData extends DataManager{

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

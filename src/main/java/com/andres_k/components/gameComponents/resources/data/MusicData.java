package com.andres_k.components.gameComponents.resources.data;

import com.andres_k.components.soundComponents.EnumSound;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 21/01/2016.
 */
public class MusicData extends DataManager {

    public MusicData() {

    }

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException {
        this.initialiseMethods();
        MusicController.addMusic(EnumSound.BACKGROUND_LOAD);
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initBackgroundHome")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initBackgroundSelect")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initBackgroundMulti")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initBackgroundCredits")));
    }

    public void initBackgroundHome() throws NoSuchMethodException, SlickException, JSONException {
        MusicController.addMusic(EnumSound.BACKGROUND_HOME);
    }
    public void initBackgroundSelect() throws NoSuchMethodException, SlickException, JSONException {
        MusicController.addMusic(EnumSound.BACKGROUND_SELECT);
    }
    public void initBackgroundMulti() throws NoSuchMethodException, SlickException, JSONException {
        MusicController.addMusic(EnumSound.BACKGROUND_MULTI);
    }
    public void initBackgroundCredits() throws NoSuchMethodException, SlickException, JSONException {
        MusicController.addMusic(EnumSound.BACKGROUND_CREDITS);
    }
}

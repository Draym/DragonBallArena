package com.andres_k.components.soundComponents;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.utils.configs.ConfigPath;

/**
 * Created by andres_k on 07/07/2015.
 */
public enum ESound {
    NOTHING(""),
    //musics
    BACKGROUND_LOAD(ConfigPath.musics + "/DBZ_theme.ogg"),
    BACKGROUND_HOME(ConfigPath.musics + "/DBZ_namek.ogg"),
    BACKGROUND_MULTI(ConfigPath.musics + "/DBZ_embraceBlueSky.ogg"),
    BACKGROUND_CREDITS(ConfigPath.musics + "/DBZ_capsuleObtain.ogg"),
    BACKGROUND_SELECT(ConfigPath.musics + "/DBZ_overTheGalaxy.ogg"),
    BACKGROUND_GAME(ConfigPath.musics + "/backgroundGame.ogg"),

    //sounds
    EFFECT_FLASH(ConfigPath.effects + "/DBZ_effect_flash.ogg");

    private String path;
    private EGameObject object;

    ESound(String path){
        this.path = path;
        this.object = EGameObject.NULL;
    }

    ESound(EGameObject object, String path){
        this.path = path;
        this.object = object;
    }

    public String getPath(){
        return this.path;
    }

    public EGameObject getObject(){
        return this.object;
    }

    public static ESound getSound(EGameObject object){
        ESound[] sounds = ESound.values();

        for (ESound sound : sounds) {
            if (sound.getObject() == object) {
                return sound;
            }
        }
        return NOTHING;
    }
}

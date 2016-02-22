package com.andres_k.components.resourceComponent.sounds;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.utils.configs.ConfigPath;

/**
 * Created by andres_k on 07/07/2015.
 */
public enum ESound {
    NOTHING(""),
    //musics
    BACKGROUND_LOAD(ConfigPath.sound_musics + "/DBZ_theme.ogg"),
    BACKGROUND_HOME(ConfigPath.sound_musics + "/DBZ_namek.ogg"),
    BACKGROUND_MULTI(ConfigPath.sound_musics + "/DBZ_embraceBlueSky.ogg"),
    BACKGROUND_CREDITS(ConfigPath.sound_musics + "/DBZ_capsuleObtain.ogg"),
    BACKGROUND_SELECT(ConfigPath.sound_musics + "/DBZ_overTheGalaxy.ogg"),
    BACKGROUND_GAME(ConfigPath.sound_musics + "/backgroundGame.ogg"),

    //soundsGame
    EFFECT_FLASH(ConfigPath.sound_effects + "/DBZ_effect_flash.ogg"),

    //soundsMenu
    BUTTON_CLICK(ConfigPath.sound_gui + "/buttonClick.wav"),
    BUTTON_FOCUS(ConfigPath.sound_gui + "/buttonFocus.wav"),
    VALIDATE(ConfigPath.sound_gui + "/validate.wav"),
    UNVALIDATE(ConfigPath.sound_gui + "/unValidate.wav"),

    //soundGoku
    GOKU_KAMEHAMEHA_AIM(ConfigPath.sound_voices + "/goku" + "/Kamehameha - Aim.wav"),
    GOKU_KAMEHAMEHA_FIRE(ConfigPath.sound_voices + "/goku" + "/Kamehameha - Fire.wav");

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

package com.andres_k.components.soundComponents;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andres_k on 07/07/2015.
 */
public class MusicController {
    private static Map<EnumSound, Music> musics;
    private static boolean needInit = true;
    private static float pitch;
    private static float volume;
    private static float maxVolume;


    public static void init() throws SlickException {
        if (needInit == true) {
            musics = new HashMap<>();
            //musics.put(EnumSound.BACKGROUND_HOME, new Music(EnumSound.BACKGROUND_HOME.getPath()));
            //musics.put(EnumSound.BACKGROUND_GAME, new Music(EnumSound.BACKGROUND_GAME.getPath()));
            pitch = 1.0f;
            volume = 1.0f;
            maxVolume = 2.0f;
            needInit = false;
        }
    }

    public static boolean play(EnumSound value) {
        if (!needInit && musics.containsKey(value)) {
            if (musics.get(value).playing()) {
                musics.get(value).resume();
            } else {
                musics.get(value).play(pitch, volume);
            }
            return true;
        }
        return false;
    }

    public static boolean loop(EnumSound value) {
        if (!needInit && musics.containsKey(value)) {
            if (musics.get(value).playing()) {
                musics.get(value).resume();
            } else {
                musics.get(value).loop(pitch, volume);
            }
            return true;
        }
        return false;
    }

    public static boolean resume(EnumSound value) {
        if (!needInit && musics.containsKey(value)) {
            musics.get(value).resume();
            return true;
        }
        return false;
    }

    public static boolean stop(EnumSound value) {
        if (!needInit && musics.containsKey(value)) {
            musics.get(value).stop();
            return true;
        }
        return false;
    }

    public static void changeVolume(float value) {
        volume = value;

        for (Map.Entry<EnumSound, Music> entry : musics.entrySet()) {
            entry.getValue().setVolume(volume);
        }
    }

    public static float getVolume() {
        return volume;
    }

    public static float getMaxVolume() {
        return maxVolume;
    }
}

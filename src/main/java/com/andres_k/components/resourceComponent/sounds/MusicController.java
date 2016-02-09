package com.andres_k.components.resourceComponent.sounds;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andres_k on 07/07/2015.
 */
public class MusicController {
    private static Map<ESound, Music> musics;
    private static boolean needInit = true;
    private static float pitch;
    private static float volume;
    private static float maxVolume;


    public static void init() {
        if (needInit) {
            musics = new HashMap<>();
            pitch = 1.0f;
            volume = 1.0f;
            maxVolume = 2.0f;
            needInit = false;
        }
    }

    public static void addMusic(ESound sound) throws SlickException {
        if (!needInit) {
            musics.put(sound, new Music(sound.getPath()));
        }
    }

    public static boolean play(ESound value) {
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

    public static boolean loop(ESound value) {
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

    public static boolean resume(ESound value) {
        if (!needInit && musics.containsKey(value)) {
            musics.get(value).resume();
            return true;
        }
        return false;
    }

    public static boolean stop(ESound value) {
        if (!needInit && musics.containsKey(value)) {
            musics.get(value).stop();
            return true;
        }
        return false;
    }

    public static void changeVolume(float value) {
        volume = value;

        for (Map.Entry<ESound, Music> entry : musics.entrySet()) {
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

package com.andres_k.components.resourceComponent.sounds;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 07/07/2015.
 */
public class SoundController {
    private static List<SoundElement> sounds;
    private static boolean needInit = true;
    private static float pitch;
    private static float volume;
    private static float maxVolume;

    public static void init() {
        if (needInit) {
            sounds = new ArrayList<>();
            pitch = 1.0f;
            volume = 1.0f;
            maxVolume = 2.0f;
            needInit = false;
        }
    }

    public static void clearSounds() {
        if (!needInit) {
            for (int i = 0; i < sounds.size(); ++i) {
                if (!sounds.get(i).getSound().playing()) {
                    sounds.remove(i);
                    --i;
                }
            }
        }
    }

    public static void update() {
        if (!needInit) {
            clearSounds();
        }
    }

    public static String play(ESound value) {
        if (!needInit) {
            try {
                Sound sound = new Sound(value.getPath());
                SoundElement soundElement = new SoundElement(sound, value);
                //sounds.add(soundElement);

                soundElement.getSound().play(pitch, volume);
                return soundElement.getId();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String loop(ESound value) {
        if (!needInit) {
            try {
                Sound sound = new Sound(value.getPath());

                SoundElement soundElement = new SoundElement(sound, value);
                sounds.add(soundElement);

                soundElement.getSound().loop(pitch, volume);
                return soundElement.getId();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean stop(String id) {
        if (!needInit) {
            for (SoundElement element : sounds) {
                if (element.getId().equals(id)) {
                    element.getSound().stop();
                    return true;
                }
            }
        }
        return false;
    }

    public static void changeVolume(float value) {
        volume = value;
    }

    public static float getVolume() {
        return volume;
    }

    public static float getMaxVolume() {
        return maxVolume;
    }
}

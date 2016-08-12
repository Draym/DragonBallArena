package com.andres_k.components.resourceComponent.sounds;

import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 07/07/2015.
 */
public final class MusicController implements Observer {
    private Map<ESound, Music> musics;
    private float pitch;
    private float volume;
    private float maxVolume;


    private MusicController() {
        this.musics = new HashMap<>();
        this.pitch = 1.0f;
        this.volume = 1.0f;
        this.maxVolume = 1.0f;
    }

    private static class SingletonHolder {
        private final static MusicController instance = new MusicController();
    }

    public static MusicController get() {
        return SingletonHolder.instance;
    }

    public void addMusic(ESound sound) throws SlickException {
        this.musics.put(sound, new Music(sound.getPath()));
    }

    public boolean play(ESound value) {
        if (this.volume <= 0) {
            return false;
        }
        if (this.musics.containsKey(value)) {
            if (this.musics.get(value).playing()) {
                this.musics.get(value).resume();
            } else {
                this.musics.get(value).play(this.pitch, this.volume);
            }
            return true;
        }
        return false;
    }

    public boolean loop(ESound value) {
        if (this.volume <= 0) {
            return false;
        }
        if (this.musics.containsKey(value)) {
            if (this.musics.get(value).playing()) {
                this.musics.get(value).stop();
                this.musics.get(value).loop(this.pitch, this.volume);
            } else {
                this.musics.get(value).loop(this.pitch, this.volume);
            }
            return true;
        }
        return false;
    }

    public boolean resume(ESound value) {
        if (this.volume <= 0) {
            return false;
        }
        if (this.musics.containsKey(value)) {
            this.musics.get(value).resume();
            return true;
        }
        return false;
    }

    public boolean stop(ESound value) {
        if (this.musics.containsKey(value)) {
            this.musics.get(value).stop();
            return true;
        }
        return false;
    }

    public void changeVolume(float value) {
        this.volume = value;

        for (Map.Entry<ESound, Music> entry : this.musics.entrySet()) {
            entry.getValue().setVolume(this.volume);
        }
    }

    public float getVolume() {
        return this.volume;
    }

    public float getMaxVolume() {
        return this.maxVolume;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof TaskComponent) {
            TaskComponent task = (TaskComponent) arg;
            if (task.getTask() instanceof Pair) {
                if (((Pair) task.getTask()).getV1().equals(ETaskType.CHANGE_VOLUME) && ((Pair) task.getTask()).getV2() instanceof Float) {
                    this.changeVolume((Float) ((Pair) task.getTask()).getV2());
                }
            }
        }
    }
}

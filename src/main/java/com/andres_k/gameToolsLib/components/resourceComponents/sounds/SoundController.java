package com.andres_k.gameToolsLib.components.resourceComponents.sounds;

import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.taskComponent.utils.TaskComponent;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by com.andres_k on 07/07/2015.
 */
public final class SoundController implements Observer {
   //private List<SoundElement> sounds;
    private float pitch;
    private float volume;
    private float maxVolume;

    public SoundController() {
     //   this.sounds = new ArrayList<>();
        this.pitch = 1.0f;
        this.volume = 1.0f;
        this.maxVolume = 1.0f;
    }

    private static class SingletonHolder {
        private final static SoundController instance = new SoundController();
    }

    public static SoundController get() {
        return SingletonHolder.instance;
    }
/*
    public void clearSounds() {
        for (int i = 0; i < this.sounds.size(); ++i) {
            if (!this.sounds.get(i).getSound().playing()) {
                this.sounds.remove(i);
                --i;
            }
        }
    }

    public void update() {
        clearSounds();
    }
*/

    public void play(ESound value) {
        if (value != ESound.NOTHING && this.volume > 0) {
            try {
                Sound sound = new Sound(value.getPath());
                sound.play(this.pitch, this.volume);
                //SoundElement soundElement = new SoundElement(sound, value);
                //sounds.add(soundElement);
                //soundElement.getSound().play(this.pitch, this.volume);
                //return soundElement.getId();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        //return null;
    }

    public String loop(ESound value) {
        if (value != ESound.NOTHING && this.volume > 0) {
            try {
                Sound sound = new Sound(value.getPath());
                SoundElement soundElement = new SoundElement(sound, value);
//                this.sounds.add(soundElement);

                soundElement.getSound().loop(this.pitch, this.volume);
                return soundElement.getId();
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void changeVolume(float value) {
        this.volume = value;
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

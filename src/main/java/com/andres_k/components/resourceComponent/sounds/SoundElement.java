package com.andres_k.components.resourceComponent.sounds;

import org.newdawn.slick.Sound;

import java.util.UUID;

/**
 * Created by andres_k on 07/07/2015.
 */
public class SoundElement {
    private Sound sound;
    private String id;
    private ESound type;

    public SoundElement(Sound sound, ESound type){
        this.sound = sound;
        this.type = type;
        this.id = UUID.randomUUID().toString();
    }

    //GETTER
    public String getId() {
        return this.id;
    }

    public ESound getType() {
        return type;
    }

    public Sound getSound(){
        return this.sound;
    }
}

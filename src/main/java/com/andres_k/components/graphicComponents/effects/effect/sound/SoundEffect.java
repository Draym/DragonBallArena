package com.andres_k.components.graphicComponents.effects.effect.sound;

import com.andres_k.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.resourceComponent.sounds.SoundController;
import org.newdawn.slick.Graphics;

/**
 * Created by andres_k on 21/02/2016.
 */
public class SoundEffect extends Effect {
    private ESound sound;
    public SoundEffect(String id, ESound sound) {
        super(id, EffectType.SOUND);
        this.sound = sound;
    }

    @Override
    public Effect copy() {
        return new SoundEffect(this.getId(), this.sound);
    }

    @Override
    public boolean update() {
        SoundController.get().play(this.sound);
        this.stop();
        return true;
    }

    @Override
    public void draw(Graphics g) {
    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        return false;
    }
}

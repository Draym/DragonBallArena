package com.andres_k.components.graphicComponents.effects;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 25/01/2016.
 */
public class EffectManager {
    private List<Effect> effects;

    public EffectManager() {
        this.effects = new ArrayList<>();
    }

    public void update() {
        for (int i = 0; i < this.effects.size(); ++i) {
            this.effects.get(i).update();
            if (!this.effects.get(i).isRunning()) {
                this.effects.remove(i);
            }
        }
    }

    public void render(Graphics g) {
        this.effects.forEach(effect -> effect.draw(g));
    }

    public void playEffect(Effect effect) {
        if (effect != null) {
            effect.play();
            this.effects.add(effect);
        }
    }

    public void stopEffect(String id) {
        this.effects.stream().filter(effect -> effect.getId().equals(id)).forEach(this.effects::remove);
    }

    public boolean hasActivity() {
        return !this.effects.isEmpty();
    }

    public boolean effectIsRunning(String id) {
        return this.effects.stream().anyMatch(effect -> effect.getId().equals(id));
    }

    public boolean effectIsActive(EffectType type) {
        return this.effects.stream().anyMatch(effect -> effect.getType() == type);
    }
}

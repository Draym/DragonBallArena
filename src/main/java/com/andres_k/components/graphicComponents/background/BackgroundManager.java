package com.andres_k.components.graphicComponents.background;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import org.newdawn.slick.Graphics;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by andres_k on 08/02/2016.
 */
public class BackgroundManager {
    private Map<EBackground, BackgroundComponent> components;

    public BackgroundManager() {
        this.components = new LinkedHashMap<>();
    }

    public void draw(Graphics g) {
        this.components.entrySet().forEach(entry -> entry.getValue().draw(g));
    }

    public void update() {
        this.components.entrySet().forEach(entry -> entry.getValue().update());
    }


    public void addComponent(EBackground type, BackgroundComponent component) {
        this.components.put(type, component);
    }

    public void playEffect(EBackground key, int priority, Effect effect) {
        if (this.components.containsKey(key)) {
            this.components.get(key).playEffect(priority, effect);
        }
    }

    public void stopEffect(EBackground key, String id) {
        if (this.components.containsKey(key)) {
            this.components.get(key).stopEffect(id);
        }
    }

    // GETTERS
    public boolean isReady() {
        return this.components.entrySet().stream().allMatch(entry -> entry.getValue().isReady());
    }

    public boolean isRunning() {
        return this.components.entrySet().stream().anyMatch(entry -> entry.getValue().isRunning());
    }

    public boolean hasActivity() {
        return this.components.entrySet().stream().anyMatch(entry -> entry.getValue().hasActivity());
    }

    public boolean effectIsRunning(String id) {
        return this.components.entrySet().stream().anyMatch(entry -> entry.getValue().effectIsRunning(id));
    }

    public boolean effectIsActive(EffectType type) {
        return this.components.entrySet().stream().anyMatch(entry -> entry.getValue().effectIsActive(type));
    }

}

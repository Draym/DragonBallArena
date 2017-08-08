package com.andres_k.gameToolsLib.components.graphicComponents.background;

import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.Effect;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.custom.component.graphicComponents.background.EBackground;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by com.andres_k on 08/02/2016.
 */
public class BackgroundManager {
    private Map<EBackground, BackgroundComponent> components;

    public BackgroundManager() {
        this.components = new LinkedHashMap<>();
    }

    public void draw(Graphics g) throws SlickException {
        for (Map.Entry<EBackground, BackgroundComponent> entry : this.components.entrySet()) {
            entry.getValue().draw(g);
        }
    }

    public void update() {
        this.components.entrySet().forEach(entry -> entry.getValue().update());
    }

    public void run() {
        this.components.entrySet().forEach(entry -> entry.getValue().run());
    }

    public void stop() {
        this.components.entrySet().forEach(entry -> entry.getValue().stop());
    }

    public void addComponent(EBackground type, BackgroundComponent component) {
        this.components.put(type, component);
    }

    public void playEffect(EBackground key, Effect effect, int priority) {
        if (this.components.containsKey(key)) {
            this.components.get(key).playEffect(effect, priority);
        }
    }

    // GETTERS
    public BackgroundComponent getComponent(EBackground id) {
        return this.components.get(id);
    }

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

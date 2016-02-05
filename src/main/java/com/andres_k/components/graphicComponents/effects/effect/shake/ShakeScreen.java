package com.andres_k.components.graphicComponents.effects.effect.shake;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 25/01/2016.
 */
public class ShakeScreen extends Effect {
    private long timer;
    private int speed;
    private int current;
    List<Pair<Integer, Integer>> pattern;

    public ShakeScreen(String id, long duration, int power, int speed) {
        super(id, EffectType.SCREEN_SHAKE, duration);
        this.timer = duration;
        this.current = 0;
        this.speed = speed;
        this.pattern = new ArrayList<>();
        this.pattern.add(new Pair<>(-power, -power));
        this.pattern.add(new Pair<>(power, 0));
        this.pattern.add(new Pair<>(-power, power));
        this.pattern.add(new Pair<>(power, 0));
    }

    @Override
    public void update() {
        this.delta += GameConfig.currentTimeLoop;

        if (this.delta >= speed) {
            this.current = (this.current + 1 < this.pattern.size() ? this.current + 1 : 0);
            this.timer -= delta;
            if (this.timer <= 0) {
                this.running = false;
            }
            Display.setLocation(Display.getX() + this.pattern.get(this.current).getV1(), Display.getY() + this.pattern.get(this.current).getV2());
        }
    }

    @Override
    public void draw(Graphics g) {
    }
}

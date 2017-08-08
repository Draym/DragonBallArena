package com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.shake;

import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.Effect;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import org.lwjgl.opengl.Display;

/**
 * Created by com.andres_k on 25/01/2016.
 */
public class ShakeScreen extends ShakeIt {
    public ShakeScreen(String id, long duration, int power, int speedInterval) {
        super(id, EffectType.SHAKE_SCREEN, duration, power, speedInterval);
    }

    @Override
    public Effect copy() {
        return new ShakeScreen(this.getId(), this.duration, this.power, this.interval);
    }

    @Override
    public boolean update() {
        this.delta += GameConfig.currentTimeLoop;

        if (this.delta >= this.interval) {
            this.current = (this.current + 1 < this.pattern.size() ? this.current + 1 : 0);
            this.timer -= delta;
            if (this.timer <= 0) {
                this.stop();
            }
            Display.setLocation(Display.getX() + this.pattern.get(this.current).getV1(), Display.getY() + this.pattern.get(this.current).getV2());
            this.delta = 0;
        }
        return true;
    }
}

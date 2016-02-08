package com.andres_k.components.graphicComponents.effects;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 25/01/2016.
 */
public class EffectManager {
    private List<Effect> effects;
    private ImageConfiguration conf;

    public EffectManager() {
        this.effects = new ArrayList<>();
        this.conf = new ImageConfiguration();
    }

    public void update() {
        boolean result;
        int level = -1;

        for (int i = 0; i < this.effects.size(); ++i) {
            if (level == -1) {
                level = this.effects.get(i).getPriority();
            } else if (level != this.effects.get(i).getPriority()) {
                break;
            }
            result = this.effects.get(i).update();
            if (!this.effects.get(i).isRunning()) {
                this.effects.remove(i);
            }
            if (!result) {
                break;
            }
        }
    }

    public void draw(Graphics g) {
        this.effects.forEach(effect -> effect.draw(g));
    }

    public void draw(Graphics g, Image image, Float x, Float y) {
        this.conf.reset();
        this.conf.changePosition(x, y);
        this.conf.changeSizes(image.getWidth(), image.getHeight());
        int level = -1;

        for (Effect effect : this.effects) {
            if (level == -1) {
                level = effect.getPriority();
            } else if (level != effect.getPriority()) {
                break;
            }
            if (!effect.applyChanges(this.conf))
                break;
        }

        if (this.conf.drawable) {
            image.draw(this.conf.x, this.conf.y, this.conf.scale, this.conf.color);
            image.setCenterOfRotation(image.getWidth() / 2, image.getHeight() / 2);
            image.rotate(this.conf.rotation);
            this.draw(g);
        }
    }

    public void playEffect(int priority, Effect effect) {
        if (effect != null && priority >= 0) {
            effect.play();
            int index = 0;

            for (Effect tmp : this.effects) {
                if (priority < tmp.getPriority())
                    break;
                ++index;
            }
            effect.setPriority(priority);
            this.effects.add(index, effect);
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

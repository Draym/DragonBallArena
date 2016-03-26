package com.andres_k.components.graphicComponents.effects;

import com.andres_k.components.graphicComponents.effects.effect.Effect;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.components.graphicComponents.effects.effect.directive.ClearEffects;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 25/01/2016.
 */
public class EffectManager {
    private List<Pair<Integer, Pair<Integer, Effect>>> stockedEffects;
    private List<Effect> availableEffects;
    private ImageConfiguration conf;

    public EffectManager() {
        this.stockedEffects = new ArrayList<>();
        this.availableEffects = new ArrayList<>();
        this.conf = new ImageConfiguration();
    }

    public EffectManager(EffectManager effectManager) {
        this.stockedEffects = new ArrayList<>();
        for (Pair<Integer, Pair<Integer, Effect>> effect : effectManager.stockedEffects) {
            this.stockedEffects.add(new Pair<>(effect.getV1(), new Pair<>(effect.getV2().getV1(), effect.getV2().getV2().copy())));
        }
        this.availableEffects = new ArrayList<>();
        for (Effect effect : effectManager.availableEffects) {
            this.availableEffects.add(effect.copy());
        }
        this.conf = new ImageConfiguration(effectManager.conf);
    }

    public void activateEffects(int index) {
        this.stockedEffects.stream().forEach(entity -> {
            if (entity.getV1() == index) {
                if (entity.getV2().getV2() instanceof ClearEffects) {
                    this.availableEffects.clear();
                } else {
                    this.playEffect(entity.getV2().getV1(), entity.getV2().getV2());
                }
            }
        });
    }

    public void restart() {
        this.availableEffects.clear();
        this.stockedEffects.forEach(entry -> entry.getV2().getV2().restart());
    }

    public void update() {
        boolean result;
        int level = -1;

        for (int i = 0; i < this.availableEffects.size(); ++i) {
            if (level == -1) {
                level = this.availableEffects.get(i).getPriority();
            } else if (level != this.availableEffects.get(i).getPriority()) {
                break;
            }
            result = this.availableEffects.get(i).update();
            if (!this.availableEffects.get(i).isRunning()) {
                this.availableEffects.remove(i);
            }
            if (!result) {
                break;
            }
        }
    }

    private void draw(Graphics g) {
        this.availableEffects.forEach(effect -> effect.draw(g));
    }

    public void draw(Graphics g, Image image, Float x, Float y, boolean flipX, boolean flipY) {
        this.conf.reset();
        this.conf.changePosition(x, y);
        this.conf.changeSizes(image.getWidth(), image.getHeight());
        this.conf.setFlip(flipX, flipY);
        int level = -1;

        for (Effect effect : this.availableEffects) {
            if (level == -1) {
                level = effect.getPriority();
            } else if (level != effect.getPriority()) {
                break;
            }
            if (!effect.applyChanges(this.conf)) {
                break;
            }
        }

        if (this.conf.drawable) {
            image.setCenterOfRotation((image.getWidth() * this.conf.scale) / 2, (image.getHeight() * this.conf.scale) / 2);
            image.rotate(this.conf.rotation);
            if (conf.color == null) {
                image.draw(this.conf.x, this.conf.y, this.conf.scale);
            } else {
                image.draw(this.conf.x, this.conf.y, this.conf.scale, this.conf.color);
            }
            this.draw(g);
        }
    }

    public void addEffect(int index, Effect effect, int priority) {
        this.stockedEffects.add(new Pair<>(index, new Pair<>(priority, effect)));
    }

    public void playEffect(int priority, Effect effect) {
        if (effect != null && priority >= 0) {
            effect.play();
            int pos = 0;

            for (Effect tmp : this.availableEffects) {
                if (priority < tmp.getPriority())
                    break;
                ++pos;
            }
            effect.setPriority(priority);
            this.availableEffects.add(pos, effect);
        }
    }

    public boolean hasActivity() {
        return !this.availableEffects.isEmpty();
    }

    public boolean effectIsRunning(String id) {
        return this.availableEffects.stream().anyMatch(effect -> effect.getId().equals(id));
    }

    public boolean effectIsActive(EffectType type) {
        return this.availableEffects.stream().anyMatch(effect -> effect.getType() == type);
    }
}

package com.andres_k.components.controllers;

import com.andres_k.components.gameComponents.resources.ResourceManager;
import com.andres_k.components.graphicComponents.background.Background;
import com.andres_k.components.graphicComponents.background.EnumBackground;
import com.andres_k.components.graphicComponents.effects.EffectFactory;
import com.andres_k.components.soundComponents.EnumSound;
import com.andres_k.components.soundComponents.SoundController;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.Observable;

/**
 * Created by andres_k on 08/07/2015.
 */
public class LoadController extends WindowController {

    public LoadController() throws JSONException, SlickException {
    }

    @Override
    public void enter() {
    }

    @Override
    public void leave() {
    }

    @Override
    public void init() throws SlickException {
        this.background = new Background(ResourceManager.get().getBackgroundAnimator(EnumBackground.LOAD_SCREEN));
        this.background.playEffect(EffectFactory.createFlashScreen(200));
        this.background.playEffect(EffectFactory.createShakeScreen(300, 5, 150));
        SoundController.play(EnumSound.EFFECT_FLASH);
    }

    @Override
    public void renderWindow(Graphics g) {
        this.background.draw(g);
    }

    @Override
    public void updateWindow(GameContainer gameContainer) {
        this.background.update();
    }

    @Override
    public void keyPressed(int key, char c) {

    }

    @Override
    public void keyReleased(int key, char c) {
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
    }

    @Override
    public void mousePressed(int button, int x, int y) {

    }

    @Override
    public void update(Observable o, Object arg) {
    }
}

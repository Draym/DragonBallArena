package com.andres_k.components.controllers;

import com.andres_k.components.graphicComponents.background.EBackground;
import com.andres_k.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.components.graphicComponents.effects.EffectFactory;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.resourceComponent.sounds.SoundController;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.stockage.Tuple;
import com.andres_k.utils.tools.Console;
import org.codehaus.jettison.json.JSONException;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.Observable;

/**
 * Created by andres_k on 08/07/2015.
 */
public class LoadController extends WindowController {

    private int index;
    private boolean loadCompleted;

    public LoadController() throws JSONException, SlickException {
        super(ELocation.LOAD_CONTROLLER);
        this.index = 1;
        this.loadCompleted = false;
    }

    @Override
    public void enter() throws SlickException {
    }

    @Override
    public void leave() {
    }

    @Override
    public void init() throws SlickException {
        this.backgroundManager.addComponent(EBackground.LOAD_SCREEN, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.LOAD_SCREEN)));
        this.backgroundManager.playEffect(EBackground.LOAD_SCREEN, 0, EffectFactory.createFlashEffect(200));
        this.backgroundManager.playEffect(EBackground.LOAD_SCREEN, 0, EffectFactory.createShakeScreen(300, 5, 110));

        this.backgroundManager.addComponent(EBackground.LOGO, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.LOGO), 370, -50));
        this.backgroundManager.playEffect(EBackground.LOGO, 0, EffectFactory.hideIt(1000));
        this.backgroundManager.playEffect(EBackground.LOGO, 1, EffectFactory.zoomIt(12, 0.5f, 1.2f));
//        this.backgroundManager.playEffect(EBackground.LOGO, 2, EffectFactory.createShakeScreen(160, 3, 80));
        SoundController.play(ESound.EFFECT_FLASH);
    }

    @Override
    public void renderWindow(Graphics g) {
        this.backgroundManager.draw(g);
    }

    @Override
    public void update(GameContainer gameContainer) throws SlickException {
        this.backgroundManager.update();

        if (!this.loadCompleted && !this.backgroundManager.hasActivity()) {
            try {
                this.loadCompleted = ResourceManager.get().initialise(index);
                CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.LOAD_GUI_LoadingBar, new Tuple<>(ETaskType.CUT, "body", ResourceManager.get().getPercentInitialised())));
                Console.write("index {" + this.index + "} -> " + (this.loadCompleted ? "loadCompleted" : "running") + " " + (ResourceManager.get().getPercentInitialised() * 100f)+ "%\n");
            } catch (Exception e) {
                throw new SlickException(e.getMessage());
            }
            ++this.index;
        }

    }

    @Override
    public void keyPressed(int key, char c) {

    }

    @Override
    public void keyReleased(int key, char c) {
        if (this.loadCompleted && key == Keyboard.KEY_RETURN) {
            this.stateWindow.enterState(EnumWindow.HOME.getValue());
        }
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

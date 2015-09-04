package com.andres_k.components.graphicComponents.graphic.windowInterface;

import com.andres_k.components.gameComponents.animations.AnimatorOverlayData;
import com.andres_k.components.gameComponents.controllers.InterfaceController;
import com.andres_k.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.components.soundComponents.EnumSound;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.graphicComponents.userInterface.overlay.windowOverlay.InterfaceOverlay;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by andres_k on 08/07/2015.
 */
public class WindowInterface extends WindowBasedGame {

    private Animation background;
    private Image logo;
    private long delta;

    public WindowInterface(int idWindow, GenericSendTask interfaceTask) throws JSONException, SlickException {
        this.idWindow = idWindow;

        this.animatorOverlay = new AnimatorOverlayData();

        this.controller = new InterfaceController();
        interfaceTask.addObserver(this.controller);
        this.controller.addObserver(interfaceTask);

        this.overlay = new InterfaceOverlay();
        interfaceTask.addObserver(this.overlay);
        this.overlay.addObserver(interfaceTask);
    }

    @Override
    public int getID() {
        return this.getIdWindow();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container = gameContainer;
        this.stateWindow = stateBasedGame;

        try {
            this.animatorOverlay.init();
        } catch (JSONException e) {
            throw new SlickException(e.getMessage());
        }


        try {
            this.overlay.initElementsComponent(this.animatorOverlay);
        } catch (Exception e) {
            throw new SlickException(e.getMessage());
        }

        this.controller.setStateWindow(this.stateWindow);
        this.controller.setWindow(this);
        try {
            this.controller.init();
        } catch (JSONException e) {
            throw new SlickException(e.getMessage());
        }

        this.background = new Animation();
        this.background.addFrame(new Image("image/background/backgroundHome.png"), 300);
        this.background.setLooping(false);
        this.logo = new Image("image/background/logo.png");
    }


    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container.setTargetFrameRate(60);
        this.container.setShowFPS(GlobalVariable.showFps);
        this.container.setAlwaysRender(false);
        this.container.setVSync(false);

        this.delta = 0;

        MusicController.loop(EnumSound.BACKGROUND_HOME);
        this.overlay.enter();
        this.controller.enter();
        GlobalVariable.appGameContainer.setDisplayMode(WindowConfig.getW1SizeX(), WindowConfig.getW1SizeY(), false);

    }


    @Override
    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        MusicController.stop(EnumSound.BACKGROUND_HOME);
        this.controller.leave();
        this.clean();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawAnimation(this.background, 0, 0);
        graphics.drawImage(this.logo, 20, 100);
        this.controller.renderWindow(graphics);
        this.overlay.draw(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        this.delta += i;

        if (this.delta > 30) {
            this.controller.updateWindow(gameContainer);
            this.overlay.updateOverlay();
            this.delta = 0;
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        boolean absorbed = this.overlay.event(key, c, EnumInput.PRESSED);
        if (!absorbed) {
            this.controller.keyPressed(key, c);
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        boolean absorbed = this.overlay.event(key, c, EnumInput.RELEASED);
        if (!absorbed) {
            this.controller.keyReleased(key, c);
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        this.controller.mousePressed(button, x, y);
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        if (!this.overlay.isOnFocus(x, y)) {
            this.controller.mouseReleased(button, x, y);
        }
    }

    @Override
    public void quit() {
        this.clean();
        this.container.exit();
    }

    @Override
    public void clean() {
        this.overlay.leave();
    }
}

package com.andres_k.components.graphicComponents.graphic.windowHome;

import com.andres_k.components.gameComponents.animations.data.AnimationFactory;
import com.andres_k.components.gameComponents.animations.data.AnimatorOverlayData;
import com.andres_k.components.controllers.HomeController;
import com.andres_k.components.graphicComponents.graphic.WindowBasedGame;
import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.components.soundComponents.EnumSound;
import com.andres_k.components.soundComponents.MusicController;
import com.andres_k.components.graphicComponents.userInterface.overlay.windowOverlay.HomeOverlay;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by andres_k on 08/07/2015.
 */
public class WindowHome extends WindowBasedGame {

    private Animation background;
    private Image logo;
    private long delta;

    public WindowHome(int idWindow, GenericSendTask interfaceTask) throws JSONException, SlickException {
        this.idWindow = idWindow;

        this.animatorOverlay = new AnimatorOverlayData();

        this.controller = new HomeController();
        interfaceTask.addObserver(this.controller);
        this.controller.addObserver(interfaceTask);

        this.overlay = new HomeOverlay();
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
        } catch (JSONException | NoSuchMethodException e) {
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
        } catch (JSONException | NoSuchMethodException e) {
            throw new SlickException(e.getMessage());
        }

        this.background = AnimationFactory.loadAnimation("image/background/Home/backgroundHome ", ".jpg", 37, 37 /*100*/ /*192*/, true, 80);
        this.background.setPingPong(true);
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
        graphics.drawAnimation(this.background, 0, -9);
        graphics.drawImage(this.logo, -1400, 0);
        this.controller.renderWindow(graphics);
        this.overlay.draw(graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        this.delta += i;

        if (this.delta > GlobalVariable.timeLoop) {
            GlobalVariable.currentTimeLoop = this.delta;
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

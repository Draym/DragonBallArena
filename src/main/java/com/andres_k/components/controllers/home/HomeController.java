package com.andres_k.components.controllers.home;

import com.andres_k.components.controllers.WindowController;
import com.andres_k.components.gameComponents.gameObject.GameObjectController;
import com.andres_k.components.graphicComponents.background.EBackground;
import com.andres_k.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.components.networkComponents.networkGame.NetworkController;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ELocation;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.Observable;

/**
 * Created by andres_k on 08/07/2015.
 */
public class HomeController extends WindowController {

    public HomeController(int idWindow) throws JSONException, SlickException {
        super(ELocation.HOME_CONTROLLER, idWindow);
    }

    @Override
    public void enter() throws SlickException {
        this.backgroundManager.run();
        if (NetworkController.get().isConnected()) {
            NetworkController.get().disconnect();
            GameObjectController.get().leave();
        }
    }

    @Override
    public void leave() {
    }

    @Override
    public void init() throws SlickException {
        this.backgroundManager.addComponent(EBackground.HOME_SCREEN, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.HOME_SCREEN), 0, -9));
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
        super.update(o, arg);
    }
}

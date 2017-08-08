package com.andres_k.custom.component.controllers.waiting;

import com.andres_k.custom.component.graphicComponents.background.EBackground;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.gameToolsLib.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.EffectFactory;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.components.controllers.waiting.LoadController;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by kevin on 03/05/2017.
 */
public class MyLoadController extends LoadController {
    public MyLoadController(int idWindow) throws JSONException, SlickException {
        super(idWindow);
    }

    @Override
    public void init() throws SlickException {
        super.init();
        this.backgroundManager.addComponent(EBackground.LOAD_SCREEN, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.LOAD_SCREEN)));
        this.backgroundManager.playEffect(EBackground.LOAD_SCREEN, EffectFactory.createFlashEffect(200), 0);
        this.backgroundManager.playEffect(EBackground.LOAD_SCREEN, EffectFactory.createShakeScreen(300, 5, 110), 0);
        this.backgroundManager.playEffect(EBackground.LOAD_SCREEN, EffectFactory.createSoundEffect(ESound.EFFECT_FLASH), 0);

        this.backgroundManager.addComponent(EBackground.LOGO, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.LOGO), 370, -50));
        this.backgroundManager.playEffect(EBackground.LOGO, EffectFactory.hideIt(1000), 0);
        this.backgroundManager.playEffect(EBackground.LOGO, EffectFactory.zoomIt(10, 0.1f, 1.3f), 1);
        this.backgroundManager.playEffect(EBackground.LOGO, EffectFactory.createShakeScreen(160, 4, 30), 2);
    }
}

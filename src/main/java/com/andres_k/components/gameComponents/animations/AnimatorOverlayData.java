package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorOverlayData extends AnimatorData{
    private HashMap<EnumOverlayElement, AnimatorController> roundAnimator;
    private HashMap<EnumOverlayElement, AnimatorController> iconAnimator;
    private HashMap<EnumOverlayElement, AnimatorController> menuAnimator;


    public AnimatorOverlayData() {
        this.animatorFactory = new AnimatorOverlayFactory();
        this.roundAnimator = new HashMap<>();
        this.iconAnimator = new HashMap<>();
        this.menuAnimator = new HashMap<>();
    }

    public void init() throws SlickException, JSONException, NoSuchMethodException {
        if (this.needInit) {
            this.initRound();
            this.initIcon();
            this.initMenu();
            this.needInit = false;
        }
    }

    public void initRound() throws SlickException, JSONException, NoSuchMethodException {
        this.addRoundAnimator(this.animatorFactory.getAnimator(EnumSprites.NEW_GAME), EnumOverlayElement.NEW_GAME);
        this.addRoundAnimator(this.animatorFactory.getAnimator(EnumSprites.END_GAME), EnumOverlayElement.END_GAME);
        this.addRoundAnimator(this.animatorFactory.getAnimator(EnumSprites.NEW_ROUND), EnumOverlayElement.NEW_ROUND);
        this.addRoundAnimator(this.animatorFactory.getAnimator(EnumSprites.TIMER), EnumOverlayElement.TIMER);
    }

    public void initIcon() throws SlickException {
    }

    public void initMenu() throws SlickException, JSONException, NoSuchMethodException {
        this.addMenuAnimator(this.animatorFactory.getAnimator(EnumSprites.EXIT), EnumOverlayElement.EXIT);
        this.addMenuAnimator(this.animatorFactory.getAnimator(EnumSprites.SETTINGS), EnumOverlayElement.SETTINGS);
        this.addMenuAnimator(this.animatorFactory.getAnimator(EnumSprites.CONTROLS), EnumOverlayElement.CONTROLS);
        this.addMenuAnimator(this.animatorFactory.getAnimator(EnumSprites.SCREEN), EnumOverlayElement.SCREEN);
        this.addMenuAnimator(this.animatorFactory.getAnimator(EnumSprites.NEW), EnumOverlayElement.NEW);
        this.addMenuAnimator(this.animatorFactory.getAnimator(EnumSprites.GO), EnumOverlayElement.GO);
        this.addMenuAnimator(this.animatorFactory.getAnimator(EnumSprites.NEXT), EnumOverlayElement.NEXT);
        this.addMenuAnimator(this.animatorFactory.getAnimator(EnumSprites.SAVE), EnumOverlayElement.SAVE);
        this.addMenuAnimator(this.animatorFactory.getAnimator(EnumSprites.HIGHSCORE), EnumOverlayElement.HIGHSCORE);
        this.addMenuAnimator(this.animatorFactory.getAnimator(EnumSprites.TOPSCORE), EnumOverlayElement.TOPSCORE);
        this.addMenuAnimator(this.animatorFactory.getAnimator(EnumSprites.ALPHABET), EnumOverlayElement.ALPHABET);
    }

    public void addRoundAnimator(AnimatorController roundAnimatorController, EnumOverlayElement type) {
        this.roundAnimator.put(type, roundAnimatorController);
    }

    public void addIconAnimator(AnimatorController iconAnimatorController, EnumOverlayElement type) {
        this.iconAnimator.put(type, iconAnimatorController);
    }

    public void addMenuAnimator(AnimatorController menuAnimatorController, EnumOverlayElement type) {
        this.menuAnimator.put(type, menuAnimatorController);
    }

    // GETTERS

    public AnimatorController getAnimator(EnumOverlayElement index) throws SlickException {
        if (this.iconAnimator.containsKey(index)) {
            return new AnimatorController(this.iconAnimator.get(index));
        } else if (this.menuAnimator.containsKey(index)) {
            return new AnimatorController(this.menuAnimator.get(index));
        } else if (this.roundAnimator.containsKey(index)) {
            return new AnimatorController(this.roundAnimator.get(index));
        }
        return null;
    }
}


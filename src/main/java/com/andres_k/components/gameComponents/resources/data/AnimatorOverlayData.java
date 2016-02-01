package com.andres_k.components.gameComponents.resources.data;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.resources.EnumSprites;
import com.andres_k.components.gameComponents.resources.factory.AnimatorFactory;
import com.andres_k.components.gameComponents.resources.factory.AnimatorOverlayFactory;
import com.andres_k.components.graphicComponents.userInterfaceDeprecated.types.EnumOverlayElement;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorOverlayData extends DataManager {
    private AnimatorFactory animatorFactory;
    private HashMap<EnumOverlayElement, AnimatorController> roundAnimator;
    private HashMap<EnumOverlayElement, AnimatorController> menuAnimator;


    public AnimatorOverlayData() {
        this.animatorFactory = new AnimatorOverlayFactory();
        this.roundAnimator = new HashMap<>();
        this.menuAnimator = new HashMap<>();
    }

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException {
        this.initialiseMethods();
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initRound")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initMenu")));
    }

    public void initRound() throws NoSuchMethodException, SlickException, JSONException {
        this.addRoundAnimator(this.animatorFactory.getAnimator(EnumSprites.NEW_GAME), EnumOverlayElement.NEW_GAME);
        this.addRoundAnimator(this.animatorFactory.getAnimator(EnumSprites.END_GAME), EnumOverlayElement.END_GAME);
        this.addRoundAnimator(this.animatorFactory.getAnimator(EnumSprites.NEW_ROUND), EnumOverlayElement.NEW_ROUND);
        this.addRoundAnimator(this.animatorFactory.getAnimator(EnumSprites.TIMER), EnumOverlayElement.TIMER);
    }

    public void initMenu() throws NoSuchMethodException, SlickException, JSONException {
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

    private void addRoundAnimator(AnimatorController animatorController, EnumOverlayElement type) {
        this.roundAnimator.put(type, animatorController);
    }

    private void addMenuAnimator(AnimatorController animatorController, EnumOverlayElement type) {
        this.menuAnimator.put(type, animatorController);
    }

    // GETTERS

    public AnimatorController getAnimator(EnumOverlayElement index) throws SlickException {
        if (this.menuAnimator.containsKey(index)) {
            return new AnimatorController(this.menuAnimator.get(index));
        } else if (this.roundAnimator.containsKey(index)) {
            return new AnimatorController(this.roundAnimator.get(index));
        }
        return null;
    }
}


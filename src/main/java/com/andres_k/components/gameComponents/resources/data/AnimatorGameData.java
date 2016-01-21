package com.andres_k.components.gameComponents.resources.data;

import com.andres_k.components.gameComponents.resources.EnumSprites;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.resources.factory.AnimatorFactory;
import com.andres_k.components.gameComponents.resources.factory.AnimatorGameFactory;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameData extends DataManager {
    private AnimatorFactory animatorFactory;
    private HashMap<EnumGameObject, AnimatorController> playerAnimator;
    private HashMap<EnumGameObject, AnimatorController> itemAnimator;

    public AnimatorGameData() {
        this.animatorFactory = new AnimatorGameFactory();
        this.playerAnimator = new HashMap<>();
        this.itemAnimator = new HashMap<>();
    }

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException {
        this.initialiseMethods();
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initPlayers")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initItems")));
    }

    public void initPlayers() throws NoSuchMethodException, SlickException, JSONException {
        this.addPlayerAnimator(this.animatorFactory.getAnimator(EnumSprites.GOKU), EnumGameObject.GOKU);
    }

    public void initItems() throws NoSuchMethodException, SlickException, JSONException {
        this.addItemAnimator(this.animatorFactory.getAnimator(EnumSprites.GROUND), EnumGameObject.GROUND);
        this.addItemAnimator(this.animatorFactory.getAnimator(EnumSprites.WALL), EnumGameObject.WALL);
    }

    private void addPlayerAnimator(AnimatorController animatorController, EnumGameObject type) {
        this.playerAnimator.put(type, animatorController);
    }

    private void addItemAnimator(AnimatorController animatorController, EnumGameObject type) {
        this.itemAnimator.put(type, animatorController);
    }


    // GETTERS
    public AnimatorController getAnimator(EnumGameObject index) throws SlickException {
        if (this.playerAnimator.containsKey(index)) {
            return new AnimatorController(this.playerAnimator.get(index));
        } else if (this.itemAnimator.containsKey(index)) {
            return new AnimatorController(this.itemAnimator.get(index));
        }
        return null;
    }
}


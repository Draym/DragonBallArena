package com.andres_k.components.gameComponents.animations;

import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameData extends AnimatorData{
    private HashMap<EnumGameObject, Animator> itemAnimator;


    public AnimatorGameData() {
        this.animatorFactory = new AnimatorGameFactory();
        this.itemAnimator = new HashMap<>();
    }

    public void init() throws SlickException, JSONException {
        if (this.needInit) {
            this.initItem();
            this.needInit = false;
        }
    }

    public void initItem() throws SlickException, JSONException {
        this.addItemAnimator(this.animatorFactory.getAnimator(EnumSprites.GOKU), EnumGameObject.GOKU);
        this.addItemAnimator(this.animatorFactory.getAnimator(EnumSprites.GROUND), EnumGameObject.GROUND);
        this.addItemAnimator(this.animatorFactory.getAnimator(EnumSprites.WALL), EnumGameObject.WALL);
    }

    public void addItemAnimator(Animator roundAnimator, EnumGameObject type) {
        this.itemAnimator.put(type, roundAnimator);
    }


    // GETTERS
    public Animator getAnimator(EnumGameObject index) throws SlickException {
        if (this.itemAnimator.containsKey(index)) {
            return new Animator(this.itemAnimator.get(index));
        }
        return null;
    }

}


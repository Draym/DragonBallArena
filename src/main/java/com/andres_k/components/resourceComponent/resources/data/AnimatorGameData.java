package com.andres_k.components.resourceComponent.resources.data;

import com.andres_k.components.resourceComponent.resources.ESprites;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.resourceComponent.resources.factory.AnimatorFactory;
import com.andres_k.components.resourceComponent.resources.factory.AnimatorGameFactory;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameData extends DataManager {
    private AnimatorFactory animatorFactory;
    private HashMap<EGameObject, AnimatorController> playerAnimator;
    private HashMap<EGameObject, AnimatorController> itemAnimator;

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

    @Override
    public String success() {
        return "> Game complete";
    }

    public void initPlayers() throws NoSuchMethodException, SlickException, JSONException {
        this.addPlayerAnimator(this.animatorFactory.getAnimator(ESprites.GOKU), EGameObject.GOKU);
        this.addPlayerAnimator(this.animatorFactory.getAnimator(ESprites.VEGETA), EGameObject.VEGETA);
    }

    public void initItems() throws NoSuchMethodException, SlickException, JSONException {
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.GROUND), EGameObject.GROUND);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.WALL), EGameObject.WALL);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.KI_BLAST), EGameObject.KI_BLAST);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.VEGETA_KI_BLAST_BACK), EGameObject.VEGETA_KI_BLAST_BACK);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.VEGETA_KI_BLAST_HEAD), EGameObject.VEGETA_KI_BLAST_HEAD);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.KI_BURST), EGameObject.KI_BURST);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.KAMEHA), EGameObject.KAMEHA);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.KAMEHA_Back), EGameObject.KAMEHA_Back);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.KAMEHA_Body), EGameObject.KAMEHA_Body);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.FINAL_FLASH), EGameObject.FINAL_FLASH);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.FINAL_FLASH_Back), EGameObject.FINAL_FLASH_Back);
        this.addItemAnimator(this.animatorFactory.getAnimator(ESprites.FINAL_FLASH_Body), EGameObject.FINAL_FLASH_Body);
    }

    private void addPlayerAnimator(AnimatorController animatorController, EGameObject type) {
        this.playerAnimator.put(type, animatorController);
    }

    private void addItemAnimator(AnimatorController animatorController, EGameObject type) {
        this.itemAnimator.put(type, animatorController);
    }


    // GETTERS
    public AnimatorController getAnimator(EGameObject index) throws SlickException {
        if (this.playerAnimator.containsKey(index)) {
            return new AnimatorController(this.playerAnimator.get(index));
        } else if (this.itemAnimator.containsKey(index)) {
            return new AnimatorController(this.itemAnimator.get(index));
        }
        throw new SlickException("[ERROR]: The content of " + index.getValue() + " is missing.");
    }
}


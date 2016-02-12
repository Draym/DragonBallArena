package com.andres_k.components.resourceComponent.resources;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.graphicComponents.background.EBackground;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.data.*;
import com.andres_k.utils.tools.Console;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.awt.*;

/**
 * Created by andres_k on 20/01/2016.
 */
public final class ResourceManager {
    private AnimatorGameData gameData;
    private AnimatorGuiData guiData;
    private AnimatorBackgroundData backgroundData;
    private SoundData soundData;
    private MusicData musicData;
    private FontData fontData;

    private ResourceManager() {
        this.gameData = new AnimatorGameData();
        this.guiData = new AnimatorGuiData();
        this.backgroundData = new AnimatorBackgroundData();
        this.soundData = new SoundData();
        this.musicData = new MusicData();
        this.fontData = new FontData();
    }

    private static class SingletonHolder {
        private final static ResourceManager instance = new ResourceManager();
    }

    public static ResourceManager get() {
        return SingletonHolder.instance;
    }

    public void prerequisiteContents() throws Exception {
        this.gameData.prerequisite();
        this.guiData.prerequisite();
        this.backgroundData.prerequisite();
        this.fontData.prerequisite();
    }

    public void prerequisiteMusic() throws Exception {
        try {
            this.musicData.prerequisite();
        } catch (NoSuchMethodException | JSONException e) {
            throw new SlickException(e.getMessage());
        }
    }

    public void prerequisiteSound() throws Exception {
        try {
            this.soundData.prerequisite();
        } catch (NoSuchMethodException | JSONException e) {
            throw new SlickException(e.getMessage());
        }
    }

    public float getPercentInitialised() {
        return ((((float)this.getTotalToInitialise() - (float)this.getTotalNotInitialised()) * 100f) / (float)this.getTotalToInitialise()) / 100f;
    }

    public int getTotalToInitialise() {
        int result = 0;

        result += this.gameData.getTotalMethods();
        result += this.guiData.getTotalMethods();
        result += this.backgroundData.getTotalMethods();
        result += this.musicData.getTotalMethods();
        result += this.soundData.getTotalMethods();
        return result;
    }

    public int getTotalNotInitialised() {
        int result = 0;

        result += this.gameData.getNotInitialised();
        result += this.guiData.getNotInitialised();
        result += this.backgroundData.getNotInitialised();
        result += this.musicData.getNotInitialised();
        result += this.soundData.getNotInitialised();
        return result;
    }

    public boolean initialise(int index) throws Exception {
        if ((index = this.gameData.initialise(index)) <= 0) {
            if (this.gameData.getNotInitialised() == 0)
                Console.write("GameData > complete");
            return false;
        }
        if ((index = this.guiData.initialise(index)) <= 0) {
            if (this.guiData.getNotInitialised() == 0)
                Console.write("GuiData > complete");
            return false;
        }
        if ((index = this.backgroundData.initialise(index)) <= 0) {
            if (this.backgroundData.getNotInitialised() == 0)
                Console.write("BackgroundData > complete");
            return false;
        }
        if ((index = this.musicData.initialise(index)) <= 0) {
            if (this.musicData.getNotInitialised() == 0)
                Console.write("MusicData > complete");
            return false;
        }
        if ((index = this.soundData.initialise(index)) <= 0) {
            if (this.soundData.getNotInitialised() == 0)
                Console.write("SoundData > complete");
            return false;
        }
        if ((index = this.fontData.initialise(index)) <= 0) {
            if (this.fontData.getNotInitialised() == 0)
                Console.write("FontData > complete");
            return false;
        }
        return true;
    }

    // GETTERS

    public AnimatorController getGameAnimator(EGameObject item) throws SlickException {
        return this.gameData.getAnimator(item);
    }

    public AnimatorController getGuiAnimator(EGuiElement item) throws SlickException {
        return this.guiData.getAnimator(item);
    }

    public AnimatorController getBackgroundAnimator(EBackground item) throws SlickException {
        return this.backgroundData.getAnimator(item);
    }

    public Font getFont(EFont item) {
        return this.fontData.getFont(item);
    }
}
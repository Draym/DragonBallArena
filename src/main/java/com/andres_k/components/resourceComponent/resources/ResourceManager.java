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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by andres_k on 20/01/2016.
 */
public final class ResourceManager {

    private enum Category {
        GAME, GUI, BACKGROUND, SOUND, MUSIC, FONT, INTERFACE
    }

    private Map<Category, DataManager> managers;

    private ResourceManager() {
        this.managers = new LinkedHashMap<>();
        this.managers.put(Category.GAME, new AnimatorGameData());
        this.managers.put(Category.GUI, new AnimatorGuiData());
        this.managers.put(Category.BACKGROUND, new AnimatorBackgroundData());
        this.managers.put(Category.SOUND, new SoundData());
        this.managers.put(Category.MUSIC, new MusicData());
        this.managers.put(Category.FONT, new FontData());
        this.managers.put(Category.INTERFACE, new InterfaceElementData());
    }

    private static class SingletonHolder {
        private final static ResourceManager instance = new ResourceManager();
    }

    public static ResourceManager get() {
        return SingletonHolder.instance;
    }

    public void prerequisiteContents() throws SlickException {
        for (Map.Entry<Category, DataManager> entry : this.managers.entrySet()) {
            if (entry.getKey() != Category.SOUND && entry.getKey() != Category.MUSIC) {
                try {
                    entry.getValue().prerequisite();
                } catch (NoSuchMethodException | JSONException | FontFormatException | IOException e) {
                    throw new SlickException(e.getMessage());
                }
            }
        }
    }

    public void prerequisiteMusic() throws SlickException {
        try {
            this.managers.get(Category.MUSIC).prerequisite();
        } catch (NoSuchMethodException | JSONException | FontFormatException | IOException e) {
            throw new SlickException(e.getMessage());
        }
    }

    public void prerequisiteSound() throws SlickException {
        try {
            this.managers.get(Category.SOUND).prerequisite();
        } catch (NoSuchMethodException | JSONException | FontFormatException | IOException e) {
            throw new SlickException(e.getMessage());
        }
    }

    public float getPercentInitialised() {
        return ((((float)this.getTotalToInitialise() - (float)this.getTotalNotInitialised()) * 100f) / (float)this.getTotalToInitialise()) / 100f;
    }

    public int getTotalToInitialise() {
        int result = 0;

        for (Map.Entry<Category, DataManager> entry : this.managers.entrySet()) {
            result += entry.getValue().getTotalMethods();
        }
        return result;
    }

    public int getTotalNotInitialised() {
        int result = 0;

        for (Map.Entry<Category, DataManager> entry : this.managers.entrySet()) {
            result += entry.getValue().getNotInitialised();
        }
        return result;
    }

    public boolean initialise(int index) throws SlickException {
        for (Map.Entry<Category, DataManager> entry : this.managers.entrySet()) {
            try {
                if ((index = entry.getValue().initialise(index)) <= 0) {
                    if (entry.getValue().getNotInitialised() == 0)
                        Console.write(entry.getValue().success());
                    return false;
                }
            } catch (NoSuchMethodException | JSONException | IllegalAccessException | InvocationTargetException e) {
                throw new SlickException(e.getMessage());
            }
        }
        return true;
    }

    // GETTERS

    public AnimatorController getGameAnimator(EGameObject item) throws SlickException {
        return ((AnimatorGameData)this.managers.get(Category.GAME)).getAnimator(item);
    }

    public AnimatorController getGuiAnimator(EGuiElement item) throws SlickException {
        return ((AnimatorGuiData)this.managers.get(Category.GUI)).getAnimator(item);
    }

    public AnimatorController getBackgroundAnimator(EBackground item) throws SlickException {
        return ((AnimatorBackgroundData)this.managers.get(Category.BACKGROUND)).getAnimator(item);
    }

    public Font getFont(EFont item) {
        return ((FontData)this.managers.get(Category.FONT)).getFont(item);
    }
}
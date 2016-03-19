package com.andres_k.utils.configs;


import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.utils.stockage.Pair;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.SlickException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andres_k on 11/03/2015.
 */
public class WindowConfig {
    private EnumWindow current;
    private Map<EnumWindow, Pair<Integer, Integer>> windows;

    private WindowConfig() {
        this.current = EnumWindow.EXIT;
        this.windows = new HashMap<>();
        this.windows.put(EnumWindow.LOAD, new Pair<>(1280, 697));
        this.windows.put(EnumWindow.HOME, new Pair<>(1280, 697));
        this.windows.put(EnumWindow.SELECT_SOLO, new Pair<>(1600, 900));
        this.windows.put(EnumWindow.SELECT_VERSUS, new Pair<>(1600, 900));
        this.windows.put(EnumWindow.SELECT_MULTI, new Pair<>(1600, 900));
        this.windows.put(EnumWindow.BATTLE_CONNECTION, new Pair<>(1600, 900));
        this.windows.put(EnumWindow.GAME, new Pair<>(1900, 900));
    }

    private static class SingletonHolder {
        private final static WindowConfig instance = new WindowConfig();
    }

    public static WindowConfig get() {
        return SingletonHolder.instance;
    }

    public Pair<Integer, Integer> getWindowSizes(EnumWindow window) {
        if (this.windows.containsKey(window)) {
            return this.windows.get(window);
        }
        return new Pair<>(100, 100);
    }

    public void switchWindow(EnumWindow window) throws SlickException {
        if (this.windows.containsKey(window)) {
            int x = Display.getX();
            int y = Display.getY();

            if (this.current != EnumWindow.EXIT) {
                x += ((this.windows.get(this.current).getV1() - this.windows.get(window).getV1()) / 2);
                y += ((this.windows.get(this.current).getV2() - this.windows.get(window).getV2()) / 2);
            }
            GlobalVariable.appGameContainer.setDisplayMode(this.windows.get(window).getV1(), this.windows.get(window).getV2(), false);
            Display.setLocation(x, y);
            this.current = window;
        }
    }

    public EnumWindow getCurrent() {
        return this.current;
    }

    public void setCurrent(EnumWindow window) {
        if (window != EnumWindow.EXIT) {
            this.current = window;
        }
    }

    public int centerPosX(EnumWindow window, int sizeX) {
        if (this.windows.containsKey(window)) {
            return (this.windows.get(window).getV1() / 2) - (sizeX / 2);
        }
        return 0;
    }

    public int centerPosY(EnumWindow window, int sizeY) {
        if (this.windows.containsKey(window)) {
            return (this.windows.get(window).getV2() / 2) - (sizeY / 2);
        }
        return 0;
    }
}

package com.andres_k.components.resourceComponent.resources.data;

import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 13/07/2015.
 */
public abstract class DataManager {
    protected List<Pair<Boolean, Method>> methods;

    protected DataManager() {
        this.methods = new ArrayList<>();
    }

    public abstract void prerequisite() throws NoSuchMethodException, SlickException, JSONException, IOException, FontFormatException;

    public int initialise(int index) throws NoSuchMethodException, SlickException, JSONException, InvocationTargetException, IllegalAccessException {
        for (Pair<Boolean, Method> item : this.methods) {
            if (index <= 0)
                break;
            if (!item.getV1()) {
                item.getV2().invoke(this);
                item.setV1(true);
            }
            index -= 1;
        }
        return index;
    }

    public long getNotInitialised() {
        return this.methods.stream().filter(item -> !item.getV1()).count();
    }

    public long getTotalMethods() {
        return this.methods.size();
    }

    protected abstract void initialiseMethods() throws NoSuchMethodException;

    public abstract String success();
}

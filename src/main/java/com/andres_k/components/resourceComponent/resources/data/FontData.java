package com.andres_k.components.resourceComponent.resources.data;

import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.fonts.FontFactory;
import com.andres_k.utils.configs.ConfigPath;
import com.andres_k.utils.stockage.Pair;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by andres_k on 09/02/2016.
 */
public class FontData extends DataManager {

    private HashMap<EFont, Font> font;

    public FontData() {
        this.font = new HashMap<>();
    }

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException, IOException, FontFormatException {
        this.addFont(EFont.MODERN, FontFactory.createFont(ConfigPath.fonts + "/modern.ttf"));
        this.initialiseMethods();
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("init")));
    }

    @Override
    public String success() {
        return "> Font complete";
    }

    public void init() throws Exception {
        this.addFont(EFont.BASIC, new Font("Times New Roman", Font.BOLD, 16));
    }

    private void addFont(EFont type, Font item) {
        this.font.put(type, item);
    }

    public Font getFont(EFont type) {
        if (this.font.containsKey(type)) {
            return this.font.get(type);
        }
        return this.font.get(EFont.BASIC);
    }
}

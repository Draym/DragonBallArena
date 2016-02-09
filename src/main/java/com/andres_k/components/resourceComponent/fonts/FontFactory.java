package com.andres_k.components.resourceComponent.fonts;

import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by andres_k on 09/02/2016.
 */
public class FontFactory {
    public static Font createFont(String url) throws IOException, FontFormatException {
        InputStream inputStream = ResourceLoader.getResourceAsStream(url);
        Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        return font.deriveFont(16f);
    }
}

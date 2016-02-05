package com.andres_k.components.graphicComponents.effects.effect.flash;

import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorRect;
import com.andres_k.utils.configs.WindowConfig;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by andres_k on 25/01/2016.
 */
public class FlashScreen extends FlashEffect {

    public FlashScreen(String id, long duration) {
        super(id, EffectType.SCREEN_FLASH, duration, new ColorRect(new Rectangle(0, 0, WindowConfig.getWMediumSizeX(), WindowConfig.getWMediumSizeY()), new Color(255, 255, 255, 30)));
    }

}

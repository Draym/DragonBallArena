package com.andres_k.components.graphicComponents.effects.effect.flash;

import com.andres_k.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorRect;
import com.andres_k.utils.tools.ColorTools;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by andres_k on 07/02/2016.
 */
public class FlashImage extends FlashIt {
    public FlashImage(String id, long speed) {
        super(id, EffectType.FLASH, speed, new ColorRect(new Rectangle(0, 0, 0, 0), ColorTools.get(ColorTools.Colors.FULL_TRANSPARENT_WHITE)));
    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        this.flash.setPosition(conf.x, conf.y);
        this.flash.setSizes(conf.imageSizeX, conf.imageSizeY);
        return true;
    }
}

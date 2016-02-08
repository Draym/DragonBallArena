package com.andres_k.components.graphicComponents.effects.effect.flash;

import com.andres_k.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorShape;

/**
 * Created by andres_k on 26/01/2016.
 */
public class FlashShape extends FlashIt {

    public FlashShape(String id, long speed, ColorShape shape) {
        super(id, EffectType.FLASH, speed, shape);
    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        //do nothing;
        return true;
    }
}

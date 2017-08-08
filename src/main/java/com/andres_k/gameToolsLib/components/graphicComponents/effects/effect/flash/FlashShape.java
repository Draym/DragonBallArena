package com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.flash;

import com.andres_k.gameToolsLib.components.graphicComponents.effects.ImageConfiguration;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.Effect;
import com.andres_k.gameToolsLib.components.graphicComponents.effects.effect.EffectType;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;

/**
 * Created by com.andres_k on 26/01/2016.
 */
public class FlashShape extends FlashIt {

    public FlashShape(String id, long speed, ColorShape shape) {
        super(id, EffectType.FLASH, speed, shape);
    }

    @Override
    public Effect copy() {
        return new FlashShape(this.getId(), this.speed, this.flash);
    }

    @Override
    public boolean applyChanges(ImageConfiguration conf) {
        //do nothing;
        return true;
    }
}

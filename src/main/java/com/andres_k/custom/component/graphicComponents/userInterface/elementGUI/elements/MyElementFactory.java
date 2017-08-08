package com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.elements;

import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.ElementWithTitle;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexRelayElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.specific.StateBarPlayerElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by kevin on 08/08/2017.
 */
public class MyElementFactory extends ElementFactory{
    public static ComplexRelayElement createPackSelectPlayer(ELocation packSelectId) throws SlickException {
        ComplexRelayElement selectPlayer = new ComplexRelayElement(packSelectId.getId(), new ColorRect(new Rectangle(0, 0, 500, 500)), false, true);

        int decalX = 0;
        int decalY = 0;

        EGuiElement elements[] = GameConfig.playerChoiceGui;
        EGameObject types[] = GameConfig.playerChoiceType;
        for (Integer i = 0; i < elements.length; ++i) {
            if (i < types.length) {
                ImageElement img = new ImageElement(new ColorRect(new Rectangle(decalX, decalY, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.AVATAR_BORDER), true);
                img.addTasks(MyElementFactory.createImageFocusTasks());
                Button button = new Button(img, MyElementFactory.createBasicButtonTasks(ELocation.getEnumByLocation(selectPlayer.getId()), ELocation.getEnumByLocation(selectPlayer.getId()), new Pair<>(ETaskType.RELAY, i), ESound.BUTTON_FOCUS, ESound.VALIDATE));
                selectPlayer.addItem(new ElementWithTitle(new ImageElement(new ColorRect(new Rectangle(8, 8, 0, 0)), ResourceManager.get().getGuiAnimator(elements[i]), true), button, true));
            }
            if (i == 3) {
                decalX = 0;
                decalY += 140;
            } else {
                decalX += 140;
            }
        }
        return selectPlayer;
    }

    public static StateBarPlayerElement createStateBarPlayer(String playerId, int width, int height, EGuiElement icon, boolean flip) throws SlickException {
        return new StateBarPlayerElement(playerId, new ColorRect(new Rectangle(0, 0, width, height)), ResourceManager.get().getGuiAnimator(icon), flip, true);
    }
}

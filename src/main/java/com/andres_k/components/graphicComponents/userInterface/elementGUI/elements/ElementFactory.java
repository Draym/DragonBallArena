package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements;

import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EStatus;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.ButtonLock;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.TextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.ElementWithTitle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexRelayElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 09/02/2016.
 */
public class ElementFactory {
    public static TextElement createText(String text, Color color, EFont font, int size,float decalX, float decalY) {
        return new TextElement(new StringTimer(text), color, font, size, decalX, decalY, Element.PositionInBody.MIDDLE_MID, true);
    }

    public static ElementWithTitle createTitleTextElement(String text, Color color, EFont font, int size, float decalX, float decalY, GuiElement element) throws SlickException {
        return new ElementWithTitle(createText(text, color, font, size, decalX, decalY), element, true);
    }

    public static ElementWithTitle createButtonTitleText(String text, Color color, EFont font, int size, EGuiElement animator, List<Pair<EStatus, Object>> tasks, List<Pair<EStatus, Object>> contentTasks) throws SlickException {
        return createButtonTitleText(text, color, font, size, animator, 0, 0, tasks, contentTasks);
    }

    public static ElementWithTitle createButtonTitleText(String text, Color color, EFont font, int size, EGuiElement animator, float decalX, float decalY, List<Pair<EStatus, Object>> tasks, List<Pair<EStatus, Object>> contentTasks) throws SlickException {
        ImageElement img = new ImageElement(new ColorRect(new Rectangle(decalX, decalY, 0, 0)), ResourceManager.get().getGuiAnimator(animator), true);
        img.addTasks(contentTasks);
        return createTitleTextElement(text, color, font, size, 30, 0, new Button(img, tasks));
    }

    public static ElementWithTitle createButtonLockTitleText(String text, Color color, EFont font, int size, EGuiElement animator, float decalX, float decalY, List<Pair<EStatus, Object>> tasks, List<Pair<EStatus, Object>> contentTasks) throws SlickException {
        ImageElement img = new ImageElement(new ColorRect(new Rectangle(decalX, decalY, 0, 0)), ResourceManager.get().getGuiAnimator(animator), true);
        img.addTasks(contentTasks);
        return createTitleTextElement(text, color, font, size, 30, 0, new ButtonLock(img, tasks));
    }

    public static List<Pair<EStatus, Object>> createBasicButtonTasks(ELocation local, ELocation target, Object task) {
        return createBasicButtonTasks(local, target, task, ESound.BUTTON_FOCUS, ESound.BUTTON_CLICK);
    }

    public static List<Pair<EStatus, Object>> createImageFocusTasks() {
        List<Pair<EStatus, Object>> component = new ArrayList<>();
        component.add(new Pair<>(EStatus.ON_FOCUS, new Tuple<>(ETaskType.SETTER, "animation", EAnimation.ON_FOCUS)));
        component.add(new Pair<>(EStatus.OFF_FOCUS, new Tuple<>(ETaskType.SETTER, "animation", EAnimation.IDLE)));
        return component;
    }

    public static List<Pair<EStatus, Object>> createImageClickTasks() {
        List<Pair<EStatus, Object>> component = new ArrayList<>();
        component.add(new Pair<>(EStatus.ON_CLICK, new Tuple<>(ETaskType.SETTER, "animation", EAnimation.ON_CLICK)));
        component.add(new Pair<>(EStatus.OFF_CLICK, new Tuple<>(ETaskType.SETTER, "animation", EAnimation.IDLE)));
        return component;
    }

    public static List<Pair<EStatus, Object>> createBasicButtonTasks(ELocation local, ELocation target, Object task, ESound onFocus, ESound onClick) {
        List<Pair<EStatus, Object>> component = new ArrayList<>();
        component.add(new Pair<>(EStatus.ON_CLICK, new TaskComponent(local, target, task)));
        component.add(new Pair<>(EStatus.ON_CLICK, new Pair<>(ETaskType.PLAY_SOUND, onClick)));
        component.add(new Pair<>(EStatus.ON_FOCUS, new Pair<>(ETaskType.PLAY_SOUND, onFocus)));
        return component;
    }

    public static List<Pair<EStatus, Object>> createBasicModalTasks(ELocation local, ELocation target) {
        List<Pair<EStatus, Object>> component = new ArrayList<>();
        component.add(new Pair<>(EStatus.ON_CREATE, new TaskComponent(local, target, ETaskType.STOP_ACTIVITY)));
        component.add(new Pair<>(EStatus.ON_KILL, new TaskComponent(local, target, ETaskType.START_ACTIVITY)));
        return component;
    }

    public static ComplexRelayElement createPackSelectPlayer(ELocation packSelectId) throws SlickException {
        ComplexRelayElement selectPlayer = new ComplexRelayElement(packSelectId.getId(), new ColorRect(new Rectangle(0, 0, 500, 500)), false, true);

        int decalX = 0;
        int decalY = 0;

        EGuiElement elements[] = GameConfig.playerChoiceGui;
        EGameObject types[] = GameConfig.playerChoiceType;
        for (Integer i = 0; i < elements.length; ++i) {
            if (i < types.length) {
                ImageElement img = new ImageElement(new ColorRect(new Rectangle(decalX, decalY, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.AVATAR_BORDER), true);
                img.addTasks(ElementFactory.createImageFocusTasks());
                Button button = new Button(img, ElementFactory.createBasicButtonTasks(ELocation.getEnumByLocation(selectPlayer.getId()), ELocation.getEnumByLocation(selectPlayer.getId()), new Pair<>(ETaskType.RELAY, i), ESound.BUTTON_FOCUS, ESound.VALIDATE));
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
}

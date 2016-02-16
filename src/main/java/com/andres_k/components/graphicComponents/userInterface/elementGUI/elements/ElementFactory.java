package com.andres_k.components.graphicComponents.userInterface.elementGUI.elements;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EStatus;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.TextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.ElementWithTitle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
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

    public static ElementWithTitle createButton(String text, Color color, EFont font, int size, EGuiElement animator, List<Pair<EStatus, Object>> tasks) throws SlickException {
        return createButton(text, color, size, font, animator, 0, 0, tasks);
    }

    public static ElementWithTitle createButton(String text, Color color, int size, EFont font, EGuiElement animator, float decalX, float decalY, List<Pair<EStatus, Object>> tasks) throws SlickException {
        return createTitleElement(text, color, font, size, 30, 0, new Button(new ImageElement(new ColorRect(new Rectangle(decalX, decalY, 0, 0)), ResourceManager.get().getGuiAnimator(animator), true), tasks));
    }

    public static ElementWithTitle createTitleElement(String text, Color color, EFont font, int size, float decalX, float decalY, GuiElement element) throws SlickException {
        return new ElementWithTitle(createText(text, color, font, size, decalX, decalY), element, true);
    }

    public static List<Pair<EStatus, Object>> createBasicButtonTasks(ELocation local, ELocation target, Object task) {
        return createBasicButtonTasks(local, target, task, ESound.BUTTON_FOCUS, ESound.BUTTON_CLICK);
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
}

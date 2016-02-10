package com.andres_k.components.graphicComponents.userInterface.elementGUI;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ElementWithTitle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.TextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 09/02/2016.
 */
public class ElementFactory {
    public static TextElement createText(String text, Color color, int size, EFont font) {
        return new TextElement(ELocation.UNKNOWN.getId(), new StringTimer(text), color, font, size, Element.PositionInBody.MIDDLE_MID, true);
    }

    public static ElementWithTitle createButton(String text, Color color, int size, EFont font, EGuiElement button, List<Pair<EStatus, Object>> tasks) throws SlickException {
        return new ElementWithTitle(createText(text, color, size, font), new Pair<>(30f, 0f), new Button(new ImageElement(ResourceManager.get().getGuiAnimator(button), true), tasks), true);
    }

    public static List<Pair<EStatus, Object>> createBasicButtonTasks(ELocation local, ELocation target, Object task) {
        return createBasicButtonTasks(local, target, task, ESound.BUTTON_CLICK, ESound.BUTTON_FOCUS);
    }

    public static List<Pair<EStatus, Object>> createBasicButtonTasks(ELocation local, ELocation target, Object task, ESound onClick, ESound onFocus) {
        List<Pair<EStatus, Object>> component = new ArrayList<>();
        component.add(new Pair<>(EStatus.ON_CLICK, new TaskComponent(local, target, task)));
        component.add(new Pair<>(EStatus.ON_CLICK, new Pair<>(ETaskType.PLAY_SOUND, onClick)));
        component.add(new Pair<>(EStatus.ON_FOCUS, new Pair<>(ETaskType.PLAY_SOUND, onFocus)));
        return component;
    }

    public static List<Pair<EStatus, Object>> createBasicModalTasks(ELocation local, ELocation target) {
        List<Pair<EStatus, Object>> component = new ArrayList<>();
        component.add(new Pair<>(EStatus.ON_CREATE, ETaskType.START_ACTIVITY));
        component.add(new Pair<>(EStatus.ON_CREATE, new TaskComponent(local, target, ETaskType.STOP_ACTIVITY)));
        component.add(new Pair<>(EStatus.ON_KILL, new TaskComponent(local, target, ETaskType.START_ACTIVITY)));
        component.add(new Pair<>(EStatus.ON_KILL, ETaskType.STOP_ACTIVITY));
        return component;
    }
}

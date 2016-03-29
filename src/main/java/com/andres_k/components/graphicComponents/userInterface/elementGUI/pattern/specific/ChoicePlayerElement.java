package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.specific;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Tuple;
import com.andres_k.utils.tools.ColorTools;
import com.andres_k.utils.tools.Console;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by andres_k on 17/02/2016.
 */
public class ChoicePlayerElement extends ComplexElement {
    private int currentChoice;
    private ELocation target;

    public ChoicePlayerElement(String id, ColorShape container, ELocation target, boolean activated) {
        super(EGuiType.SPECIFIC_CHOICE_PLAYER, id, container, activated);
        this.currentChoice = 0;
        this.target = target;
    }

    @Override
    public void reset() {
        super.reset();
        this.currentChoice = 0;
    }

    @Override
    public void init() throws SlickException {
        this.addItem(new ImageElement(new ColorRect(new Rectangle(54, 600, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.DISABLED), true));
        this.addItem(new ImageElement(new ColorRect(new Rectangle(4, 4, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.CARDS_ALL), true));
        this.addItem(ElementFactory.createButtonLockTitleText("Lock", ColorTools.get(ColorTools.Colors.GUI_GREEN), EFont.MODERN, 20, EGuiElement.BUTTON_LOCK, 54, 600, ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.getEnumByLocation(this.getId()), ETaskType.LAUNCH), ElementFactory.createImageClickTasks()));
    }

    @Override
    public Object doTask(Object task) {

        Console.write("ChoicePlayer: " + task);
        if (task instanceof Integer) {
            Integer choice = (int) task;

            if (choice < GameConfig.playerChoiceGui.length && this.items.size() > 1) {
                this.currentChoice = choice;
                this.items.get(1).doTask(new Tuple<>(ETaskType.SETTER, "index", choice));
                this.setActivated(true);
                if (this.currentChoice < GameConfig.playerChoiceType.length && this.items.size() > 2) {
                    EGameObject type = GameConfig.playerChoiceType[this.currentChoice];

                    if (type.isAvailable()) {
                        this.items.get(0).setActivated(false);
                        this.items.get(2).setActivated(true);
                    } else {
                        this.items.get(0).setActivated(true);
                        this.items.get(2).setActivated(false);
                    }
                }
                return true;
            }
        } else if (task instanceof ETaskType) {
            if (task.equals(ETaskType.LAUNCH)) {
                if (this.currentChoice < GameConfig.playerChoiceType.length) {
                    GameConfig.typePlayer.add(GameConfig.playerChoiceType[this.currentChoice]);
                    CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.getId(), target.getId(), ETaskType.NEXT));
                    return true;
                }
            }
        }
        return super.doTask(task);
    }
}

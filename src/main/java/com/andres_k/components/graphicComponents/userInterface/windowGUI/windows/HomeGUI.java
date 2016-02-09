package com.andres_k.components.graphicComponents.userInterface.windowGUI.windows;

import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EStatus;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.ElementFactory;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.list.ListElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.UserInterface;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.ColorTools;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.util.List;
import java.util.Observable;

/**
 * Created by andres_k on 01/02/2016.
 */
public class HomeGUI extends UserInterface {

    public HomeGUI() {
        super(ELocation.HOME_GUI);
    }

    @Override
    public void init() throws SlickException {
        // menu
        ComplexElement menu = new ComplexElement(ELocation.HOME_GUI_Menu.getId(), new ColorRect(new Rectangle(470, 105, 320, 382)), true);
        menu.addItem(new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.MENU), true));

        ListElement buttonList = new ListElement(new ColorRect(new Rectangle(32, 74, 259, 279)), 0, 9, true);
        buttonList.addItem(ElementFactory.createButton("Practice", ColorTools.get(ColorTools.Colors.GUI_BLUE), 25, EFont.MODERN, EGuiElement.BUTTON_PLAY_SOLO, ElementFactory.createBasicButtonTasks(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, EnumWindow.SELECT_SOLO)));
        buttonList.addItem(ElementFactory.createButton("Versus !", ColorTools.get(ColorTools.Colors.GUI_BLUE), 25, EFont.MODERN, EGuiElement.BUTTON_PLAY_VERSUS, ElementFactory.createBasicButtonTasks(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, EnumWindow.SELECT_VERSUS)));
        buttonList.addItem(ElementFactory.createButton("Battle  ", ColorTools.get(ColorTools.Colors.GUI_ORANGE), 25, EFont.MODERN, EGuiElement.BUTTON_PLAY_MULTI, ElementFactory.createBasicButtonTasks(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, EnumWindow.SELECT_MULTI)));

        List<Pair<EStatus, Object>> tasks = ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.HOME_GUI_Menu, ETaskType.STOP_ACTIVITY);
        tasks.add(new Pair<>(EStatus.ON_CLICK, new TaskComponent(ELocation.UNKNOWN, ELocation.HOME_GUI_Settings, ETaskType.START_ACTIVITY)));
        buttonList.addItem(ElementFactory.createButton("Settings", ColorTools.get(ColorTools.Colors.GUI_GREY), 25, EFont.MODERN, EGuiElement.BUTTON_SETTING,  tasks));
        menu.addItem(buttonList);
        this.taskManager.register(ELocation.HOME_GUI_Menu.getId(), menu);
        this.elements.add(menu);

        // settings
    }

    @Override
    public void initOnEnter() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

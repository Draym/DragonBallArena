package com.andres_k.components.graphicComponents.userInterface.windowGUI.windows;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
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
import com.andres_k.utils.tools.ColorTools;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

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
        ComplexElement menu = new ComplexElement(new ColorRect(new Rectangle(470, 105, 320, 382)), true);
        menu.addItem(new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.MENU), true));

        ListElement buttonList = new ListElement(new ColorRect(new Rectangle(32, 74, 259, 279)), 0, 9, true);
        buttonList.addItem(ElementFactory.createButton("Practice", ColorTools.get(ColorTools.Colors.GUI_BLUE), 25, EFont.MODERN, EGuiElement.BUTTON_PLAY_SOLO,  new TaskComponent(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, ETaskType.TO_SELECT_SOLO)));
        buttonList.addItem(ElementFactory.createButton("Versus !", ColorTools.get(ColorTools.Colors.GUI_BLUE), 25, EFont.MODERN, EGuiElement.BUTTON_PLAY_VERSUS,  new TaskComponent(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, ETaskType.TO_SELECT_VERSUS)));
        buttonList.addItem(ElementFactory.createButton("Battle  ", ColorTools.get(ColorTools.Colors.GUI_ORANGE), 25, EFont.MODERN, EGuiElement.BUTTON_PLAY_MULTI,  new TaskComponent(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, ETaskType.TO_SELECT_MULTI)));
        buttonList.addItem(ElementFactory.createButton("Settings", ColorTools.get(ColorTools.Colors.GUI_GREY), 25, EFont.MODERN, EGuiElement.BUTTON_SETTING,  new TaskComponent(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, ETaskType.TO_SELECT_SOLO)));

        menu.addItem(buttonList);

        this.elements.add(menu);
    }

    @Override
    public void initOnEnter() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

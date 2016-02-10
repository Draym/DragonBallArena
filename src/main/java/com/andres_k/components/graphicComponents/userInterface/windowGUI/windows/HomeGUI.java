package com.andres_k.components.graphicComponents.userInterface.windowGUI.windows;

import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.ElementFactory;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.list.ListElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.modal.Modal;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorCircle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.UserInterface;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.tools.ColorTools;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
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
        ComplexElement menu = new ComplexElement(ELocation.HOME_GUI_Menu.getId(), new ColorRect(new Rectangle(470, 105, 320, 382)), true);
        menu.addItem(new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.MENU), true));

        ListElement buttonList = new ListElement(new ColorRect(new Rectangle(32, 74, 259, 279)), 0, 9, true);
        buttonList.addItem(ElementFactory.createButton("Practice", ColorTools.get(ColorTools.Colors.GUI_BLUE), 25, EFont.MODERN, EGuiElement.BUTTON_PLAY_SOLO, ElementFactory.createBasicButtonTasks(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, EnumWindow.SELECT_SOLO)));
        buttonList.addItem(ElementFactory.createButton("Versus !", ColorTools.get(ColorTools.Colors.GUI_BLUE), 25, EFont.MODERN, EGuiElement.BUTTON_PLAY_VERSUS, ElementFactory.createBasicButtonTasks(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, EnumWindow.SELECT_VERSUS)));
        buttonList.addItem(ElementFactory.createButton("Battle  ", ColorTools.get(ColorTools.Colors.GUI_ORANGE), 25, EFont.MODERN, EGuiElement.BUTTON_PLAY_MULTI, ElementFactory.createBasicButtonTasks(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, EnumWindow.SELECT_MULTI)));

        buttonList.addItem(ElementFactory.createButton("Settings", ColorTools.get(ColorTools.Colors.GUI_GREY), 25, EFont.MODERN, EGuiElement.BUTTON_SETTING,  ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.HOME_GUI_Settings, ETaskType.ON_CREATE)));
        menu.addItem(buttonList);
        this.taskManager.register(ELocation.HOME_GUI_Menu.getId(), menu);
        this.elements.add(menu);

        // settings
        ComplexElement settings = new ComplexElement(new ColorRect(new Rectangle(350, 115, 600, 495)), true);
        settings.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.SETTING)), true));
        settings.addItem(new Button(new ImageElement(new ColorCircle(new Circle(568, 48, 29)), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.HOME_GUI_Settings, ETaskType.ON_KILL, ESound.UNVALIDATE, ESound.NOTHING)));

        Modal modal = new Modal(ELocation.HOME_GUI_Settings.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.getWLowSizeX(), WindowConfig.getWLowSizeY()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), settings);
        modal.addTasks(ElementFactory.createBasicModalTasks(ELocation.HOME_GUI_Settings, ELocation.HOME_GUI_Menu));
        this.taskManager.register(ELocation.HOME_GUI_Settings.getId(), modal);
        this.elements.add(modal);
    }

    @Override
    public void initOnEnter() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

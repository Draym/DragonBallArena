package com.andres_k.components.graphicComponents.userInterface.windowGUI.windows.home;

import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElementsManager;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.list.ListElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.modal.Modal;
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
        ComplexElement menu = new ComplexElement(ELocation.HOME_GUI_Menu.getId(), new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.HOME, 320) - 15, WindowConfig.get().centerPosY(EnumWindow.HOME, 382) - 80, 320, 382)), true);
        menu.addItem(new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.PANEL4), true));

        ListElement buttonList = new ListElement(new ColorRect(new Rectangle(32, 74, 259, 279)), 0, 9, true);
        buttonList.addItem(ElementFactory.createButtonTitleText("Practice", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_PLAY_SOLO, ElementFactory.createBasicButtonTasks(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, EnumWindow.SELECT_SOLO), ElementFactory.createImageFocusTasks()));
        buttonList.addItem(ElementFactory.createButtonTitleText("Versus !", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_PLAY_VERSUS, ElementFactory.createBasicButtonTasks(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, EnumWindow.SELECT_VERSUS), ElementFactory.createImageFocusTasks()));
        buttonList.addItem(ElementFactory.createButtonTitleText("Battle  ", ColorTools.get(ColorTools.Colors.GUI_ORANGE), EFont.MODERN, 25, EGuiElement.BUTTON_PLAY_MULTI, ElementFactory.createBasicButtonTasks(ELocation.HOME_GUI, ELocation.HOME_CONTROLLER, EnumWindow.SELECT_MULTI), ElementFactory.createImageFocusTasks()));
        buttonList.addItem(ElementFactory.createButtonTitleText("Settings", ColorTools.get(ColorTools.Colors.GUI_GREY), EFont.MODERN, 25, EGuiElement.BUTTON_SETTING, ElementFactory.createBasicButtonTasks(ELocation.HOME_GUI_Menu, ELocation.HOME_GUI_Settings, ETaskType.ON_CREATE), ElementFactory.createImageFocusTasks()));
        menu.addItem(buttonList);
        this.taskManager.register(menu.getId(), menu);
        this.elements.add(menu);

        // settings

        ComplexElement settings = new ComplexElement(new ColorRect(new Rectangle(350, 115, 582, 488)), true);
        settings.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL2)), true));
        settings.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.HOME_GUI_Settings, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));
        settings.addItem(GuiElementsManager.get().getElement(ELocation.GUI_ELEMENT_Volumes.getId()));
        settings.addItem(ElementFactory.createButtonTitleText("Controls", ColorTools.get(ColorTools.Colors.GUI_GREY), EFont.MODERN, 25, EGuiElement.BUTTON_CONTROLS, 270, 390, ElementFactory.createBasicButtonTasks(ELocation.HOME_GUI_Settings, ELocation.HOME_GUI_Controls, ETaskType.ON_CREATE), ElementFactory.createImageFocusTasks()));

        Modal settingModal = new Modal(ELocation.HOME_GUI_Settings.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.HOME).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.HOME).getV2()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), settings);
        settingModal.addTasks(ElementFactory.createBasicModalTasks(ELocation.HOME_GUI_Settings, ELocation.HOME_GUI_Menu));
        this.taskManager.register(settingModal.getId(), settingModal);
        this.elements.add(settingModal);

        // controls
        ComplexElement controls = new ComplexElement(new ColorRect(new Rectangle(350, 115, 582, 450)), true);
        controls.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL3)), true));
        controls.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.HOME_GUI_Controls, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));
        controls.addItem(GuiElementsManager.get().getElement(ELocation.GUI_ELEMENT_PlayerControls.getId()));

        Modal controlModal = new Modal(ELocation.HOME_GUI_Controls.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.HOME).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.HOME).getV1()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), controls);
        controlModal.addTasks(ElementFactory.createBasicModalTasks(ELocation.HOME_GUI_Controls, ELocation.HOME_GUI_Settings));
        this.taskManager.register(controlModal.getId(), controlModal);
        this.elements.add(controlModal);

        this.initElements();
    }

    @Override
    public void initOnEnter() throws SlickException {
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

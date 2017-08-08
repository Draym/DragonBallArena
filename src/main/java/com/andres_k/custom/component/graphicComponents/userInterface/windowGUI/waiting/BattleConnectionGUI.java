package com.andres_k.custom.component.graphicComponents.userInterface.windowGUI.waiting;

import com.andres_k.custom.component.graphicComponents.graphic.EnumWindow;
import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.elements.MyElementFactory;
import com.andres_k.custom.component.resourceComponents.sounds.ESound;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.GuiElementsManager;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.list.ListElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.list.ScrollableList;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.pattern.generic.modal.Modal;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorCircle;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.windowGUI.UserInterface;
import com.andres_k.gameToolsLib.components.resourceComponents.fonts.EFont;
import com.andres_k.gameToolsLib.components.resourceComponents.resources.ResourceManager;
import com.andres_k.gameToolsLib.utils.configs.WindowConfig;
import com.andres_k.gameToolsLib.utils.tools.ColorTools;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import java.util.Observable;

/**
 * Created by com.andres_k on 01/02/2016.
 */
public class BattleConnectionGUI extends UserInterface {

    public BattleConnectionGUI() {
        super(ELocation.BATTLE_CONNECTION_GUI);
    }

    @Override
    public void init() throws SlickException {
        // options
        ComplexElement options = new ComplexElement(ELocation.BATTLE_CONNECTION_GUI_Options.getId(), new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.BATTLE_CONNECTION, 320), WindowConfig.get().centerPosY(EnumWindow.BATTLE_CONNECTION, 382), 320, 382)), true);
        options.addItem(new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.PANEL4), true));

        ListElement buttonList = new ListElement(new ColorRect(new Rectangle(32, 74, 259, 279)), 0, 9, true);
        buttonList.addItem(MyElementFactory.createButtonTitleText(" Resume ", ColorTools.get(ColorTools.Colors.GUI_GREEN), EFont.MODERN, 25, EGuiElement.BUTTON_RESUME, MyElementFactory.createBasicButtonTasks(ELocation.BATTLE_CONNECTION_GUI, ELocation.BATTLE_CONNECTION_GUI_Options, ETaskType.ON_KILL), MyElementFactory.createImageFocusTasks()));
        buttonList.addItem(MyElementFactory.createButtonTitleText(" Combo ", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_COMBO, MyElementFactory.createBasicButtonTasks(ELocation.BATTLE_CONNECTION_GUI_Options, ELocation.BATTLE_CONNECTION_GUI_Combos, ETaskType.ON_CREATE), MyElementFactory.createImageFocusTasks()));
        buttonList.addItem(MyElementFactory.createButtonTitleText("Settings", ColorTools.get(ColorTools.Colors.GUI_GREY), EFont.MODERN, 25, EGuiElement.BUTTON_SETTING, MyElementFactory.createBasicButtonTasks(ELocation.BATTLE_CONNECTION_GUI_Options, ELocation.BATTLE_CONNECTION_GUI_Settings, ETaskType.ON_CREATE), MyElementFactory.createImageFocusTasks()));
        buttonList.addItem(MyElementFactory.createButtonTitleText("  Exit  ", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_EXIT, MyElementFactory.createBasicButtonTasks(ELocation.BATTLE_CONNECTION_GUI, ELocation.BATTLE_CONNECTION_CONTROLLER, EnumWindow.HOME), MyElementFactory.createImageFocusTasks()));
        options.addItem(buttonList);

        Modal controlsModal = new Modal(ELocation.BATTLE_CONNECTION_GUI_Options.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.BATTLE_CONNECTION).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.BATTLE_CONNECTION).getV1()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), options, true);
        this.taskManager.register(controlsModal.getId(), controlsModal);
        this.elements.add(controlsModal);

        // settings
        ComplexElement settings = new ComplexElement(new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.BATTLE_CONNECTION, 582), WindowConfig.get().centerPosY(EnumWindow.BATTLE_CONNECTION, 488), 582, 488)), true);
        settings.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL2)), true));
        settings.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), MyElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.BATTLE_CONNECTION_GUI_Settings, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));
        settings.addItem(GuiElementsManager.get().getElement(ELocation.GUI_ELEMENT_Volumes.getId()));
        settings.addItem(MyElementFactory.createButtonTitleText("Controls", ColorTools.get(ColorTools.Colors.GUI_GREY), EFont.MODERN, 25, EGuiElement.BUTTON_CONTROLS, 270, 390, MyElementFactory.createBasicButtonTasks(ELocation.BATTLE_CONNECTION_GUI_Settings, ELocation.BATTLE_CONNECTION_GUI_Controls, ETaskType.ON_CREATE), MyElementFactory.createImageFocusTasks()));

        Modal settingModal = new Modal(ELocation.BATTLE_CONNECTION_GUI_Settings.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.BATTLE_CONNECTION).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.BATTLE_CONNECTION).getV2()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), settings);
        settingModal.addTasks(MyElementFactory.createBasicModalTasks(ELocation.BATTLE_CONNECTION_GUI_Settings, ELocation.BATTLE_CONNECTION_GUI_Options));
        this.taskManager.register(settingModal.getId(), settingModal);
        this.elements.add(settingModal);

        // controls
        ComplexElement controls = new ComplexElement(new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.BATTLE_CONNECTION, 582), WindowConfig.get().centerPosY(EnumWindow.BATTLE_CONNECTION, 450), 582, 450)), true);
        controls.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL3)), true));
        controls.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), MyElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.BATTLE_CONNECTION_GUI_Controls, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));
        controls.addItem(GuiElementsManager.get().getElement(ELocation.GUI_ELEMENT_PlayerControls.getId()));

        Modal controlModal = new Modal(ELocation.BATTLE_CONNECTION_GUI_Controls.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.BATTLE_CONNECTION).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.BATTLE_CONNECTION).getV1()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), controls);
        controlModal.addTasks(MyElementFactory.createBasicModalTasks(ELocation.BATTLE_CONNECTION_GUI_Controls, ELocation.SELECT_VERSUS_GUI_Settings));
        this.taskManager.register(controlModal.getId(), controlModal);
        this.elements.add(controlModal);

        // loading
        ImageElement loadingBar = new ImageElement(new ColorRect(new Rectangle(490, 755, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.LOADING_ANIM), true);
        this.elements.add(loadingBar);

        // list status text
        ListElement textStatus = new ScrollableList(ELocation.BATTLE_CONNECTION_GUI_StatusList.getId(), new ColorRect(new Rectangle(525, 15, 575, 305), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), 0, 10, true);
        this.taskManager.register(textStatus.getId(), textStatus);
        this.elements.add(textStatus);
        this.initElements();
    }

    @Override
    public void initOnEnter() {
        ListElement textStatus = (ListElement) this.getElementById(ELocation.BATTLE_CONNECTION_GUI_StatusList.getId());
        textStatus.clearItems();
    }

    @Override
    public void cleanOnLeave() {

    }

    @Override
    public void update(Observable o, Object arg) {
    }
}

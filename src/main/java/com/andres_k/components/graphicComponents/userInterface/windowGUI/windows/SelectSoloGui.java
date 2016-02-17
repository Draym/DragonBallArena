package com.andres_k.components.graphicComponents.userInterface.windowGUI.windows;

import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElementsManager;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.complex.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.complex.ComplexRelayElement;
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
 * Created by andres_k on 09/02/2016.
 */
public class SelectSoloGui extends UserInterface {
    public SelectSoloGui() {
        super(ELocation.SELECT_SOLO_GUI);
    }

    @Override
    public void init() throws SlickException {
        // options
        ComplexElement options = new ComplexElement(ELocation.SELECT_SOLO_GUI_Options.getId(), new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.SELECT_SOLO, 320), WindowConfig.get().centerPosY(EnumWindow.SELECT_SOLO, 382), 320, 382)), true);
        options.addItem(new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.PANEL4), true));

        ListElement buttonList = new ListElement(new ColorRect(new Rectangle(32, 74, 259, 279)), 0, 9, true);
        buttonList.addItem(ElementFactory.createButtonTitleText(" Resume ", ColorTools.get(ColorTools.Colors.GUI_GREEN), EFont.MODERN, 25, EGuiElement.BUTTON_RESUME, ElementFactory.createBasicButtonTasks(ELocation.SELECT_SOLO_GUI, ELocation.SELECT_SOLO_GUI_Options, ETaskType.ON_KILL)));
        buttonList.addItem(ElementFactory.createButtonTitleText(" Combo ", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_COMBO, ElementFactory.createBasicButtonTasks(ELocation.SELECT_SOLO_GUI_Options, ELocation.SELECT_SOLO_GUI_Combos, ETaskType.ON_CREATE)));
        buttonList.addItem(ElementFactory.createButtonTitleText("Settings", ColorTools.get(ColorTools.Colors.GUI_GREY), EFont.MODERN, 25, EGuiElement.BUTTON_SETTING, ElementFactory.createBasicButtonTasks(ELocation.SELECT_SOLO_GUI_Options, ELocation.SELECT_SOLO_GUI_Settings, ETaskType.ON_CREATE)));
        buttonList.addItem(ElementFactory.createButtonTitleText("  Exit  ", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_EXIT, ElementFactory.createBasicButtonTasks(ELocation.SELECT_SOLO_GUI, ELocation.SELECT_SOLO_CONTROLLER, EnumWindow.HOME)));
        options.addItem(buttonList);

        Modal controlsModal = new Modal(ELocation.SELECT_SOLO_GUI_Options.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.SELECT_SOLO).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.SELECT_SOLO).getV1()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), options, true);
        this.taskManager.register(controlsModal.getId(), controlsModal);
        this.elements.add(controlsModal);

        // settings
        ComplexElement settings = new ComplexElement(new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.SELECT_SOLO, 582), WindowConfig.get().centerPosY(EnumWindow.SELECT_SOLO, 488), 582, 488)), true);
        settings.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL2)), true));
        settings.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.SELECT_SOLO_GUI_Settings, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));
        settings.addItem(GuiElementsManager.get().getElement(ELocation.GUI_ELEMENT_Volumes.getId()));
        settings.addItem(ElementFactory.createButtonTitleText("Controls", ColorTools.get(ColorTools.Colors.GUI_GREY), 25, EFont.MODERN, EGuiElement.BUTTON_CONTROLS, 270, 390, ElementFactory.createBasicButtonTasks(ELocation.SELECT_SOLO_GUI_Settings, ELocation.SELECT_SOLO_GUI_Controls, ETaskType.ON_CREATE)));

        Modal settingModal = new Modal(ELocation.SELECT_SOLO_GUI_Settings.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.SELECT_SOLO).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.SELECT_SOLO).getV2()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), settings);
        settingModal.addTasks(ElementFactory.createBasicModalTasks(ELocation.SELECT_SOLO_GUI_Settings, ELocation.SELECT_SOLO_GUI_Options));
        this.taskManager.register(settingModal.getId(), settingModal);
        this.elements.add(settingModal);

        // controls
        ComplexElement controls = new ComplexElement(new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.SELECT_SOLO, 582), WindowConfig.get().centerPosY(EnumWindow.SELECT_SOLO, 450), 582, 450)), true);
        controls.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL3)), true));
        controls.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.SELECT_SOLO_GUI_Controls, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));
        controls.addItem(GuiElementsManager.get().getElement(ELocation.GUI_ELEMENT_PlayerControls.getId()));

        Modal controlModal = new Modal(ELocation.SELECT_SOLO_GUI_Controls.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.SELECT_SOLO).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.SELECT_SOLO).getV1()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), controls);
        controlModal.addTasks(ElementFactory.createBasicModalTasks(ELocation.SELECT_SOLO_GUI_Controls, ELocation.SELECT_SOLO_GUI_Settings));
        this.taskManager.register(controlModal.getId(), controlModal);
        this.elements.add(controlModal);

        // packSelect

        ComplexRelayElement packSelect = (ComplexRelayElement) GuiElementsManager.get().getElement(ELocation.GUI_ELEMENT_SelectPlayer.getId());
        packSelect.setLocation(WindowConfig.get().centerPosX(EnumWindow.SELECT_SOLO, (int) packSelect.getAbsoluteWidth()), WindowConfig.get().centerPosY(EnumWindow.SELECT_SOLO, (int) packSelect.getAbsoluteHeight()) + 200);
        this.elements.add(packSelect);
        this.initElements();
    }

    @Override
    public void initOnEnter() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

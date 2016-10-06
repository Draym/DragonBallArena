package com.andres_k.components.graphicComponents.userInterface.windowGUI.windows.select;

import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EStatus;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElementsManager;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexRelayElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.list.ListElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.modal.Modal;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.specific.ChoicePlayerElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorCircle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.UserInterface;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.ColorTools;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import java.util.Observable;

/**
 * Created by andres_k on 09/02/2016.
 */
public class SelectVersusGUI extends UserInterface {
    public SelectVersusGUI() {
        super(ELocation.SELECT_VERSUS_GUI);
    }

    @Override
    public void init() throws SlickException {
        //packSelect
        ComplexRelayElement packSelect = ElementFactory.createPackSelectPlayer(ELocation.SELECT_VERSUS_GUI_SelectPackPlayer);
        packSelect.addTarget(ELocation.SELECT_VERSUS_GUI_ChoicePlayer1);
        packSelect.addTarget(ELocation.SELECT_VERSUS_GUI_ChoicePlayer2);
        packSelect.addTask(new Pair<>(EStatus.ON_FINISH, new TaskComponent(this.getLocation(), ELocation.GAME_CONTROLLER, ETaskType.LAUNCH)));
        packSelect.setLocation(WindowConfig.get().centerPosX(EnumWindow.SELECT_VERSUS, (int) packSelect.getAbsoluteWidth()), WindowConfig.get().centerPosY(EnumWindow.SELECT_VERSUS, (int) packSelect.getAbsoluteHeight()) + 200);
        this.taskManager.register(packSelect.getId(), packSelect);
        this.elements.add(packSelect);

        // playerChoice
        ChoicePlayerElement choicePlayer1 = new ChoicePlayerElement(ELocation.SELECT_VERSUS_GUI_ChoicePlayer1.getId(), new ColorRect(new Rectangle(10, 102, 370, 700)), ELocation.SELECT_VERSUS_GUI_SelectPackPlayer, false);
        this.taskManager.register(choicePlayer1.getId(), choicePlayer1);
        this.elements.add(choicePlayer1);

        ChoicePlayerElement choicePlayer2 = new ChoicePlayerElement(ELocation.SELECT_VERSUS_GUI_ChoicePlayer2.getId(), new ColorRect(new Rectangle(1210, 102, 370, 700)), ELocation.SELECT_VERSUS_GUI_SelectPackPlayer, false);
        this.taskManager.register(choicePlayer2.getId(), choicePlayer2);
        this.elements.add(choicePlayer2);

        // options
        ComplexElement options = new ComplexElement(ELocation.SELECT_VERSUS_GUI_Options.getId(), new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.SELECT_VERSUS, 320), WindowConfig.get().centerPosY(EnumWindow.SELECT_VERSUS, 382), 320, 382)), true);
        options.addItem(new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.PANEL4), true));

        ListElement buttonList = new ListElement(new ColorRect(new Rectangle(32, 74, 259, 279)), 0, 9, true);
        buttonList.addItem(ElementFactory.createButtonTitleText(" Resume ", ColorTools.get(ColorTools.Colors.GUI_GREEN), EFont.MODERN, 25, EGuiElement.BUTTON_RESUME, ElementFactory.createBasicButtonTasks(ELocation.SELECT_VERSUS_GUI, ELocation.SELECT_VERSUS_GUI_Options, ETaskType.ON_KILL), ElementFactory.createImageFocusTasks()));
        buttonList.addItem(ElementFactory.createButtonTitleText(" Combo ", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_COMBO, ElementFactory.createBasicButtonTasks(ELocation.SELECT_VERSUS_GUI_Options, ELocation.SELECT_VERSUS_GUI_Combos, ETaskType.ON_CREATE), ElementFactory.createImageFocusTasks()));
        buttonList.addItem(ElementFactory.createButtonTitleText("Settings", ColorTools.get(ColorTools.Colors.GUI_GREY), EFont.MODERN, 25, EGuiElement.BUTTON_SETTING, ElementFactory.createBasicButtonTasks(ELocation.SELECT_VERSUS_GUI_Options, ELocation.SELECT_VERSUS_GUI_Settings, ETaskType.ON_CREATE), ElementFactory.createImageFocusTasks()));
        buttonList.addItem(ElementFactory.createButtonTitleText(" Return ", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_EXIT, ElementFactory.createBasicButtonTasks(ELocation.SELECT_VERSUS_GUI, ELocation.SELECT_VERSUS_CONTROLLER, EnumWindow.HOME), ElementFactory.createImageFocusTasks()));
        options.addItem(buttonList);

        Modal controlsModal = new Modal(ELocation.SELECT_VERSUS_GUI_Options.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.SELECT_VERSUS).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.SELECT_VERSUS).getV1()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), options, true);
        this.taskManager.register(controlsModal.getId(), controlsModal);
        this.elements.add(controlsModal);

        // settings
        ComplexElement settings = new ComplexElement(new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.SELECT_VERSUS, 582), WindowConfig.get().centerPosY(EnumWindow.SELECT_VERSUS, 488), 582, 488)), true);
        settings.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL2)), true));
        settings.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.SELECT_VERSUS_GUI_Settings, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));
        settings.addItem(GuiElementsManager.get().getElement(ELocation.GUI_ELEMENT_Volumes.getId()));
        settings.addItem(ElementFactory.createButtonTitleText("Controls", ColorTools.get(ColorTools.Colors.GUI_GREY), EFont.MODERN, 25, EGuiElement.BUTTON_CONTROLS, 270, 390, ElementFactory.createBasicButtonTasks(ELocation.SELECT_VERSUS_GUI_Settings, ELocation.SELECT_VERSUS_GUI_Controls, ETaskType.ON_CREATE), ElementFactory.createImageFocusTasks()));

        Modal settingModal = new Modal(ELocation.SELECT_VERSUS_GUI_Settings.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.SELECT_VERSUS).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.SELECT_VERSUS).getV2()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), settings);
        settingModal.addTasks(ElementFactory.createBasicModalTasks(ELocation.SELECT_VERSUS_GUI_Settings, ELocation.SELECT_VERSUS_GUI_Options));
        this.taskManager.register(settingModal.getId(), settingModal);
        this.elements.add(settingModal);

        // controls
        ComplexElement controls = new ComplexElement(new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.SELECT_VERSUS, 582), WindowConfig.get().centerPosY(EnumWindow.SELECT_VERSUS, 450), 582, 450)), true);
        controls.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL3)), true));
        controls.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.SELECT_VERSUS_GUI_Controls, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));
        controls.addItem(GuiElementsManager.get().getElement(ELocation.GUI_ELEMENT_PlayerControls.getId()));

        Modal controlModal = new Modal(ELocation.SELECT_VERSUS_GUI_Controls.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.SELECT_VERSUS).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.SELECT_VERSUS).getV1()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), controls);
        controlModal.addTasks(ElementFactory.createBasicModalTasks(ELocation.SELECT_VERSUS_GUI_Controls, ELocation.SELECT_VERSUS_GUI_Settings));
        this.taskManager.register(controlModal.getId(), controlModal);
        this.elements.add(controlModal);

        this.initElements();
    }

    @Override
    public void initOnEnter() {
    }

    @Override
    public void cleanOnLeave() {

    }

    @Override
    public void update(Observable o, Object arg) {
    }
}

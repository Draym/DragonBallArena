package com.andres_k.components.graphicComponents.userInterface.windowGUI.windows.game;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.comboComponent.combo.ComboAvailableList;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElementsManager;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.TextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.ElementWithTitle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.list.ListElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.list.PaginatedList;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.modal.Modal;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorCircle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.UserInterface;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.ColorTools;
import com.andres_k.utils.tools.StringTools;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import java.util.List;
import java.util.Observable;

/**
 * Created by andres_k on 01/02/2016.
 */
public class GameGUI extends UserInterface {

    public GameGUI() {
        super(ELocation.GAME_GUI);
    }

    @Override
    public void init() throws SlickException {
        //options
        ComplexElement options = new ComplexElement(ELocation.GAME_GUI_Options.getId(), new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.GAME, 320), WindowConfig.get().centerPosY(EnumWindow.GAME, 382), 320, 382)), true);
        options.addItem(new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.PANEL4), true));

        ListElement buttonList = new ListElement(new ColorRect(new Rectangle(32, 74, 259, 279)), 0, 9, true);
        buttonList.addItem(ElementFactory.createButtonTitleText(" Resume ", ColorTools.get(ColorTools.Colors.GUI_GREEN), EFont.MODERN, 25, EGuiElement.BUTTON_RESUME, ElementFactory.createBasicButtonTasks(ELocation.GAME_GUI, ELocation.GAME_GUI_Options, ETaskType.ON_KILL), ElementFactory.createImageFocusTasks()));
        buttonList.addItem(ElementFactory.createButtonTitleText(" Combo ", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_COMBO, ElementFactory.createBasicButtonTasks(ELocation.GAME_GUI_Options, ELocation.GAME_GUI_Combos, ETaskType.ON_CREATE), ElementFactory.createImageFocusTasks()));
        buttonList.addItem(ElementFactory.createButtonTitleText("Settings", ColorTools.get(ColorTools.Colors.GUI_GREY), EFont.MODERN, 25, EGuiElement.BUTTON_SETTING, ElementFactory.createBasicButtonTasks(ELocation.GAME_GUI_Options, ELocation.GAME_GUI_Settings, ETaskType.ON_CREATE), ElementFactory.createImageFocusTasks()));
        buttonList.addItem(ElementFactory.createButtonTitleText("  Exit  ", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_EXIT, ElementFactory.createBasicButtonTasks(ELocation.GAME_GUI, ELocation.GAME_CONTROLLER, EnumWindow.HOME), ElementFactory.createImageFocusTasks()));
        options.addItem(buttonList);

        Modal optionModal = new Modal(ELocation.GAME_GUI_Options.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), options, true);
        this.taskManager.register(optionModal.getId(), optionModal);
        this.elements.add(optionModal);

        // settings
        ComplexElement settings = new ComplexElement(new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.GAME, 582), WindowConfig.get().centerPosY(EnumWindow.GAME, 488), 582, 488)), true);
        settings.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL2)), true));
        settings.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.GAME_GUI_Settings, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));
        settings.addItem(GuiElementsManager.get().getElement(ELocation.GUI_ELEMENT_Volumes.getId()));
        settings.addItem(ElementFactory.createButtonTitleText("Controls", ColorTools.get(ColorTools.Colors.GUI_GREY), EFont.MODERN, 25, EGuiElement.BUTTON_CONTROLS, 270, 390, ElementFactory.createBasicButtonTasks(ELocation.GAME_GUI_Settings, ELocation.GAME_GUI_Controls, ETaskType.ON_CREATE), ElementFactory.createImageFocusTasks()));

        Modal settingModal = new Modal(ELocation.GAME_GUI_Settings.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV2()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), settings);
        settingModal.addTasks(ElementFactory.createBasicModalTasks(ELocation.GAME_GUI_Settings, ELocation.GAME_GUI_Options));
        this.taskManager.register(settingModal.getId(), settingModal);
        this.elements.add(settingModal);

        // controls
        ComplexElement controls = new ComplexElement(new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.GAME, 582), WindowConfig.get().centerPosY(EnumWindow.GAME, 450), 582, 450)), true);
        controls.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL3)), true));
        controls.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.GAME_GUI_Controls, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));
        controls.addItem(GuiElementsManager.get().getElement(ELocation.GUI_ELEMENT_PlayerControls.getId()));

        Modal controlModal = new Modal(ELocation.GAME_GUI_Controls.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), controls);
        controlModal.addTasks(ElementFactory.createBasicModalTasks(ELocation.GAME_GUI_Controls, ELocation.GAME_GUI_Settings));
        this.taskManager.register(controlModal.getId(), controlModal);
        this.elements.add(controlModal);

        // state_players

        ListElement ally = new ListElement(ELocation.GAME_GUI_State_AlliedPlayers.getId(), new ColorRect(new Rectangle(0, 10, 500, 100)), 5, 10, true);
        this.taskManager.register(ally.getId(), ally);
        this.elements.add(ally);
        ListElement enemy = new ListElement(ELocation.GAME_GUI_State_EnemyPlayers.getId(), new ColorRect(new Rectangle(1410, 10, 500, 300)), 5, 10, true);
        this.taskManager.register(enemy.getId(), enemy);
        this.elements.add(enemy);

        // combos
        PaginatedList playerCombos = new PaginatedList(ELocation.GUI_ELEMENT_CombosList.getId(), new ColorRect(new Rectangle(5, 50, 570, 390)), new ColorRect(new Rectangle(20, 60, 540, 310)), EGuiElement.TAB_STATUS, 10, 0, 20, 0, true, true);

        ComplexElement combos = new ComplexElement(new ColorRect(new Rectangle(WindowConfig.get().centerPosX(EnumWindow.GAME, 582), WindowConfig.get().centerPosY(EnumWindow.GAME, 450), 582, 450)), true);
        combos.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL3)), true));
        combos.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.GAME_GUI_Combos, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));
        combos.addItem(playerCombos);

        Modal comboModal = new Modal(ELocation.GAME_GUI_Combos.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), combos);
        comboModal.addTasks(ElementFactory.createBasicModalTasks(ELocation.GAME_GUI_Combos, ELocation.GAME_GUI_Options));
        this.taskManager.register(comboModal.getId(), comboModal);
        this.elements.add(comboModal);

        this.initElements();
    }

    @Override
    public void initOnEnter() throws SlickException {
        ListElement ally = (ListElement) this.getElementById(ELocation.GAME_GUI_State_AlliedPlayers.getId());
        ally.clearItems();
        ListElement enemy = (ListElement) this.getElementById(ELocation.GAME_GUI_State_EnemyPlayers.getId());
        enemy.clearItems();
        PaginatedList combos = (PaginatedList) this.getElementById(ELocation.GUI_ELEMENT_CombosList.getId());
        combos.clear();

        for (EGameObject player : GameConfig.typePlayer) {
            combos.createList(ElementFactory.createText(player.getValue(), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 16, 0, 0), 0, 20);
            List<Pair<String, String>> combosList = ComboAvailableList.get().getPlayerCombos(player);
            for (Pair<String, String> item : combosList) {
                TextElement title = new TextElement(new StringTimer(StringTools.formatIt(item.getV1(), 20, ":", 10, "")), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.BASIC, 16, true);
                TextElement content = ElementFactory.createText(item.getV2(), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 16, 200, 0);
                combos.addItem(player.getValue(), new ElementWithTitle(new ColorRect(new org.newdawn.slick.geom.Rectangle(0, 0, 0, 0)), title, content, true));
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

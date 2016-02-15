package com.andres_k.components.graphicComponents.userInterface.windowGUI.windows;

import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.ElementFactory;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.list.ListElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.modal.Modal;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.UserInterface;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.tools.ColorTools;
import org.newdawn.slick.SlickException;
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
        ComplexElement menu = new ComplexElement(new ColorRect(new Rectangle(470, 105, 320, 382)), true);
        menu.addItem(new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.PANEL4), true));

        ListElement buttonList = new ListElement(new ColorRect(new Rectangle(32, 74, 259, 279)), 0, 9, true);
        buttonList.addItem(ElementFactory.createButton(" Resume ", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_PLAY_SOLO, ElementFactory.createBasicButtonTasks(ELocation.SELECT_SOLO_GUI, ELocation.SELECT_SOLO_GUI_Options, ETaskType.ON_KILL)));
        buttonList.addItem(ElementFactory.createButton(" Combos ", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, EGuiElement.BUTTON_PLAY_VERSUS, ElementFactory.createBasicButtonTasks(ELocation.SELECT_SOLO_GUI, ELocation.SELECT_SOLO_GUI_Combos, ETaskType.ON_CREATE)));
        buttonList.addItem(ElementFactory.createButton("Settings", ColorTools.get(ColorTools.Colors.GUI_ORANGE), EFont.MODERN, 25, EGuiElement.BUTTON_PLAY_MULTI, ElementFactory.createBasicButtonTasks(ELocation.SELECT_SOLO_GUI, ELocation.SELECT_SOLO_GUI_Settings, ETaskType.ON_CREATE)));
        buttonList.addItem(ElementFactory.createButton("  Exit  ", ColorTools.get(ColorTools.Colors.GUI_GREY), EFont.MODERN, 25, EGuiElement.BUTTON_SETTING, ElementFactory.createBasicButtonTasks(ELocation.SELECT_SOLO_GUI, ELocation.SELECT_SOLO_CONTROLLER, EnumWindow.HOME)));
        menu.addItem(buttonList);

        Modal controlsModal = new Modal(ELocation.SELECT_SOLO_GUI_Options.getId(), new ColorRect(new Rectangle(0, 0, WindowConfig.get().getWindowSizes(EnumWindow.SELECT_SOLO).getV1(), WindowConfig.get().getWindowSizes(EnumWindow.SELECT_SOLO).getV1()), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), menu);
        this.taskManager.register(controlsModal.getId(), controlsModal);
        this.elements.add(controlsModal);

        this.initElements();
    }

    @Override
    public void initOnEnter() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

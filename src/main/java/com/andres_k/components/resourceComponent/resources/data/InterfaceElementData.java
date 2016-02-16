package com.andres_k.components.resourceComponent.resources.data;

import com.andres_k.components.eventComponent.input.InputData;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EStatus;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElementsManager;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.SliderElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.TextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.ElementWithTitle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.list.PaginatedList;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.modal.Modal;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorCircle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.resourceComponent.sounds.MusicController;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import com.andres_k.utils.tools.ColorTools;
import com.andres_k.utils.tools.StringTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import java.awt.*;
import java.io.IOException;

/**
 * Created by andres_k on 16/02/2016.
 */
public class InterfaceElementData extends DataManager {

    @Override
    public void prerequisite() throws NoSuchMethodException, SlickException, JSONException, IOException, FontFormatException {
        this.initialiseMethods();
    }

    @Override
    protected void initialiseMethods() throws NoSuchMethodException {
        this.methods.clear();
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initSettingsModal")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initControlsModal")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initCombosModal")));
    }

    @Override
    public String success() {
        return "> Interface complete";
    }

    public void initSettingsModal() throws NoSuchMethodException, SlickException, JSONException {
        ComplexElement settings = new ComplexElement(new ColorRect(new org.newdawn.slick.geom.Rectangle(0, 0, 582, 488)), true);
        settings.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL2)), true));
        settings.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.GUI_ELEMENT_Settings, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));

        ComplexElement volumes = new ComplexElement(ELocation.HOME_GUI_Volumes.getId(), new ColorRect(new org.newdawn.slick.geom.Rectangle(30, 70, 520, 300)), true);
        volumes.addItem(new TextElement(new StringTimer("Volumes"), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, 30, 20, Element.PositionInBody.LEFT_UP, true));
        volumes.addItem(new TextElement(new StringTimer("Music :"), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 20, 0, 75, Element.PositionInBody.LEFT_UP, true));
        volumes.addItem(new SliderElement(new ColorRect(new org.newdawn.slick.geom.Rectangle(180, 70, 310, 37)), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER_VALUE), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_SLIDER), true), new Pair<>(ELocation.MUSIC_CONTROLLER, ETaskType.CHANGE_VOLUME), MusicController.get().getVolume(), true));
        volumes.addItem(new TextElement(new StringTimer("Sound Effect :"), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 20, 0, 145, Element.PositionInBody.LEFT_UP, true));
        volumes.addItem(new SliderElement(new ColorRect(new org.newdawn.slick.geom.Rectangle(180, 140, 310, 37)), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER_VALUE), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_SLIDER), true), new Pair<>(ELocation.SOUND_CONTROLLER, ETaskType.CHANGE_VOLUME), MusicController.get().getVolume(), true));

        settings.addItem(ElementFactory.createButton("Controls", ColorTools.get(ColorTools.Colors.GUI_GREY), 25, EFont.MODERN, EGuiElement.BUTTON_CONTROLS, 270, 390, ElementFactory.createBasicButtonTasks(ELocation.GUI_ELEMENT_Settings, ELocation.GUI_ELEMENT_Controls, ETaskType.ON_CREATE)));

        Modal settingModal = new Modal(ELocation.GUI_ELEMENT_Settings.getId(), new ColorRect(new org.newdawn.slick.geom.Rectangle(0, 0, 0, 0), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), settings);
        settingModal.addTasks(ElementFactory.createBasicModalTasks(ELocation.GUI_ELEMENT_Settings, ELocation.SELECT_SOLO_GUI_Options));

        CentralTaskManager.get().register(settingModal.getId(), settingModal);
        GuiElementsManager.get().add(settingModal.getId(), settingModal);
    }

    public void initControlsModal() throws NoSuchMethodException, SlickException, JSONException {
        ComplexElement controls = new ComplexElement(new ColorRect(new org.newdawn.slick.geom.Rectangle(0, 0, 582, 450)), true);
        controls.addItem(new ImageElement((ResourceManager.get().getGuiAnimator(EGuiElement.PANEL3)), true));
        controls.addItem(new Button(new ImageElement(new ColorCircle(new Circle(530, 15, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_CLOSE), true), ElementFactory.createBasicButtonTasks(ELocation.UNKNOWN, ELocation.GUI_ELEMENT_Controls, ETaskType.ON_KILL, ESound.NOTHING, ESound.UNVALIDATE)));

        PaginatedList playerControls = new PaginatedList(new ColorRect(new org.newdawn.slick.geom.Rectangle(5, 50, 570, 390)), new ColorRect(new org.newdawn.slick.geom.Rectangle(20, 60, 540, 310)), EGuiElement.TAB_STATUS, 10, 0, 20, 0, true, true);
        playerControls.addList(ElementFactory.createText("Player 1", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 16, 0, 0), 0, 20);
        playerControls.addList(ElementFactory.createText("Player 2", ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 16, 0, 0), 0, 20);
        InputData.getAvailableInput().entrySet().forEach(entry -> {
            TextElement title = new TextElement(new StringTimer(StringTools.formatIt(entry.getKey().getContainer().getValue(), 20, ":", 10, "")), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.BASIC, 16, true);
            TextElement content = ElementFactory.createText(entry.getValue(), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 16, 200, 0);
            content.addTask(new Pair<>(EStatus.ON_CLICK, new Tuple<>(ETaskType.SETTER, "color", ColorTools.Colors.TRANSPARENT_YELLOW)));
            content.addTask(new Pair<>(EStatus.OFF_CLICK, new Tuple<>(ETaskType.SETTER, "color", ColorTools.Colors.NONE)));
            content.addTask(new Pair<>(EStatus.ON_KILL, new Tuple<>(ETaskType.SETTER, "color", ColorTools.Colors.NONE)));
            playerControls.addItem("Player " + (entry.getKey().getIndex() + 1), new ElementWithTitle(new ColorRect(new org.newdawn.slick.geom.Rectangle(0, 0, 0, 0)), title, content, true));
        });
        controls.addItem(playerControls);

        Modal controlModal = new Modal(ELocation.GUI_ELEMENT_Controls.getId(), new ColorRect(new org.newdawn.slick.geom.Rectangle(0, 0, 0, 0), ColorTools.get(ColorTools.Colors.TRANSPARENT_BLACK)), controls);
        controlModal.addTasks(ElementFactory.createBasicModalTasks(ELocation.GUI_ELEMENT_Controls, ELocation.GUI_ELEMENT_Settings));

        CentralTaskManager.get().register(controls.getId(), controls);
        GuiElementsManager.get().add(controls.getId(), controls);
    }

    public void initCombosModal() throws NoSuchMethodException, SlickException, JSONException {
    }
}

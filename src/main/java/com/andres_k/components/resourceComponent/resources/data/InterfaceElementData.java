package com.andres_k.components.resourceComponent.resources.data;

import com.andres_k.components.eventComponent.input.InputData;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EStatus;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElementsManager;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.ElementFactory;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.buttons.Button;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.SliderElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.TextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.ElementWithTitle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexRelayElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.list.PaginatedList;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.components.resourceComponent.sounds.MusicController;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import com.andres_k.utils.tools.ColorTools;
import com.andres_k.utils.tools.StringTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.awt.FontFormatException;
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
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initVolumesContent")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initControlsContent")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initCombosContent")));
        this.methods.add(new Pair<>(false, this.getClass().getMethod("initSelectPlayerContent")));
    }

    @Override
    public String success() {
        return "> Interface complete";
    }

    public void initVolumesContent() throws NoSuchMethodException, SlickException, JSONException {
        ComplexElement volumes = new ComplexElement(ELocation.GUI_ELEMENT_Volumes.getId(), new ColorRect(new Rectangle(30, 70, 520, 300)), true);
        volumes.addItem(new TextElement(new StringTimer("Volumes"), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 25, 30, 20, Element.PositionInBody.LEFT_UP, true));
        volumes.addItem(new TextElement(new StringTimer("Music :"), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 20, 0, 75, Element.PositionInBody.LEFT_UP, true));
        volumes.addItem(new SliderElement(new ColorRect(new org.newdawn.slick.geom.Rectangle(180, 70, 310, 37)), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER_VALUE), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_SLIDER), true), new Pair<>(ELocation.MUSIC_CONTROLLER, ETaskType.CHANGE_VOLUME), MusicController.get().getVolume(), true));
        volumes.addItem(new TextElement(new StringTimer("Sound Effect :"), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 20, 0, 145, Element.PositionInBody.LEFT_UP, true));
        volumes.addItem(new SliderElement(new ColorRect(new org.newdawn.slick.geom.Rectangle(180, 140, 310, 37)), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.SLIDER_VALUE), true), new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.BUTTON_SLIDER), true), new Pair<>(ELocation.SOUND_CONTROLLER, ETaskType.CHANGE_VOLUME), MusicController.get().getVolume(), true));
        GuiElementsManager.get().add(volumes.getId(), volumes);
    }

    public void initControlsContent() throws NoSuchMethodException, SlickException, JSONException {
        PaginatedList playerControls = new PaginatedList(ELocation.GUI_ELEMENT_PlayerControls.getId(), new ColorRect(new Rectangle(5, 50, 570, 390)), new ColorRect(new org.newdawn.slick.geom.Rectangle(20, 60, 540, 310)), EGuiElement.TAB_STATUS, 10, 0, 20, 0, true, true);
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
        GuiElementsManager.get().add(playerControls.getId(), playerControls);
    }

    public void initCombosContent() throws NoSuchMethodException, SlickException, JSONException {
    }

    public void initSelectPlayerContent() throws NoSuchMethodException, SlickException, JSONException {
        ComplexRelayElement selectPlayer = new ComplexRelayElement(ELocation.GUI_ELEMENT_SelectPlayer.getId(), new ColorRect(new Rectangle(0, 0, 500, 500)), false, true);

        int decalX = 0;
        int decalY = 0;

        EGuiElement elements[] = GameConfig.playerChoiceGui;
        EGameObject types[] = GameConfig.playerChoiceType;
        for (Integer i = 0; i < elements.length; ++i) {
            if (i < types.length) {
                ImageElement img = new ImageElement(new ColorRect(new Rectangle(decalX, decalY, 0, 0)), ResourceManager.get().getGuiAnimator(EGuiElement.AVATAR_BORDER), true);
                img.addTasks(ElementFactory.createImageFocusTasks());
                Button button = new Button(img, ElementFactory.createBasicButtonTasks(ELocation.getEnumByLocation(selectPlayer.getId()), ELocation.getEnumByLocation(selectPlayer.getId()), new Pair<>(ETaskType.RELAY, i), ESound.BUTTON_FOCUS, ESound.VALIDATE));
                selectPlayer.addItem(new ElementWithTitle(new ImageElement(new ColorRect(new Rectangle(8, 8, 0, 0)), ResourceManager.get().getGuiAnimator(elements[i]), true), button, true));
            }
            if (i == 3) {
                decalX = 0;
                decalY += 140;
            } else {
                decalX += 140;
            }
        }
        GuiElementsManager.get().add(selectPlayer.getId(), selectPlayer);
        CentralTaskManager.get().register(selectPlayer.getId(), selectPlayer);
    }
}

package com.andres_k.components.graphicComponents.userInterface.windowGUI.windows;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.TextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.complex.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.generic.ElementWithTitle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.StringTimer;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.UserInterface;
import com.andres_k.components.resourceComponent.fonts.EFont;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.stockage.Tuple;
import com.andres_k.utils.tools.ColorTools;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.util.Observable;

/**
 * Created by andres_k on 01/02/2016.
 */
public class LoadGUI extends UserInterface {

    public LoadGUI() {
        super(ELocation.LOAD_GUI);
    }

    @Override
    public void init() throws SlickException {
        // loading

        ComplexElement loadingBar = new ComplexElement(new ColorRect(new Rectangle(446, 630, 400, 95)), true);
        ImageElement container = new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.PANEL1), true);

        TextElement title = new TextElement(ELocation.LOAD_GUI_LoadingBar_title.getId(), new StringTimer("Loading"), ColorTools.get(ColorTools.Colors.GUI_BLUE), EFont.MODERN, 20, new ColorRect(new Rectangle(66, 2, 257, 28)), Element.PositionInBody.MIDDLE_UP, true);
        this.taskManager.register(title.getId(), title);
        ImageElement value = new ImageElement(ELocation.LOAD_GUI_LoadingBar_value.getId(), ResourceManager.get().getGuiAnimator(EGuiElement.LOAD_BAR), true);
        this.taskManager.register(value.getId(), value);
        value.doTask(new Tuple<>(ETaskType.CUT, "body", 0.0f));
        loadingBar.addItem(new ElementWithTitle(title, container, true));
        loadingBar.addItem(value);
        this.elements.add(loadingBar);
        this.initElements();
    }

    @Override
    public void initOnEnter() {

    }

    @Override
    public void update(Observable o, Object arg) {
    }
}

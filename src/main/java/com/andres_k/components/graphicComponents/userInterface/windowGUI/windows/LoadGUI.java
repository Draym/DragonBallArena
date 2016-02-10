package com.andres_k.components.graphicComponents.userInterface.windowGUI.windows;

import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.ComplexElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.windowGUI.UserInterface;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.stockage.Tuple;
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
        loadingBar.addItem(new ImageElement(ResourceManager.get().getGuiAnimator(EGuiElement.LOADING_EMPTY), true));
        ImageElement loader = new ImageElement(ELocation.LOAD_GUI_LoadingBar.getId(), ResourceManager.get().getGuiAnimator(EGuiElement.LOAD_BAR), true);
        this.taskManager.register(ELocation.LOAD_GUI_LoadingBar.getId(), loader);
        loader.doTask(new Tuple<>(ETaskType.CUT, "body", 0.0f));
        loadingBar.addItem(loader);
        this.elements.add(loadingBar);

    }

    @Override
    public void initOnEnter() {

    }

    @Override
    public void update(Observable o, Object arg) {
    }
}

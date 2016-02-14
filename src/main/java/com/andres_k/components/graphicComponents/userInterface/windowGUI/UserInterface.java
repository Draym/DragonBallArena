package com.andres_k.components.graphicComponents.userInterface.windowGUI;

import com.andres_k.components.eventComponent.input.InputEvent;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.LocalTaskManager;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.stream.Collectors;

/**
 * Created by andres_k on 01/02/2016.
 */
public abstract class UserInterface implements Observer {
    protected List<GuiElement> elements;
    protected ELocation location;
    protected LocalTaskManager taskManager;

    protected UserInterface(ELocation location) {
        this.elements = new ArrayList<>();
        this.location = location;
        this.taskManager = new LocalTaskManager(this.location);
    }

    public abstract void init() throws SlickException;

    public abstract void initOnEnter() throws SlickException;

    protected void initElements() {
        this.elements.forEach(GuiElement::init);
    }

    public void enter() throws SlickException {
        this.initOnEnter();
        this.elements.forEach(GuiElement::enter);
    }

    public void leave() {
        this.elements.forEach(GuiElement::leave);
    }

    public boolean event(InputEvent input) {
        for (GuiElement guiElement : this.elements) {
            if (guiElement.event(input)) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        this.elements.forEach(GuiElement::update);
    }

    public void draw(Graphics g) {
        this.elements.forEach((guiElement) -> guiElement.draw(g));
    }

    public void register(LocalTaskManager manager) {
        manager.register(this.location.getId(), this.taskManager);
    }

    // GETTERS
    public boolean isOnFocus(int x, int y) {
        boolean result = false;
        for (GuiElement guiElement : this.elements.stream().filter(GuiElement::isActivated).collect(Collectors.toList())) {
            if (guiElement.isOnFocus(x, y)) {
                result = true;
            }
        }
        return result;
    }

    public boolean isOnClick(int x, int y) {
        boolean result = false;
        for (GuiElement guiElement : this.elements.stream().filter(GuiElement::isActivated).collect(Collectors.toList())) {
            if (guiElement.isOnClick(x, y)) {
                result = true;
            }
        }
        return result;
    }

    public GuiElement getElementById(String id) {
        for (GuiElement guiElement : this.elements) {
            GuiElement tmp = guiElement.getFromId(id);
            if (tmp != null) {
                return tmp;
            }
        }
        return null;
    }

    public ELocation getLocation() {
        return this.location;
    }
}

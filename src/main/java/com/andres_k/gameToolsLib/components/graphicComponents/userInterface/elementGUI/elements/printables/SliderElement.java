package com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.printables;

import com.andres_k.gameToolsLib.components.eventComponents.input.InputEvent;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.elements.Element;
import com.andres_k.gameToolsLib.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.taskComponent.utils.TaskComponent;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.stockage.Tuple;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by com.andres_k on 04/02/2016.
 */
public class SliderElement extends Element {
    private ImageElement sliderValue;
    private ImageElement container;
    private ImageElement sliderButton;
    private Pair<ELocation, ETaskType> task;
    private Float percentValue;

    public SliderElement(ColorShape body, ImageElement container, ImageElement sliderValue, Pair<ELocation, ETaskType> task, float value, boolean activated) {
        this(ELocation.UNKNOWN.getId(), body, container, sliderValue, null, task, value, activated);
    }

    public SliderElement(ColorShape body, ImageElement container, ImageElement sliderValue, ImageElement sliderButton, Pair<ELocation, ETaskType> task, float value, boolean activated) {
        this(ELocation.UNKNOWN.getId(), body, container, sliderValue, sliderButton, task, value, activated);
    }

    public SliderElement(String id, ColorShape body, ImageElement container, ImageElement sliderValue, ImageElement sliderButton, Pair<ELocation, ETaskType> task, float value, boolean activated) {
        super(EGuiType.PRINTABLE, id, body, PositionInBody.MIDDLE_MID, activated);
        this.sliderValue = sliderValue;
        this.container = container;
        this.sliderButton = sliderButton;
        this.task = task;
        this.percentValue = value;
        this.sliderValue.doTask(new Tuple<>(ETaskType.CUT, "body_X", this.percentValue));
    }

    @Override
    public boolean replace(Element element) throws SlickException {
        if (element instanceof ImageElement) {
            this.container.replace(element);
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        this.reset();
        this.sliderValue.init();
        this.container.init();
        if (this.sliderButton != null) {
            this.sliderButton.init();
        }
    }

    @Override
    public void enter() {
        this.reset();
        this.sliderValue.enter();
        this.container.enter();
        if (this.sliderButton != null) {
            this.sliderButton.enter();
        }
    }

    @Override
    public void leave() {
        this.sliderValue.leave();
        this.container.leave();
        if (this.sliderButton != null) {
            this.sliderButton.leave();
        }
    }

    @Override
    public void activate() {
        super.activate();
        this.sliderValue.activate();
        this.container.activate();
        if (this.sliderButton != null) {
            this.sliderButton.activate();
        }
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.sliderValue.deactivate();
        this.container.deactivate();
        if (this.sliderButton != null) {
            this.sliderButton.deactivate();
        }
    }

    @Override
    public void draw(Graphics g) {
        if (this.activated) {
            this.draw(g, 0, 0);
        }
    }

    @Override
    public void draw(Graphics g, float decalX, float decalY) {
        if (this.activated) {
            if (this.body != null) {
                this.body.draw(g);

                this.container.draw(g, this.body.cloneAndDecalFrom(decalX, decalY));
                this.sliderValue.draw(g, this.body.cloneAndDecalFrom(decalX, decalY));
                if (this.sliderButton != null) {
                    this.sliderButton.draw(g, this.body.cloneAndDecalFrom(decalX + this.getSliderButtonPosX(), decalY + this.getSliderButtonPosY()));
                }
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorShape body) {
        if (this.activated) {

            body.draw(g);
            this.container.draw(g, body.cloneAndDecalFrom(this.body.getMinX(), this.body.getMinY()));
            this.sliderValue.draw(g, body.cloneAndDecalFrom(this.body.getMinX(), this.body.getMinY()));
            if (this.sliderButton != null) {
                this.sliderButton.draw(g, body.cloneAndDecalFrom(this.body.getMinX() + this.getSliderButtonPosX(), this.body.getMinY() + getSliderButtonPosY()));
            }
        }
    }

    @Override
    public Object doTask(Object task) {
        Object result;

        if ((result = super.doTask(task)) != null) {
            return result;
        } else if ((result = this.sliderValue.doTask(task)) != null) {
            return result;
        }
        return null;
    }

    @Override
    public GuiElement getFromId(String id) {
        if (this.getId().equals(id)) {
            return this;
        } else if (this.sliderValue.getId().equals(id)) {
            return this.sliderValue;
        } else if (this.container.getId().equals(id)) {
            return this.container;
        } else if (this.sliderButton != null && this.sliderButton.getId().equals(id)) {
            return this.sliderButton;
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        if (this.sliderValue.isEmpty()) {
            return true;
        } else if (this.container.isEmpty()) {
            return true;
        }
        return false;

    }

    @Override
    public boolean isNull() {
        if (this.sliderValue.isNull()) {
            return true;
        } else if (this.container.isNull()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean event(InputEvent input) {
        if (this.activated) {
            if (this.sliderValue.event(input)) {
                return true;
            }
        }
        return false;
    }

    // GETTERS
    @Override
    public boolean isOnFocus(float x, float y) {
        this.focused = false;
        if (this.activated) {
            if (this.sliderValue.isOnFocus(x - this.getBody().getMinX(), y - this.getBody().getMinY())) {
                this.focused = true;
            }
            if (this.container.isOnFocus(x - this.getBody().getMinX(), y - this.getBody().getMinY())) {
                this.focused = true;
            }
            if (this.sliderButton != null && this.sliderButton.isOnFocus(x - this.getBody().getMinX() + this.getSliderButtonPosX(), y - this.getBody().getMinY() + this.getSliderButtonPosY())) {
                this.focused = true;
            }
        }
        return this.focused;
    }

    @Override
    public boolean isOnClick(float x, float y) {
        this.clicked = false;
        if (this.activated) {
            if (this.container.isOnClick(x - this.getBody().getMinX(), y - this.getBody().getMinY())) {
                this.clicked = true;
                this.percentValue = (x - this.getBody().getMinX()) / this.container.getAbsoluteWidth();
                this.sliderValue.doTask(new Tuple<>(ETaskType.CUT, "body_X", this.percentValue));
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.getEnumByLocation(this.getId()), task.getV1(), new Pair<>(task.getV2(), this.percentValue)));
            }
            if (this.sliderButton != null && this.sliderButton.isOnClick(x - this.getBody().getMinX() + this.getSliderButtonPosX(), y - this.getBody().getMinY() + this.getSliderButtonPosY())) {
                this.clicked = true;
            }
        }
        return this.clicked;
    }

    private float getSliderButtonPosX() {
        if (this.sliderButton == null) {
            return 0;
        }
        return (this.sliderValue.getAbsoluteWidth() * this.percentValue - (this.sliderButton.getAbsoluteWidth() / 2));
    }

    private float getSliderButtonPosY() {
        if (this.sliderButton == null) {
            return 0;
        }
        return -((this.sliderButton.getAbsoluteHeight() / 2) - (this.container.getAbsoluteHeight() / 2));
    }
}

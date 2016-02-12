package com.andres_k.components.graphicComponents.userInterface.elementGUI;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 01/02/2016.
 */
public abstract class GuiElement implements Observer {
    protected String id;
    protected boolean focused;
    protected boolean activated;
    protected boolean clicked;
    protected EGuiType type;
    protected ColorShape body;

    protected List<Pair<EStatus, Object>> tasks;

    protected GuiElement(EGuiType type, String id, ColorShape body, boolean activated) {
        this.type = type;
        this.id = id;
        this.body = body;
        this.activated = activated;
        this.focused = false;
        this.clicked = false;
        this.tasks = new ArrayList<>();
    }

    // FUNCTIONS
    public abstract void init();

    public abstract void enter();

    public abstract void leave();

    public void activate() {
        this.activated = true;
    }

    public void deactivate() {
        this.activated = false;
        this.clicked = false;
        this.focused = false;
    }

    public void clear() {
        this.leave();
    }

    public void update() {
    }

    public void draw(Graphics g) {
    }

    public void draw(Graphics g, float decalX, float decalY) {
    }

    public void draw(Graphics g, ColorShape container) {
    }

    public boolean event(int key, char c, EInput type) {
        return false;
    }

    public Object doTask(Object task) {
        Console.write("ID: " + this.id + "  , Task: " + task + " , activity:" + this.isActivated());

        if (task instanceof ETaskType) {
            ETaskType action = (ETaskType) task;

            if (action == ETaskType.START_ACTIVITY && !this.isActivated()) {
                this.activate();
            } else if (action == ETaskType.STOP_ACTIVITY && this.isActivated()) {
                this.deactivate();
            } else if (action == ETaskType.ON_CREATE) {
                this.OnCreate();
            } else if (action == ETaskType.ON_KILL) {
                this.OnKill();
            } else if (action == ETaskType.ON_FOCUS) {
                this.OnFocus();
            } else if (action == ETaskType.ON_CLICK) {
                this.OnClick();
            } else if (action == ETaskType.GETTER) {
                return this;
            }
        } else if (task instanceof TaskComponent) {
            Console.write("send request");
            CentralTaskManager.get().sendRequest((TaskComponent) task);
        }
        return null;
    }

    // GETTERS

    public boolean isOnFocus(float x, float y) {
        boolean result = false;
        if (this.activated && this.body != null) {
            result = this.body.isOnFocus(x, y);
            if (result) {
                this.OnFocus();
            }
        }
        this.focused = result;
        return this.focused;
    }


    public boolean isOnClick(float x, float y) {
        if (this.activated && this.focused) {
            this.OnClick();
        }
        this.clicked = this.focused;
        return this.clicked;
    }

    public ColorShape getBody() {
        return this.body;
    }

    public final boolean isFocused() {
        return this.focused;
    }

    public final String getId() {
        return this.id;
    }

    public final EGuiType getType() {
        return this.type;
    }

    public GuiElement getFromId(String id) {
        if (this.getId().equals(id)) {
            return this;
        }
        return null;
    }

    public boolean isActivated() {
        return this.activated;
    }

    public boolean isAlive() {
        return true;
    }

    public boolean isBodyPrintable() {
        return this.body != null && this.body.isPrintable();
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public Color getBodyColor() {
        if (this.body != null) {
            return this.body.getColor();
        } else {
            return Color.transparent;
        }
    }

    public float getAbsoluteWidth() {
        if (this.body == null)
            return 0;
        return this.body.getMaxX() - this.body.getMinX();
    }

    public float getAbsoluteHeight() {
        if (this.body == null)
            return 0;
        return this.body.getMaxY() - this.body.getMinY();
    }

    // SETTERS
    public void setId(String value) {
        this.id = value;
    }

    public void setActivated(boolean value) {
        this.activated = value;
    }

    public void setPosX(float value) {
        this.body.setPosX(value);
    }

    public void setPosY(float value) {
        this.body.setPosY(value);
    }

    public void setBody(ColorShape body) {
        if (this.body != null) {
            if (body.getColor() == null) {
                body.setColor(this.body.getColor());
            }
        }
        this.body = body;
    }

    public void setBodyColor(Color color) {
        if (this.body != null) {
            this.body.setColor(color);
        }
    }

    // OBSERVER
    @Override
    public void update(Observable o, Object arg) {
        Console.write("Element task: " + arg);
        if (arg instanceof TaskComponent) {
            this.doTask(((TaskComponent) arg).getTask());
        } else {
            this.doTask(arg);
        }
    }

    // TASKS

    public final void addTask(Pair<EStatus, Object> task) {
        this.tasks.add(task);
    }

    public final void addTasks(List<Pair<EStatus, Object>> tasks) {
        this.tasks.addAll(tasks);
    }

    protected final void OnClick() {
        if (!this.clicked) {
            this.tasks.stream().filter(task -> task.getV1() == EStatus.ON_CLICK).forEach(task -> this.doTask(task.getV2()));
        }
    }

    protected final void OnFocus() {
        if (!this.focused) {
            this.tasks.stream().filter(task -> task.getV1() == EStatus.ON_FOCUS).forEach(task -> this.doTask(task.getV2()));
        }
    }

    protected final void OnCreate() {
        if (!this.isActivated()) {
            this.tasks.stream().filter(task -> task.getV1() == EStatus.ON_CREATE).forEach(task -> this.doTask(task.getV2()));
        }
    }

    protected final void OnKill() {
        if (this.isActivated()) {
            this.tasks.stream().filter(task -> task.getV1() == EStatus.ON_KILL).forEach(task -> this.doTask(task.getV2()));
        }
    }
}

package com.andres_k.components.graphicComponents.userInterface.elementGUI;

import com.andres_k.components.eventComponent.input.InputEvent;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.Console;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

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
    private boolean saveActivated;
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
        this.saveActivated = activated;
        this.focused = false;
        this.clicked = false;
        this.tasks = new ArrayList<>();
        this.tasks.add(new Pair<>(EStatus.ON_CREATE, ETaskType.START_ACTIVITY));
        this.tasks.add(new Pair<>(EStatus.ON_KILL, ETaskType.STOP_ACTIVITY));
    }

    // FUNCTIONS
    public abstract void init() throws SlickException;

    public abstract void enter();

    public abstract void leave();

    public void reset() {
        this.activated = this.saveActivated;
        this.clicked = false;
        this.focused = false;
    }

    public void activate() {
        Console.write("" + this + " -> ACTIVATE");
        this.activated = true;
    }

    public void deactivate() {
        Console.write("" + this + " -> DEACTIVATE");
        this.activated = false;
        this.clicked = false;
        this.focused = false;
    }

    public void clear() {
    }

    public void update() {
    }

    public void draw(Graphics g) {
    }

    public void draw(Graphics g, float decalX, float decalY) {
    }

    public void draw(Graphics g, ColorShape container) {
    }

    public boolean event(InputEvent input) {
        return false;
    }

    public Object doTask(Object task) {
        Console.write("ID: " + this.id + "  , Task: " + task + " , activity:" + this.isActivated());
        boolean result = true;

        if (task instanceof ETaskType) {
            ETaskType action = (ETaskType) task;

            if (action == ETaskType.START_ACTIVITY) {
                this.activate();
            } else if (action == ETaskType.STOP_ACTIVITY) {
                this.deactivate();
            } else if (action == ETaskType.ON_CREATE) {
                this.OnCreate(this.activated);
            } else if (action == ETaskType.ON_KILL) {
                this.OnKill(this.activated);
            } else if (action == ETaskType.ON_FOCUS) {
                this.OnFocus(this.focused);
            } else if (action == ETaskType.OFF_FOCUS) {
                this.OffFocus(this.focused);
            } else if (action == ETaskType.ON_CLICK) {
                this.OnClick(this.clicked);
            } else if (action == ETaskType.OFF_CLICK) {
                this.OffClick(this.clicked);
            } else if (action == ETaskType.GETTER) {
                return this;
            } else {
                result = false;
            }
        } else if (task instanceof TaskComponent) {
            CentralTaskManager.get().sendRequest((TaskComponent) task);
        } else {
            result = false;
        }
        if (result) {
            return true;
        }
        return null;
    }

    // GETTERS

    public boolean isOnFocus(float x, float y) {
        boolean save = this.focused;
        this.focused = false;
        if (this.activated && this.body != null) {
            this.focused = this.body.isOnFocus(x, y);
            if (this.focused) {
                this.OnFocus(save);
            }
        }
        if (!this.focused) {
            this.OffFocus(save);
        }
        return this.focused;
    }

    public boolean isOnClick(float x, float y) {
        boolean save = this.clicked;
        if (this.activated && this.focused) {
            this.clicked = true;
            this.OnClick(save);
        } else {
            this.clicked = false;
            this.OffClick(save);
        }
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

    public boolean isClicked() {
        return this.clicked;
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

    public float getLocationMinX() {
        if (this.body == null)
            return 0;
        return this.body.getMinX();
    }

    public float getLocationMaxX() {
        return this.getLocationMinX() + this.getAbsoluteWidth();
    }

    public float getLocationMinY() {
        if (this.body == null)
            return 0;
        return this.body.getMinY();
    }

    public float getLocationMaxY() {
        return this.getLocationMinY() + this.getAbsoluteHeight();
    }

    public float getBodyWidth() {
        if (this.body == null)
            return 0;
        return this.body.getMaxX() - this.body.getMinX();
    }

    public float getBodyHeight() {
        if (this.body == null)
            return 0;
        return this.body.getMaxY() - this.body.getMinY();
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

    public void setSizes(float sizeX, float sizeY) {
        if (this.body != null) {
            this.body.setSizes(sizeX, sizeY);
        }
    }

    public void setPosX(float posX) {
        if (this.body != null) {
            this.body.setPosX(posX);
        }
    }

    public void setPosY(float posY) {
        if (this.body != null) {
            this.body.setPosY(posY);
        }
    }

    public final void setLocation(float x, float y) {
        if (this.body != null) {
            this.body.setPosition(x, y);
        }
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

    protected final void OnClick(boolean clicked) {
        Console.write("OnClick: " + this);
        if (!clicked) {
            Console.write("do task: " + this.tasks.stream().filter(task-> task.getV1() == EStatus.ON_CLICK).count());
            this.tasks.stream().filter(task -> task.getV1() == EStatus.ON_CLICK).forEach(task -> this.doTask(task.getV2()));
        }
    }

    protected final void OffClick(boolean clicked) {
        Console.write("OffClick: " + this);
        if (clicked) {
            this.tasks.stream().filter(task -> task.getV1() == EStatus.OFF_CLICK).forEach(task -> this.doTask(task.getV2()));
        }
    }

    protected final void OnFocus(boolean focused) {
        if (!focused) {
            this.tasks.stream().filter(task -> task.getV1() == EStatus.ON_FOCUS).forEach(task -> this.doTask(task.getV2()));
        }
    }

    protected final void OffFocus(boolean focused) {
        if (focused) {
            this.tasks.stream().filter(task -> task.getV1() == EStatus.OFF_FOCUS).forEach(task -> this.doTask(task.getV2()));
        }
    }

    protected final void OnCreate(boolean activated) {
        if (!activated) {
            this.tasks.stream().filter(task -> task.getV1() == EStatus.ON_CREATE).forEach(task -> this.doTask(task.getV2()));
        }
    }

    protected final void OnKill(boolean activated) {
        if (activated) {
            this.tasks.stream().filter(task -> task.getV1() == EStatus.ON_KILL).forEach(task -> this.doTask(task.getV2()));
        }
    }

    @Override
    public String toString() {
        return "[" + this.id + ", " + this.type + "]";
    }
}

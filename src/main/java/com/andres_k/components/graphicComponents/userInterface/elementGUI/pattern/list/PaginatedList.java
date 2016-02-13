package com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.list;

import com.andres_k.components.eventComponent.input.InputEvent;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.EGuiType;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.GuiElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.ImageElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.elements.printables.TextElement;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.pattern.ElementWithTitle;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.elementGUI.tools.shapes.ColorShape;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andres_k on 12/02/2016.
 */
public class PaginatedList extends GuiElement {
    protected Map<String, ListElement> lists;
    protected List<ElementWithTitle> tabs;
    protected ColorShape bodyList;
    protected String current;
    protected EGuiElement tabImage;
    protected Pair<Float, Float> marginTab;
    protected Pair<Float, Float> startTab;

    public PaginatedList(ColorShape container, ColorShape bodyList, EGuiElement tabImage, float marginTabX, float marginTabY, float startTabX, float startTabY, boolean activated) {
        this(ELocation.UNKNOWN.getId(), container, bodyList, tabImage, marginTabX, marginTabY, startTabX, startTabY, activated);
    }

    public PaginatedList(String id, ColorShape container, ColorShape bodyList, EGuiElement tabImage, float marginTabX, float marginTabY, float startTabX, float startTabY, boolean activated) {
        super(EGuiType.LIST, id, container, activated);
        this.lists = new LinkedHashMap<>();
        this.tabs = new ArrayList<>();
        this.bodyList = bodyList;
        this.tabImage = tabImage;
        this.marginTab = new Pair<>(marginTabX, marginTabY);
        this.startTab = new Pair<>(startTabX, startTabY);
        this.current = "";
    }

    @Override
    public void init() {
        this.lists.entrySet().forEach(entry -> entry.getValue().init());
    }

    @Override
    public void enter() {
        this.lists.entrySet().forEach(entry -> entry.getValue().enter());
    }

    @Override
    public void leave() {
        this.lists.entrySet().forEach(entry -> entry.getValue().leave());
    }

    @Override
    public void activate() {
        super.activate();
        this.lists.entrySet().forEach(entry -> entry.getValue().activate());
    }

    @Override
    public void deactivate() {
        super.deactivate();
        this.lists.entrySet().forEach(entry -> entry.getValue().deactivate());
    }

    @Override
    public void clear() {
        super.clear();
        this.clearListItems();
        this.clearTabs();
        this.lists.clear();
    }

    @Override
    public void update() {
        this.lists.entrySet().forEach(entry -> entry.getValue().update());
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
            this.body.cloneAndDecalFrom(decalX, decalY).draw(g);

            ListElement current = this.getCurrentList();
            if (current != null) {
                current.draw(g, this.body.getMinX() + decalX, this.body.getMinY() + decalY);
            }
            for (GuiElement item : this.tabs) {
                item.draw(g, this.body.getMinX() + decalX, this.body.getMinY() + decalY);
            }
        }
    }

    @Override
    public void draw(Graphics g, ColorShape body) {
        if (this.activated) {
            body.draw(g);
            ColorShape container = body.cloneAndDecalFrom(this.body.getMinX(), this.body.getMinY());

            ListElement current = this.getCurrentList();
            if (current != null) {
                current.draw(g, container);
            }
            for (GuiElement item : this.tabs) {
                item.draw(g, container);
            }
        }
    }

    @Override
    public boolean event(InputEvent input) {
        if (this.activated) {

            ListElement current = this.getCurrentList();
            if (current != null) {
                if (current.event(input)) {
                    return true;
                }
            }
        }
        return false;
    }

    // GETTERS
    @Override
    public boolean isOnFocus(float x, float y) {
        this.focused = false;
        if (this.activated) {
            ListElement current = this.getCurrentList();
            if (current != null) {
                if (current.isOnFocus(this.body.getMinX() + x, this.body.getMinY() + y)) {
                    this.focused = true;
                }
            }
        }
        return this.focused;
    }

    @Override
    public boolean isOnClick(float x, float y) {
        if (this.activated) {
            ListElement current = this.getCurrentList();
            if (current != null) {
                if (current.isOnFocus(x - this.body.getMinX(), y - this.body.getMinY())) {
                    return true;
                }
            }

            for (ElementWithTitle tab : this.tabs) {
                if (tab.isOnFocus(x - this.body.getMinX(), y - this.body.getMinY())) {
                    this.switchCurrent(tab.getId());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GuiElement getFromId(String id) {
        if (this.getId().equals(id)) {
            return this;
        }

        ListElement current = this.getCurrentList();
        if (current != null) {
            return current.getFromId(id);
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return this.lists.isEmpty();
    }

    @Override
    public boolean isNull() {
        ListElement current = this.getCurrentList();
        return current != null && current.isNull();
    }

    // MODIFIER
    public void addList(TextElement text, float marginX, float marginY) throws SlickException {
        this.tabs.add(new ElementWithTitle(text.getValue(), text, new ImageElement(new ColorRect(new Rectangle(this.getNextTabPosX(), this.getNextTabPosY(), 0, 0)), ResourceManager.get().getGuiAnimator(this.tabImage), true), true));
        this.lists.put(text.getValue(), new ListElement(this.bodyList, marginX, marginY, true));
        if (this.lists.size() == 1) {
            this.switchCurrent(text.getValue());
        }
    }

    public void addItem(String key, GuiElement item) {
        if (this.lists.containsKey(key)) {
            this.lists.get(key).addItem(item);
        }
    }

    public void deleteItem(GuiElement item) {
        this.deleteItem(item.getId());
    }

    public void deleteItem(String id) {
        if (this.lists.containsKey(id)) {
            this.lists.remove(id);
        }
    }

    public void clearListItems() {
        this.lists.entrySet().forEach(entry -> entry.getValue().clear());
    }

    public void clearTabs() {
        this.tabs.clear();
        this.current = "";
    }

    // TOOLS
    private void switchCurrent(String id) {
        if (!this.current.equals(id) && this.lists.containsKey(id)) {
            this.current = id;
            this.tabs.forEach(tab -> {
                if (tab.getId().equals(id)) {
                    tab.doTask(new Tuple<>(ETaskType.SETTER, "index", 1));
                } else {
                    tab.doTask(new Tuple<>(ETaskType.SETTER, "index", 0));
                }
            });
        }
    }

    // GETTERS

    public ListElement getCurrentList() {
        if (this.lists.containsKey(this.current)) {
            return this.lists.get(this.current);
        }
        return null;
    }

    private float getNextTabPosX() {
        if (!this.tabs.isEmpty() && this.marginTab.getV1() != 0) {
            return this.tabs.get(this.tabs.size() - 1).getBody().getMaxX() + this.marginTab.getV1();
        }
        return this.startTab.getV1();
    }

    private float getNextTabPosY() {
        if (!this.tabs.isEmpty() && this.marginTab.getV2() != 0) {
            return this.tabs.get(this.tabs.size() - 1).getBody().getMaxY() + this.marginTab.getV2();
        }
        return this.startTab.getV2();
    }
}

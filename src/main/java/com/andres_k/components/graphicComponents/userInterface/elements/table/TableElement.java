package com.andres_k.components.graphicComponents.userInterface.elements.table;

import com.andres_k.components.graphicComponents.userInterface.elements.InterfaceElement;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.tools.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.tools.listElements.ButtonListElement;
import com.andres_k.components.graphicComponents.userInterface.tools.listElements.ImageListElement;
import com.andres_k.components.graphicComponents.userInterface.tools.listElements.ListElement;
import com.andres_k.components.graphicComponents.userInterface.tools.listElements.StringListElement;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.ColorTools;
import com.andres_k.utils.tools.ConsoleWrite;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by andres_k on 27/06/2015.
 */
public class TableElement extends InterfaceElement {
    protected Map<Element, ListElement> table;
    protected Map<String, Pair<ColorRect, ColorRect>> positionBody;

    public TableElement(EnumOverlayElement type, ColorRect body, boolean activated, boolean[] needActivatedParent) {
        this.parentInit(body, type, activated, needActivatedParent);
        this.childInit();
    }

    public TableElement(EnumOverlayElement type, ColorRect body) {
        this.parentInit(body, type, true, new boolean[]{true, true});
        this.childInit();
    }

    // INIT

    private void childInit() {
        this.table = new LinkedHashMap<>();
        this.positionBody = new LinkedHashMap<>();
    }

    // FUNCTIONS
    @Override
    public void leave() {
        this.activatedTimer.leave();
        for (Map.Entry<Element, ListElement> item : this.table.entrySet()) {
            item.getKey().leave();
            item.getValue().leave();
        }
    }

    @Override
    public void clearData() {
        for (Map.Entry<Element, ListElement> item : this.table.entrySet()) {
            item.getKey().leave();
            item.getValue().clear();
        }
        this.table.clear();
        this.initPositionBody();
    }

    @Override
    public void doTask(Object task) throws SlickException {
        if (task instanceof Element) {
            this.addElement((Element) task);
        } else if (task instanceof Pair) {
            ConsoleWrite.debug("Table received: " + task);
            if (((Pair) task).getV1() instanceof Integer) {
                Pair<Integer, Boolean> received = (Pair<Integer, Boolean>) task;
                if (received.getV1() < this.reachable.length) {
                    this.reachable[received.getV1()] = received.getV2();
                }
            } else if (((Pair) task).getV1() instanceof EnumOverlayElement) {
                Pair<EnumOverlayElement, Object> received = (Pair<EnumOverlayElement, Object>) task;
                Element element = this.containsId(received.getV1().getValue());

                ConsoleWrite.debug("find element: " + element + "\n");
                if (element != null) {
                    element.doTask(received.getV2());
                }
            }
        } else if (task instanceof Integer) {
            int i = 0;
            for (Map.Entry<Element, ListElement> entry : this.table.entrySet()) {
                if ((Integer) task == i) {
                    entry.getKey().setBodyColor(ColorTools.get(ColorTools.Colors.TRANSPARENT_YELLOW));
                } else {
                    entry.getKey().setBodyColor(null);
                }
                ++i;
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        if (this.isActivated()) {
            this.body.draw(g);
            for (Map.Entry<Element, ListElement> item : this.table.entrySet()) {
                item.getKey().draw(g, this.positionBody.get(item.getKey().getId()).getV1());
                item.getValue().draw(g);
            }
        }
    }

    @Override
    public void update() {
        for (Map.Entry<Element, ListElement> item : this.table.entrySet()) {
            item.getKey().update();
            item.getValue().update();
        }
    }

    @Override
    public Object eventPressed(int key, char c) {
        return null;
    }

    @Override
    public Object eventReleased(int key, char c) {
        return null;
    }

    @Override
    public Object isOnFocus(int x, int y) {
        return null;
    }

    public void addElement(Element item) throws SlickException {
        Element key = this.containsKey(item);
        if (key != null) {
            //           Debug.debug("add elem: '" + item.toString() + "'");
            if (checkSameHeadId(item.getId())) {
                key.replace(item);
            } else {
                this.table.get(key).setBody(this.positionBody.get(key.getId()).getV2());
                this.table.get(key).addToPrint(item, Element.PositionInBody.MIDDLE_MID);
            }
        } else {
            if (this.checkSameHeadId(item.getId())) {
                if (item.getType() == EnumOverlayElement.STRING) {
                    this.table.put(item, new StringListElement());
                } else if (item.getType() == EnumOverlayElement.IMAGE) {
                    this.table.put(item, new ImageListElement());
                } else if (item.getType() == EnumOverlayElement.BUTTON) {
                    this.table.put(item, new ButtonListElement(5));
                } else {
                    return;
                }
//                Debug.debug("add table: " + item.toString());
                this.initPositionBody();
                this.initTableBody();
            }
        }
    }

    protected boolean checkSameHeadId(String id) {
        if (id.contains(":")) {
            String v1 = id.substring(0, id.indexOf(":"));
            String v2 = id.substring(id.indexOf(":") + 1, id.length());
            return v1.equals(v2);
        }
        return true;
    }

    protected boolean containsHeadId(String head, String id) {
        if (head.contains(":") && id.contains(":")) {
            return head.substring(0, head.indexOf(":")).equals(id.substring(0, id.indexOf(":")));
        } else {
            return head.equals(id);
        }
    }

    protected Element containsKey(Element item) {
        for (Map.Entry<Element, ListElement> entry : this.table.entrySet()) {
            if (this.containsHeadId(entry.getKey().getId(), item.getId())) {
                return entry.getKey();
            }
        }
        return null;
    }

    protected Element containsKey(String id) {
        for (Map.Entry<Element, ListElement> entry : this.table.entrySet()) {
            if (this.containsHeadId(entry.getKey().getId(), id)) {
                return entry.getKey();
            }
        }
        return null;
    }

    protected Element containsId(String id) {
        for (Map.Entry<Element, ListElement> entry : this.table.entrySet()) {
            if (entry.getKey().getId().equals(id)) {
                return entry.getKey();
            }
            Element element = entry.getValue().getElement(id);
            if (element != null) {
                return element;
            }
        }
        return null;
    }

    protected void sendTaskToAll(Object task) {
        for (Map.Entry<Element, ListElement> entry : this.table.entrySet()) {
            entry.getKey().doTask(task);
            entry.getValue().sendTask(task);
        }
    }

    protected void initPositionBody() {
        float border = 10;
        float currentX = this.body.getMinX();
        float width = this.body.getSizeX() / this.table.size();
        float currentY = this.body.getMinY();

        this.positionBody.clear();
        for (Map.Entry<Element, ListElement> entry : this.table.entrySet()) {
            float height = this.body.getSizeY() - entry.getKey().getAbsoluteHeight() - border;
            height = (height < 0 ? 0 : height);

            float widthWithBorder = width - (border * 2);
            widthWithBorder = (widthWithBorder < 0 ? 0 : widthWithBorder);
            if (entry.getKey().getType() == EnumOverlayElement.IMAGE) {
                this.positionBody.put(entry.getKey().getId(), new Pair<>(new ColorRect(new Rectangle(currentX, currentY, widthWithBorder, entry.getKey().getAbsoluteHeight())),
                        new ColorRect(new Rectangle(currentX + border, currentY + entry.getKey().getAbsoluteHeight(), widthWithBorder, height))));
            } else if (entry.getKey().getType() == EnumOverlayElement.STRING) {
                this.positionBody.put(entry.getKey().getId(), new Pair<>(new ColorRect(new Rectangle(currentX + border, currentY, widthWithBorder, entry.getKey().getAbsoluteHeight())),
                        new ColorRect(new Rectangle(currentX + border, currentY + entry.getKey().getAbsoluteHeight(), widthWithBorder, height), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE))));
            } else if (entry.getKey().getType() == EnumOverlayElement.BUTTON) {
                this.positionBody.put(entry.getKey().getId(), new Pair<>(new ColorRect(new Rectangle(currentX + border, currentY, widthWithBorder, entry.getKey().getAbsoluteHeight())),
                        new ColorRect(new Rectangle(currentX + border, currentY + entry.getKey().getAbsoluteHeight(), widthWithBorder, height), ColorTools.get(ColorTools.Colors.TRANSPARENT_GREYBLUE))));
            }
            currentX += width;
        }
    }

    protected void initTableBody() {
        for (Map.Entry<Element, ListElement> entry : this.table.entrySet()) {
            if (this.positionBody.containsKey(entry.getKey().getId())) {
                entry.getValue().setBody(this.positionBody.get(entry.getKey().getId()).getV2());
            }
        }
    }
}

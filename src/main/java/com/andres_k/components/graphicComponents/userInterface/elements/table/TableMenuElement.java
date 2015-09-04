package com.andres_k.components.graphicComponents.userInterface.elements.table;

import com.andres_k.components.graphicComponents.input.EnumInput;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.tools.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.tools.listElements.ListElement;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.ColorTools;
import com.andres_k.utils.tools.StringTools;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import java.util.Map;

/**
 * Created by andres_k on 02/07/2015.
 */
public class TableMenuElement extends TableElement {
    private GenericSendTask genericSendTask;
    private Element focusedElement;

    public TableMenuElement(EnumOverlayElement type, GenericSendTask genericSendTask, ColorRect body) {
        super(type, body, false, new boolean[]{true, true});
        this.genericSendTask = genericSendTask;
        this.focusedElement = null;
    }

    // FUNCTION
    @Override
    public void doTask(Object task) throws SlickException {
        if (task instanceof Element) {
            this.addElement((Element) task);
        } else if (task instanceof EnumTask) {
            if (task == EnumTask.CLEAR){
                this.clearData();
            }
        } else if (task instanceof Pair) {
            if (((Pair) task).getV1() instanceof Integer && ((Pair) task).getV2() instanceof Boolean) {
                Pair<Integer, Boolean> received = (Pair<Integer, Boolean>) task;
                if (received.getV1() < this.reachable.length) {
                    this.reachable[received.getV1()] = received.getV2();
                }
            }
        } else if (task instanceof Integer) {
            int i = 0;
            for (Map.Entry<String, Pair<ColorRect, ColorRect>> entry : this.positionBody.entrySet()) {
                if ((Integer) task == i) {
                    entry.getValue().getV1().setColor(ColorTools.get(ColorTools.Colors.TRANSPARENT_YELLOW));
                } else {
                    entry.getValue().getV1().setColor(null);
                }
                ++i;
            }
        } else if (task instanceof String) {
            String value = this.focusedElement.toString();
            String keyString = (String) task;
            String newValue;
            if (value.contains(":")) {
                value = value.substring(0, value.indexOf(":"));
                newValue = StringTools.duplicateString(" ", 14 - value.length()) + keyString + StringTools.duplicateString(" ", 18 - keyString.length());
            } else {
                newValue = keyString;
            }
            this.focusedElement.doTask(newValue);
            this.initFocusElement();
        }
    }

    @Override
    public Object eventReleased(int key, char c) {
        if (key == Input.KEY_ESCAPE) {
            if (this.isActivated()) {
                this.stop();
                this.initFocusElement();
            } else {
                if (this.type == EnumOverlayElement.TABLE_MENU) {
                    this.start();
                    return true;
                }
            }
        } else {
            if (this.focusedElement != null) {
                String keyString = Input.getKeyName(key);
                String value = this.focusedElement.toString();
                if (value.contains(":")) {
                    value = value.substring(0, value.indexOf(":"));
                }
                return new Pair<>(EnumInput.getEnumByValue(value), keyString);
            }
        }
        return null;
    }

    @Override
    public Object isOnFocus(int x, int y) {
        if (this.isActivated()) {
            int listIndex = 0;

            for (Map.Entry<Element, ListElement> entry : this.table.entrySet()) {
                Object result = entry.getValue().isOnFocus(x, y);
                if (result != null) {
                    if (result instanceof Element) {
                        Element element = (Element) result;
                        if (((Element) result).getType() == EnumOverlayElement.CONTROLS) {
                            this.initFocusElement();
                            this.focusedElement = element;
                            this.focusedElement.setBodyColor(ColorTools.get(ColorTools.Colors.TRANSPARENT_YELLOW));
                        } else if (((Element) result).getType() == EnumOverlayElement.SCREEN) {
                            boolean newValue;
                            if (ColorTools.compareColor(element.getBody().getColor(), ColorTools.Colors.TRANSPARENT_GREEN)) {
                                element.setBodyColor(ColorTools.get(ColorTools.Colors.TRANSPARENT_RED));
                                newValue = false;
                            } else {
                                element.setBodyColor(ColorTools.get(ColorTools.Colors.TRANSPARENT_GREEN));
                                newValue = true;
                            }
                            EnumOverlayElement type = element.getType();
                            if (element.getId().contains(":")) {
                                type = EnumOverlayElement.getEnumByValue(element.getId().substring(element.getId().indexOf(":") + 1));
                            }
                            this.genericSendTask.sendTask(new Pair<>(type, new Pair<>(listIndex, newValue)));
                        }
                        return result;
                    } else if (result instanceof Boolean && (Boolean) result == true) {
                        return true;
                    }
                }
                ++listIndex;
            }
        }
        this.initFocusElement();
        return null;
    }

    private void initFocusElement() {
        if (this.focusedElement != null) {
            this.focusedElement.setBodyColor(null);
            this.focusedElement = null;
        }
    }
}

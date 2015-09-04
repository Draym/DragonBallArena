package com.andres_k.components.graphicComponents.userInterface.tools.elements;

import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ColorRect;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/**
 * Created by andres_k on 29/06/2015.
 */
public class SelectionStringField extends Element {
    private StringElement stringElement;
    private boolean focused;
    private boolean visible;
    private String target;

    public SelectionStringField(ColorRect body, StringElement stringElement, String id, boolean visible) {
        this.stringElement = stringElement;
        this.body = body;
        this.focused = false;
        this.type = EnumOverlayElement.SELECT_FIELD;
        this.id = id;
        this.target = "";
        this.visible = visible;
    }

    @Override
    public void leave() {
        this.stringElement.leave();
    }

    public void draw(Graphics g) {
        if (this.focused) {
            this.body.draw(g);
            this.drawValue(g, new ColorRect(this.body.getBody()));
        } else if (this.visible) {
            this.drawValue(g, new ColorRect(this.body.getBody()));
        }
    }

    private void drawValue(Graphics g, ColorRect body) {
        if (!this.stringElement.getValue().equals("")) {
            this.stringElement.addToValue(0, ": ");
            this.stringElement.draw(g, body);
            this.stringElement.deleteValue(0, 2);
        }
    }

    @Override
    public void draw(Graphics g, ColorRect body) {
        if (focused) {
            this.body.draw(g);
            this.drawValue(g, body);
        } else if (this.visible) {
            this.drawValue(g, body);
        }
    }

    @Override
    public void update() {
        this.stringElement.update();
    }

    @Override
    public boolean replace(Element element) {
        return this.stringElement.replace(element);
    }

    @Override
    public Object doTask(Object task) {
        if (task instanceof Pair && ((Pair) task).getV1() instanceof EnumTask) {
            EnumTask order = (EnumTask) ((Pair) task).getV1();
            String value = (String) ((Pair) task).getV2();

            if (order == EnumTask.SEND_TO) {
                this.target = value;
            } else if (order == EnumTask.GETTER) {
                if (value.equals("focus")) {
                    if (this.focused) {
                        return true;
                    } else {
                        return null;
                    }
                }
            }
        } else if (task instanceof Tuple && ((Tuple) task).getV1() instanceof EnumTask) {
            EnumTask order = (EnumTask) ((Tuple) task).getV1();
            Object target = ((Tuple) task).getV2();
            Object value = ((Tuple) task).getV3();

            if (order == EnumTask.SETTER) {
                if (target.equals("focus")) {
                    this.focused = (boolean) value;
                } else if (target.equals("current")) {
                    this.stringElement.setValue((String) value);
                }
            } else if (order == EnumTask.EVENT && this.focused) {
                int key = (int) target;
                char c = (char) value;
                if (key == Input.KEY_BACK) {
                    this.stringElement.deleteValue(this.stringElement.getValue().length() - 1, 1);
                } else if (c >= 32 && c <= 126) {
                    this.stringElement.addToValue(this.stringElement.getValue().length(), String.valueOf(c));
                }
            }
        }

        return null;
    }

    @Override
    public boolean isActivated() {
        return this.stringElement.isActivated();
    }

    @Override
    public boolean isEmpty() {
        return this.stringElement.isEmpty();
    }

    @Override
    public boolean isNull() {
        return this.stringElement.isNull();
    }

    // GETTERS

    @Override
    public Object isOnFocus(float x, float y) {
        if (this.body.isOnFocus(x, y)) {
            this.focused = true;
        } else {
            this.focused = false;
            return null;
        }
        return true;
    }

    @Override
    public float getAbsoluteWidth() {
        return this.stringElement.getAbsoluteWidth();
    }

    @Override
    public float getAbsoluteHeight() {
        return this.stringElement.getAbsoluteHeight();
    }

    @Override
    public String toString() {
        if (!this.target.equals("")) {
            return this.target + ":" + this.stringElement.toString();
        }
        return this.stringElement.toString();
    }
}

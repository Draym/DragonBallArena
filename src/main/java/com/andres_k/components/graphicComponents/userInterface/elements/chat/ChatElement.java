package com.andres_k.components.graphicComponents.userInterface.elements.chat;


import com.andres_k.components.graphicComponents.userInterface.elements.InterfaceElement;
import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.tools.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.tools.elements.SelectionStringField;
import com.andres_k.components.graphicComponents.userInterface.tools.elements.StringElement;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ActivatedTimer;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.tools.items.StringTimer;
import com.andres_k.components.graphicComponents.userInterface.tools.listElements.ListElement;
import com.andres_k.components.graphicComponents.userInterface.tools.listElements.StringListElement;
import com.andres_k.components.networkComponents.messages.MessageOverlayChat;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.utils.configs.CurrentUser;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.stockage.Tuple;
import com.andres_k.utils.tools.ConsoleWrite;
import com.andres_k.utils.tools.StringTools;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by andres_k on 20/06/2015.
 */

public class ChatElement extends InterfaceElement {
    private ListElement stringListElement;
    private SelectionStringField selectionStringField;

    public ChatElement(EnumOverlayElement type, ColorRect body) {
        this.parentInit(body, type, false, new boolean[]{true, true});
        this.childInit();
    }

    // INIT
    @Override
    protected void parentInit(ColorRect body, EnumOverlayElement type, boolean activated, boolean[] needActivatedParent) {
        this.body = body;
        this.reachable = needActivatedParent;
        this.activatedTimer = new ActivatedTimer(activated, false, 7000);
        this.type = type;
    }

    private void childInit() {
        this.selectionStringField = new SelectionStringField(new ColorRect(new Rectangle(this.body.getMinX() + 20, this.body.getMinY() + 170, 300, StringTools.charSizeY()), new Color(0.2f, 0.2f, 0.3f, 0.6f)),
                new StringElement(new StringTimer(""), Color.white, Element.PositionInBody.LEFT_MID), EnumOverlayElement.SELECT_FIELD.getValue() + "chat", true);
        float chatSizeY = 170;
        this.stringListElement = new StringListElement(new ColorRect(new Rectangle(this.body.getMinX(), this.body.getMinY(), this.body.getSizeX(), chatSizeY)));
    }

    // FUNCTIONS
    @Override
    public void leave() {
        this.activatedTimer.leave();
        this.stringListElement.leave();
        this.stringListElement.clear();
    }

    @Override
    public void draw(Graphics g) {
        if (this.isActivated()) {
            this.body.draw(g);
            this.stringListElement.draw(g);
            this.selectionStringField.draw(g);
        }
    }

    @Override
    public void update() {
        if (!this.isActivated() && this.selectionFocused()) {
            this.setSelectionFocus(false);
        }
    }

    @Override
    public void clearData() {
        this.stringListElement.clear();
    }

    private boolean selectionFocused() {
        if (this.selectionStringField.doTask(new Pair<>(EnumTask.GETTER, "focus")) != null) {
            return true;
        } else {
            return false;
        }
    }

    private void setSelectionFocus(boolean value) {
        this.selectionStringField.doTask(new Tuple<>(EnumTask.SETTER, "focus", value));
    }

    @Override
    public Object eventPressed(int key, char c) {
        if (key == Input.KEY_ENTER) {
            if (this.selectionFocused()) {
                this.setSelectionFocus(false);
                if (!this.selectionStringField.toString().equals("")) {
                    MessageOverlayChat request = new MessageOverlayChat(CurrentUser.getPseudo(), CurrentUser.getId(), true, this.selectionStringField.toString());
                    this.selectionStringField.doTask(new Tuple<>(EnumTask.SETTER, "current", ""));
                    return request;
                }
            } else {
                this.setSelectionFocus(true);
                this.activatedTimer.setActivated(true);
            }
            this.activatedTimer.startTimer();
        } else if (key == Input.KEY_ESCAPE && this.selectionFocused()) {
            return true;
        } else if (this.selectionFocused()) {
            this.selectionStringField.doTask(new Tuple<>(EnumTask.EVENT, key, c));
            this.activatedTimer.startTimer();
        } else {
            return null;
        }
        return true;
    }

    @Override
    public Object eventReleased(int key, char c) {
        if (this.selectionFocused()) {
            if (key == Input.KEY_ESCAPE) {
                this.activatedTimer.startTimer();
                this.setSelectionFocus(false);
            }
            return true;
        }
        return null;
    }

    @Override
    public Object isOnFocus(int x, int y) {
        if (this.isActivated()) {
            Object result = this.stringListElement.isOnFocus(x, y);
            if (result instanceof Element) {
                //todo catach l'element et l'envoyer au selectField pour envois de message by id
                ConsoleWrite.debug("element CATCH");
                this.selectionStringField.doTask(new Pair<>(EnumTask.SEND_TO, ((Element) result).getId()));
                return result;
            }
            if (this.selectionStringField.isOnFocus(x, y) != null) {
                ConsoleWrite.debug("selection CATCH");
                return true;
            }
        }
        return null;
    }

    @Override
    public void doTask(Object task) {
        if (task instanceof MessageOverlayChat) {
            this.activatedTimer.setActivated(true);
            this.addMessage((MessageOverlayChat) task);
            this.activatedTimer.startTimer();
        } else if (task instanceof Pair) {
            Pair<Integer, Boolean> received = (Pair<Integer, Boolean>) task;
            if (received.getV1() < this.reachable.length) {
                this.reachable[received.getV1()] = received.getV2();
            }
        }
    }

    public void addMessage(MessageOverlayChat message) {
        this.stringListElement.addToPrint(new Tuple<>(Color.black, this.getMessageToPrint(message.getPseudo(), message.getMessage()), message.getId()), Element.PositionInBody.LEFT_MID);
    }

    public String getMessageToPrint(String pseudo, String message) {
        return pseudo + ": " + message;
    }
}
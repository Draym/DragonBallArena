package com.andres_k.components.graphicComponents.userInterface.elements.table;

import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.tools.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.tools.elements.StringElement;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ColorRect;
import com.andres_k.components.graphicComponents.userInterface.tools.items.StringTimer;
import com.andres_k.components.networkComponents.messages.MessageRoundScore;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 02/07/2015.
 */
public class TableActivateElement extends TableElement {
    private int toActivate;

    public TableActivateElement(EnumOverlayElement type, ColorRect body, int toActivate) {
        super(type, body, false, new boolean[]{true, true});
        this.toActivate = toActivate;
    }

    // FUNCTION
    @Override
    public void doTask(Object task) throws SlickException {
        if (task instanceof Element) {
            this.addElement((Element) task);
        } else if (task instanceof Pair) {
            Pair<Integer, Boolean> received = (Pair<Integer, Boolean>) task;
            if (received.getV1() < this.reachable.length) {
                this.reachable[received.getV1()] = received.getV2();
            }
        } else if (this.type == EnumOverlayElement.TABLE_STAT) {
            if (task instanceof MessageRoundScore) {
                MessageRoundScore message = (MessageRoundScore) task;

                Element element = new StringElement(new StringTimer(message.getPseudo() + " : " + String.valueOf(message.getScore() + " pts.")), Color.black, message.getTeamId() + ":" + message.getId(), Element.PositionInBody.MIDDLE_MID);
                this.addElement(element);
            }
        }
    }

    @Override
    public Object eventPressed(int key, char c) {
        if (key == this.toActivate) {
            return true;
        }
        return null;
    }

    @Override
    public Object eventReleased(int key, char c) {
        if (key == this.toActivate) {
            if (this.isActivated()) {
                this.stop();
            } else {
                this.start();
            }
            return true;
        } else if (key == Input.KEY_ESCAPE){
            this.stop();
        }
        return null;
    }

}

package com.andres_k.components.graphicComponents.userInterface.elements.table;

import com.andres_k.components.graphicComponents.userInterface.overlay.EnumOverlayElement;
import com.andres_k.components.graphicComponents.userInterface.tools.elements.Element;
import com.andres_k.components.graphicComponents.userInterface.tools.items.ColorRect;
import com.andres_k.components.networkComponents.messages.MessageRoundStart;
import com.andres_k.components.taskComponent.EnumTask;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.ConsoleWrite;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 02/07/2015.
 */
public class TableAppearElement extends TableElement {

    public TableAppearElement(EnumOverlayElement type, ColorRect body) {
        super(type, body, false, new boolean[]{true, true});
    }

    // FUNCTION
    @Override
    public void doTask(Object task) throws SlickException {
        ConsoleWrite.debug("\nReceived in " + this.type + " -> " + task);
        if (task instanceof Element) {
            this.addElement((Element) task);
        } else if (task instanceof Pair) {
            if (((Pair) task).getV1() instanceof Integer && ((Pair) task).getV2() instanceof Boolean) {
                Pair<Integer, Boolean> received = (Pair<Integer, Boolean>) task;
                if (received.getV1() < this.reachable.length) {
                    this.reachable[received.getV1()] = received.getV2();
                }
            }
        } else if (this.type == EnumOverlayElement.TABLE_ROUND_NEW) {
            if (task instanceof MessageRoundStart) {
                MessageRoundStart message = (MessageRoundStart) task;

                if (message.isStarted()) {
                    this.sendTaskToAll(EnumTask.START);
                    this.activatedTimer.startTimer();
                } else {
                    this.activatedTimer.stopTimer();
                }
            }
        }
    }
}

package com.andres_k.gameToolsLib.components.networkComponents.networkSend.messageServer;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.gameToolsLib.components.networkComponents.networkSend.MessageModel;

/**
 * Created by com.andres_k on 30/03/2016.
 */
public class MessageInputPlayer extends MessageModel {
    private EInput event;
    private EInput input;

    public MessageInputPlayer() {
    }

    public MessageInputPlayer(EInput event, EInput input) {
        this.event = event;
        this.input = input;
    }

    public EInput getEvent() {
        return this.event;
    }

    public void setEvent(EInput event) {
        this.event = event;
    }

    public EInput getInput() {
        return this.input;
    }

    public void setInput(EInput input) {
        this.input = input;
    }
}

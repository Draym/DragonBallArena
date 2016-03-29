package com.andres_k.components.networkComponents.networkSend.messageServer;

import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.networkComponents.networkSend.MessageModel;

/**
 * Created by andres_k on 18/11/2015.
 */
public class MessageActionPlayer extends MessageModel {
    private EAnimation action;
    private int index;

    public MessageActionPlayer() {
    }

    public MessageActionPlayer(EAnimation action, int index) {
        this.action = action;
        this.index = index;
    }

    public EAnimation getAction() {
        return this.action;
    }

    public int getIndex() {
        return this.index;
    }

    public void setAction(EAnimation action) {
        this.action = action;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

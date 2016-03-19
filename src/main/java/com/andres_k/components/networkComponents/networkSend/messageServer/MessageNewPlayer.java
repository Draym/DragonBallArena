package com.andres_k.components.networkComponents.networkSend.messageServer;

import com.andres_k.components.networkComponents.networkSend.MessageModel;

/**
 * Created by andres_k on 18/11/2015.
 */
public class MessageNewPlayer extends MessageModel {
    private String playerType;

    public MessageNewPlayer() {}

    public String getPlayerType() {
        return this.playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }
}

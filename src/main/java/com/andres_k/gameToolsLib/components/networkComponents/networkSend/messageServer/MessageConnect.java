package com.andres_k.gameToolsLib.components.networkComponents.networkSend.messageServer;

import com.andres_k.gameToolsLib.components.networkComponents.networkSend.MessageModel;

/**
 * Created by com.andres_k on 18/11/2015.
 */
public class MessageConnect extends MessageModel {
    private String playerType;
    private String gameId;

    public MessageConnect() {
    }

    public MessageConnect(String gameId, String playerType) {
        this.gameId = gameId;
        this.playerType = playerType;
    }

    public String getPlayerType() {
        return this.playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}

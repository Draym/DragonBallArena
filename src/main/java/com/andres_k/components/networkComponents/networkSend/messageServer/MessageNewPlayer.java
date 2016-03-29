package com.andres_k.components.networkComponents.networkSend.messageServer;

import com.andres_k.components.networkComponents.networkSend.MessageModel;

/**
 * Created by andres_k on 18/11/2015.
 */
public class MessageNewPlayer extends MessageModel {
    private String playerType;
    private String gameId;
    private float x;
    private float y;

    public MessageNewPlayer() {}

    public String getPlayerType() {
        return this.playerType;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}

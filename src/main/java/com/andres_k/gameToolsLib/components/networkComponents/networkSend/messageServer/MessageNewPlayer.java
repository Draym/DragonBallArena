package com.andres_k.gameToolsLib.components.networkComponents.networkSend.messageServer;

import com.andres_k.gameToolsLib.components.networkComponents.networkSend.MessageModel;

/**
 * Created by com.andres_k on 18/11/2015.
 */
public class MessageNewPlayer extends MessageModel {
    private String playerType;
    private String gameId;
    private int playerTeam;
    private float x;
    private float y;

    public MessageNewPlayer() {
    }

    public int getPlayerTeam() {
        return this.playerTeam;
    }

    public void setPlayerTeam(int playerTeam) {
        this.playerTeam = playerTeam;
    }

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

package com.andres_k.components.networkComponents.networkSend.messageServer;

import com.andres_k.components.networkComponents.networkSend.MessageModel;

/**
 * Created by andres_k on 30/03/2016.
 */
public class MessageGameEnd extends MessageModel {
    private String winner;
    private String winnerType;

    public MessageGameEnd() {}

    public MessageGameEnd(String winner, String winnerType) {
        this.winner = winner;
        this.winnerType = winnerType;
    }

    public String getWinner() {
        return this.winner;
    }

    public String getWinnerType() {
        return this.winnerType;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setWinnerType(String winnerType) {
        this.winnerType = winnerType;
    }
}

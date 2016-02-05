package com.andres_k.components.networkComponents.networkSend.messageInterface;

import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.networkComponents.networkSend.MessageModel;

/**
 * Created by andres_k on 27/06/2015.
 */
public class MessageRoundScore extends MessageModel {
    private String teamId;
    private EGameObject object;
    private int score;

    public MessageRoundScore(String pseudo, String id, String teamId, EGameObject object, int score){
        super(pseudo, id);

        this.teamId = teamId;
        this.object = object;
        this.score = score;
    }

    // GETTERS
    public String getTeamId() {
        return this.teamId;
    }

    public EGameObject getObject() {
        return this.object;
    }

    public int getScore() {
        return this.score;
    }
}

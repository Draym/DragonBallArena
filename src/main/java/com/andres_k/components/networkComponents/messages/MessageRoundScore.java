package com.andres_k.components.networkComponents.messages;

import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.networkComponents.MessageModel;

/**
 * Created by andres_k on 27/06/2015.
 */
public class MessageRoundScore extends MessageModel {
    private String teamId;
    private EnumGameObject object;
    private int score;

    public MessageRoundScore(String pseudo, String id, String teamId, EnumGameObject object, int score){
        super(pseudo, id);

        this.teamId = teamId;
        this.object = object;
        this.score = score;
    }

    // GETTERS
    public String getTeamId() {
        return this.teamId;
    }

    public EnumGameObject getObject() {
        return this.object;
    }

    public int getScore() {
        return this.score;
    }
}

package com.andres_k.components.networkComponents.networkGame;

import com.andres_k.components.networkComponents.networkSend.MessageModel;

import java.util.UUID;

/**
 * Created by andres_k on 19/03/2016.
 */
public final class NetworkProfile {
    private String pseudo;
    private String id;

    private NetworkProfile() {
        this.pseudo = "unknown";
        this.id = UUID.randomUUID().toString();
    }

    private static class SingletonHolder {
        private final static NetworkProfile instance = new NetworkProfile();
    }

    public static NetworkProfile get() {
        return SingletonHolder.instance;
    }

    public MessageModel formatMessage(MessageModel message) {
        message.setPseudo(this.pseudo);
        message.setId(this.id);
        return message;
    }

    // GETTERS

    public String getPseudo() {
        return this.pseudo;
    }

    public String getId() {
        return this.id;
    }

    // SETTERS
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

}

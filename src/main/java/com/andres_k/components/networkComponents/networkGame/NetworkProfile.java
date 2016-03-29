package com.andres_k.components.networkComponents.networkGame;

import com.andres_k.components.networkComponents.networkSend.MessageModel;

import java.util.UUID;

/**
 * Created by andres_k on 19/03/2016.
 */
public final class NetworkProfile {
    private String pseudo;
    private String networkId;
    private String gameId;

    private NetworkProfile() {
        this.pseudo = "unknown";
        this.gameId = "none";
        this.networkId = UUID.randomUUID().toString();
    }

    private static class SingletonHolder {
        private final static NetworkProfile instance = new NetworkProfile();
    }

    public static NetworkProfile get() {
        return SingletonHolder.instance;
    }

    public MessageModel formatMessage(MessageModel message) {
        message.setPseudo(this.pseudo);
        message.setId(this.networkId);
        return message;
    }

    public boolean itsMyNetworkProfile(String id) {
        return !this.networkId.equals("none") && this.networkId.equals(id);
    }

    public boolean itsMyGameProfile(String id) {
        return !this.gameId.equals("none") && this.gameId.equals(id);
    }
    // GETTERS

    public String getPseudo() {
        return this.pseudo;
    }

    public String getNetworkId() {
        return this.networkId;
    }

    public String getGameId() {
        return this.gameId;
    }

    // SETTERS
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setGameId(String id) {
        this.gameId = id;
    }

}

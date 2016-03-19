package com.andres_k.components.networkComponents.networkSend;

/**
 * Created by andres_k on 08/07/2015.
 */
public abstract class MessageModel {
    protected String pseudo;
    protected String id;

    public MessageModel() {
        this.pseudo = "";
        this.id = "";
    }

    public MessageModel(String pseudo, String id){
        this.pseudo = pseudo;
        this.id = id;
    }

    public String getPseudo(){
        return this.pseudo;
    }

    public String getId(){
        return this.id;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setId(String id) {
        this.id = id;
    }
}

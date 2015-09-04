package com.andres_k.components.networkComponents.messages;

import com.andres_k.components.networkComponents.MessageModel;

/**
 * Created by andres_k on 08/07/2015.
 */
public class MessageOverlayChat extends MessageModel {
    private boolean all;
    private String message;

    public MessageOverlayChat(String pseudo, String id, boolean all, String message) {
        super(pseudo, id);
        this.all = all;
        this.message = message;
    }

    // GETTERS
    public boolean isAll() {
        return this.all;
    }

    public String getMessage() {
        return this.message;
    }
}

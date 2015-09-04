package com.andres_k.components.networkComponents.messages;

import com.andres_k.components.networkComponents.MessageModel;

/**
 * Created by andres_k on 11/07/2015.
 */
public class MessageOverlayMenu extends MessageModel {
    private boolean activated;

    public MessageOverlayMenu(String pseudo, String id, boolean value){
        super(pseudo, id);
        this.activated = value;
    }

    public boolean isActivated(){
        return this.activated;
    }
}

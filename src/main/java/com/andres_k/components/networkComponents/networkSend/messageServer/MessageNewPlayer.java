package com.andres_k.components.networkComponents.networkSend.messageServer;

import com.andres_k.components.networkComponents.networkSend.MessageModel;

/**
 * Created by andres_k on 18/11/2015.
 */
public class MessageNewPlayer extends MessageModel {
    public MessageNewPlayer(String pseudo, String id) {
        super(pseudo, id);
    }
}
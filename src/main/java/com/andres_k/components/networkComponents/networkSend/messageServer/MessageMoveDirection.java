package com.andres_k.components.networkComponents.networkSend.messageServer;


import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.networkComponents.networkSend.MessageModel;

/**
 * Created by andres_k on 29/03/2016.
 */
public class MessageMoveDirection extends MessageModel {
    private EDirection direction;

    public MessageMoveDirection() {
    }

    public MessageMoveDirection(EDirection direction) {
        this.direction = direction;
    }

    public EDirection getDirection() {
        return this.direction;
    }

    public void setDirection(EDirection direction) {
        this.direction = direction;
    }
}

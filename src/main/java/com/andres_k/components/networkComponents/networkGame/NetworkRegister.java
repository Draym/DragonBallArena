package com.andres_k.components.networkComponents.networkGame;

import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.networkComponents.networkSend.MessageModel;
import com.andres_k.components.networkComponents.networkSend.messageServer.*;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * Created by andres_k on 11/03/2015.
 */

public class NetworkRegister {
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(MessageModel.class);
        kryo.register(MessageConnect.class);
        kryo.register(MessageDisconnect.class);
        kryo.register(MessageActionPlayer.class);
        kryo.register(MessageNewPlayer.class);
        kryo.register(MessageDeletePlayer.class);
        kryo.register(MessageGameLaunch.class);
        kryo.register(EAnimation.class);
    }
}

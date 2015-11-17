package com.andres_k.components.networkComponents.networkGame;

import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.networkComponents.networkSend.MessageModel;
import com.andres_k.components.taskComponent.EnumTargetTask;
import com.andres_k.components.taskComponent.GenericSendTask;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.configs.NetworkServerConfig;
import com.andres_k.utils.stockage.Tuple;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.Observable;

/**
 * Created by andres_k on 11/03/2015.
 */
public class NetworkController {
    private Client client;
    private GenericSendTask masterTask;

    public NetworkController(GenericSendTask masterTask) {
        this.masterTask = masterTask;
        this.client = new Client();
        NetworkRegister.register(this.client);
        this.client.start();
    }

    //FUNCTIONS
    public boolean connect(NetworkServerConfig config) {
        try {
            this.client.connect(5000, config.getAddress(), config.getTcpPort(), config.getUdpPort());
            this.client.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if (object instanceof MessageModel) {
                        MessageModel response = (MessageModel) object;
                        masterTask.sendTask(TaskFactory.createTask(EnumTargetTask.SERVER_MESSAGE, EnumTargetTask.GAME, response));
                    }
                }
            });
            this.masterTask.sendTask(TaskFactory.createTask(EnumTargetTask.SERVER_MESSAGE, EnumTargetTask.INTERFACE, EnumWindow.GAME));
            return true;
        } catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void disconnect() {
        this.client.close();
    }

    public void call(MessageModel request) {
        /* QUAND LE SERVER SERA ON :
        if (this.client.isConnected()) {
            this.client.sendTCP(request);
        }*/
        this.masterTask.sendTask(TaskFactory.createTask(EnumTargetTask.SERVER_MESSAGE, EnumTargetTask.GAME, request));
    }

    public void doTask(Observable o, Object arg) {
        Tuple<EnumTargetTask, EnumTargetTask, Object> task = (Tuple<EnumTargetTask, EnumTargetTask, Object>) arg;

        if (task.getV2().equals(EnumTargetTask.SERVER_MESSAGE)) {
            if (task.getV3() instanceof MessageModel) {
                this.call((MessageModel) task.getV3());
            }
        } else if (task.getV2().equals(EnumTargetTask.SERVER_CONFIG)) {
            if (task.getV3() instanceof NetworkServerConfig) {
                this.connect((NetworkServerConfig) task.getV3());
            }
        }
    }
}

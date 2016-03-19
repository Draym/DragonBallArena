package com.andres_k.components.networkComponents.networkGame;

import com.andres_k.components.networkComponents.networkSend.MessageModel;
import com.andres_k.components.networkComponents.networkSend.messageServer.MessageGameLaunch;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.configs.NetworkServerConfig;
import com.andres_k.utils.tools.Console;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

/**
 * Created by andres_k on 11/03/2015.
 */
public final class NetworkController {
    private Client client;

    private NetworkController() {
        this.client = new Client();
        NetworkRegister.register(this.client);
        this.client.start();
    }

    private static class SingletonHolder {
        private final static NetworkController instance = new NetworkController();
    }

    public static NetworkController get() {
        return SingletonHolder.instance;
    }

    //FUNCTIONS
    public boolean connect(NetworkServerConfig config) {
        try {
            this.client.connect(5000, config.getAddress(), config.getTcpPort(), config.getUdpPort());
            this.client.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if (object instanceof MessageModel) {
                        MessageModel response = (MessageModel) object;
                        Console.write("RECEIVED TO SERVER: " + object);
                        if (response instanceof MessageGameLaunch) {
                            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.SERVER, ELocation.BATTLE_CONNECTION_CONTROLLER, response));
                        } else {
                            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.SERVER, ELocation.GAME_CONTROLLER, response));
                        }
                    }
                }
            });
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

    public void sendMessage(MessageModel request) {
        if (GameConfig.onLine) {
            if (this.client.isConnected()) {
                this.client.sendTCP(NetworkProfile.get().formatMessage(request));
            }
        }
        //faire le mode offline avec:
        //CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.SERVER_MESSAGE, ELocation.GAME_CONTROLLER, request));
    }

    /*
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof TaskComponent) {
            TaskComponent task = (TaskComponent) arg;

            if (task.getTarget().equals(ELocation.SERVER_MESSAGE)) {
                if (task.getTask() instanceof MessageModel) {
                    this.call((MessageModel) task.getTask());
                }
            } else if (task.getTarget().equals(ELocation.SERVER_CONFIG)) {
                if (task.getTask() instanceof NetworkServerConfig) {
                    this.connect((NetworkServerConfig) task.getTask());
                }
            }
        }
    }*/
}

package com.andres_k.components.networkComponents.networkGame;

import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.components.networkComponents.networkSend.MessageModel;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.configs.NetworkServerConfig;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by andres_k on 11/03/2015.
 */
public class NetworkController implements Observer {
    private Client client;

    public NetworkController() {
        this.client = new Client();
        NetworkRegister.register(this.client);

        CentralTaskManager.get().register(ELocation.SERVER.getId(), this);
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
                        CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.SERVER_MESSAGE, ELocation.GAME_CONTROLLER, response));
                    }
                }
            });
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.SERVER_MESSAGE, ELocation.HOME_CONTROLLER, EnumWindow.GAME));
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
        //faire le mode offline avec:
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.SERVER_MESSAGE, ELocation.GAME_CONTROLLER, request));
    }

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
    }
}

package com.andres_k.gameToolsLib.components.networkComponents.networkGame;

import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.elements.MyElementFactory;
import com.andres_k.gameToolsLib.components.controllers.EMode;
import com.andres_k.gameToolsLib.components.networkComponents.networkSend.MessageModel;
import com.andres_k.gameToolsLib.components.networkComponents.networkSend.messageServer.MessageGameLaunch;
import com.andres_k.gameToolsLib.components.resourceComponents.fonts.EFont;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.taskComponent.TaskFactory;
import com.andres_k.gameToolsLib.components.taskComponent.utils.TaskComponent;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import com.andres_k.gameToolsLib.utils.configs.NetworkServerConfig;
import com.andres_k.gameToolsLib.utils.stockage.Pair;
import com.andres_k.gameToolsLib.utils.tools.ColorTools;
import com.andres_k.gameToolsLib.utils.tools.Console;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

/**
 * Created by com.andres_k on 11/03/2015.
 */
public final class NetworkController {
    private Client client;
    private boolean connected;

    private NetworkController() {
        this.connected = false;
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
    public boolean connect() {
        try {
            this.client.connect(5000, NetworkServerConfig.getAddress(), NetworkServerConfig.getTcpPort(), NetworkServerConfig.getUdpPort());
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
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.SERVER, ELocation.BATTLE_CONNECTION_GUI_StatusList, new Pair<>(ETaskType.ADD, MyElementFactory.createText("Status: connection success", ColorTools.get(ColorTools.Colors.GUI_GREEN), EFont.BASIC, 25, 20, 0))));
            this.connected = true;
            return true;
        } catch (IOException e) {
            Console.write("ERROR: " + e.getMessage());
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.SERVER, ELocation.BATTLE_CONNECTION_GUI_StatusList, new Pair<>(ETaskType.ADD, MyElementFactory.createText("Status: connection failed", ColorTools.get(ColorTools.Colors.GUI_RED), EFont.BASIC, 25, 20, 0))));
            this.connected = false;
            return false;
        }
    }

    public void disconnect() {
        this.client.close();
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void sendMessage(String senderId, MessageModel request) {
        Console.write("User try to send : " + senderId);
        if (GameConfig.mode == EMode.ONLINE) {
            if (NetworkProfile.get().itsMyGameProfile(senderId)) {
                if (this.client.isConnected()) {
                    Console.write("SEND: " + request);
                    this.client.sendTCP(NetworkProfile.get().formatMessage(request));
                }
            }
        }
    }
}

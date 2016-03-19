package com.andres_k.components.controllers.waiting;

import com.andres_k.components.controllers.WindowController;
import com.andres_k.components.graphicComponents.background.EBackground;
import com.andres_k.components.graphicComponents.background.wallpaper.Wallpaper;
import com.andres_k.components.networkComponents.networkGame.NetworkController;
import com.andres_k.components.networkComponents.networkSend.MessageModel;
import com.andres_k.components.networkComponents.networkSend.messageServer.MessageConnect;
import com.andres_k.components.networkComponents.networkSend.messageServer.MessageGameLaunch;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.TaskFactory;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.configs.NetworkServerConfig;
import com.andres_k.utils.tools.Console;
import org.newdawn.slick.SlickException;

import java.util.Observable;

/**
 * Created by andres_k on 19/03/2016.
 */
public class BattleConnectionController extends WindowController {
    public BattleConnectionController(int idWindow) {
        super(ELocation.BATTLE_CONNECTION_CONTROLLER, idWindow);
    }

    @Override
    public void enter() throws SlickException {
        if (NetworkController.get().connect(new NetworkServerConfig(55555, 55556, "localhost"))) {
            Console.write("Connection: OK");
            NetworkController.get().sendMessage(new MessageConnect(GameConfig.typePlayer.get(0).getValue()));
        } else {
            Console.write("Connection: ERROR");
        }
    }

    @Override
    public void leave() {
    }

    @Override
    public void init() throws SlickException {
        this.backgroundManager.addComponent(EBackground.BATTLE_CONNECTION_SCREEN, new Wallpaper(ResourceManager.get().getBackgroundAnimator(EBackground.BATTLE_CONNECTION_SCREEN), 0, 0));
    }

    @Override
    public void keyPressed(int key, char c) {}

    @Override
    public void keyReleased(int key, char c) {}

    @Override
    public void mouseReleased(int button, int x, int y) {}

    @Override
    public void mousePressed(int button, int x, int y) {}

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof TaskComponent) {
            TaskComponent received = (TaskComponent) arg;

            if (received.getTarget().equals(this.location)) {
                if (received.getTask() instanceof MessageModel) {
                    this.resolveNetworkTask((MessageModel) received.getTask());
                    return;
                }
            }
        }
        super.update(o, arg);
    }

    public void resolveNetworkTask(MessageModel task) {
        if (task instanceof MessageGameLaunch) {
            CentralTaskManager.get().sendRequest(TaskFactory.createTask(this.location, ELocation.GAME_CONTROLLER, ETaskType.START));
        }
    }

}

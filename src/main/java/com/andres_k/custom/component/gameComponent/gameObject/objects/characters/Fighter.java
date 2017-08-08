package com.andres_k.custom.component.gameComponent.gameObject.objects.characters;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.graphicComponents.userInterface.elementGUI.EGuiElement;
import com.andres_k.custom.component.taskComponents.ELocation;
import com.andres_k.custom.component.taskComponents.ETaskType;
import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.characters.Player;
import com.andres_k.gameToolsLib.components.networkComponents.networkGame.NetworkController;
import com.andres_k.gameToolsLib.components.networkComponents.networkSend.messageServer.MessageStatePlayer;
import com.andres_k.gameToolsLib.components.taskComponent.CentralTaskManager;
import com.andres_k.gameToolsLib.components.taskComponent.TaskFactory;
import com.andres_k.gameToolsLib.utils.configs.GlobalVariable;
import com.andres_k.gameToolsLib.utils.stockage.Tuple;
import org.newdawn.slick.SlickException;

import java.util.Timer;

/**
 * Created by kevin on 08/08/2017.
 */
public class Fighter extends Player {
    protected final float maxKi;
    protected float currentKi;
    protected final float maxEnergy;
    protected float currentEnergy;
    protected boolean transformed;
    protected Timer transformationTimer;

    protected Fighter(AnimatorController animatorController, EGameObject type, String id, int team, float x, float y, float life, float damage, float moveSpeed, float gravitySpeed, float weight) {
        super(animatorController, type, id, team, x, y, life, damage, moveSpeed, gravitySpeed, weight);

        this.event.addEvent(EInput.ATTACK_A);
        this.event.addEvent(EInput.ATTACK_B);
        this.event.addEvent(EInput.ATTACK_C);
        this.event.addEvent(EInput.ATTACK_D);
        this.event.addEvent(EInput.ATTACK_SPE);

        this.maxKi = 1000;
        this.maxEnergy = 500;
        this.currentKi = this.maxKi;
        this.currentEnergy = this.maxEnergy;
        this.transformed = false;
        this.transformationTimer = new Timer();
    }

    @Override
    public void update() throws SlickException {
        super.update();
        if (this.transformed && this.currentEnergy <= 0) {
            this.tryToTransformTo(this.type);
        }
    }

    @Override
    public void resetActions() {
        super.resetActions();
        this.transformationTimer.cancel();
        this.transformationTimer.purge();
        this.transformationTimer = new Timer();
    }

    @Override
    public Object doTask(Object task) {
        super.doTask(task);
        if (task instanceof Tuple) {
            if (((Tuple) task).getV1() instanceof ETaskType && ((Tuple) task).getV2() instanceof String) {
                Tuple<ETaskType, String, Object> received = (Tuple<ETaskType, String, Object>) task;

                if (received.getV1().equals(ETaskType.ADD)) {
                    if (received.getV2().equals("ki")) {
                        this.incrementCurrentKi((Float) received.getV3());
                    } else if (received.getV2().equals("energy")) {
                        this.incrementCurrentEnergy((Float) received.getV3());
                    }
                }
            }
        }
        return null;
    }

    // GETTERS
    public float getCurrentKi() {
        return this.currentKi;
    }

    public float getCurrentEnergy() {
        return this.currentEnergy;
    }

    // SETTERS
    public void setCurrentKi(float value) {
        this.currentKi = value;
        NetworkController.get().sendMessage(this.id, new MessageStatePlayer(this));
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, (this.team == 1 ? ELocation.GAME_GUI_State_AlliedPlayers : ELocation.GAME_GUI_State_EnemyPlayers), new Tuple<>(ETaskType.RELAY, this.getId() + GlobalVariable.id_delimiter + EGuiElement.STATE_PLAYER, new Tuple<>(ETaskType.SETTER, "ki", this.currentKi * 100 / this.maxKi))));
    }

    public void setCurrentEnergy(float value) {
        this.currentEnergy = value;
        NetworkController.get().sendMessage(this.id, new MessageStatePlayer(this));
        CentralTaskManager.get().sendRequest(TaskFactory.createTask(ELocation.UNKNOWN, (this.team == 1? ELocation.GAME_GUI_State_AlliedPlayers : ELocation.GAME_GUI_State_EnemyPlayers), new Tuple<>(ETaskType.RELAY, this.getId() + GlobalVariable.id_delimiter + EGuiElement.STATE_PLAYER, new Tuple<>(ETaskType.SETTER, "energy", this.currentEnergy * 100 / this.maxEnergy))));
    }

    public void incrementCurrentKi(float value) {
        if ((value < 0 && this.currentKi != 0) || (value > 0 && this.currentKi != this.maxKi)) {
            this.currentKi += value;
            if (this.currentKi < 0) {
                this.setCurrentKi(0);
            } else if (this.currentKi > this.maxKi) {
                this.setCurrentKi(this.maxKi);
            }
            this.setCurrentKi(this.currentKi);
        }
    }

    public void incrementCurrentEnergy(float value) {
        if ((value < 0 && this.currentEnergy != 0) || (value > 0 && this.currentEnergy != this.maxEnergy)) {
            this.currentEnergy += value;
            if (this.currentEnergy < 0) {
                this.setCurrentEnergy(0);
            } else if (this.currentEnergy > this.maxEnergy) {
                this.setCurrentEnergy(this.maxEnergy);
            }
            this.setCurrentEnergy(this.currentEnergy);
        }
    }
}

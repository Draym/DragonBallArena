package com.andres_k.components.networkComponents.networkSend.messageServer;

import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.networkComponents.networkSend.MessageModel;

/**
 * Created by andres_k on 19/03/2016.
 */
public class MessageStatePlayer extends MessageModel {
    private float life;
    private int ki;
    private int energy;

    public MessageStatePlayer(Player player) {
        this.life = player.getCurrentLife();
        this.ki = player.getCurrentKi();
        this.energy = player.getCurrentEnergy();
    }

    public float getLife() {
        return this.life;
    }

    public int getKi() {
        return this.ki;
    }

    public int getEnergy() {
        return this.energy;
    }

    public void setLife(float life) {
        this.life = life;
    }

    public void setKi(int ki) {
        this.ki = ki;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}

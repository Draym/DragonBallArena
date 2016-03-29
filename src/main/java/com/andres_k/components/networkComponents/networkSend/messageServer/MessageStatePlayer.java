package com.andres_k.components.networkComponents.networkSend.messageServer;

import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.networkComponents.networkSend.MessageModel;

/**
 * Created by andres_k on 19/03/2016.
 */
public class MessageStatePlayer extends MessageModel {
    private float life;
    private float ki;
    private float energy;
    private float x;
    private float y;

    public MessageStatePlayer() {
    }

    public MessageStatePlayer(Player player) {
        this.life = player.getCurrentLife();
        this.ki = player.getCurrentKi();
        this.energy = player.getCurrentEnergy();
        this.x = player.getPosX();
        this.y = player.getPosY();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getLife() {
        return this.life;
    }

    public float getKi() {
        return this.ki;
    }

    public float getEnergy() {
        return this.energy;
    }

    public void setLife(float life) {
        this.life = life;
    }

    public void setKi(float ki) {
        this.ki = ki;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }
}

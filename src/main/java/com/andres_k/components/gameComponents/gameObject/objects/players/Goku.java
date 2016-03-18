package com.andres_k.components.gameComponents.gameObject.objects.players;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.gameComponents.gameObject.objects.entities.KiEntity;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.KiProjectiles;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.linked.Genkidama;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.linked.Kameha;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 13/10/2015.
 */
public class Goku extends Player {

    public Goku(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.GOKU, id, x, y, 3000, 1, 220, 15);
        try {
            this.specialActions.put(EGameObject.GENKIDAMA.getValue(), this.getClass().getMethod("createGenkidama"));
            this.specialActions.put(EGameObject.KAMEHA.getValue(), this.getClass().getMethod("createKamehameha"));
            this.specialActions.put(EGameObject.KI_BLAST.getValue(), this.getClass().getMethod("createKiBlast"));
            this.checkBeforeLaunch.put(EAnimation.KI_FINAL_ATTACK, this.getClass().getMethod("checkToLaunchGenkidama"));
            this.checkBeforeLaunch.put(EAnimation.KI_SPE_ATTACK, this.getClass().getMethod("checkToLaunchKamehameha"));
            this.checkBeforeLaunch.put(EAnimation.KI_BASIC_ATTACK, this.getClass().getMethod("checkToLaunchKiBlast"));
            this.checkBeforeLaunch.put(EAnimation.KI_CHARGE, this.getClass().getMethod("checkToLaunchKiCharge"));
            this.checkBeforeLaunch.put(EAnimation.RUSH, this.getClass().getMethod("checkToLaunchRush"));
            this.checkBeforeLaunch.put(EAnimation.DEFENSE, this.getClass().getMethod("checkToLaunchDefense"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void createGenkidama() {
        try {
            this.incrementCurrentKi(-400);
            KiEntity entity = new Genkidama(ResourceManager.get().getGameAnimator(EGameObject.GENKIDAMA), EGameObject.GENKIDAMA, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 40 : this.getPosX() - 40), this.getPosY() + 10, this.getAnimatorController().getEyesDirection(), 500 * this.damage, 900, 0);
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createKamehameha() {
        try {
            this.incrementCurrentKi(-205);
            KiEntity entity = new Kameha(ResourceManager.get().getGameAnimator(EGameObject.KAMEHA), ResourceManager.get().getGameAnimator(EGameObject.KAMEHA_Body), ResourceManager.get().getGameAnimator(EGameObject.KAMEHA_Back), this.id, this.getPosX() + 120, this.getPosY() + 10, this.getAnimatorController().getEyesDirection(), 300 * this.damage, 1500);
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createKiBlast() {
        try {
            this.incrementCurrentKi(-30);
            KiEntity entity = new KiProjectiles(ResourceManager.get().getGameAnimator(EGameObject.KI_BLAST), EGameObject.KI_BLAST, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 100 : this.getPosX() + - 100), this.getPosY() - 40, this.getAnimatorController().getEyesDirection(), 25 * this.damage, 900, 0);
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public boolean checkToLaunchRush() {
        this.incrementCurrentKi(-1);
        return (this.currentKi > 1);
    }

    public boolean checkToLaunchGenkidama() {
        return (this.currentKi > 400 && this.isOnEarth());
    }

    public boolean checkToLaunchKamehameha() {
        return (this.currentKi > 205 && this.isOnEarth());
    }

    public boolean checkToLaunchKiBlast() {
        return (this.currentKi > 30);
    }

    public boolean checkToLaunchKiCharge() {
        return (this.isOnEarth());
    }

    public boolean checkToLaunchDefense() {
        return (this.isOnEarth());
    }
}

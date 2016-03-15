package com.andres_k.components.gameComponents.gameObject.objects.players;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.gameComponents.gameObject.objects.entities.KiEntity;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.KiAttack;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.KiProjectiles;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 14/03/2016.
 */
public class Vegeta extends Player {
    public Vegeta(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.VEGETA, id, x, y, 1000, 1, 200, 10);
        try {
            this.specialActions.put(EGameObject.KI_BURST.getValue(), this.getClass().getMethod("createKiBurst"));
            this.specialActions.put(EGameObject.KI_BLAST.getValue(), this.getClass().getMethod("createKiBlast"));
            this.checkBeforeLaunch.put(EAnimation.KI_SPE_ATTACK, this.getClass().getMethod("checkToLaunchKiBurst"));
            this.checkBeforeLaunch.put(EAnimation.KI_BASIC_ATTACK, this.getClass().getMethod("checkToLaunchKiBlast"));
            this.checkBeforeLaunch.put(EAnimation.KI_CHARGE, this.getClass().getMethod("checkToLaunchKiCharge"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void createKiBurst() {
        try {
            this.incrementCurrentKi(-50);
            KiEntity entity = new KiAttack(ResourceManager.get().getGameAnimator(EGameObject.KI_BURST), EGameObject.KI_BURST, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 70 : this.getPosX() - 80), this.getPosY() - 5, this.getAnimatorController().getEyesDirection(), 70 * this.damage, 0, 0);
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createKiBlast() {
        try {
            this.incrementCurrentKi(-5);
            KiEntity entity1 = new KiAttack(ResourceManager.get().getGameAnimator(EGameObject.VEGETA_KI_BLAST_BACK), EGameObject.KI_BLAST, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 95 : this.getPosX() - 95), this.getPosY() - 15, this.getAnimatorController().getEyesDirection(), this.damage, 0, 0);
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity1)));
            KiEntity entity2 = new KiProjectiles(ResourceManager.get().getGameAnimator(EGameObject.VEGETA_KI_BLAST_HEAD), EGameObject.KI_BLAST, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 95 : this.getPosX() - 95), this.getPosY() - 15, this.getAnimatorController().getEyesDirection(),  5 * this.damage, 1200, 0);
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity2)));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public boolean checkToLaunchKiBurst() {
        return (this.currentKi > 50 && this.isOnEarth());
    }

    public boolean checkToLaunchKiBlast() {
        return (this.currentKi > 5);
    }

    public boolean checkToLaunchKiCharge() {
        return (this.isOnEarth());
    }

}

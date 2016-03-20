package com.andres_k.components.gameComponents.gameObject.objects.players;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.gameComponents.gameObject.objects.entities.KiEntity;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.KiAttack;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.KiProjectiles;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.linked.FinalFlash;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.SlickException;

import java.util.TimerTask;

/**
 * Created by andres_k on 14/03/2016.
 */
public class Vegeta extends Player {
    public Vegeta(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.VEGETA, id, x, y, 3300, 1, 220, 15);
        try {
            this.specialActions.put(EGameObject.VEGETA.getValue(), this.getClass().getMethod("transformBasic"));
            this.specialActions.put(EGameObject.VEGETA_S1.getValue(), this.getClass().getMethod("transformS1"));
            this.specialActions.put(EGameObject.KI_BLAST.getValue(), this.getClass().getMethod("createKiBlast"));
            this.specialActions.put(EGameObject.KI_BURST.getValue(), this.getClass().getMethod("createKiBurst"));
            this.specialActions.put(EGameObject.KI_FINAL.getValue(), this.getClass().getMethod("createKiFinal"));
            this.specialActions.put(EGameObject.KI_EXPLODE.getValue(), this.getClass().getMethod("createKiExplode"));
            this.checkBeforeLaunch.put(EAnimation.KI_BASIC_ATTACK, this.getClass().getMethod("checkToLaunchKiBlast"));
            this.checkBeforeLaunch.put(EAnimation.KI_SPE_ATTACK, this.getClass().getMethod("checkToLaunchKiBurst"));
            this.checkBeforeLaunch.put(EAnimation.KI_FINAL_ATTACK, this.getClass().getMethod("checkToLaunchKiFinal"));
            this.checkBeforeLaunch.put(EAnimation.KI_CHARGE, this.getClass().getMethod("checkToLaunchKiCharge"));
            this.checkBeforeLaunch.put(EAnimation.KI_EXPLODE, this.getClass().getMethod("checkToLaunchKiExplode"));
            this.checkBeforeLaunch.put(EAnimation.RUSH, this.getClass().getMethod("checkToLaunchRush"));
            this.checkBeforeLaunch.put(EAnimation.DEFENSE, this.getClass().getMethod("checkToLaunchDefense"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void transformBasic() {
        if (this.transformed) {
            try {
                if (ResourceManager.get().getGameAnimator(EGameObject.VEGETA) != null) {
                    this.transformationTimer.cancel();
                    this.transformed = false;
                    this.movement.setCurrentSpeed(220);
                    this.maxLife = 3300;
                    this.setCurrentLife(this.currentLife);
                    EAnimation saveAnim = this.animatorController.currentAnimationType();
                    int index = this.animatorController.getIndex();
                    EDirection saveDir = this.animatorController.getEyesDirection();
                    this.animatorController = ResourceManager.get().getGameAnimator(EGameObject.VEGETA_S1);
                    this.animatorController.forceCurrentAnimation(saveAnim, index);
                    this.animatorController.setEyesDirection(saveDir);
                }
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public void transformS1() {
        if (this.currentKi == this.maxKi && !this.transformed) {
            try {
                if (ResourceManager.get().getGameAnimator(EGameObject.VEGETA_S1) != null) {
                    this.transformed = true;
//                    this.movement.setCurrentSpeed(this.movement.getCurrentSpeed() * 1.5f);
                    this.maxLife = this.maxLife * 2f;
                    this.damage = this.damage * 3f;
                    this.setCurrentEnergy(this.maxEnergy);
                    this.setCurrentLife(this.maxLife);
                    EAnimation saveAnim = this.animatorController.currentAnimationType();
                    int index = this.animatorController.getIndex();
                    EDirection saveDir = this.animatorController.getEyesDirection();
                    this.animatorController = ResourceManager.get().getGameAnimator(EGameObject.VEGETA_S1);
                    this.animatorController.forceCurrentAnimation(saveAnim, index);
                    this.animatorController.setEyesDirection(saveDir);
                    this.transformationTimer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            incrementCurrentEnergy(-50);
                        }
                    }, 11000, 10000);
                }
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public void createKiFinal() {
        try {
            if (this.currentKi >= 300) {
                KiEntity entity = new FinalFlash(ResourceManager.get().getGameAnimator(EGameObject.FINAL_FLASH), ResourceManager.get().getGameAnimator(EGameObject.FINAL_FLASH_Body), ResourceManager.get().getGameAnimator(EGameObject.FINAL_FLASH_Back), this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 70 : this.getPosX() - 80), this.getPosY(), (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() - 147 : this.getPosX() - 223), this.getAnimatorController().getEyesDirection(), this.currentKi * this.damage, 1700);
                this.setCurrentKi(0);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createKiBurst() {
        try {
            if (this.currentKi >= 100) {
                this.incrementCurrentKi(-100);
                KiEntity entity = new KiAttack(ResourceManager.get().getGameAnimator(EGameObject.KI_BURST), EGameObject.KI_BURST, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 70 : this.getPosX() - 80), this.getPosY() - 5, this.getAnimatorController().getEyesDirection(), 200 * this.damage, 0, 0);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createKiExplode() {
        try {
            if (this.currentKi >= 150) {
                this.incrementCurrentKi(-150);
                KiEntity entity = new KiAttack(ResourceManager.get().getGameAnimator(EGameObject.KI_EXPLODE), EGameObject.KI_EXPLODE, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() - 85 : this.getPosX() + 85), this.getPosY(), this.getAnimatorController().getEyesDirection(), 280 * this.damage, 0, 0);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createKiBlast() {
        try {
            if (this.currentKi >= 15) {
                this.incrementCurrentKi(-15);
                KiEntity entity1 = new KiAttack(ResourceManager.get().getGameAnimator(EGameObject.VEGETA_KI_BLAST_BACK), EGameObject.KI_BLAST, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 95 : this.getPosX() - 95), this.getPosY() - 15, this.getAnimatorController().getEyesDirection(), this.damage, 0, 0);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity1)));
                KiEntity entity2 = new KiProjectiles(ResourceManager.get().getGameAnimator(EGameObject.VEGETA_KI_BLAST_HEAD), EGameObject.KI_BLAST, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 95 : this.getPosX() - 95), this.getPosY() - 15, this.getAnimatorController().getEyesDirection(), 10 * this.damage, 1200, 0);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity2)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public boolean checkToLaunchRush() {
        this.incrementCurrentKi(-1);
        return (this.currentKi > 1);
    }

    public boolean checkToLaunchKiFinal() {
        return (this.currentKi > 300 && this.isOnEarth());
    }

    public boolean checkToLaunchKiBurst() {
        return (this.currentKi > 100 && this.isOnEarth());
    }

    public boolean checkToLaunchKiBlast() {
        return (this.currentKi > 15);
    }

    public boolean checkToLaunchKiCharge() {
        return (this.isOnEarth());
    }

    public boolean checkToLaunchKiExplode() {
        return (this.currentKi > 150);
    }

    public boolean checkToLaunchDefense() {
        return (this.isOnEarth());
    }
}

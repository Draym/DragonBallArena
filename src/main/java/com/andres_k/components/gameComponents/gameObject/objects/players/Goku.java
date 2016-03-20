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

import java.util.TimerTask;

/**
 * Created by andres_k on 13/10/2015.
 */
public class Goku extends Player {

    public Goku(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.GOKU, id, x, y, 3000, 1, 220, 15);
        try {
            this.specialActions.put(EGameObject.GOKU.getValue(), this.getClass().getMethod("transformBasic"));
            this.specialActions.put(EGameObject.GOKU_S1.getValue(), this.getClass().getMethod("transformS1"));
            this.specialActions.put(EGameObject.MEGA_KAMEHA.getValue(), this.getClass().getMethod("createMegaKamehameha"));
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

    public void transformBasic() {
        if (this.transformed) {
            try {
            if (ResourceManager.get().getGameAnimator(EGameObject.GOKU) != null) {
                this.transformationTimer.cancel();
                this.transformed = false;
                this.movement.setCurrentSpeed(220);
                this.movement.setWeight(15);
                this.maxLife = 3000;
                this.setCurrentLife(this.currentLife);
                this.animatorController = ResourceManager.get().getGameAnimator(EGameObject.GOKU);
            }
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public void transformS1() {
        if (this.currentKi == this.maxKi && !this.transformed) {
            try {
                if (ResourceManager.get().getGameAnimator(EGameObject.GOKU_S1) != null) {
                    this.transformed = true;
                    this.movement.setCurrentSpeed(this.movement.getCurrentSpeed() * 3f);
                    this.movement.setWeight(this.movement.getWeight() * 3f);
                    this.maxLife = this.maxLife * 1.5f;
                    this.damage = this.damage * 1.5f;
                    this.setCurrentEnergy(this.maxEnergy);
                    this.setCurrentLife(this.maxLife);
                    EAnimation saveAnim = this.animatorController.currentAnimationType();
                    int index = this.animatorController.getIndex();
                    EDirection saveDir = this.animatorController.getEyesDirection();
                    this.animatorController = ResourceManager.get().getGameAnimator(EGameObject.GOKU_S1);
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

    public void createMegaKamehameha() {
        try {
            if (this.currentKi >= 400) {
                this.incrementCurrentKi(-400);
                KiEntity entity = new Kameha(ResourceManager.get().getGameAnimator(EGameObject.KAMEHA), ResourceManager.get().getGameAnimator(EGameObject.KAMEHA_Body), ResourceManager.get().getGameAnimator(EGameObject.KAMEHA_Back), this.id, this.getPosX() + 120, this.getPosY() + 10, this.getAnimatorController().getEyesDirection(), 600 * this.damage, 1500);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createGenkidama() {
        try {
            if (this.currentKi >= 400) {
                this.incrementCurrentKi(-400);
                KiEntity entity = new Genkidama(ResourceManager.get().getGameAnimator(EGameObject.GENKIDAMA), EGameObject.GENKIDAMA, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 40 : this.getPosX() - 40), this.getPosY() + 10, this.getAnimatorController().getEyesDirection(), 500 * this.damage, 900, 0);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createKamehameha() {
        try {
            if (this.currentKi >= 205) {
                this.incrementCurrentKi(-205);
                KiEntity entity = new Kameha(ResourceManager.get().getGameAnimator(EGameObject.KAMEHA), ResourceManager.get().getGameAnimator(EGameObject.KAMEHA_Body), ResourceManager.get().getGameAnimator(EGameObject.KAMEHA_Back), this.id, this.getPosX() + 120, this.getPosY() + 10, this.getAnimatorController().getEyesDirection(), 300 * this.damage, 1500);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createKiBlast() {
        try {
            if (this.currentKi >= 30) {
                this.incrementCurrentKi(-30);
                KiEntity entity = new KiProjectiles(ResourceManager.get().getGameAnimator(EGameObject.KI_BLAST), EGameObject.KI_BLAST, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 100 : this.getPosX() + -100), this.getPosY() - 40, this.getAnimatorController().getEyesDirection(), 25 * this.damage, 900, 0);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
            }
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

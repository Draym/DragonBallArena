package com.andres_k.components.gameComponents.gameObject.objects.players;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.gameComponents.gameObject.objects.entities.KiEntity;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.KiProjectiles;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.vulnerable.Genkidama;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.linked.Kameha;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.linked.MegaKameha;
import com.andres_k.components.resourceComponent.resources.ResourceManager;
import com.andres_k.components.taskComponent.CentralTaskManager;
import com.andres_k.components.taskComponent.ELocation;
import com.andres_k.components.taskComponent.ETaskType;
import com.andres_k.components.taskComponent.utils.TaskComponent;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.RandomTools;
import org.newdawn.slick.SlickException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by andres_k on 13/10/2015.
 */
public class Goku extends Player {

    public Goku(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.GOKU, id, x, y, 3000, 1, 270, 220, 15);
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
                    this.transformationTimer.purge();
                    this.transformationTimer = new Timer();
                    this.transformed = false;
                    this.movement.setMoveSpeed(220f);
                    this.movement.setWeight(15f);
                    this.maxLife = 3000;
                    this.damage = 1f;
                    this.setCurrentLife(this.currentLife);
                    EAnimation saveAnim = this.animatorController.currentAnimationType();
                    int index = this.animatorController.getIndex();
                    EDirection saveDir = this.animatorController.getEyesDirection();
                    this.animatorController = ResourceManager.get().getGameAnimator(EGameObject.GOKU);
                    this.animatorController.forceCurrentAnimation(saveAnim, index);
                    this.animatorController.setEyesDirection(saveDir);
                }
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public void transformS1() {
        if (this.currentKi == this.maxKi && !this.transformed && RandomTools.isSuccess((100 - this.getCurrentPercentLife()) / 2)) {
            try {
                if (ResourceManager.get().getGameAnimator(EGameObject.GOKU_S1) != null) {
                    this.transformed = true;
                    this.movement.setMoveSpeed(this.movement.getMoveSpeed() * 1.5f);
                    this.movement.setWeight(this.movement.getWeight() * 1.5f);
                    this.maxLife = this.maxLife * 2f;
                    this.damage = this.damage * 3f;
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
                            incrementCurrentEnergy(-100);
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
            if (this.currentKi >= 700) {
                this.incrementCurrentKi(-700);
                KiEntity entity = new MegaKameha(ResourceManager.get().getGameAnimator(EGameObject.MEGA_KAMEHA), ResourceManager.get().getGameAnimator(EGameObject.MEGA_KAMEHA_Body), ResourceManager.get().getGameAnimator(EGameObject.MEGA_KAMEHA_Back), this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 130 : this.getPosX() - this.getScaledValue(130)), this.getPosY(), (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() : this.getPosX() - 370), this.getAnimatorController().getEyesDirection(), 1000 * this.damage, 2400);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createGenkidama() {
        try {
            if (this.currentKi >= 500) {
                this.incrementCurrentKi(-500);
                KiEntity entity = new Genkidama(ResourceManager.get().getGameAnimator(EGameObject.GENKIDAMA), this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 40 : this.getPosX() - this.getScaledValue(40)), this.getPosY() + this.getScaledValue(10), this.getAnimatorController().getEyesDirection(), 500 * this.damage, 800, 0);
                CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createKamehameha() {
        try {
            if (this.currentKi >= 300) {
                this.incrementCurrentKi(-300);
                KiEntity entity = new Kameha(ResourceManager.get().getGameAnimator(EGameObject.KAMEHA), ResourceManager.get().getGameAnimator(EGameObject.KAMEHA_Body), ResourceManager.get().getGameAnimator(EGameObject.KAMEHA_Back), this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 120 : this.getPosX() - this.getScaledValue(120)), this.getPosY() + this.getScaledValue(10), this.getAnimatorController().getEyesDirection(), 700 * this.damage, 2000);
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
                KiEntity entity = new KiProjectiles(ResourceManager.get().getGameAnimator(EGameObject.KI_BLAST), EGameObject.KI_BLAST, this.id, (this.animatorController.getEyesDirection() == EDirection.RIGHT ? this.getPosX() + 100 : this.getPosX() - this.getScaledValue(100)), this.getPosY() - this.getScaledValue(40), this.getAnimatorController().getEyesDirection(), 40 * this.damage, 900, 0);
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
        if (this.transformed) {
            return (this.currentKi > 700 && this.isOnEarth());
        } else {
            return (this.currentKi > 500 && this.isOnEarth());
        }
    }

    public boolean checkToLaunchKamehameha() {
        return (this.currentKi > 300 && this.isOnEarth());
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

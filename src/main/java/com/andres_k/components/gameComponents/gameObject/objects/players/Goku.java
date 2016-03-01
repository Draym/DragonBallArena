package com.andres_k.components.gameComponents.gameObject.objects.players;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.gameComponents.gameObject.objects.entities.KiEntity;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.KiBlast;
import com.andres_k.components.gameComponents.gameObject.objects.entities.ki.linked.Kamehameha;
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
        super(animatorController, EGameObject.GOKU, id, x, y, 100, 1, 200, 15);
        try {
            this.specialActions.put(EGameObject.KAMEHA.getValue(), this.getClass().getMethod("createKamehameha"));
            this.specialActions.put(EGameObject.KI_BLAST.getValue(), this.getClass().getMethod("createKiBlast"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void createKamehameha() {
        try {
            KiEntity entity = new Kamehameha(ResourceManager.get().getGameAnimator(EGameObject.KAMEHA), ResourceManager.get().getGameAnimator(EGameObject.KAMEHA_Body), ResourceManager.get().getGameAnimator(EGameObject.KAMEHA_Back), this.id, this.getPosX() + 120, this.getPosY() + 10, this.getAnimatorController().getEyesDirection(), 100, 1300);
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void createKiBlast() {
        try {
            KiEntity entity = new KiBlast(ResourceManager.get().getGameAnimator(EGameObject.KI_BLAST), this.id, this.getPosX() + 120, this.getPosY() + 10, this.getAnimatorController().getEyesDirection(), 20, 800);
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, entity)));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

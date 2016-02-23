package com.andres_k.components.gameComponents.gameObject.objects.players;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.objects.Player;
import com.andres_k.components.gameComponents.gameObject.objects.entities.Kamehameha;
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
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void createKamehameha() {
        try {
            Kamehameha kameha = new Kamehameha(ResourceManager.get().getGameAnimator(EGameObject.KAMEHA), this.id, this.getPosX(), this.getPosY(), this.getAnimatorController().getEyesDirection(), 100, 500);
            CentralTaskManager.get().sendRequest(new TaskComponent(ELocation.UNKNOWN, ELocation.GAME_CONTROLLER, new Pair<>(ETaskType.CREATE, kameha)));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

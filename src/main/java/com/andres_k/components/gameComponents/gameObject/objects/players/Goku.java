package com.andres_k.components.gameComponents.gameObject.objects.players;

import com.andres_k.components.gameComponents.animations.Animator;
import com.andres_k.components.gameComponents.gameObject.EnumGameObject;
import com.andres_k.components.gameComponents.gameObject.objects.Player;

/**
 * Created by andres_k on 13/10/2015.
 */
public class Goku extends Player {
    public Goku(Animator animator, String id, float x, float y) {
        super(animator, EnumGameObject.GOKU, id, x, y, 100, 1, 10);
    }
}

package com.andres_k.components.gameComponents.gameObject.commands.actionComponent;

import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.animations.details.AnimationRepercussionItem;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.movement.EDirection;
import com.andres_k.utils.configs.GameConfig;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 17/11/2015.
 */
public class BasicActions {

    // ACTIONS
    public static void idle(GameObject object) {
        object.getMovement().setUseGravity(true);
        object.getMovement().stopMovement();
    }

    public static void explode(GameObject object) {
        if (object.getAnimatorController().getIndex() != 2) {
            object.getMovement().setUseGravity(false);
            if (object.getLastAttacker() != null) {
                AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
                if (repercussionItem != null) {
                    repercussionItem.applyMoveRepercussion(object);
                }
            }
        } else {
            if (!object.getMovement().isUseGravity()) {
                object.getMovement().setUseGravity(true);
            }
            object.getMovement().setPushY(0f);
        }
        if (!object.isOnEarth() && object.getAnimatorController().getIndex() == 0) {
            object.getAnimatorController().forceCurrentAnimationIndex(1);
        }
        if (object.isOnEarth() && object.getAnimatorController().getIndex() != 0) {
            object.getAnimatorController().forceCurrentAnimationIndex(0);
            object.getMovement().stopMovement();
        }
    }

    public static void defense(GameObject object) {
        object.getMovement().setPushX(0f);
        object.getMovement().setPushY(0f);
    }

    public static void transposition(GameObject object) {
        object.getMovement().setUseGravity(false);
        object.getMovement().setPushX(0f);
        object.getMovement().setPushY(0f);
        try {
            if (object.getAnimatorController().currentAnimation().getFrame() == 3) {
                object.teleportBehindMyAttacker();
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public static void block(GameObject object) {
        object.getMovement().setPushX(0f);
        object.getMovement().setPushY(0f);
        if (!object.isOnEarth()) {
            object.getAnimatorController().getCurrentContainer().getConfig().setNextType(EAnimation.FALL);
        }
        if (object.getLastAttacker() != null && object.getAnimatorController().hasAnimation(EAnimation.TRANSPOSITION)) {
            object.getAnimatorController().forceCurrentAnimationType(EAnimation.TRANSPOSITION);
            object.setUseAttackerTimer(false);
        }
    }

    public static void receipt(GameObject object) {
        object.getMovement().setUseGravity(true);
        object.getMovement().stopMovement();
    }

    public static void rush(GameObject object) {
        object.getMovement().addPushY(-0.25f);
        object.getMovement().setPushY(0);
        object.getMovement().setUseGravity(true);
        object.getMovement().setMoveDirection(object.getAnimatorController().getEyesDirection());
        if (object.getMovement().getMoveDirection() != EDirection.NONE) {
            object.getMovement().setPushX(GameConfig.speedTravel * 3.0f + object.getPosX() * 0.003f);
        }
    }

    // TOUCHED
    public static void touchedSimple(GameObject object) {
        object.getMovement().setUseGravity(false);
        AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
        if (repercussionItem != null) {
            repercussionItem.applyMoveRepercussion(object);
        }
    }

    public static void touchedMedium(GameObject object) {
        object.getMovement().setUseGravity(false);
        AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
        if (repercussionItem != null) {
            repercussionItem.applyMoveRepercussion(object);
        }
    }

    public static void touchedPropels(GameObject object) {
        object.getMovement().setUseGravity(false);
        AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
        if (repercussionItem != null) {
            repercussionItem.applyMoveRepercussion(object);
        }
    }

    public static void touchedFlip(GameObject object) {
        object.getMovement().setUseGravity(false);
        object.getMovement().setPushY(0f);
        AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
        if (repercussionItem != null) {
            repercussionItem.applyMoveRepercussion(object);
        }
    }

    public static void touchedProjected(GameObject object) {
        object.getMovement().setUseGravity(false);
        object.getMovement().setPushY(0f);
        AnimationRepercussionItem repercussionItem = object.getLastAttacker().getAnimatorController().getCurrentContainer().getRepercussion();
        if (repercussionItem != null) {
            repercussionItem.applyMoveRepercussion(object);
        }
    }

    public static void touchedFall(GameObject object) {
        if (object.isOnEarth()) {
            object.getAnimatorController().toNextAnimation();
        }
    }

    public static void touchedReceipt(GameObject object) {
        object.getMovement().setUseGravity(false);
        object.getMovement().setPushX(0);
        object.getMovement().setPushY(0);
    }

    // MOVEMENT
    public static void fall(GameObject object) {
        object.getMovement().setPushY(0f);

        if (object.getMovement().getMoveDirection() != EDirection.NONE) {
            object.getMovement().setPushX(GameConfig.speedTravel);
        }
        if (!object.isOnEarth()) {
            if (object.getMovement().getGravity() > 8) {
                object.getAnimatorController().getCurrentContainer().getConfig().setNextIndex(1);
            }
        }
        if (object.isOnEarth()) {
            object.getAnimatorController().toNextAnimation();
        }
    }

    public static void run(GameObject object) {
        object.getMovement().setPushY(0);
        object.getMovement().setUseGravity(true);
        if (object.isOnEarth()) {
            object.getMovement().addPushY(-1.5f);
        } else {
            object.getMovement().addPushY(-0.20f);
        }
        if (object.getMovement().getMoveDirection() != EDirection.NONE) {
            object.getMovement().setPushX(GameConfig.speedTravel);
        }
    }

    public static void jump(GameObject object) {
        if (object.getMovement().getMoveDirection() != EDirection.NONE) {
            object.getMovement().setPushX(GameConfig.speedTravel);
        }
        object.getMovement().setPushY(-GameConfig.speedJump);
    }
}

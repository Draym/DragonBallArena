package com.andres_k.components.gameComponents.animations.data;

import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.animations.EnumSprites;
import com.andres_k.components.gameComponents.animations.container.AnimatorConfig;
import com.andres_k.components.gameComponents.animations.container.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.goku.GokuActions;
import com.andres_k.utils.configs.ConfigPath;
import com.andres_k.utils.tools.StringTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameFactory extends AnimatorFactory {
    public AnimatorController getAnimator(EnumSprites index) throws SlickException, JSONException, NoSuchMethodException {
        if (index.getIndex() == EnumSprites.PLAYER.getIndex()) {
            return this.getPlayerAnimator(index);
        } else if (index.getIndex() == EnumSprites.ITEM.getIndex()) {
            return this.getItemAnimator(index);
        }
        return null;
    }

    public AnimatorController getPlayerAnimator(EnumSprites index) throws SlickException, JSONException, NoSuchMethodException {
        String name;
        String state;
        AnimatorController animatorController = new AnimatorController();
        if (index == EnumSprites.GOKU) {
            name = "/goku";
            state = "/basic";
            //IDLE
            animatorController.addAnimation(EnumAnimation.IDLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuIdle.png", 247, 247), EnumAnimation.IDLE.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
            animatorController.addCollision(EnumAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuIdle.json")));
            animatorController.addConfig(EnumAnimation.IDLE, 0, new AnimatorConfig(GokuActions.class.getMethod("idle", GameObject.class)));
            // FALL
            animatorController.addAnimation(EnumAnimation.FALL, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuFall.png", 247, 247), EnumAnimation.FALL.isLoop(), 0, 1, 0, 1, new int[]{200}));
            animatorController.addCollision(EnumAnimation.FALL, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuFall.json")));
            animatorController.addConfig(EnumAnimation.FALL, 0, new AnimatorConfig(GokuActions.class.getMethod("fall", GameObject.class), EnumAnimation.RECEIPT));
            // RUN
            animatorController.addAnimation(EnumAnimation.RUN, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuRun.png", 247, 247), EnumAnimation.RUN.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
            animatorController.addCollision(EnumAnimation.RUN, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuRun.json")));
            animatorController.addConfig(EnumAnimation.RUN, 0, new AnimatorConfig(GokuActions.class.getMethod("run", GameObject.class)));
            // RUSH
            animatorController.addAnimation(EnumAnimation.RUSH, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuRush.png", 247, 247), EnumAnimation.RUSH.isLoop(), 0, 2, 0, 1, new int[]{200, 200}));
            animatorController.addCollision(EnumAnimation.RUSH, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuRush.json")));
            animatorController.addConfig(EnumAnimation.RUSH, 0, new AnimatorConfig(GokuActions.class.getMethod("rush", GameObject.class)));
            // RECEIPT
            animatorController.addAnimation(EnumAnimation.RECEIPT, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuReceipt.png", 247, 247), EnumAnimation.RECEIPT.isLoop(), 0, 1, 0, 1, new int[]{130}));
            animatorController.addCollision(EnumAnimation.RECEIPT, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuReceipt.json")));
            animatorController.addConfig(EnumAnimation.RECEIPT, 0, new AnimatorConfig(GokuActions.class.getMethod("receipt", GameObject.class), EnumAnimation.IDLE));
            animatorController.addAnimation(EnumAnimation.RECEIPT, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuReceipt.png", 247, 247), EnumAnimation.RECEIPT.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 100}));
            animatorController.addCollision(EnumAnimation.RECEIPT, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuReceipt.json")));
            animatorController.addConfig(EnumAnimation.RECEIPT, 1, new AnimatorConfig(GokuActions.class.getMethod("receipt", GameObject.class), EnumAnimation.IDLE));
            // JUMP
            animatorController.addAnimation(EnumAnimation.JUMP, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuJump1.png", 247, 247), EnumAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{150, 150, 300}));
            animatorController.addCollision(EnumAnimation.JUMP, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuJump1.json")));
            animatorController.addConfig(EnumAnimation.JUMP, 0, new AnimatorConfig(GokuActions.class.getMethod("jump", GameObject.class), 1, EnumAnimation.FALL, 0));
            animatorController.addAnimation(EnumAnimation.JUMP, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuJump2.png", 247, 247), EnumAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 300}));
            animatorController.addCollision(EnumAnimation.JUMP, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuJump2.json")));
            animatorController.addConfig(EnumAnimation.JUMP, 1, new AnimatorConfig(GokuActions.class.getMethod("jump", GameObject.class), 1, EnumAnimation.FALL, 0));
            // DEFENSE
            animatorController.addAnimation(EnumAnimation.DEFENSE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuDef.png", 247, 247), EnumAnimation.DEFENSE.isLoop(), 0, 4, 0, 1, new int[]{200, 300, 300, 300}));
            animatorController.addCollision(EnumAnimation.DEFENSE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuDef.json")));
            animatorController.addConfig(EnumAnimation.DEFENSE, 0, new AnimatorConfig(GokuActions.class.getMethod("defense", GameObject.class)));
            // BLOCK
            animatorController.addAnimation(EnumAnimation.BLOCK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuBlock.png", 247, 247), EnumAnimation.BLOCK.isLoop(), 0, 1, 0, 1, new int[]{200}));
            animatorController.addCollision(EnumAnimation.BLOCK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuBlock.json")));
            animatorController.addConfig(EnumAnimation.BLOCK, 0, new AnimatorConfig(GokuActions.class.getMethod("block", GameObject.class)));
            // HAND ATTACK
            animatorController.addAnimation(EnumAnimation.HAND_ATTACK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuHandAttack1.png", 247, 247), EnumAnimation.HAND_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{150, 150, 100, 150}));
            animatorController.addCollision(EnumAnimation.HAND_ATTACK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuHandAttack1.json")));
            animatorController.addConfig(EnumAnimation.HAND_ATTACK, 0, new AnimatorConfig(GokuActions.class.getMethod("handAttack", GameObject.class), EnumAnimation.HAND_ATTACK, 2));
            animatorController.addAnimation(EnumAnimation.HAND_ATTACK, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuHandAttack2.png", 247, 247), EnumAnimation.HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 100}));
            animatorController.addCollision(EnumAnimation.HAND_ATTACK, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuHandAttack2.json")));
            animatorController.addConfig(EnumAnimation.HAND_ATTACK, 1, new AnimatorConfig(GokuActions.class.getMethod("handAttack", GameObject.class), EnumAnimation.HAND_ATTACK, 2));
            animatorController.addAnimation(EnumAnimation.HAND_ATTACK, 2, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuHandAttack3.png", 247, 247), EnumAnimation.HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 200}));
            animatorController.addCollision(EnumAnimation.HAND_ATTACK, 2, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuHandAttack3.json")));
            animatorController.addConfig(EnumAnimation.HAND_ATTACK, 2, new AnimatorConfig(GokuActions.class.getMethod("handAttack", GameObject.class)));

            // HAND FLY PROPELS
//            animatorController.addAnimation(EnumAnimation.HAND_FLY_PROPELS, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.images + name + state + "/gokuHandFlyPropels.png", 65, 70), EnumAnimation.HAND_FLY_PROPELS.isLoop(), 0, 5, 0, 1, new int[]{150, 150, 150, 150, 150}));

        }
        return animatorController;
    }

    public AnimatorController getItemAnimator(EnumSprites index) throws SlickException, JSONException {
        AnimatorController animatorController = new AnimatorController();
        if (index == EnumSprites.GROUND) {
            animatorController.addCollision(EnumAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/ground.json")));
        }
        if (index == EnumSprites.WALL) {
            animatorController.addCollision(EnumAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/wall.json")));
        }
        return animatorController;
    }
}

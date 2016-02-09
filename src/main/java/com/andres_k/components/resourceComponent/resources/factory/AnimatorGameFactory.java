package com.andres_k.components.resourceComponent.resources.factory;

import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.resourceComponent.resources.ESprites;
import com.andres_k.components.gameComponents.animations.AnimatorConfig;
import com.andres_k.components.gameComponents.animations.AnimatorController;
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

    @Override
    public AnimatorController getAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException {
        if (index.getIndex() == ESprites.PLAYER.getIndex()) {
            return this.getPlayerAnimator(index);
        } else if (index.getIndex() == ESprites.ITEM.getIndex()) {
            return this.getItemAnimator(index);
        }
        return null;
    }

    public AnimatorController getPlayerAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException {
        String name;
        String state;
        AnimatorController animatorController = new AnimatorController();
        if (index == ESprites.GOKU) {
            name = "/goku";
            state = "/basic";
            //IDLE
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuIdle.png", 247, 247), EAnimation.IDLE.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
            animatorController.addCollision(EAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuIdle.json")));
            animatorController.addConfig(EAnimation.IDLE, 0, new AnimatorConfig(GokuActions.class.getMethod("idle", GameObject.class)));
            // FALL
            animatorController.addAnimation(EAnimation.FALL, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuFall.png", 247, 247), EAnimation.FALL.isLoop(), 0, 1, 0, 1, new int[]{200}));
            animatorController.addCollision(EAnimation.FALL, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuFall.json")));
            animatorController.addConfig(EAnimation.FALL, 0, new AnimatorConfig(GokuActions.class.getMethod("fall", GameObject.class), EAnimation.RECEIPT));
            // RUN
            animatorController.addAnimation(EAnimation.RUN, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuRun.png", 247, 247), EAnimation.RUN.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
            animatorController.addCollision(EAnimation.RUN, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuRun.json")));
            animatorController.addConfig(EAnimation.RUN, 0, new AnimatorConfig(GokuActions.class.getMethod("run", GameObject.class)));
            // RUSH
            animatorController.addAnimation(EAnimation.RUSH, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuRush.png", 247, 247), EAnimation.RUSH.isLoop(), 0, 2, 0, 1, new int[]{200, 200}));
            animatorController.addCollision(EAnimation.RUSH, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuRush.json")));
            animatorController.addConfig(EAnimation.RUSH, 0, new AnimatorConfig(GokuActions.class.getMethod("rush", GameObject.class)));
            // RECEIPT
            animatorController.addAnimation(EAnimation.RECEIPT, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 1, 0, 1, new int[]{130}));
            animatorController.addCollision(EAnimation.RECEIPT, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuReceipt.json")));
            animatorController.addConfig(EAnimation.RECEIPT, 0, new AnimatorConfig(GokuActions.class.getMethod("receipt", GameObject.class), EAnimation.IDLE));
            animatorController.addAnimation(EAnimation.RECEIPT, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 100}));
            animatorController.addCollision(EAnimation.RECEIPT, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuReceipt.json")));
            animatorController.addConfig(EAnimation.RECEIPT, 1, new AnimatorConfig(GokuActions.class.getMethod("receipt", GameObject.class), EAnimation.IDLE));
            // JUMP
            animatorController.addAnimation(EAnimation.JUMP, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuJump1.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{150, 150, 300}));
            animatorController.addCollision(EAnimation.JUMP, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuJump1.json")));
            animatorController.addConfig(EAnimation.JUMP, 0, new AnimatorConfig(GokuActions.class.getMethod("jump", GameObject.class), 1, EAnimation.FALL, 0));
            animatorController.addAnimation(EAnimation.JUMP, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuJump2.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 300}));
            animatorController.addCollision(EAnimation.JUMP, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuJump2.json")));
            animatorController.addConfig(EAnimation.JUMP, 1, new AnimatorConfig(GokuActions.class.getMethod("jump", GameObject.class), 1, EAnimation.FALL, 0));
            // DEFENSE
            animatorController.addAnimation(EAnimation.DEFENSE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuDef.png", 247, 247), EAnimation.DEFENSE.isLoop(), 0, 4, 0, 1, new int[]{200, 300, 300, 300}));
            animatorController.addCollision(EAnimation.DEFENSE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuDef.json")));
            animatorController.addConfig(EAnimation.DEFENSE, 0, new AnimatorConfig(GokuActions.class.getMethod("defense", GameObject.class)));
            // BLOCK
            animatorController.addAnimation(EAnimation.BLOCK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuBlock.png", 247, 247), EAnimation.BLOCK.isLoop(), 0, 1, 0, 1, new int[]{200}));
            animatorController.addCollision(EAnimation.BLOCK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuBlock.json")));
            animatorController.addConfig(EAnimation.BLOCK, 0, new AnimatorConfig(GokuActions.class.getMethod("block", GameObject.class)));
            // HAND ATTACK
            animatorController.addAnimation(EAnimation.HAND_ATTACK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuHandAttack1.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{150, 150, 100, 150}));
            animatorController.addCollision(EAnimation.HAND_ATTACK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuHandAttack1.json")));
            animatorController.addConfig(EAnimation.HAND_ATTACK, 0, new AnimatorConfig(GokuActions.class.getMethod("handAttack", GameObject.class), EAnimation.HAND_ATTACK, 2));
            animatorController.addAnimation(EAnimation.HAND_ATTACK, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuHandAttack2.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 100}));
            animatorController.addCollision(EAnimation.HAND_ATTACK, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuHandAttack2.json")));
            animatorController.addConfig(EAnimation.HAND_ATTACK, 1, new AnimatorConfig(GokuActions.class.getMethod("handAttack", GameObject.class), EAnimation.HAND_ATTACK, 2));
            animatorController.addAnimation(EAnimation.HAND_ATTACK, 2, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuHandAttack3.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 200}));
            animatorController.addCollision(EAnimation.HAND_ATTACK, 2, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuHandAttack3.json")));
            animatorController.addConfig(EAnimation.HAND_ATTACK, 2, new AnimatorConfig(GokuActions.class.getMethod("handAttack", GameObject.class)));

            // HAND FLY PROPELS
//            animatorController.addAnimation(EnumAnimation.HAND_FLY_PROPELS, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuHandFlyPropels.png", 65, 70), EnumAnimation.HAND_FLY_PROPELS.isLoop(), 0, 5, 0, 1, new int[]{150, 150, 150, 150, 150}));

        }
        return animatorController;
    }

    public AnimatorController getItemAnimator(ESprites index) throws SlickException, JSONException {
        AnimatorController animatorController = new AnimatorController();
        if (index == ESprites.GROUND) {
            animatorController.addCollision(EAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/ground.json")));
        }
        if (index == ESprites.WALL) {
            animatorController.addCollision(EAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/wall.json")));
        }
        return animatorController;
    }
}

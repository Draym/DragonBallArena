package com.andres_k.components.resourceComponent.resources.factory;

import com.andres_k.components.gameComponents.animations.details.AnimationConfigItem;
import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.animations.details.AnimationRepercussionItem;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.goku.GokuActions;
import com.andres_k.components.graphicComponents.effects.EffectFactory;
import com.andres_k.components.resourceComponent.resources.ESprites;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.utils.configs.ConfigPath;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
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
        } else if (index.getIndex() == ESprites.ENTITY.getIndex()) {
            return this.getEntityAnimator(index);
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
            animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(GokuActions.class.getMethod("idle", GameObject.class), true));
            // FALL
            animatorController.addAnimation(EAnimation.FALL, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuFall.png", 247, 247), EAnimation.FALL.isLoop(), 0, 1, 0, 1, new int[]{200}));
            animatorController.addCollision(EAnimation.FALL, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuFall.json")));
            animatorController.addConfig(EAnimation.FALL, 0, new AnimationConfigItem(GokuActions.class.getMethod("fall", GameObject.class), true, EAnimation.RECEIPT));
            // RUN
            animatorController.addAnimation(EAnimation.RUN, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuRun.png", 247, 247), EAnimation.RUN.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
            animatorController.addCollision(EAnimation.RUN, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuRun.json")));
            animatorController.addConfig(EAnimation.RUN, 0, new AnimationConfigItem(GokuActions.class.getMethod("run", GameObject.class), true));
            // RUSH
            animatorController.addAnimation(EAnimation.RUSH, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuRush.png", 247, 247), EAnimation.RUSH.isLoop(), 0, 2, 0, 1, new int[]{40, 40}));
            animatorController.addCollision(EAnimation.RUSH, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuRush.json")));
            animatorController.addConfig(EAnimation.RUSH, 0, new AnimationConfigItem(GokuActions.class.getMethod("rush", GameObject.class), true));
            // ATTACK_RUSH
            animatorController.addAnimation(EAnimation.RUSH_ATTACK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuRushAttack1.png", 247, 247), EAnimation.RUSH_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{200, 200}));
            animatorController.addCollision(EAnimation.RUSH_ATTACK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuRushAttack1.json")));
            animatorController.addConfig(EAnimation.RUSH_ATTACK, 0, new AnimationConfigItem(GokuActions.class.getMethod("rushAttack", GameObject.class), true, EAnimation.RUSH_ATTACK, 1));
            animatorController.addRepercussion(EAnimation.RUSH_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROPELS, 60, new Pair<>(GameConfig.speedTravel, 0f)));
            animatorController.addAnimation(EAnimation.RUSH_ATTACK, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuRushAttack2.png", 247, 247), EAnimation.RUSH_ATTACK.isLoop(), 0, 3, 0, 1, new int[]{200, 200, 200}));
            animatorController.addCollision(EAnimation.RUSH_ATTACK, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuRushAttack2.json")));
            animatorController.addConfig(EAnimation.RUSH_ATTACK, 1, new AnimationConfigItem(GokuActions.class.getMethod("rushAttack", GameObject.class), true, EAnimation.RUSH_ATTACK, 2));
            animatorController.addRepercussion(EAnimation.RUSH_ATTACK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_PROPELS, 60, new Pair<>(GameConfig.speedTravel, 0f)));
            animatorController.addEffect(EAnimation.RUSH_ATTACK, 1, 0, EffectFactory.createSoundEffect(ESound.GOKU_RUSH_ATTACK));
            animatorController.addAnimation(EAnimation.RUSH_ATTACK, 2, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuRushAttack3.png", 247, 247), EAnimation.RUSH_ATTACK.isLoop(), 0, 1, 0, 1, new int[]{200}));
            animatorController.addCollision(EAnimation.RUSH_ATTACK, 2, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuRushAttack3.json")));
            animatorController.addConfig(EAnimation.RUSH_ATTACK, 2, new AnimationConfigItem(GokuActions.class.getMethod("rushAttack", GameObject.class), true));
            animatorController.addRepercussion(EAnimation.RUSH_ATTACK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_PROPELS, 60, new Pair<>(GameConfig.speedTravel, 0f)));
            // RECEIPT
            animatorController.addAnimation(EAnimation.RECEIPT, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 1, 0, 1, new int[]{130}));
            animatorController.addCollision(EAnimation.RECEIPT, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuReceipt.json")));
            animatorController.addConfig(EAnimation.RECEIPT, 0, new AnimationConfigItem(GokuActions.class.getMethod("receipt", GameObject.class), true));
            animatorController.addAnimation(EAnimation.RECEIPT, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 100}));
            animatorController.addCollision(EAnimation.RECEIPT, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuReceipt.json")));
            animatorController.addConfig(EAnimation.RECEIPT, 1, new AnimationConfigItem(GokuActions.class.getMethod("receipt", GameObject.class), true));
            // JUMP
            animatorController.addAnimation(EAnimation.JUMP, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuJump1.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{150, 150, 300}));
            animatorController.addCollision(EAnimation.JUMP, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuJump1.json")));
            animatorController.addConfig(EAnimation.JUMP, 0, new AnimationConfigItem(GokuActions.class.getMethod("jump", GameObject.class), true, 1, EAnimation.FALL, 0));
            animatorController.addAnimation(EAnimation.JUMP, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuJump2.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 300}));
            animatorController.addCollision(EAnimation.JUMP, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuJump2.json")));
            animatorController.addConfig(EAnimation.JUMP, 1, new AnimationConfigItem(GokuActions.class.getMethod("jump", GameObject.class), true, 1, EAnimation.FALL, 0));
            // DEFENSE
            animatorController.addAnimation(EAnimation.DEFENSE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuDef.png", 247, 247), EAnimation.DEFENSE.isLoop(), 0, 4, 0, 1, new int[]{200, 300, 300, 300}));
            animatorController.addCollision(EAnimation.DEFENSE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuDef.json")));
            animatorController.addConfig(EAnimation.DEFENSE, 0, new AnimationConfigItem(GokuActions.class.getMethod("defense", GameObject.class), true));
            // BLOCK
            animatorController.addAnimation(EAnimation.BLOCK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuBlock.png", 247, 247), EAnimation.BLOCK.isLoop(), 0, 1, 0, 1, new int[]{200}));
            animatorController.addCollision(EAnimation.BLOCK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuBlock.json")));
            animatorController.addConfig(EAnimation.BLOCK, 0, new AnimationConfigItem(GokuActions.class.getMethod("block", GameObject.class), true));
            animatorController.addEffect(EAnimation.BLOCK, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_BLOCK));
            // HAND_ATTACK
            animatorController.addAnimation(EAnimation.HAND_ATTACK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuHandAttack1.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{150, 150, 100, 150}));
            animatorController.addCollision(EAnimation.HAND_ATTACK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuHandAttack1.json")));
            animatorController.addConfig(EAnimation.HAND_ATTACK, 0, new AnimationConfigItem(GokuActions.class.getMethod("handAttack", GameObject.class), true, EAnimation.HAND_ATTACK, 2));
            animatorController.addRepercussion(EAnimation.HAND_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 20, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
            animatorController.addAnimation(EAnimation.HAND_ATTACK, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuHandAttack2.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 100}));
            animatorController.addCollision(EAnimation.HAND_ATTACK, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuHandAttack2.json")));
            animatorController.addConfig(EAnimation.HAND_ATTACK, 1, new AnimationConfigItem(GokuActions.class.getMethod("handAttack", GameObject.class), true, EAnimation.HAND_ATTACK, 2));
            animatorController.addRepercussion(EAnimation.HAND_ATTACK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 10, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
            animatorController.addAnimation(EAnimation.HAND_ATTACK, 2, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuHandAttack3.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 200}));
            animatorController.addCollision(EAnimation.HAND_ATTACK, 2, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuHandAttack3.json")));
            animatorController.addConfig(EAnimation.HAND_ATTACK, 2, new AnimationConfigItem(GokuActions.class.getMethod("handAttack", GameObject.class), true));
            animatorController.addRepercussion(EAnimation.HAND_ATTACK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 10, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
            // HAND_FLY_PROPELS
            animatorController.addAnimation(EAnimation.HAND_FLY_PROPELS, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuHandFlyPropels.png", 247, 247), EAnimation.HAND_FLY_PROPELS.isLoop(), 0, 5, 0, 1, new int[]{150, 120, 100, 120, 150}));
            animatorController.addCollision(EAnimation.HAND_FLY_PROPELS, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuHandFlyPropels.json")));
            animatorController.addConfig(EAnimation.HAND_FLY_PROPELS, 0, new AnimationConfigItem(GokuActions.class.getMethod("handFlyPropels", GameObject.class), true, EAnimation.FALL));
            animatorController.addRepercussion(EAnimation.HAND_FLY_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_FLIP, 40, new Pair<>(0f, 0f)));
            animatorController.addEffect(EAnimation.HAND_FLY_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_HAND_FLY_PROPELS));
            // KI_SPE_ATTACK
            animatorController.addAnimation(EAnimation.KI_SPE_ATTACK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuKamehameha.png", 247, 247), EAnimation.KI_SPE_ATTACK.isLoop(), 0, 10, 0, 1, new int[]{300, 350, 400, 400, 620, 200, 10000, 150, 150, 150}));
            animatorController.addCollision(EAnimation.KI_SPE_ATTACK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuKamehameha.json")));
            animatorController.addConfig(EAnimation.KI_SPE_ATTACK, 0, new AnimationConfigItem(GokuActions.class.getMethod("kiSpeAttack", GameObject.class), false));
            animatorController.addEffect(EAnimation.KI_SPE_ATTACK, 0, 0, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_expr.png", 85, 70), false, 0, 3, 0, 1, new int[]{1000, 1000, 800}), 60, 60, false));
            animatorController.addEffect(EAnimation.KI_SPE_ATTACK, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_KAMEHAMEHA_AIM));
            animatorController.addEffect(EAnimation.KI_SPE_ATTACK, 0, 5, EffectFactory.createSoundEffect(ESound.GOKU_KAMEHAMEHA_FIRE));
            // KI_SPE_ATTACK
            animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuKiBlast1.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 3, 0, 1, new int[]{150, 100, 100}));
            animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuKiBlast1.json")));
            animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 0, new AnimationConfigItem(EAnimation.KI_BASIC_ATTACK, 1));
            animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuKiBlast2.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 5, 0, 1, new int[]{100, 100, 40, 40, 40}));
            animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuKiBlast2.json")));
            animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 1, new AnimationConfigItem(GokuActions.class.getMethod("kiBasicAttack", GameObject.class), false, EAnimation.KI_BASIC_ATTACK, 3));
            animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 2, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuKiBlast3.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 6, 0, 1, new int[]{100, 100, 40, 40, 40, 40}));
            animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 2, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuKiBlast3.json")));
            animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 2, new AnimationConfigItem(GokuActions.class.getMethod("kiBasicAttack", GameObject.class), false, EAnimation.KI_BASIC_ATTACK, 3));
            animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 3, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuKiBlast4.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 80}));
            animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 3, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuKiBlast4.json")));
            animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 3, new AnimationConfigItem(EAnimation.IDLE, 0));
            // JUMP_KICK
            animatorController.addAnimation(EAnimation.JUMP_KICK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuJumpKick1.png", 247, 247), EAnimation.JUMP_KICK.isLoop(), 0, 4, 0, 1, new int[]{200, 150, 150, 100}));
            animatorController.addCollision(EAnimation.JUMP_KICK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuJumpKick1.json")));
            animatorController.addConfig(EAnimation.JUMP_KICK, 0, new AnimationConfigItem(GokuActions.class.getMethod("jumpKickAttack", GameObject.class), false, EAnimation.JUMP_KICK, 2));
            animatorController.addEffect(EAnimation.JUMP_KICK, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_JUMP_KICK));
            animatorController.addRepercussion(EAnimation.JUMP_KICK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 20, new Pair<>(GameConfig.speedTravel * 2, 0f)));
            animatorController.addAnimation(EAnimation.JUMP_KICK, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuJumpKick2.png", 247, 247), EAnimation.JUMP_KICK.isLoop(), 0, 5, 0, 1, new int[]{60, 60, 60, 60, 60}));
            animatorController.addCollision(EAnimation.JUMP_KICK, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuJumpKick2.json")));
            animatorController.addConfig(EAnimation.JUMP_KICK, 1, new AnimationConfigItem(GokuActions.class.getMethod("jumpKickAttack", GameObject.class), false, EAnimation.JUMP_KICK, 2));
            animatorController.addRepercussion(EAnimation.JUMP_KICK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 10, new Pair<>(GameConfig.speedTravel * 2, 0f)));
            animatorController.addAnimation(EAnimation.JUMP_KICK, 2, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuJumpKick3.png", 247, 247), EAnimation.JUMP_KICK.isLoop(), 0, 4, 0, 1, new int[]{100, 150, 150, 100}));
            animatorController.addCollision(EAnimation.JUMP_KICK, 2, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuJumpKick3.json")));
            animatorController.addConfig(EAnimation.JUMP_KICK, 2, new AnimationConfigItem(EAnimation.IDLE, 0));
            animatorController.addRepercussion(EAnimation.JUMP_KICK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 10, new Pair<>(GameConfig.speedTravel * 2, 0f)));
            // SPIRAL_KICK
            animatorController.addAnimation(EAnimation.SPIRAL_KICK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuSpiralKick1.png", 247, 247), EAnimation.SPIRAL_KICK.isLoop(), 0, 7, 0, 1, new int[]{200, 150, 150, 100, 100, 100, 100}));
            animatorController.addCollision(EAnimation.SPIRAL_KICK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuSpiralKick1.json")));
            animatorController.addConfig(EAnimation.SPIRAL_KICK, 0, new AnimationConfigItem(GokuActions.class.getMethod("spiralKickAttack", GameObject.class), false, EAnimation.SPIRAL_KICK, 1));
            animatorController.addRepercussion(EAnimation.SPIRAL_KICK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 30, new Pair<>(GameConfig.speedTravel * 3, 0f), true));
            animatorController.addAnimation(EAnimation.SPIRAL_KICK, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuSpiralKick2.png", 247, 247), EAnimation.SPIRAL_KICK.isLoop(), 0, 4, 0, 1, new int[]{100, 100, 100, 100}));
            animatorController.addCollision(EAnimation.SPIRAL_KICK, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuSpiralKick2.json")));
            animatorController.addConfig(EAnimation.SPIRAL_KICK, 1, new AnimationConfigItem(GokuActions.class.getMethod("spiralKickAttack", GameObject.class), false, EAnimation.SPIRAL_KICK, 2));
            animatorController.addRepercussion(EAnimation.SPIRAL_KICK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 30, new Pair<>(GameConfig.speedTravel * 3, 0f), true));
            animatorController.addAnimation(EAnimation.SPIRAL_KICK, 2, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuSpiralKick3.png", 247, 247), EAnimation.SPIRAL_KICK.isLoop(), 0, 2, 0, 1, new int[]{100, 150}));
            animatorController.addCollision(EAnimation.SPIRAL_KICK, 2, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuSpiralKick3.json")));
            animatorController.addConfig(EAnimation.SPIRAL_KICK, 2, new AnimationConfigItem(EAnimation.IDLE, 0));
            animatorController.addRepercussion(EAnimation.SPIRAL_KICK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 30, new Pair<>(GameConfig.speedTravel * 3, 0f), true));
            // KICK_PROPELS
            animatorController.addAnimation(EAnimation.KICK_PROPELS, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuKickPropels1.png", 247, 247), EAnimation.KICK_PROPELS.isLoop(), 0, 7, 0, 1, new int[]{200, 150, 150, 100, 100, 100, 100}));
            animatorController.addCollision(EAnimation.KICK_PROPELS, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuKickPropels1.json")));
            animatorController.addConfig(EAnimation.KICK_PROPELS, 0, new AnimationConfigItem(GokuActions.class.getMethod("kickPropelsAttack", GameObject.class), false, EAnimation.IDLE));
            animatorController.addRepercussion(EAnimation.KICK_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_FLIP, 70, new Pair<>(0f, -GameConfig.speedTravel)));
            animatorController.addEffect(EAnimation.KICK_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_KICK_PROPELS));
            // KI_CHARGE
            animatorController.addAnimation(EAnimation.KI_CHARGE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuKiCharge1.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 2, 0, 1, new int[]{200, 150}));
            animatorController.addCollision(EAnimation.KI_CHARGE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuKiCharge1.json")));
            animatorController.addConfig(EAnimation.KI_CHARGE, 0, new AnimationConfigItem(EAnimation.IDLE, 0));
            animatorController.addAnimation(EAnimation.KI_CHARGE, 1, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuKiCharge2.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 4, 0, 1, new int[]{100, 100, 100, 100}));
            animatorController.addCollision(EAnimation.KI_CHARGE, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuKiCharge2.json")));
            animatorController.addConfig(EAnimation.KI_CHARGE, 1, new AnimationConfigItem(GokuActions.class.getMethod("kiChargeAction", GameObject.class), false, EAnimation.KI_CHARGE, 2));
            animatorController.addEffect(EAnimation.KI_CHARGE, 1, 0, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuKi.png", 247, 247), false, 0, 4, 0, 1, new int[]{100, 100, 100, 100}), -47, 0, false));
            animatorController.addAnimation(EAnimation.KI_CHARGE, 2, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuKiCharge3.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 1, 0, 1, new int[]{150}));
            animatorController.addCollision(EAnimation.KI_CHARGE, 2, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuKiCharge3.json")));
            animatorController.addConfig(EAnimation.KI_CHARGE, 2, new AnimationConfigItem(EAnimation.IDLE, 0));
            // TOUCHED_SIMPLE
            animatorController.addAnimation(EAnimation.TOUCHED_SIMPLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuTouchedSimple.png", 247, 247), EAnimation.TOUCHED_SIMPLE.isLoop(), 0, 1, 0, 1, new int[]{300}));
            animatorController.addCollision(EAnimation.TOUCHED_SIMPLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuTouchedSimple.json")));
            animatorController.addConfig(EAnimation.TOUCHED_SIMPLE, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedSimple", GameObject.class), true, EAnimation.IDLE));
            animatorController.addEffect(EAnimation.TOUCHED_SIMPLE, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_SIMPLE));
            // TOUCHED_MEDIUM
            animatorController.addAnimation(EAnimation.TOUCHED_MEDIUM, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuTouchedMedium.png", 247, 247), EAnimation.TOUCHED_MEDIUM.isLoop(), 0, 1, 0, 1, new int[]{400}));
            animatorController.addCollision(EAnimation.TOUCHED_MEDIUM, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuTouchedMedium.json")));
            animatorController.addConfig(EAnimation.TOUCHED_MEDIUM, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedMedium", GameObject.class), true, EAnimation.IDLE));
            animatorController.addEffect(EAnimation.TOUCHED_MEDIUM, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_MEDIUM));
            // TOUCHED_PROPELS
            animatorController.addAnimation(EAnimation.TOUCHED_PROPELS, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuTouchedPropels.png", 247, 247), EAnimation.TOUCHED_PROPELS.isLoop(), 0, 2, 0, 1, new int[]{300, 300}));
            animatorController.addCollision(EAnimation.TOUCHED_PROPELS, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuTouchedPropels.json")));
            animatorController.addConfig(EAnimation.TOUCHED_PROPELS, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedPropels", GameObject.class), true, EAnimation.IDLE));
            animatorController.addEffect(EAnimation.TOUCHED_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_PROPELS));
            // TOUCHED_FLIP
            animatorController.addAnimation(EAnimation.TOUCHED_FLIP, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuTouchedFlip.png", 247, 247), EAnimation.TOUCHED_FLIP.isLoop(), 0, 4, 0, 1, new int[]{150, 150, 150, 150}));
            animatorController.addCollision(EAnimation.TOUCHED_FLIP, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuTouchedFlip.json")));
            animatorController.addConfig(EAnimation.TOUCHED_FLIP, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedFlip", GameObject.class), true, EAnimation.TOUCHED_FALL));
            //TOUCHED_FALL
            animatorController.addAnimation(EAnimation.TOUCHED_FALL, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{100}));
            animatorController.addCollision(EAnimation.TOUCHED_FALL, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuTouchedFall.json")));
            animatorController.addConfig(EAnimation.TOUCHED_FALL, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedFall", GameObject.class), true, EAnimation.TOUCHED_RECEIPT));
            // TOUCHED_RECEIPT
            animatorController.addAnimation(EAnimation.TOUCHED_RECEIPT, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + name + state + "/gokuTouchedReceipt.png", 247, 247), EAnimation.TOUCHED_RECEIPT.isLoop(), 0, 6, 0, 1, new int[]{150, 200, 150, 150, 150, 150}));
            animatorController.addCollision(EAnimation.TOUCHED_RECEIPT, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + name + state + "/gokuTouchedReceipt.json")));
            animatorController.addConfig(EAnimation.TOUCHED_RECEIPT, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedReceipt", GameObject.class), true, EAnimation.IDLE));
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

    public AnimatorController getEntityAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.KAMEHA) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_head0.png", 247, 247), false, 0, 2, 0, 1, new int[]{150, 150}));
            animatorController.addCollision(EAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_head0.json")));
            animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(EAnimation.KI_SPE_ATTACK, 0));
            animatorController.addAnimation(EAnimation.KI_SPE_ATTACK, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_head1.png", 247, 247), true, 0, 2, 0, 1, new int[]{150, 150}));
            animatorController.addCollision(EAnimation.KI_SPE_ATTACK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_head1.json")));
            animatorController.addRepercussion(EAnimation.KI_SPE_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 100, new Pair<>(GameConfig.speedTravel, 0f)));
        } else if (index == ESprites.KAMEHA_Back) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_back1.png", 247, 247), true, 0, 2, 0, 1, new int[]{200, 200}));
            animatorController.addCollision(EAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_back1.json")));
            animatorController.addAnimation(EAnimation.EXPLODE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_back2.png", 247, 247), false, 0, 4, 0, 1, new int[]{100, 100, 100, 100}));
            animatorController.addCollision(EAnimation.EXPLODE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_back2.json")));
        } else if (index == ESprites.KAMEHA_Body) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_body1.png", 247, 247), true, 0, 2, 0, 1, new int[]{250, 250}));
            animatorController.addCollision(EAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_body1.json")));
            animatorController.addAnimation(EAnimation.EXPLODE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_body2.png", 247, 247), false, 0, 4, 0, 1, new int[]{60, 60, 60, 60}));
            animatorController.addCollision(EAnimation.EXPLODE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_body2.json")));
        } else if (index == ESprites.KI_BLAST) {
            animatorController.addAnimation(EAnimation.IDLE, 0, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kiBlast" + "/kiBlast.png", 247, 247), true, 0, 2, 0, 1, new int[]{200, 200}));
            animatorController.addCollision(EAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kiBlast" + "/kiBlast.json")));
            animatorController.addRepercussion(EAnimation.IDLE, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 10, new Pair<>(GameConfig.speedTravel, 0f)));
        }
        return animatorController;
    }
}

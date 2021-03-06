package com.andres_k.components.resourceComponent.resources.factory;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.animations.details.AnimationConfigItem;
import com.andres_k.components.gameComponents.animations.details.AnimationRepercussionItem;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.entity.BigBangActions;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.entity.GenkidamaActions;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.players.GokuActions;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.players.GokuS1Actions;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.players.VegetaActions;
import com.andres_k.components.gameComponents.gameObject.commands.actionComponent.players.VegetaS1Actions;
import com.andres_k.components.graphicComponents.effects.EffectFactory;
import com.andres_k.components.graphicComponents.effects.effect.directive.ClearEffects;
import com.andres_k.components.resourceComponent.resources.ESprites;
import com.andres_k.components.resourceComponent.sounds.ESound;
import com.andres_k.utils.configs.ConfigPath;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import com.andres_k.utils.tools.FilesTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameFactory extends AnimatorFactory {

    @Override
    public AnimatorController getAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException {
        if (index.getIndex() == ESprites.PLAYER.getIndex()) {
            if (index == ESprites.GOKU) {
                return this.getGokuAnimator();
            } else if (index == ESprites.VEGETA) {
                return this.getVegetaAnimator();
            } else if (index == ESprites.GOKU_S1) {
                return this.getGokuS1Animator();
            } else if (index == ESprites.VEGETA_S1) {
                return this.getVegetaS1Animator();
            }
        } else if (index.getIndex() == ESprites.ITEM.getIndex()) {
            return this.getItemAnimator(index);
        } else if (index.getIndex() == ESprites.ENTITY.getIndex()) {
            return this.getEntityAnimator(index);
        }
        return null;
    }

    public AnimatorController getGokuAnimator() throws SlickException, JSONException, NoSuchMethodException {
        AnimatorController animatorController = new AnimatorController();
        String id = "/goku/basic";
        //IDLE
        animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuIdle.png", 247, 247), EAnimation.IDLE.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
        animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuIdle.json")));
        animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(GokuActions.class.getMethod("idle", GameObject.class), true));
        // EXPLODE
        Animation logoKO = AnimationFactory.loadAnimation(ConfigPath.image_gui + "/logo/logoKO/logoKO ", ".png", 1, 19, false, 50);
        logoKO.addFrame(new Image(ConfigPath.image_gui + "/logo/logoKO/logoKO 19.png"), 1500);
        animatorController.addAnimation(EAnimation.EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedProjected.png", 247, 247), EAnimation.TOUCHED_PROJECTED.isLoop(), 0, 1, 0, 1, new int[]{3000}));
        animatorController.addCollision(EAnimation.EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedProjected.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 0, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.NULL));
        animatorController.addEffect(EAnimation.EXPLODE, 0, 0, EffectFactory.createAnimationEffect(logoKO, -80, 80, false));
        animatorController.addEffect(EAnimation.EXPLODE, 0, 0, EffectFactory.createSoundEffect(ESound.EFFECT_KO));
        animatorController.addAnimation(EAnimation.EXPLODE, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{500}));
        animatorController.addCollision(EAnimation.EXPLODE, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedFall.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 1, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.EXPLODE, 2));
        animatorController.addAnimation(EAnimation.EXPLODE, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{3000}));
        animatorController.addCollision(EAnimation.EXPLODE, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedFall.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 2, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.NULL));
        // FALL
        animatorController.addAnimation(EAnimation.FALL, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuFall.png", 247, 247), EAnimation.FALL.isLoop(), 0, 1, 0, 1, new int[]{200}));
        animatorController.addCollision(EAnimation.FALL, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuFall.json")));
        animatorController.addConfig(EAnimation.FALL, 0, new AnimationConfigItem(GokuActions.class.getMethod("fall", GameObject.class), true, EAnimation.RECEIPT));
        // RUN
        animatorController.addAnimation(EAnimation.RUN, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuRun.png", 247, 247), EAnimation.RUN.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
        animatorController.addCollision(EAnimation.RUN, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuRun.json")));
        animatorController.addConfig(EAnimation.RUN, 0, new AnimationConfigItem(GokuActions.class.getMethod("run", GameObject.class), true));
        // RUSH
        animatorController.addAnimation(EAnimation.RUSH, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuRush.png", 247, 247), EAnimation.RUSH.isLoop(), 0, 2, 0, 1, new int[]{40, 40}));
        animatorController.addCollision(EAnimation.RUSH, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuRush.json")));
        animatorController.addConfig(EAnimation.RUSH, 0, new AnimationConfigItem(GokuActions.class.getMethod("rush", GameObject.class), true));
        // ATTACK_RUSH
        animatorController.addAnimation(EAnimation.RUSH_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuRushAttack1.png", 247, 247), EAnimation.RUSH_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 100}));
        animatorController.addCollision(EAnimation.RUSH_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuRushAttack1.json")));
        animatorController.addConfig(EAnimation.RUSH_ATTACK, 0, new AnimationConfigItem(GokuActions.class.getMethod("rushAttack", GameObject.class), true, EAnimation.RUSH_ATTACK, 1));
        animatorController.addRepercussion(EAnimation.RUSH_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROPELS, 150, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        animatorController.addAnimation(EAnimation.RUSH_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuRushAttack2.png", 247, 247), EAnimation.RUSH_ATTACK.isLoop(), 0, 3, 0, 1, new int[]{200, 200, 200}));
        animatorController.addCollision(EAnimation.RUSH_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuRushAttack2.json")));
        animatorController.addConfig(EAnimation.RUSH_ATTACK, 1, new AnimationConfigItem(GokuActions.class.getMethod("rushAttack", GameObject.class), true, EAnimation.RUSH_ATTACK, 2));
        animatorController.addRepercussion(EAnimation.RUSH_ATTACK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_PROPELS, 150, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        animatorController.addEffect(EAnimation.RUSH_ATTACK, 1, 0, EffectFactory.createSoundEffect(ESound.GOKU_RUSH_ATTACK));
        animatorController.addAnimation(EAnimation.RUSH_ATTACK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuRushAttack3.png", 247, 247), EAnimation.RUSH_ATTACK.isLoop(), 0, 1, 0, 1, new int[]{200}));
        animatorController.addCollision(EAnimation.RUSH_ATTACK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuRushAttack3.json")));
        animatorController.addConfig(EAnimation.RUSH_ATTACK, 2, new AnimationConfigItem(GokuActions.class.getMethod("rushAttack", GameObject.class), true));
        animatorController.addRepercussion(EAnimation.RUSH_ATTACK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_PROPELS, 150, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        // RECEIPT
        animatorController.addAnimation(EAnimation.RECEIPT, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 1, 0, 1, new int[]{130}));
        animatorController.addCollision(EAnimation.RECEIPT, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuReceipt.json")));
        animatorController.addConfig(EAnimation.RECEIPT, 0, new AnimationConfigItem(GokuActions.class.getMethod("receipt", GameObject.class), true));
        animatorController.addAnimation(EAnimation.RECEIPT, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 100}));
        animatorController.addCollision(EAnimation.RECEIPT, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuReceipt.json")));
        animatorController.addConfig(EAnimation.RECEIPT, 1, new AnimationConfigItem(GokuActions.class.getMethod("receipt", GameObject.class), true));
        // JUMP
        animatorController.addAnimation(EAnimation.JUMP, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuJump1.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{150, 150, 300}));
        animatorController.addCollision(EAnimation.JUMP, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuJump1.json")));
        animatorController.addConfig(EAnimation.JUMP, 0, new AnimationConfigItem(GokuActions.class.getMethod("jump", GameObject.class), true, 1, EAnimation.FALL, 0));
        animatorController.addAnimation(EAnimation.JUMP, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuJump2.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 300}));
        animatorController.addCollision(EAnimation.JUMP, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuJump2.json")));
        animatorController.addConfig(EAnimation.JUMP, 1, new AnimationConfigItem(GokuActions.class.getMethod("jump", GameObject.class), true, 1, EAnimation.FALL, 0));
        // DEFENSE
        animatorController.addAnimation(EAnimation.DEFENSE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuDef.png", 247, 247), EAnimation.DEFENSE.isLoop(), 0, 4, 0, 1, new int[]{200, 300, 300, 300}));
        animatorController.addCollision(EAnimation.DEFENSE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuDef.json")));
        animatorController.addConfig(EAnimation.DEFENSE, 0, new AnimationConfigItem(GokuActions.class.getMethod("defense", GameObject.class), true));
        // BLOCK
        animatorController.addAnimation(EAnimation.BLOCK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuBlock.png", 247, 247), EAnimation.BLOCK.isLoop(), 0, 1, 0, 1, new int[]{300}));
        animatorController.addCollision(EAnimation.BLOCK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuBlock.json")));
        animatorController.addConfig(EAnimation.BLOCK, 0, new AnimationConfigItem(GokuActions.class.getMethod("block", GameObject.class), true));
        animatorController.addEffect(EAnimation.BLOCK, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_BLOCK));
        //TRANSPOSITION
        animatorController.addAnimation(EAnimation.TRANSPOSITION, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTransposition.png", 247, 247), EAnimation.TRANSPOSITION.isLoop(), 0, 6, 0, 1, new int[]{200, 100, 100, 100, 200, 200}));
        animatorController.addCollision(EAnimation.TRANSPOSITION, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTransposition.json")));
        animatorController.addConfig(EAnimation.TRANSPOSITION, 0, new AnimationConfigItem(GokuActions.class.getMethod("transposition", GameObject.class), false));
        // HAND_ATTACK
        animatorController.addAnimation(EAnimation.HAND_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuHandAttack1.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{100, 100, 60, 100}));
        animatorController.addCollision(EAnimation.HAND_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuHandAttack1.json")));
        animatorController.addConfig(EAnimation.HAND_ATTACK, 0, new AnimationConfigItem(GokuActions.class.getMethod("handAttack", GameObject.class), true, EAnimation.HAND_ATTACK, 2));
        animatorController.addRepercussion(EAnimation.HAND_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 50, new Pair<>(GameConfig.speedTravel * 1.5f, 0f), true));
        animatorController.addAnimation(EAnimation.HAND_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuHandAttack2.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{70, 70}));
        animatorController.addCollision(EAnimation.HAND_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuHandAttack2.json")));
        animatorController.addConfig(EAnimation.HAND_ATTACK, 1, new AnimationConfigItem(GokuActions.class.getMethod("handAttack", GameObject.class), true, EAnimation.HAND_ATTACK, 2));
        animatorController.addRepercussion(EAnimation.HAND_ATTACK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 50, new Pair<>(GameConfig.speedTravel * 1.5f, 0f), true));
        animatorController.addAnimation(EAnimation.HAND_ATTACK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuHandAttack3.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{70, 100}));
        animatorController.addCollision(EAnimation.HAND_ATTACK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuHandAttack3.json")));
        animatorController.addConfig(EAnimation.HAND_ATTACK, 2, new AnimationConfigItem(GokuActions.class.getMethod("handAttack", GameObject.class), true));
        animatorController.addRepercussion(EAnimation.HAND_ATTACK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 50, new Pair<>(GameConfig.speedTravel * 1.5f, 0f), true));
        // HAND_FLY_PROPELS
        animatorController.addAnimation(EAnimation.HAND_FLY_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuHandFlyPropels.png", 247, 247), EAnimation.HAND_FLY_PROPELS.isLoop(), 0, 5, 0, 1, new int[]{120, 90, 70, 90, 120}));
        animatorController.addCollision(EAnimation.HAND_FLY_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuHandFlyPropels.json")));
        animatorController.addConfig(EAnimation.HAND_FLY_PROPELS, 0, new AnimationConfigItem(GokuActions.class.getMethod("handFlyPropels", GameObject.class), true, EAnimation.FALL));
        animatorController.addRepercussion(EAnimation.HAND_FLY_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_FLIP, 150, new Pair<>(0f, 0f)));
        animatorController.addEffect(EAnimation.HAND_FLY_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_HAND_FLY_PROPELS));
        // JUMP_KICK
        animatorController.addAnimation(EAnimation.JUMP_KICK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuJumpKick1.png", 247, 247), EAnimation.JUMP_KICK.isLoop(), 0, 4, 0, 1, new int[]{120, 70, 80, 70}));
        animatorController.addCollision(EAnimation.JUMP_KICK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuJumpKick1.json")));
        animatorController.addConfig(EAnimation.JUMP_KICK, 0, new AnimationConfigItem(GokuActions.class.getMethod("jumpKickAttack", GameObject.class), false, EAnimation.JUMP_KICK, 2));
        animatorController.addRepercussion(EAnimation.JUMP_KICK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 80, new Pair<>(GameConfig.speedTravel * 2, 0f)));
        animatorController.addAnimation(EAnimation.JUMP_KICK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuJumpKick2.png", 247, 247), EAnimation.JUMP_KICK.isLoop(), 0, 5, 0, 1, new int[]{60, 60, 60, 60, 60}));
        animatorController.addCollision(EAnimation.JUMP_KICK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuJumpKick2.json")));
        animatorController.addConfig(EAnimation.JUMP_KICK, 1, new AnimationConfigItem(GokuActions.class.getMethod("jumpKickAttack", GameObject.class), false, EAnimation.JUMP_KICK, 2));
        animatorController.addRepercussion(EAnimation.JUMP_KICK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 80, new Pair<>(GameConfig.speedTravel * 2, 0f)));
        animatorController.addAnimation(EAnimation.JUMP_KICK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuJumpKick3.png", 247, 247), EAnimation.JUMP_KICK.isLoop(), 0, 4, 0, 1, new int[]{80, 130, 130, 80}));
        animatorController.addCollision(EAnimation.JUMP_KICK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuJumpKick3.json")));
        animatorController.addConfig(EAnimation.JUMP_KICK, 2, new AnimationConfigItem(EAnimation.IDLE, 0));
        animatorController.addRepercussion(EAnimation.JUMP_KICK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 60, new Pair<>(GameConfig.speedTravel * 2, 0f)));
        // SPIRAL_KICK
        animatorController.addAnimation(EAnimation.SPIRAL_KICK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuSpiralKick1.png", 247, 247), EAnimation.SPIRAL_KICK.isLoop(), 0, 7, 0, 1, new int[]{150, 100, 100, 50, 50, 50, 50}));
        animatorController.addCollision(EAnimation.SPIRAL_KICK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuSpiralKick1.json")));
        animatorController.addConfig(EAnimation.SPIRAL_KICK, 0, new AnimationConfigItem(GokuActions.class.getMethod("spiralKickAttack", GameObject.class), false, EAnimation.SPIRAL_KICK, 1));
        animatorController.addRepercussion(EAnimation.SPIRAL_KICK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 200, new Pair<>(GameConfig.speedTravel * 3, 0f), true));
        animatorController.addAnimation(EAnimation.SPIRAL_KICK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuSpiralKick2.png", 247, 247), EAnimation.SPIRAL_KICK.isLoop(), 0, 4, 0, 1, new int[]{50, 50, 50, 50}));
        animatorController.addCollision(EAnimation.SPIRAL_KICK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuSpiralKick2.json")));
        animatorController.addConfig(EAnimation.SPIRAL_KICK, 1, new AnimationConfigItem(GokuActions.class.getMethod("spiralKickAttack", GameObject.class), false, EAnimation.SPIRAL_KICK, 2));
        animatorController.addRepercussion(EAnimation.SPIRAL_KICK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 200, new Pair<>(GameConfig.speedTravel * 3, 0f), true));
        animatorController.addAnimation(EAnimation.SPIRAL_KICK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuSpiralKick3.png", 247, 247), EAnimation.SPIRAL_KICK.isLoop(), 0, 2, 0, 1, new int[]{50, 100}));
        animatorController.addCollision(EAnimation.SPIRAL_KICK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuSpiralKick3.json")));
        animatorController.addConfig(EAnimation.SPIRAL_KICK, 2, new AnimationConfigItem(EAnimation.IDLE, 0));
        animatorController.addRepercussion(EAnimation.SPIRAL_KICK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 200, new Pair<>(GameConfig.speedTravel * 3, 0f), true));
        // KICK_PROPELS
        animatorController.addAnimation(EAnimation.KICK_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKickPropels1.png", 247, 247), EAnimation.KICK_PROPELS.isLoop(), 0, 7, 0, 1, new int[]{150, 100, 100, 50, 50, 50, 50}));
        animatorController.addCollision(EAnimation.KICK_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKickPropels1.json")));
        animatorController.addConfig(EAnimation.KICK_PROPELS, 0, new AnimationConfigItem(GokuActions.class.getMethod("kickPropelsAttack", GameObject.class), false));
        animatorController.addRepercussion(EAnimation.KICK_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_FLIP, 200, new Pair<>(0f, -GameConfig.speedTravel)));
        animatorController.addEffect(EAnimation.KICK_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_KICK_PROPELS));
        // KI_SIMPLE_PROPELS
        animatorController.addAnimation(EAnimation.KI_SIMPLE_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiSimplePropels.png", 247, 247), EAnimation.KI_SIMPLE_PROPELS.isLoop(), 0, 10, 0, 1, new int[]{80, 60, 50, 50, 30, 50, 80, 80, 700, 50}));
        animatorController.addCollision(EAnimation.KI_SIMPLE_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiSimplePropels.json")));
        animatorController.addConfig(EAnimation.KI_SIMPLE_PROPELS, 0, new AnimationConfigItem(EAnimation.IDLE, 0));
        animatorController.addRepercussion(EAnimation.KI_SIMPLE_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 250, new Pair<>(GameConfig.speedTravel * 2f, -0.5f)));
        animatorController.addEffect(EAnimation.KI_SIMPLE_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_HAND_FLY_PROPELS));
        // KI_FINAL_ATTACK
        animatorController.addAnimation(EAnimation.KI_FINAL_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuGenkidama.png", 247, 247), EAnimation.KI_FINAL_ATTACK.isLoop(), 0, 10, 0, 1, new int[]{550, 370, 370, 370, 120, 120, 120, 5000, 300, 250}));
        animatorController.addCollision(EAnimation.KI_FINAL_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuGenkidama.json")));
        animatorController.addConfig(EAnimation.KI_FINAL_ATTACK, 0, new AnimationConfigItem(GokuActions.class.getMethod("kiFinalAttack", GameObject.class), false));
        animatorController.addEffect(EAnimation.KI_FINAL_ATTACK, 0, 1, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/genkidama" + "/genkidama1.png", 260, 260), true, 0, 2, 0, 1, new int[]{80, 80}), -90, -50, false));
        animatorController.addEffect(EAnimation.KI_FINAL_ATTACK, 0, 2, new ClearEffects());
        animatorController.addEffect(EAnimation.KI_FINAL_ATTACK, 0, 2, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/genkidama" + "/genkidama2.png", 260, 260), true, 0, 2, 0, 1, new int[]{80, 80}), -90, -80, false));
        animatorController.addEffect(EAnimation.KI_FINAL_ATTACK, 0, 3, new ClearEffects());
        animatorController.addEffect(EAnimation.KI_FINAL_ATTACK, 0, 3, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/genkidama" + "/genkidama3.png", 260, 260), true, 0, 2, 0, 1, new int[]{80, 80}), -90, -100, false));
        animatorController.addEffect(EAnimation.KI_FINAL_ATTACK, 0, 5, new ClearEffects());
        // KI_SPE_ATTACK
        animatorController.addAnimation(EAnimation.KI_SPE_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKamehameha1.png", 247, 247), EAnimation.KI_SPE_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{300, 350, 400, 700}));
        animatorController.addCollision(EAnimation.KI_SPE_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKamehameha1.json")));
        animatorController.addConfig(EAnimation.KI_SPE_ATTACK, 0, new AnimationConfigItem(EAnimation.KI_SPE_ATTACK, 1));
        animatorController.addEffect(EAnimation.KI_SPE_ATTACK, 0, 0, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_expr.png", 85, 70), false, 0, 2, 0, 1, new int[]{1000, 1000}), 60, 60, false));
        animatorController.addEffect(EAnimation.KI_SPE_ATTACK, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_KAMEHAMEHA_AIM));
        animatorController.addAnimation(EAnimation.KI_SPE_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKamehameha2.png", 247, 247), EAnimation.KI_SPE_ATTACK.isLoop(), 0, 6, 0, 1, new int[]{220, 80, 5000, 300, 100, 100}));
        animatorController.addCollision(EAnimation.KI_SPE_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKamehameha2.json")));
        animatorController.addConfig(EAnimation.KI_SPE_ATTACK, 1, new AnimationConfigItem(GokuActions.class.getMethod("kiSpeAttack", GameObject.class), false));
        animatorController.addEffect(EAnimation.KI_SPE_ATTACK, 1, 1, EffectFactory.createSoundEffect(ESound.GOKU_KAMEHAMEHA_FIRE));
        animatorController.addEffect(EAnimation.KI_SPE_ATTACK, 1, 1, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_expr.png", 85, 70), false, 2, 3, 0, 1, new int[]{800}), 60, 60, false));
        // KI_BASIC_ATTACK
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiBlast1.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 3, 0, 1, new int[]{150, 100, 100}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiBlast1.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 0, new AnimationConfigItem(EAnimation.KI_BASIC_ATTACK, 1));
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiBlast2.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 5, 0, 1, new int[]{100, 100, 40, 40, 40}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiBlast2.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 1, new AnimationConfigItem(GokuActions.class.getMethod("kiBasicAttack", GameObject.class), false, EAnimation.KI_BASIC_ATTACK, 3));
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiBlast3.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 6, 0, 1, new int[]{100, 100, 40, 40, 40, 40}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiBlast3.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 2, new AnimationConfigItem(GokuActions.class.getMethod("kiBasicAttack", GameObject.class), false, EAnimation.KI_BASIC_ATTACK, 3));
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 3, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiBlast4.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 80}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 3, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiBlast4.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 3, new AnimationConfigItem(EAnimation.IDLE, 0));
        // KI_CHARGE
        animatorController.addAnimation(EAnimation.KI_CHARGE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiCharge1.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 2, 0, 1, new int[]{200, 150}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiCharge1.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 0, new AnimationConfigItem(EAnimation.IDLE, 0));
        animatorController.addAnimation(EAnimation.KI_CHARGE, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiCharge2.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 4, 0, 1, new int[]{100, 100, 100, 100}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiCharge2.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 1, new AnimationConfigItem(GokuActions.class.getMethod("kiChargeAction", GameObject.class), false, EAnimation.KI_CHARGE, 2));
        animatorController.addEffect(EAnimation.KI_CHARGE, 1, 0, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKi.png", 247, 247), false, 0, 4, 0, 1, new int[]{100, 100, 100, 100}), -47, 0, false));
        animatorController.addAnimation(EAnimation.KI_CHARGE, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiCharge3.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 1, 0, 1, new int[]{150}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiCharge3.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 2, new AnimationConfigItem(EAnimation.IDLE, 0));
        // TOUCHED_SIMPLE
        animatorController.addAnimation(EAnimation.TOUCHED_SIMPLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedSimple.png", 247, 247), EAnimation.TOUCHED_SIMPLE.isLoop(), 0, 1, 0, 1, new int[]{300}));
        animatorController.addCollision(EAnimation.TOUCHED_SIMPLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedSimple.json")));
        animatorController.addConfig(EAnimation.TOUCHED_SIMPLE, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedSimple", GameObject.class), true));
        animatorController.addEffect(EAnimation.TOUCHED_SIMPLE, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_SIMPLE));
        // TOUCHED_MEDIUM
        animatorController.addAnimation(EAnimation.TOUCHED_MEDIUM, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedMedium.png", 247, 247), EAnimation.TOUCHED_MEDIUM.isLoop(), 0, 1, 0, 1, new int[]{400}));
        animatorController.addCollision(EAnimation.TOUCHED_MEDIUM, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedMedium.json")));
        animatorController.addConfig(EAnimation.TOUCHED_MEDIUM, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedMedium", GameObject.class), false));
        animatorController.addEffect(EAnimation.TOUCHED_MEDIUM, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_MEDIUM));
        // TOUCHED_PROPELS
        animatorController.addAnimation(EAnimation.TOUCHED_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedPropels.png", 247, 247), EAnimation.TOUCHED_PROPELS.isLoop(), 0, 2, 0, 1, new int[]{300, 300}));
        animatorController.addCollision(EAnimation.TOUCHED_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedPropels.json")));
        animatorController.addConfig(EAnimation.TOUCHED_PROPELS, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedPropels", GameObject.class), false));
        animatorController.addEffect(EAnimation.TOUCHED_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_PROPELS));
        // TOUCHED_FLIP
        animatorController.addAnimation(EAnimation.TOUCHED_FLIP, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedFlip.png", 247, 247), EAnimation.TOUCHED_FLIP.isLoop(), 0, 4, 0, 1, new int[]{150, 150, 150, 150}));
        animatorController.addCollision(EAnimation.TOUCHED_FLIP, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedFlip.json")));
        animatorController.addConfig(EAnimation.TOUCHED_FLIP, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedFlip", GameObject.class), true, EAnimation.TOUCHED_FALL));
        // TOUCHED_PROJECTED
        animatorController.addAnimation(EAnimation.TOUCHED_PROJECTED, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedProjected.png", 247, 247), EAnimation.TOUCHED_PROJECTED.isLoop(), 0, 1, 0, 1, new int[]{500}));
        animatorController.addCollision(EAnimation.TOUCHED_PROJECTED, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedProjected.json")));
        animatorController.addConfig(EAnimation.TOUCHED_PROJECTED, 0, new AnimationConfigItem(VegetaActions.class.getMethod("touchedProjected", GameObject.class), true, EAnimation.TOUCHED_FALL));
        //TOUCHED_FALL
        animatorController.addAnimation(EAnimation.TOUCHED_FALL, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{10000}));
        animatorController.addCollision(EAnimation.TOUCHED_FALL, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedFall.json")));
        animatorController.addConfig(EAnimation.TOUCHED_FALL, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedFall", GameObject.class), true, EAnimation.TOUCHED_RECEIPT));
        // TOUCHED_RECEIPT
        animatorController.addAnimation(EAnimation.TOUCHED_RECEIPT, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedReceipt.png", 247, 247), EAnimation.TOUCHED_RECEIPT.isLoop(), 0, 6, 0, 1, new int[]{150, 200, 150, 150, 150, 150}));
        animatorController.addCollision(EAnimation.TOUCHED_RECEIPT, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedReceipt.json")));
        animatorController.addConfig(EAnimation.TOUCHED_RECEIPT, 0, new AnimationConfigItem(GokuActions.class.getMethod("touchedReceipt", GameObject.class), true));
        return animatorController;
    }

    public AnimatorController getVegetaAnimator() throws SlickException, JSONException, NoSuchMethodException {
        AnimatorController animatorController = new AnimatorController();
        String id = "/vegeta/basic";

        //IDLE
        animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaIdle.png", 247, 247), EAnimation.IDLE.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
        animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaIdle.json")));
        animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(VegetaActions.class.getMethod("idle", GameObject.class), true));
        // EXPLODE
        Animation logoKO = AnimationFactory.loadAnimation(ConfigPath.image_gui + "/logo/logoKO/logoKO ", ".png", 1, 19, false, 50);
        logoKO.addFrame(new Image(ConfigPath.image_gui + "/logo/logoKO/logoKO 19.png"), 1500);
        animatorController.addAnimation(EAnimation.EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedProjected.png", 247, 247), EAnimation.TOUCHED_PROJECTED.isLoop(), 0, 1, 0, 1, new int[]{3000}));
        animatorController.addCollision(EAnimation.EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedProjected.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 0, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.NULL));
        animatorController.addEffect(EAnimation.EXPLODE, 0, 0, EffectFactory.createAnimationEffect(logoKO, -80, 80, false));
        animatorController.addEffect(EAnimation.EXPLODE, 0, 0, EffectFactory.createSoundEffect(ESound.EFFECT_KO));
        animatorController.addAnimation(EAnimation.EXPLODE, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{500}));
        animatorController.addCollision(EAnimation.EXPLODE, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedFall.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 1, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.EXPLODE, 2));
        animatorController.addAnimation(EAnimation.EXPLODE, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{3000}));
        animatorController.addCollision(EAnimation.EXPLODE, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedFall.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 2, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.NULL));
        // FALL
        animatorController.addAnimation(EAnimation.FALL, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaFall.png", 247, 247), EAnimation.FALL.isLoop(), 0, 1, 0, 1, new int[]{200}));
        animatorController.addCollision(EAnimation.FALL, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaFall.json")));
        animatorController.addConfig(EAnimation.FALL, 0, new AnimationConfigItem(VegetaActions.class.getMethod("fall", GameObject.class), true, EAnimation.RECEIPT));
        // RUN
        animatorController.addAnimation(EAnimation.RUN, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaRun.png", 247, 247), EAnimation.RUN.isLoop(), 0, 3, 0, 1, new int[]{200, 200, 200}));
        animatorController.addCollision(EAnimation.RUN, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaRun.json")));
        animatorController.addConfig(EAnimation.RUN, 0, new AnimationConfigItem(VegetaActions.class.getMethod("run", GameObject.class), true));
        // RUSH
        animatorController.addAnimation(EAnimation.RUSH, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaRush.png", 247, 247), EAnimation.RUSH.isLoop(), 0, 2, 0, 1, new int[]{40, 40}));
        animatorController.addCollision(EAnimation.RUSH, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaRush.json")));
        animatorController.addConfig(EAnimation.RUSH, 0, new AnimationConfigItem(VegetaActions.class.getMethod("rush", GameObject.class), true));
        // ATTACK_RUSH
        animatorController.addAnimation(EAnimation.RUSH_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaRushAttack.png", 247, 247), EAnimation.RUSH_ATTACK.isLoop(), 0, 6, 0, 1, new int[]{180, 180, 280, 120, 120, 120}));
        animatorController.addCollision(EAnimation.RUSH_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaRushAttack.json")));
        animatorController.addConfig(EAnimation.RUSH_ATTACK, 0, new AnimationConfigItem(VegetaActions.class.getMethod("rushAttack", GameObject.class), true));
        animatorController.addRepercussion(EAnimation.RUSH_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROPELS, 200, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        // RECEIPT
        animatorController.addAnimation(EAnimation.RECEIPT, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 1, 0, 1, new int[]{130}));
        animatorController.addCollision(EAnimation.RECEIPT, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaReceipt.json")));
        animatorController.addConfig(EAnimation.RECEIPT, 0, new AnimationConfigItem(VegetaActions.class.getMethod("receipt", GameObject.class), true));
        animatorController.addAnimation(EAnimation.RECEIPT, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 100}));
        animatorController.addCollision(EAnimation.RECEIPT, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaReceipt.json")));
        animatorController.addConfig(EAnimation.RECEIPT, 1, new AnimationConfigItem(VegetaActions.class.getMethod("receipt", GameObject.class), true));
        // JUMP
        animatorController.addAnimation(EAnimation.JUMP, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaJump1.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 2, 0, 1, new int[]{150, 300}));
        animatorController.addCollision(EAnimation.JUMP, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaJump1.json")));
        animatorController.addConfig(EAnimation.JUMP, 0, new AnimationConfigItem(VegetaActions.class.getMethod("jump", GameObject.class), true, 1, EAnimation.FALL, 0));
        animatorController.addAnimation(EAnimation.JUMP, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaJump2.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 2, 0, 1, new int[]{100, 300}));
        animatorController.addCollision(EAnimation.JUMP, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaJump2.json")));
        animatorController.addConfig(EAnimation.JUMP, 1, new AnimationConfigItem(VegetaActions.class.getMethod("jump", GameObject.class), true, 1, EAnimation.FALL, 0));
        // DEFENSE
        animatorController.addAnimation(EAnimation.DEFENSE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaDef.png", 247, 247), EAnimation.DEFENSE.isLoop(), 0, 4, 0, 1, new int[]{200, 300, 300, 300}));
        animatorController.addCollision(EAnimation.DEFENSE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaDef.json")));
        animatorController.addConfig(EAnimation.DEFENSE, 0, new AnimationConfigItem(VegetaActions.class.getMethod("defense", GameObject.class), true));
        // BLOCK
        animatorController.addAnimation(EAnimation.BLOCK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaBlock.png", 247, 247), EAnimation.BLOCK.isLoop(), 0, 1, 0, 1, new int[]{300}));
        animatorController.addCollision(EAnimation.BLOCK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaBlock.json")));
        animatorController.addConfig(EAnimation.BLOCK, 0, new AnimationConfigItem(VegetaActions.class.getMethod("block", GameObject.class), true));
        animatorController.addEffect(EAnimation.BLOCK, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_BLOCK));
        //TRANSPOSITION
        animatorController.addAnimation(EAnimation.TRANSPOSITION, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTransposition.png", 247, 247), EAnimation.TRANSPOSITION.isLoop(), 0, 6, 0, 1, new int[]{200, 100, 100, 100, 200, 200}));
        animatorController.addCollision(EAnimation.TRANSPOSITION, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTransposition.json")));
        animatorController.addConfig(EAnimation.TRANSPOSITION, 0, new AnimationConfigItem(GokuActions.class.getMethod("transposition", GameObject.class), false));
        // HAND_ATTACK
        animatorController.addAnimation(EAnimation.HAND_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandAttack.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{80, 100, 80, 100}));
        animatorController.addCollision(EAnimation.HAND_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandAttack.json")));
        animatorController.addConfig(EAnimation.HAND_ATTACK, 0, new AnimationConfigItem(VegetaActions.class.getMethod("handAttack", GameObject.class), true));
        animatorController.addRepercussion(EAnimation.HAND_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 40, new Pair<>(GameConfig.speedTravel * 1.5f, 0f), true));
        // HAND_FLY_PROPELS
        animatorController.addAnimation(EAnimation.HAND_FLY_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandPropels1.png", 247, 247), EAnimation.HAND_FLY_PROPELS.isLoop(), 0, 5, 0, 1, new int[]{120, 100, 80, 120, 130}));
        animatorController.addCollision(EAnimation.HAND_FLY_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandPropels1.json")));
        animatorController.addConfig(EAnimation.HAND_FLY_PROPELS, 0, new AnimationConfigItem(VegetaActions.class.getMethod("handFlyPropels", GameObject.class), false, EAnimation.FALL));
        animatorController.addRepercussion(EAnimation.HAND_FLY_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_FLIP, 150, new Pair<>(0f, 0f)));
        animatorController.addEffect(EAnimation.HAND_FLY_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_HAND_FLY_PROPELS));
        // MOVE_HAND_ATTACK
        animatorController.addAnimation(EAnimation.MOVE_HAND_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaMoveHandAttack1.png", 247, 247), EAnimation.MOVE_HAND_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{120, 120, 180, 180}));
        animatorController.addCollision(EAnimation.MOVE_HAND_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaMoveHandAttack1.json")));
        animatorController.addConfig(EAnimation.MOVE_HAND_ATTACK, 0, new AnimationConfigItem(VegetaActions.class.getMethod("moveHandAttack", GameObject.class), true, EAnimation.MOVE_HAND_ATTACK, 1));
        animatorController.addRepercussion(EAnimation.MOVE_HAND_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 200, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        animatorController.addAnimation(EAnimation.MOVE_HAND_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaMoveHandAttack2.png", 247, 247), EAnimation.MOVE_HAND_ATTACK.isLoop(), 0, 3, 0, 1, new int[]{120, 80, 80}));
        animatorController.addCollision(EAnimation.MOVE_HAND_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaMoveHandAttack2.json")));
        animatorController.addConfig(EAnimation.MOVE_HAND_ATTACK, 1, new AnimationConfigItem(EAnimation.IDLE, 0));
        // JUMP_HAND_ATTACK
        animatorController.addAnimation(EAnimation.JUMP_HAND_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandFly1.png", 247, 247), EAnimation.JUMP_HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{80, 120}));
        animatorController.addCollision(EAnimation.JUMP_HAND_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandFly1.json")));
        animatorController.addConfig(EAnimation.JUMP_HAND_ATTACK, 0, new AnimationConfigItem(VegetaActions.class.getMethod("jumpHandAttack", GameObject.class), false, EAnimation.JUMP_HAND_ATTACK, 1));
        animatorController.addAnimation(EAnimation.JUMP_HAND_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandFly2.png", 247, 247), EAnimation.JUMP_HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{220, 120}));
        animatorController.addCollision(EAnimation.JUMP_HAND_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandFly2.json")));
        animatorController.addConfig(EAnimation.JUMP_HAND_ATTACK, 1, new AnimationConfigItem(VegetaActions.class.getMethod("jumpHandAttack", GameObject.class), false));
        animatorController.addRepercussion(EAnimation.JUMP_HAND_ATTACK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 250, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        animatorController.addAnimation(EAnimation.JUMP_HAND_ATTACK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandFly3.png", 247, 247), EAnimation.JUMP_HAND_ATTACK.isLoop(), 0, 3, 0, 1, new int[]{130, 220, 130}));
        animatorController.addCollision(EAnimation.JUMP_HAND_ATTACK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandFly3.json")));
        animatorController.addConfig(EAnimation.JUMP_HAND_ATTACK, 2, new AnimationConfigItem(VegetaActions.class.getMethod("jumpHandAttack", GameObject.class), false, EAnimation.JUMP_HAND_ATTACK, 3));
        animatorController.addRepercussion(EAnimation.JUMP_HAND_ATTACK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 250, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        animatorController.addAnimation(EAnimation.JUMP_HAND_ATTACK, 3, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandFly4.png", 247, 247), EAnimation.JUMP_HAND_ATTACK.isLoop(), 0, 1, 0, 1, new int[]{200}));
        animatorController.addCollision(EAnimation.JUMP_HAND_ATTACK, 3, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandFly4.json")));
        animatorController.addConfig(EAnimation.JUMP_HAND_ATTACK, 3, new AnimationConfigItem(VegetaActions.class.getMethod("jumpHandAttack", GameObject.class), false, EAnimation.FALL, 0));
        // KNEES_PROPELS
        animatorController.addAnimation(EAnimation.KNEES_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKneesAttack.png", 247, 247), EAnimation.KNEES_ATTACK.isLoop(), 0, 5, 0, 1, new int[]{60, 120, 180, 120, 80}));
        animatorController.addCollision(EAnimation.KNEES_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKneesAttack.json")));
        animatorController.addConfig(EAnimation.KNEES_ATTACK, 0, new AnimationConfigItem(VegetaActions.class.getMethod("kneesAttack", GameObject.class), false, EAnimation.FALL));
        animatorController.addRepercussion(EAnimation.KNEES_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 250, new Pair<>(GameConfig.speedTravel, -GameConfig.speedTravel)));
        // KICK_PROPELS
        animatorController.addAnimation(EAnimation.KICK_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKickPropels.png", 247, 247), EAnimation.KICK_PROPELS.isLoop(), 0, 5, 0, 1, new int[]{180, 120, 120, 80, 80}));
        animatorController.addCollision(EAnimation.KICK_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKickPropels.json")));
        animatorController.addConfig(EAnimation.KICK_PROPELS, 0, new AnimationConfigItem(VegetaActions.class.getMethod("kickPropelsAttack", GameObject.class), false, EAnimation.IDLE));
        animatorController.addRepercussion(EAnimation.KICK_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_FLIP, 200, new Pair<>(GameConfig.speedTravel / 2f, -GameConfig.speedTravel)));
        animatorController.addEffect(EAnimation.KICK_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_KICK_PROPELS));
        //KICK_ATTACK
        animatorController.addAnimation(EAnimation.KICK_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKickAttack1.png", 247, 247), EAnimation.KICK_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 100}));
        animatorController.addCollision(EAnimation.KICK_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKickAttack1.json")));
        animatorController.addConfig(EAnimation.KICK_ATTACK, 0, new AnimationConfigItem(EAnimation.KICK_ATTACK, 1));
        animatorController.addAnimation(EAnimation.KICK_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKickAttack2.png", 247, 247), EAnimation.KICK_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{100, 60, 120, 60}));
        animatorController.addCollision(EAnimation.KICK_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKickAttack2.json")));
        animatorController.addConfig(EAnimation.KICK_ATTACK, 1, new AnimationConfigItem(VegetaActions.class.getMethod("kickAttack", GameObject.class), false));
        animatorController.addRepercussion(EAnimation.KICK_ATTACK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 100, new Pair<>(GameConfig.speedTravel, 0f)));
        // SPIRAL_KICK
        animatorController.addAnimation(EAnimation.SPIRAL_KICK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKickFlip.png", 247, 247), EAnimation.SPIRAL_KICK.isLoop(), 0, 5, 0, 1, new int[]{130, 130, 130, 130, 100}));
        animatorController.addCollision(EAnimation.SPIRAL_KICK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKickFlip.json")));
        animatorController.addConfig(EAnimation.SPIRAL_KICK, 0, new AnimationConfigItem(VegetaActions.class.getMethod("spiralKick", GameObject.class), false));
        animatorController.addRepercussion(EAnimation.SPIRAL_KICK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 400, new Pair<>(GameConfig.speedTravel, 0f)));
        // KI_FINAL_ATTACK
        animatorController.addAnimation(EAnimation.KI_FINAL_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaFinalFlash.png", 247, 247), EAnimation.KI_FINAL_ATTACK.isLoop(), 0, 11, 0, 1, new int[]{150, 300, 150, 100, 150, 100, 100, 5000, 400, 250, 200}));
        animatorController.addCollision(EAnimation.KI_FINAL_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaFinalFlash.json")));
        animatorController.addConfig(EAnimation.KI_FINAL_ATTACK, 0, new AnimationConfigItem(VegetaActions.class.getMethod("kiFinalAttack", GameObject.class), false));
        animatorController.addEffect(EAnimation.KI_FINAL_ATTACK, 0, 0, EffectFactory.createSoundEffect(ESound.VEGETA_FINAL_FLASH));
        animatorController.addEffect(EAnimation.KI_FINAL_ATTACK, 0, 6, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/finalFlash" + "/finalFlashBackEffect.png", 247, 247), false, 0, 2, 0, 1, new int[]{150, 5000}), -24, 0, false));
        animatorController.addEffect(EAnimation.KI_FINAL_ATTACK, 0, 8, new ClearEffects());
        animatorController.addEffect(EAnimation.KI_FINAL_ATTACK, 0, 8, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/finalFlash" + "/finalFlashBackEffect.png", 247, 247), false, 2, 3, 0, 1, new int[]{350}), -24, 0, false));
        // KI_SPE_ATTACK
        animatorController.addAnimation(EAnimation.KI_SPE_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiBurst.png", 247, 247), EAnimation.KI_SPE_ATTACK.isLoop(), 0, 6, 0, 1, new int[]{150, 150, 150, 200, 300, 150}));
        animatorController.addCollision(EAnimation.KI_SPE_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiBurst.json")));
        animatorController.addConfig(EAnimation.KI_SPE_ATTACK, 0, new AnimationConfigItem(VegetaActions.class.getMethod("kiSpeAttack", GameObject.class), false));
        // KI_BASIC_ATTACK
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiBlast1.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 100}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiBlast1.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 0, new AnimationConfigItem(EAnimation.KI_BASIC_ATTACK, 1));
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiBlast2.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{80, 150, 80, 150}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiBlast2.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 1, new AnimationConfigItem(VegetaActions.class.getMethod("kiBasicAttack", GameObject.class), false));
        // KI_EXPLODE
        animatorController.addAnimation(EAnimation.KI_EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiExplode.png", 247, 247), EAnimation.KI_EXPLODE.isLoop(), 0, 6, 0, 1, new int[]{100, 100, 150, 150, 300, 150}));
        animatorController.addCollision(EAnimation.KI_EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiExplode.json")));
        animatorController.addConfig(EAnimation.KI_EXPLODE, 0, new AnimationConfigItem(VegetaActions.class.getMethod("kiExplode", GameObject.class), false));
        // KI_CHARGE
        animatorController.addAnimation(EAnimation.KI_CHARGE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiCharge1.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 2, 0, 1, new int[]{200, 150}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiCharge1.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 0, new AnimationConfigItem(EAnimation.IDLE, 0));
        animatorController.addAnimation(EAnimation.KI_CHARGE, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiCharge2.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 4, 0, 1, new int[]{100, 100, 100, 100}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiCharge2.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 1, new AnimationConfigItem(VegetaActions.class.getMethod("kiChargeAction", GameObject.class), false, EAnimation.KI_CHARGE, 2));
        animatorController.addEffect(EAnimation.KI_CHARGE, 1, 0, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKi.png", 247, 247), false, 0, 4, 0, 1, new int[]{100, 100, 100, 100}), -60, 0, false));
        animatorController.addAnimation(EAnimation.KI_CHARGE, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiCharge3.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 1, 0, 1, new int[]{150}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiCharge3.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 2, new AnimationConfigItem(EAnimation.IDLE, 0));
        // TOUCHED_SIMPLE
        animatorController.addAnimation(EAnimation.TOUCHED_SIMPLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedSimple.png", 247, 247), EAnimation.TOUCHED_SIMPLE.isLoop(), 0, 1, 0, 1, new int[]{300}));
        animatorController.addCollision(EAnimation.TOUCHED_SIMPLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedSimple.json")));
        animatorController.addConfig(EAnimation.TOUCHED_SIMPLE, 0, new AnimationConfigItem(VegetaActions.class.getMethod("touchedSimple", GameObject.class), true));
        animatorController.addEffect(EAnimation.TOUCHED_SIMPLE, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_SIMPLE));
        // TOUCHED_MEDIUM
        animatorController.addAnimation(EAnimation.TOUCHED_MEDIUM, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedMedium.png", 247, 247), EAnimation.TOUCHED_MEDIUM.isLoop(), 0, 1, 0, 1, new int[]{400}));
        animatorController.addCollision(EAnimation.TOUCHED_MEDIUM, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedMedium.json")));
        animatorController.addConfig(EAnimation.TOUCHED_MEDIUM, 0, new AnimationConfigItem(VegetaActions.class.getMethod("touchedMedium", GameObject.class), true));
        animatorController.addEffect(EAnimation.TOUCHED_MEDIUM, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_MEDIUM));
        // TOUCHED_PROPELS
        animatorController.addAnimation(EAnimation.TOUCHED_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedPropels.png", 247, 247), EAnimation.TOUCHED_PROPELS.isLoop(), 0, 2, 0, 1, new int[]{300, 300}));
        animatorController.addCollision(EAnimation.TOUCHED_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedPropels.json")));
        animatorController.addConfig(EAnimation.TOUCHED_PROPELS, 0, new AnimationConfigItem(VegetaActions.class.getMethod("touchedPropels", GameObject.class), true));
        animatorController.addEffect(EAnimation.TOUCHED_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_PROPELS));
        // TOUCHED_FLIP
        animatorController.addAnimation(EAnimation.TOUCHED_FLIP, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedFlip.png", 247, 247), EAnimation.TOUCHED_FLIP.isLoop(), 0, 4, 0, 1, new int[]{150, 150, 150, 150}));
        animatorController.addCollision(EAnimation.TOUCHED_FLIP, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedFlip.json")));
        animatorController.addConfig(EAnimation.TOUCHED_FLIP, 0, new AnimationConfigItem(VegetaActions.class.getMethod("touchedFlip", GameObject.class), true, EAnimation.TOUCHED_FALL));
        // TOUCHED_PROJECTED
        animatorController.addAnimation(EAnimation.TOUCHED_PROJECTED, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedProjected.png", 247, 247), EAnimation.TOUCHED_PROJECTED.isLoop(), 0, 1, 0, 1, new int[]{500}));
        animatorController.addCollision(EAnimation.TOUCHED_PROJECTED, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedProjected.json")));
        animatorController.addConfig(EAnimation.TOUCHED_PROJECTED, 0, new AnimationConfigItem(VegetaActions.class.getMethod("touchedProjected", GameObject.class), true, EAnimation.TOUCHED_FALL));
        //TOUCHED_FALL
        animatorController.addAnimation(EAnimation.TOUCHED_FALL, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{10000}));
        animatorController.addCollision(EAnimation.TOUCHED_FALL, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedFall.json")));
        animatorController.addConfig(EAnimation.TOUCHED_FALL, 0, new AnimationConfigItem(VegetaActions.class.getMethod("touchedFall", GameObject.class), true, EAnimation.TOUCHED_RECEIPT));
        // TOUCHED_RECEIPT
        animatorController.addAnimation(EAnimation.TOUCHED_RECEIPT, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedReceipt.png", 247, 247), EAnimation.TOUCHED_RECEIPT.isLoop(), 0, 6, 0, 1, new int[]{150, 200, 150, 150, 150, 150}));
        animatorController.addCollision(EAnimation.TOUCHED_RECEIPT, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedReceipt.json")));
        animatorController.addConfig(EAnimation.TOUCHED_RECEIPT, 0, new AnimationConfigItem(VegetaActions.class.getMethod("touchedReceipt", GameObject.class), true));
        return animatorController;
    }

    public AnimatorController getItemAnimator(ESprites index) throws SlickException, JSONException {
        AnimatorController animatorController = new AnimatorController();
        if (index == ESprites.GROUND) {
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/ground.json")));
        }
        if (index == ESprites.WALL) {
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/map/wall.json")));
        }
        return animatorController;
    }

    public AnimatorController getEntityAnimator(ESprites index) throws SlickException, JSONException, NoSuchMethodException {
        AnimatorController animatorController = new AnimatorController();

        if (index == ESprites.FINAL_FLASH) {
            animatorController.addAnimation(EAnimation.RUN, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/finalFlash" + "/finalFlash_head.png", 247, 247), true, 0, 1, 0, 1, new int[]{150}));
            animatorController.addCollision(EAnimation.RUN, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/finalFlash" + "/finalFlash_head.json")));
            animatorController.addRepercussion(EAnimation.RUN, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 1, new Pair<>(GameConfig.speedTravel, 0f)));
            animatorController.forceCurrentAnimationType(EAnimation.RUN);
        } else if (index == ESprites.FINAL_FLASH_Back) {
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/finalFlash" + "/finalFlash_back2.png", 247, 247), true, 0, 1, 0, 1, new int[]{200}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/finalFlash" + "/finalFlash_back2.json")));
            animatorController.addAnimation(EAnimation.EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/finalFlash" + "/finalFlash_back3.png", 247, 247), false, 0, 3, 0, 1, new int[]{100, 100, 100}));
            animatorController.addCollision(EAnimation.EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/finalFlash" + "/finalFlash_back3.json")));
        } else if (index == ESprites.FINAL_FLASH_Body) {
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/finalFlash" + "/finalFlash_body.png", 247, 247), true, 0, 1, 0, 1, new int[]{250, 250}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/finalFlash" + "/finalFlash_body.json")));
        } else if (index == ESprites.KAMEHA) {
/*
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_head0.png", 247, 247), false, 0, 2, 0, 1, new int[]{150, 150}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_head0.json")));
            animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(EAnimation.RUN, 0));
*/
            animatorController.addAnimation(EAnimation.RUN, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_head1.png", 247, 247), true, 0, 2, 0, 1, new int[]{150, 150}));
            animatorController.addCollision(EAnimation.RUN, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_head1.json")));
            animatorController.addRepercussion(EAnimation.RUN, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 1, new Pair<>(GameConfig.speedTravel, 0f)));
            animatorController.forceCurrentAnimationType(EAnimation.RUN);
        } else if (index == ESprites.KAMEHA_Back) {
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_back1.png", 247, 247), true, 0, 2, 0, 1, new int[]{200, 200}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_back1.json")));
            animatorController.addAnimation(EAnimation.EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_back2.png", 247, 247), false, 0, 4, 0, 1, new int[]{100, 100, 100, 100}));
            animatorController.addCollision(EAnimation.EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_back2.json")));
        } else if (index == ESprites.KAMEHA_Body) {
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_body1.png", 247, 247), true, 0, 2, 0, 1, new int[]{250, 250}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_body1.json")));
            animatorController.addAnimation(EAnimation.EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_body2.png", 247, 247), false, 0, 4, 0, 1, new int[]{60, 60, 60, 60}));
            animatorController.addCollision(EAnimation.EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kameha" + "/kameha_body2.json")));
        } else if (index == ESprites.MEGA_KAMEHA) {
/*
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/megaKameha" + "/megaKameha_head0.png", 247, 247), false, 0, 1, 0, 1, new int[]{600}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/megaKameha" + "/kameha_head0.json")));
            animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(EAnimation.RUN, 0));
*/
            animatorController.addAnimation(EAnimation.RUN, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/megaKameha" + "/megaKameha_head1.png", 247, 247), true, 0, 1, 0, 1, new int[]{150}));
            animatorController.addCollision(EAnimation.RUN, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/megaKameha" + "/megaKameha_head1.json")));
            animatorController.addRepercussion(EAnimation.RUN, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 1, new Pair<>(GameConfig.speedTravel, 0f)));
            animatorController.forceCurrentAnimationType(EAnimation.RUN);
        } else if (index == ESprites.MEGA_KAMEHA_Back) {
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/megaKameha" + "/megaKameha_back1.png", 247, 247), true, 0, 1, 0, 1, new int[]{200}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/megaKameha" + "/megaKameha_back1.json")));
            animatorController.addAnimation(EAnimation.EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/megaKameha" + "/megaKameha_back2.png", 247, 247), false, 0, 4, 0, 1, new int[]{100, 100, 100, 100}));
            animatorController.addCollision(EAnimation.EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/megaKameha" + "/megaKameha_back2.json")));
        } else if (index == ESprites.MEGA_KAMEHA_Body) {
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/megaKameha" + "/megaKameha_body1.png", 247, 247), true, 0, 1, 0, 1, new int[]{250}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/megaKameha" + "/megaKameha_body1.json")));
            animatorController.addAnimation(EAnimation.EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/megaKameha" + "/megaKameha_body2.png", 247, 247), false, 0, 4, 0, 1, new int[]{60, 60, 60, 60}));
            animatorController.addCollision(EAnimation.EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/megaKameha" + "/megaKameha_body2.json")));
        } else if (index == ESprites.KI_BLAST) {
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kiBlast" + "/gokuKiBlast_head.png", 247, 247), true, 0, 2, 0, 1, new int[]{200, 200}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kiBlast" + "/gokuKiBlast_head.json")));
            animatorController.addRepercussion(EAnimation.IDLE, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 1, new Pair<>(GameConfig.speedTravel / 3f, 0f)));
        } else if (index == ESprites.VEGETA_KI_BLAST_BACK) {
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kiBlast" + "/vegetaKiBlast_back.png", 247, 247), false, 0, 3, 0, 1, new int[]{100, 80, 80}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kiBlast" + "/vegetaKiBlast_back.json")));
            animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(EAnimation.EXPLODE, 0));
        } else if (index == ESprites.VEGETA_KI_BLAST_HEAD) {
            animatorController.addAnimation(EAnimation.RUN, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kiBlast" + "/vegetaKiBlast_head.png", 247, 247), false, 0, 1, 0, 1, new int[]{100}));
            animatorController.addCollision(EAnimation.RUN, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kiBlast" + "/vegetaKiBlast_head.json")));
            animatorController.forceCurrentAnimationType(EAnimation.RUN);
        } else if (index == ESprites.KI_BURST) {
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kiBurst" + "/kiBurst.png", 247, 247), false, 0, 6, 0, 1, new int[]{100, 100, 200, 100, 70, 40}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kiBurst" + "/kiBurst.json")));
            animatorController.addRepercussion(EAnimation.IDLE, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 1, new Pair<>(GameConfig.speedTravel, -GameConfig.speedTravel / 3f)));
            animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(EAnimation.EXPLODE, 0));
        } else if (index == ESprites.KI_EXPLODE) {
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kiExplode" + "/kiExplode.png", 247, 247), false, 0, 4, 0, 1, new int[]{150, 150, 150, 150}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/kiExplode" + "/kiExplode.json")));
            animatorController.addRepercussion(EAnimation.IDLE, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 1, new Pair<>(GameConfig.speedTravel, -GameConfig.speedTravel)));
            animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(EAnimation.EXPLODE, 0));
        } else if (index == ESprites.GENKIDAMA) {
            animatorController.addAnimation(EAnimation.RUN, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/genkidama" + "/genkidama3.png", 260, 260), true, 0, 2, 0, 1, new int[]{100, 100}));
            animatorController.addCollision(EAnimation.RUN, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/genkidama" + "/genkidama3.json")));
            animatorController.addRepercussion(EAnimation.RUN, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 1, new Pair<>(GameConfig.speedTravel / 3f, -GameConfig.speedTravel / 3f)));
            animatorController.addAnimation(EAnimation.EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/genkidama" + "/genkidama4.png", 260, 260), false, 0, 4, 0, 1, new int[]{150, 120, 100, 80}));
            animatorController.addCollision(EAnimation.EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/genkidama" + "/genkidama4.json")));
            animatorController.addRepercussion(EAnimation.EXPLODE, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 1, new Pair<>(GameConfig.speedTravel, -GameConfig.speedTravel / 2f)));
            animatorController.addConfig(EAnimation.EXPLODE, 0, new AnimationConfigItem(GenkidamaActions.class.getMethod("stopOnExplode", GameObject.class), false, EAnimation.NULL));
            animatorController.forceCurrentAnimationType(EAnimation.RUN);
        } else if (index == ESprites.BIG_BANG) {
            animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/bigBang" + "/bigBang0.png", 247, 247), false, 0, 2, 0, 1, new int[]{150, 200}));
            animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/bigBang" + "/bigBang0.json")));
            animatorController.addRepercussion(EAnimation.IDLE, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 1, new Pair<>(GameConfig.speedTravel / 3f, 0f)));
            animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(EAnimation.RUN, 0));
            animatorController.addAnimation(EAnimation.RUN, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/bigBang" + "/bigBang1.png", 247, 247), true, 0, 1, 0, 1, new int[]{100}));
            animatorController.addCollision(EAnimation.RUN, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/bigBang" + "/bigBang1.json")));
            animatorController.addRepercussion(EAnimation.RUN, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 1, new Pair<>(GameConfig.speedTravel, -GameConfig.speedTravel / 3f)));
            animatorController.addAnimation(EAnimation.EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/bigBang" + "/bigBang2.png", 263, 263), false, 0, 7, 0, 1, new int[]{50, 50, 50, 60, 80, 100, 50, 80}));
            animatorController.addCollision(EAnimation.EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/entity/bigBang" + "/bigBang2.json")));
            animatorController.addRepercussion(EAnimation.EXPLODE, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 1, new Pair<>(GameConfig.speedTravel * 2f, -GameConfig.speedTravel)));
            animatorController.addConfig(EAnimation.EXPLODE, 0, new AnimationConfigItem(BigBangActions.class.getMethod("stopOnExplode", GameObject.class), false, EAnimation.NULL));
        }
        return animatorController;
    }

    public AnimatorController getGokuS1Animator() throws SlickException, JSONException, NoSuchMethodException {
        AnimatorController animatorController = new AnimatorController();
        String id = "/goku/S1";
        //IDLE
        animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuIdle.png", 247, 247), EAnimation.IDLE.isLoop(), 0, 4, 0, 1, new int[]{250, 250, 250, 250}));
        animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuIdle.json")));
        animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("idle", GameObject.class), true));
        // EXPLODE
        // EXPLODE
        Animation logoKO = AnimationFactory.loadAnimation(ConfigPath.image_gui + "/logo/logoKO/logoKO ", ".png", 1, 19, false, 50);
        logoKO.addFrame(new Image(ConfigPath.image_gui + "/logo/logoKO/logoKO 19.png"), 1500);
        animatorController.addAnimation(EAnimation.EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedProjected.png", 247, 247), EAnimation.TOUCHED_PROJECTED.isLoop(), 0, 1, 0, 1, new int[]{3000}));
        animatorController.addCollision(EAnimation.EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedProjected.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 0, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.NULL));
        animatorController.addEffect(EAnimation.EXPLODE, 0, 0, EffectFactory.createAnimationEffect(logoKO, -80, 80, false));
        animatorController.addEffect(EAnimation.EXPLODE, 0, 0, EffectFactory.createSoundEffect(ESound.EFFECT_KO));
        animatorController.addAnimation(EAnimation.EXPLODE, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{500}));
        animatorController.addCollision(EAnimation.EXPLODE, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedFall.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 1, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.EXPLODE, 2));
        animatorController.addAnimation(EAnimation.EXPLODE, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{3000}));
        animatorController.addCollision(EAnimation.EXPLODE, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedFall.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 2, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.NULL));
        // FALL
        animatorController.addAnimation(EAnimation.FALL, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuFall.png", 247, 247), EAnimation.FALL.isLoop(), 0, 1, 0, 1, new int[]{200}));
        animatorController.addCollision(EAnimation.FALL, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuFall.json")));
        animatorController.addConfig(EAnimation.FALL, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("fall", GameObject.class), true, EAnimation.RECEIPT));
        // RUN
        animatorController.addAnimation(EAnimation.RUN, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuRun.png", 247, 247), EAnimation.RUN.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
        animatorController.addCollision(EAnimation.RUN, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuRun.json")));
        animatorController.addConfig(EAnimation.RUN, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("run", GameObject.class), true));
        // RUSH
        animatorController.addAnimation(EAnimation.RUSH, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuRush.png", 247, 247), EAnimation.RUSH.isLoop(), 0, 2, 0, 1, new int[]{40, 40}));
        animatorController.addCollision(EAnimation.RUSH, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuRush.json")));
        animatorController.addConfig(EAnimation.RUSH, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("rush", GameObject.class), true));
        // ATTACK_RUSH
        animatorController.addAnimation(EAnimation.RUSH_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuRushAttack1.png", 247, 247), EAnimation.RUSH_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 100}));
        animatorController.addCollision(EAnimation.RUSH_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuRushAttack1.json")));
        animatorController.addConfig(EAnimation.RUSH_ATTACK, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("rushAttack", GameObject.class), true, EAnimation.RUSH_ATTACK, 1));
        animatorController.addRepercussion(EAnimation.RUSH_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROPELS, 150, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        animatorController.addAnimation(EAnimation.RUSH_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuRushAttack2.png", 247, 247), EAnimation.RUSH_ATTACK.isLoop(), 0, 3, 0, 1, new int[]{200, 200, 200}));
        animatorController.addCollision(EAnimation.RUSH_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuRushAttack2.json")));
        animatorController.addConfig(EAnimation.RUSH_ATTACK, 1, new AnimationConfigItem(GokuS1Actions.class.getMethod("rushAttack", GameObject.class), true, EAnimation.RUSH_ATTACK, 2));
        animatorController.addRepercussion(EAnimation.RUSH_ATTACK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_PROPELS, 150, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        animatorController.addEffect(EAnimation.RUSH_ATTACK, 1, 0, EffectFactory.createSoundEffect(ESound.GOKU_RUSH_ATTACK));
        animatorController.addAnimation(EAnimation.RUSH_ATTACK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuRushAttack3.png", 247, 247), EAnimation.RUSH_ATTACK.isLoop(), 0, 1, 0, 1, new int[]{200}));
        animatorController.addCollision(EAnimation.RUSH_ATTACK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuRushAttack3.json")));
        animatorController.addConfig(EAnimation.RUSH_ATTACK, 2, new AnimationConfigItem(GokuS1Actions.class.getMethod("rushAttack", GameObject.class), true));
        animatorController.addRepercussion(EAnimation.RUSH_ATTACK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_PROPELS, 150, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        // RECEIPT
        animatorController.addAnimation(EAnimation.RECEIPT, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 1, 0, 1, new int[]{130}));
        animatorController.addCollision(EAnimation.RECEIPT, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuReceipt.json")));
        animatorController.addConfig(EAnimation.RECEIPT, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("receipt", GameObject.class), true));
        animatorController.addAnimation(EAnimation.RECEIPT, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 100}));
        animatorController.addCollision(EAnimation.RECEIPT, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuReceipt.json")));
        animatorController.addConfig(EAnimation.RECEIPT, 1, new AnimationConfigItem(GokuS1Actions.class.getMethod("receipt", GameObject.class), true));
        // JUMP
        animatorController.addAnimation(EAnimation.JUMP, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuJump1.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{150, 150, 300}));
        animatorController.addCollision(EAnimation.JUMP, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuJump1.json")));
        animatorController.addConfig(EAnimation.JUMP, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("jump", GameObject.class), true, 1, EAnimation.FALL, 0));
        animatorController.addAnimation(EAnimation.JUMP, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuJump2.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 300}));
        animatorController.addCollision(EAnimation.JUMP, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuJump2.json")));
        animatorController.addConfig(EAnimation.JUMP, 1, new AnimationConfigItem(GokuS1Actions.class.getMethod("jump", GameObject.class), true, 1, EAnimation.FALL, 0));
        // DEFENSE
        animatorController.addAnimation(EAnimation.DEFENSE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuDef.png", 247, 247), EAnimation.DEFENSE.isLoop(), 0, 4, 0, 1, new int[]{200, 300, 300, 300}));
        animatorController.addCollision(EAnimation.DEFENSE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuDef.json")));
        animatorController.addConfig(EAnimation.DEFENSE, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("defense", GameObject.class), true));
        // BLOCK
        animatorController.addAnimation(EAnimation.BLOCK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuBlock.png", 247, 247), EAnimation.BLOCK.isLoop(), 0, 1, 0, 1, new int[]{300}));
        animatorController.addCollision(EAnimation.BLOCK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuBlock.json")));
        animatorController.addConfig(EAnimation.BLOCK, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("block", GameObject.class), true));
        animatorController.addEffect(EAnimation.BLOCK, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_BLOCK));
        //TRANSPOSITION
        animatorController.addAnimation(EAnimation.TRANSPOSITION, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTransposition.png", 247, 247), EAnimation.TRANSPOSITION.isLoop(), 0, 6, 0, 1, new int[]{200, 100, 100, 100, 200, 200}));
        animatorController.addCollision(EAnimation.TRANSPOSITION, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTransposition.json")));
        animatorController.addConfig(EAnimation.TRANSPOSITION, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("transposition", GameObject.class), false));
        // HAND_ATTACK
        animatorController.addAnimation(EAnimation.HAND_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuHandAttack1.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{100, 100, 60, 100}));
        animatorController.addCollision(EAnimation.HAND_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuHandAttack1.json")));
        animatorController.addConfig(EAnimation.HAND_ATTACK, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("handAttack", GameObject.class), true, EAnimation.HAND_ATTACK, 2));
        animatorController.addRepercussion(EAnimation.HAND_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 50, new Pair<>(GameConfig.speedTravel * 1.5f, 0f), true));
        animatorController.addAnimation(EAnimation.HAND_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuHandAttack2.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{70, 70}));
        animatorController.addCollision(EAnimation.HAND_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuHandAttack2.json")));
        animatorController.addConfig(EAnimation.HAND_ATTACK, 1, new AnimationConfigItem(GokuS1Actions.class.getMethod("handAttack", GameObject.class), true, EAnimation.HAND_ATTACK, 2));
        animatorController.addRepercussion(EAnimation.HAND_ATTACK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 50, new Pair<>(GameConfig.speedTravel * 1.5f, 0f), true));
        animatorController.addAnimation(EAnimation.HAND_ATTACK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuHandAttack3.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{70, 100}));
        animatorController.addCollision(EAnimation.HAND_ATTACK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuHandAttack3.json")));
        animatorController.addConfig(EAnimation.HAND_ATTACK, 2, new AnimationConfigItem(GokuS1Actions.class.getMethod("handAttack", GameObject.class), true));
        animatorController.addRepercussion(EAnimation.HAND_ATTACK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 50, new Pair<>(GameConfig.speedTravel * 1.5f, 0f), true));
        // HAND_FLY_PROPELS
        animatorController.addAnimation(EAnimation.HAND_FLY_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuHandFlyPropels.png", 247, 247), EAnimation.HAND_FLY_PROPELS.isLoop(), 0, 5, 0, 1, new int[]{120, 90, 70, 90, 120}));
        animatorController.addCollision(EAnimation.HAND_FLY_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuHandFlyPropels.json")));
        animatorController.addConfig(EAnimation.HAND_FLY_PROPELS, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("handFlyPropels", GameObject.class), true, EAnimation.FALL));
        animatorController.addRepercussion(EAnimation.HAND_FLY_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_FLIP, 0, 150, 0, 0, 50, 100, 0, new Pair<>(0f, 0f), false));
        animatorController.addEffect(EAnimation.HAND_FLY_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_HAND_FLY_PROPELS));
        // JUMP_KICK
        animatorController.addAnimation(EAnimation.JUMP_KICK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuJumpKick1.png", 247, 247), EAnimation.JUMP_KICK.isLoop(), 0, 4, 0, 1, new int[]{120, 70, 80, 70}));
        animatorController.addCollision(EAnimation.JUMP_KICK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuJumpKick1.json")));
        animatorController.addConfig(EAnimation.JUMP_KICK, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("jumpKickAttack", GameObject.class), false, EAnimation.JUMP_KICK, 2));
        animatorController.addRepercussion(EAnimation.JUMP_KICK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 80, new Pair<>(GameConfig.speedTravel * 2, 0f)));
        animatorController.addAnimation(EAnimation.JUMP_KICK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuJumpKick2.png", 247, 247), EAnimation.JUMP_KICK.isLoop(), 0, 5, 0, 1, new int[]{60, 60, 60, 60, 60}));
        animatorController.addCollision(EAnimation.JUMP_KICK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuJumpKick2.json")));
        animatorController.addConfig(EAnimation.JUMP_KICK, 1, new AnimationConfigItem(GokuS1Actions.class.getMethod("jumpKickAttack", GameObject.class), false, EAnimation.JUMP_KICK, 2));
        animatorController.addRepercussion(EAnimation.JUMP_KICK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 80, new Pair<>(GameConfig.speedTravel * 2, 0f)));
        animatorController.addAnimation(EAnimation.JUMP_KICK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuJumpKick3.png", 247, 247), EAnimation.JUMP_KICK.isLoop(), 0, 4, 0, 1, new int[]{80, 120, 120, 80}));
        animatorController.addCollision(EAnimation.JUMP_KICK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuJumpKick3.json")));
        animatorController.addConfig(EAnimation.JUMP_KICK, 2, new AnimationConfigItem(EAnimation.IDLE, 0));
        animatorController.addRepercussion(EAnimation.JUMP_KICK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 80, new Pair<>(GameConfig.speedTravel * 2, 0f)));
        // SPIRAL_KICK
        animatorController.addAnimation(EAnimation.SPIRAL_KICK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuSpiralKick1.png", 247, 247), EAnimation.SPIRAL_KICK.isLoop(), 0, 7, 0, 1, new int[]{150, 100, 100, 50, 50, 50, 50}));
        animatorController.addCollision(EAnimation.SPIRAL_KICK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuSpiralKick1.json")));
        animatorController.addConfig(EAnimation.SPIRAL_KICK, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("spiralKickAttack", GameObject.class), false, EAnimation.SPIRAL_KICK, 1));
        animatorController.addRepercussion(EAnimation.SPIRAL_KICK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 200, new Pair<>(GameConfig.speedTravel * 3, 0f), true));
        animatorController.addAnimation(EAnimation.SPIRAL_KICK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuSpiralKick2.png", 247, 247), EAnimation.SPIRAL_KICK.isLoop(), 0, 4, 0, 1, new int[]{50, 50, 50, 50}));
        animatorController.addCollision(EAnimation.SPIRAL_KICK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuSpiralKick2.json")));
        animatorController.addConfig(EAnimation.SPIRAL_KICK, 1, new AnimationConfigItem(GokuS1Actions.class.getMethod("spiralKickAttack", GameObject.class), false, EAnimation.SPIRAL_KICK, 2));
        animatorController.addRepercussion(EAnimation.SPIRAL_KICK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 200, new Pair<>(GameConfig.speedTravel * 3, 0f), true));
        animatorController.addAnimation(EAnimation.SPIRAL_KICK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuSpiralKick3.png", 247, 247), EAnimation.SPIRAL_KICK.isLoop(), 0, 2, 0, 1, new int[]{50, 100}));
        animatorController.addCollision(EAnimation.SPIRAL_KICK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuSpiralKick3.json")));
        animatorController.addConfig(EAnimation.SPIRAL_KICK, 2, new AnimationConfigItem(EAnimation.IDLE, 0));
        animatorController.addRepercussion(EAnimation.SPIRAL_KICK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 200, new Pair<>(GameConfig.speedTravel * 3, 0f), true));
        // KICK_PROPELS
        animatorController.addAnimation(EAnimation.KICK_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKickPropels1.png", 247, 247), EAnimation.KICK_PROPELS.isLoop(), 0, 7, 0, 1, new int[]{150, 100, 100, 50, 50, 50, 50}));
        animatorController.addCollision(EAnimation.KICK_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKickPropels1.json")));
        animatorController.addConfig(EAnimation.KICK_PROPELS, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("kickPropelsAttack", GameObject.class), false));
        animatorController.addRepercussion(EAnimation.KICK_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_FLIP, 200, new Pair<>(0f, -GameConfig.speedTravel)));
        animatorController.addEffect(EAnimation.KICK_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_KICK_PROPELS));
        // KI_SIMPLE_PROPELS
        animatorController.addAnimation(EAnimation.KI_SIMPLE_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiSimplePropels.png", 247, 247), EAnimation.KI_SIMPLE_PROPELS.isLoop(), 0, 10, 0, 1, new int[]{110, 90, 80, 80, 60, 80, 110, 110, 100, 80}));
        animatorController.addCollision(EAnimation.KI_SIMPLE_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiSimplePropels.json")));
        animatorController.addConfig(EAnimation.KI_SIMPLE_PROPELS, 0, new AnimationConfigItem(EAnimation.IDLE, 0));
        animatorController.addRepercussion(EAnimation.KI_SIMPLE_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 250, new Pair<>(GameConfig.speedTravel * 2f, -0.5f)));
        animatorController.addEffect(EAnimation.KI_SIMPLE_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_HAND_FLY_PROPELS));
        // KI_FINAL_ATTACK
        animatorController.addAnimation(EAnimation.KI_FINAL_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuMegaKameha.png", 247, 247), EAnimation.KI_FINAL_ATTACK.isLoop(), 0, 14, 0, 1, new int[]{250, 250, 250, 250, 250, 150, 200, 250, 200, 5000, 250, 250, 150, 200}));
        animatorController.addCollision(EAnimation.KI_FINAL_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuMegaKameha.json")));
        animatorController.addConfig(EAnimation.KI_FINAL_ATTACK, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("kiFinalAttack", GameObject.class), true));
        // KI_SPE_ATTACK
        animatorController.addAnimation(EAnimation.KI_SPE_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKamehameha1.png", 247, 247), EAnimation.KI_SPE_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{300, 350, 400, 700}));
        animatorController.addCollision(EAnimation.KI_SPE_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKamehameha1.json")));
        animatorController.addConfig(EAnimation.KI_SPE_ATTACK, 0, new AnimationConfigItem(EAnimation.KI_SPE_ATTACK, 1));
        animatorController.addEffect(EAnimation.KI_SPE_ATTACK, 0, 0, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_expr.png", 85, 70), false, 0, 2, 0, 1, new int[]{1000, 1000}), 60, 60, false));
        animatorController.addEffect(EAnimation.KI_SPE_ATTACK, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_KAMEHAMEHA_AIM));
        animatorController.addAnimation(EAnimation.KI_SPE_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKamehameha2.png", 247, 247), EAnimation.KI_SPE_ATTACK.isLoop(), 0, 6, 0, 1, new int[]{220, 200, 5000, 300, 100, 100}));
        animatorController.addCollision(EAnimation.KI_SPE_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKamehameha2.json")));
        animatorController.addConfig(EAnimation.KI_SPE_ATTACK, 1, new AnimationConfigItem(GokuS1Actions.class.getMethod("kiSpeAttack", GameObject.class), false));
        animatorController.addEffect(EAnimation.KI_SPE_ATTACK, 1, 1, EffectFactory.createSoundEffect(ESound.GOKU_KAMEHAMEHA_FIRE));
        animatorController.addEffect(EAnimation.KI_SPE_ATTACK, 1, 1, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + "/entity/kameha" + "/kameha_expr.png", 85, 70), false, 2, 3, 0, 1, new int[]{800}), 60, 60, false));
        // KI_BASIC_ATTACK
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiBlast1.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 3, 0, 1, new int[]{150, 100, 100}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiBlast1.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 0, new AnimationConfigItem(EAnimation.KI_BASIC_ATTACK, 1));
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiBlast2.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 5, 0, 1, new int[]{100, 100, 40, 40, 40}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiBlast2.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 1, new AnimationConfigItem(GokuS1Actions.class.getMethod("kiBasicAttack", GameObject.class), false, EAnimation.KI_BASIC_ATTACK, 3));
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiBlast3.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 6, 0, 1, new int[]{100, 100, 40, 40, 40, 40}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiBlast3.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 2, new AnimationConfigItem(GokuS1Actions.class.getMethod("kiBasicAttack", GameObject.class), false, EAnimation.KI_BASIC_ATTACK, 3));
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 3, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiBlast4.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 80}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 3, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiBlast4.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 3, new AnimationConfigItem(EAnimation.IDLE, 0));
        // KI_CHARGE
        animatorController.addAnimation(EAnimation.KI_CHARGE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiCharge1.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 2, 0, 1, new int[]{200, 150}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiCharge1.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 0, new AnimationConfigItem(EAnimation.IDLE, 0));
        animatorController.addAnimation(EAnimation.KI_CHARGE, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiCharge2.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 4, 0, 1, new int[]{100, 100, 100, 100}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiCharge2.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 1, new AnimationConfigItem(GokuS1Actions.class.getMethod("kiChargeAction", GameObject.class), false, EAnimation.KI_CHARGE, 2));
        animatorController.addEffect(EAnimation.KI_CHARGE, 1, 0, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKi.png", 247, 247), false, 0, 4, 0, 1, new int[]{100, 100, 100, 100}), -47, 0, false));
        animatorController.addAnimation(EAnimation.KI_CHARGE, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuKiCharge3.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 1, 0, 1, new int[]{150}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuKiCharge3.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 2, new AnimationConfigItem(EAnimation.IDLE, 0));
        // TOUCHED_SIMPLE
        animatorController.addAnimation(EAnimation.TOUCHED_SIMPLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedSimple.png", 247, 247), EAnimation.TOUCHED_SIMPLE.isLoop(), 0, 1, 0, 1, new int[]{300}));
        animatorController.addCollision(EAnimation.TOUCHED_SIMPLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedSimple.json")));
        animatorController.addConfig(EAnimation.TOUCHED_SIMPLE, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("touchedSimple", GameObject.class), true));
        animatorController.addEffect(EAnimation.TOUCHED_SIMPLE, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_SIMPLE));
        // TOUCHED_MEDIUM
        animatorController.addAnimation(EAnimation.TOUCHED_MEDIUM, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedMedium.png", 247, 247), EAnimation.TOUCHED_MEDIUM.isLoop(), 0, 1, 0, 1, new int[]{400}));
        animatorController.addCollision(EAnimation.TOUCHED_MEDIUM, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedMedium.json")));
        animatorController.addConfig(EAnimation.TOUCHED_MEDIUM, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("touchedMedium", GameObject.class), false));
        animatorController.addEffect(EAnimation.TOUCHED_MEDIUM, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_MEDIUM));
        // TOUCHED_PROPELS
        animatorController.addAnimation(EAnimation.TOUCHED_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedPropels.png", 247, 247), EAnimation.TOUCHED_PROPELS.isLoop(), 0, 2, 0, 1, new int[]{300, 300}));
        animatorController.addCollision(EAnimation.TOUCHED_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedPropels.json")));
        animatorController.addConfig(EAnimation.TOUCHED_PROPELS, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("touchedPropels", GameObject.class), false));
        animatorController.addEffect(EAnimation.TOUCHED_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_PROPELS));
        // TOUCHED_FLIP
        animatorController.addAnimation(EAnimation.TOUCHED_FLIP, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedFlip.png", 247, 247), EAnimation.TOUCHED_FLIP.isLoop(), 0, 4, 0, 1, new int[]{150, 150, 150, 150}));
        animatorController.addCollision(EAnimation.TOUCHED_FLIP, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedFlip.json")));
        animatorController.addConfig(EAnimation.TOUCHED_FLIP, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("touchedFlip", GameObject.class), true, EAnimation.TOUCHED_FALL));
        // TOUCHED_PROJECTED
        animatorController.addAnimation(EAnimation.TOUCHED_PROJECTED, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedProjected.png", 247, 247), EAnimation.TOUCHED_PROJECTED.isLoop(), 0, 1, 0, 1, new int[]{500}));
        animatorController.addCollision(EAnimation.TOUCHED_PROJECTED, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedProjected.json")));
        animatorController.addConfig(EAnimation.TOUCHED_PROJECTED, 0, new AnimationConfigItem(VegetaActions.class.getMethod("touchedProjected", GameObject.class), true, EAnimation.TOUCHED_FALL));
        //TOUCHED_FALL
        animatorController.addAnimation(EAnimation.TOUCHED_FALL, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{10000}));
        animatorController.addCollision(EAnimation.TOUCHED_FALL, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedFall.json")));
        animatorController.addConfig(EAnimation.TOUCHED_FALL, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("touchedFall", GameObject.class), true, EAnimation.TOUCHED_RECEIPT));
        // TOUCHED_RECEIPT
        animatorController.addAnimation(EAnimation.TOUCHED_RECEIPT, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/gokuTouchedReceipt.png", 247, 247), EAnimation.TOUCHED_RECEIPT.isLoop(), 0, 6, 0, 1, new int[]{150, 200, 150, 150, 150, 150}));
        animatorController.addCollision(EAnimation.TOUCHED_RECEIPT, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/gokuTouchedReceipt.json")));
        animatorController.addConfig(EAnimation.TOUCHED_RECEIPT, 0, new AnimationConfigItem(GokuS1Actions.class.getMethod("touchedReceipt", GameObject.class), true));
        return animatorController;
    }

    public AnimatorController getVegetaS1Animator() throws SlickException, JSONException, NoSuchMethodException {
        AnimatorController animatorController = new AnimatorController();
        String id = "/vegeta/S1";

        //IDLE
        animatorController.addAnimation(EAnimation.IDLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaIdle.png", 247, 247), EAnimation.IDLE.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
        animatorController.addCollision(EAnimation.IDLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaIdle.json")));
        animatorController.addConfig(EAnimation.IDLE, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("idle", GameObject.class), true));
        // EXPLODE
        // EXPLODE
        Animation logoKO = AnimationFactory.loadAnimation(ConfigPath.image_gui + "/logo/logoKO/logoKO ", ".png", 1, 19, false, 50);
        logoKO.addFrame(new Image(ConfigPath.image_gui + "/logo/logoKO/logoKO 19.png"), 1500);
        animatorController.addAnimation(EAnimation.EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedProjected.png", 247, 247), EAnimation.TOUCHED_PROJECTED.isLoop(), 0, 1, 0, 1, new int[]{3000}));
        animatorController.addCollision(EAnimation.EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedProjected.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 0, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.NULL));
        animatorController.addEffect(EAnimation.EXPLODE, 0, 0, EffectFactory.createAnimationEffect(logoKO, -80, 80, false));
        animatorController.addEffect(EAnimation.EXPLODE, 0, 0, EffectFactory.createSoundEffect(ESound.EFFECT_KO));
        animatorController.addAnimation(EAnimation.EXPLODE, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{500}));
        animatorController.addCollision(EAnimation.EXPLODE, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedFall.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 1, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.EXPLODE, 2));
        animatorController.addAnimation(EAnimation.EXPLODE, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{3000}));
        animatorController.addCollision(EAnimation.EXPLODE, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedFall.json")));
        animatorController.addConfig(EAnimation.EXPLODE, 2, new AnimationConfigItem(GokuActions.class.getMethod("explode", GameObject.class), true, EAnimation.NULL));
        // FALL
        animatorController.addAnimation(EAnimation.FALL, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaFall.png", 247, 247), EAnimation.FALL.isLoop(), 0, 1, 0, 1, new int[]{200}));
        animatorController.addCollision(EAnimation.FALL, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaFall.json")));
        animatorController.addConfig(EAnimation.FALL, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("fall", GameObject.class), true, EAnimation.RECEIPT));
        // RUN
        animatorController.addAnimation(EAnimation.RUN, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaRun.png", 247, 247), EAnimation.RUN.isLoop(), 0, 3, 0, 1, new int[]{200, 200, 200}));
        animatorController.addCollision(EAnimation.RUN, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaRun.json")));
        animatorController.addConfig(EAnimation.RUN, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("run", GameObject.class), true));
        // RUSH
        animatorController.addAnimation(EAnimation.RUSH, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaRush.png", 247, 247), EAnimation.RUSH.isLoop(), 0, 2, 0, 1, new int[]{40, 40}));
        animatorController.addCollision(EAnimation.RUSH, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaRush.json")));
        animatorController.addConfig(EAnimation.RUSH, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("rush", GameObject.class), true));
        // ATTACK_RUSH
        animatorController.addAnimation(EAnimation.RUSH_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaRushAttack.png", 247, 247), EAnimation.RUSH_ATTACK.isLoop(), 0, 6, 0, 1, new int[]{180, 180, 280, 120, 120, 120}));
        animatorController.addCollision(EAnimation.RUSH_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaRushAttack.json")));
        animatorController.addConfig(EAnimation.RUSH_ATTACK, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("rushAttack", GameObject.class), true));
        animatorController.addRepercussion(EAnimation.RUSH_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROPELS, 200, new Pair<>(GameConfig.speedTravel * 2, 0f), true));
        // RECEIPT
        animatorController.addAnimation(EAnimation.RECEIPT, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 1, 0, 1, new int[]{130}));
        animatorController.addCollision(EAnimation.RECEIPT, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaReceipt.json")));
        animatorController.addConfig(EAnimation.RECEIPT, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("receipt", GameObject.class), true));
        animatorController.addAnimation(EAnimation.RECEIPT, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaReceipt.png", 247, 247), EAnimation.RECEIPT.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 100}));
        animatorController.addCollision(EAnimation.RECEIPT, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaReceipt.json")));
        animatorController.addConfig(EAnimation.RECEIPT, 1, new AnimationConfigItem(VegetaS1Actions.class.getMethod("receipt", GameObject.class), true));
        // JUMP
        animatorController.addAnimation(EAnimation.JUMP, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaJump1.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 2, 0, 1, new int[]{150, 300}));
        animatorController.addCollision(EAnimation.JUMP, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaJump1.json")));
        animatorController.addConfig(EAnimation.JUMP, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("jump", GameObject.class), true, 1, EAnimation.FALL, 0));
        animatorController.addAnimation(EAnimation.JUMP, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaJump2.png", 247, 247), EAnimation.JUMP.isLoop(), 0, 2, 0, 1, new int[]{100, 300}));
        animatorController.addCollision(EAnimation.JUMP, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaJump2.json")));
        animatorController.addConfig(EAnimation.JUMP, 1, new AnimationConfigItem(VegetaS1Actions.class.getMethod("jump", GameObject.class), true, 1, EAnimation.FALL, 0));
        // DEFENSE
        animatorController.addAnimation(EAnimation.DEFENSE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaDef.png", 247, 247), EAnimation.DEFENSE.isLoop(), 0, 4, 0, 1, new int[]{200, 300, 300, 300}));
        animatorController.addCollision(EAnimation.DEFENSE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaDef.json")));
        animatorController.addConfig(EAnimation.DEFENSE, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("defense", GameObject.class), true));
        // BLOCK
        animatorController.addAnimation(EAnimation.BLOCK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaBlock.png", 247, 247), EAnimation.BLOCK.isLoop(), 0, 1, 0, 1, new int[]{300}));
        animatorController.addCollision(EAnimation.BLOCK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaBlock.json")));
        animatorController.addConfig(EAnimation.BLOCK, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("block", GameObject.class), true));
        animatorController.addEffect(EAnimation.BLOCK, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_BLOCK));
        //TRANSPOSITION
        animatorController.addAnimation(EAnimation.TRANSPOSITION, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTransposition.png", 247, 247), EAnimation.TRANSPOSITION.isLoop(), 0, 6, 0, 1, new int[]{200, 100, 100, 100, 200, 200}));
        animatorController.addCollision(EAnimation.TRANSPOSITION, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTransposition.json")));
        animatorController.addConfig(EAnimation.TRANSPOSITION, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("transposition", GameObject.class), false));
        // HAND_ATTACK
        animatorController.addAnimation(EAnimation.HAND_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandAttack.png", 247, 247), EAnimation.HAND_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{80, 100, 80, 100}));
        animatorController.addCollision(EAnimation.HAND_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandAttack.json")));
        animatorController.addConfig(EAnimation.HAND_ATTACK, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("handAttack", GameObject.class), true));
        animatorController.addRepercussion(EAnimation.HAND_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 40, new Pair<>(GameConfig.speedTravel * 1.5f, 0f), true));
        // HAND_FLY_PROPELS
        animatorController.addAnimation(EAnimation.HAND_FLY_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandPropels1.png", 247, 247), EAnimation.HAND_FLY_PROPELS.isLoop(), 0, 5, 0, 1, new int[]{120, 100, 80, 100, 120}));
        animatorController.addCollision(EAnimation.HAND_FLY_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandPropels1.json")));
        animatorController.addConfig(EAnimation.HAND_FLY_PROPELS, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("handFlyPropels", GameObject.class), false, EAnimation.FALL));
        animatorController.addRepercussion(EAnimation.HAND_FLY_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_FLIP, 0, 150, 0, 0, 50, 100, 0, new Pair<>(0f, 0f), false));
        animatorController.addEffect(EAnimation.HAND_FLY_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_HAND_FLY_PROPELS));
        // MOVE_HAND_ATTACK
        animatorController.addAnimation(EAnimation.MOVE_HAND_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaMoveHandAttack1.png", 247, 247), EAnimation.MOVE_HAND_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{120, 120, 180, 180}));
        animatorController.addCollision(EAnimation.MOVE_HAND_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaMoveHandAttack1.json")));
        animatorController.addConfig(EAnimation.MOVE_HAND_ATTACK, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("moveHandAttack", GameObject.class), true, EAnimation.MOVE_HAND_ATTACK, 1));
        animatorController.addRepercussion(EAnimation.MOVE_HAND_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 200, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        animatorController.addAnimation(EAnimation.MOVE_HAND_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaMoveHandAttack2.png", 247, 247), EAnimation.MOVE_HAND_ATTACK.isLoop(), 0, 3, 0, 1, new int[]{120, 80, 80}));
        animatorController.addCollision(EAnimation.MOVE_HAND_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaMoveHandAttack2.json")));
        animatorController.addConfig(EAnimation.MOVE_HAND_ATTACK, 1, new AnimationConfigItem(EAnimation.IDLE, 0));
        // JUMP_HAND_ATTACK
        animatorController.addAnimation(EAnimation.JUMP_HAND_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandFly1.png", 247, 247), EAnimation.JUMP_HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{80, 120}));
        animatorController.addCollision(EAnimation.JUMP_HAND_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandFly1.json")));
        animatorController.addConfig(EAnimation.JUMP_HAND_ATTACK, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("jumpHandAttack", GameObject.class), false, EAnimation.JUMP_HAND_ATTACK, 1));
        animatorController.addAnimation(EAnimation.JUMP_HAND_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandFly2.png", 247, 247), EAnimation.JUMP_HAND_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{220, 120}));
        animatorController.addCollision(EAnimation.JUMP_HAND_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandFly2.json")));
        animatorController.addConfig(EAnimation.JUMP_HAND_ATTACK, 1, new AnimationConfigItem(VegetaS1Actions.class.getMethod("jumpHandAttack", GameObject.class), false));
        animatorController.addRepercussion(EAnimation.JUMP_HAND_ATTACK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 250, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        animatorController.addAnimation(EAnimation.JUMP_HAND_ATTACK, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandFly3.png", 247, 247), EAnimation.JUMP_HAND_ATTACK.isLoop(), 0, 3, 0, 1, new int[]{130, 220, 130}));
        animatorController.addCollision(EAnimation.JUMP_HAND_ATTACK, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandFly3.json")));
        animatorController.addConfig(EAnimation.JUMP_HAND_ATTACK, 2, new AnimationConfigItem(VegetaS1Actions.class.getMethod("jumpHandAttack", GameObject.class), false, EAnimation.JUMP_HAND_ATTACK, 3));
        animatorController.addRepercussion(EAnimation.JUMP_HAND_ATTACK, 2, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 250, new Pair<>(GameConfig.speedTravel * 2f, 0f), true));
        animatorController.addAnimation(EAnimation.JUMP_HAND_ATTACK, 3, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaHandFly4.png", 247, 247), EAnimation.JUMP_HAND_ATTACK.isLoop(), 0, 1, 0, 1, new int[]{200}));
        animatorController.addCollision(EAnimation.JUMP_HAND_ATTACK, 3, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaHandFly4.json")));
        animatorController.addConfig(EAnimation.JUMP_HAND_ATTACK, 3, new AnimationConfigItem(VegetaS1Actions.class.getMethod("jumpHandAttack", GameObject.class), false, EAnimation.FALL, 0));
        // KNEES_PROPELS
        animatorController.addAnimation(EAnimation.KNEES_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKneesAttack.png", 247, 247), EAnimation.KNEES_ATTACK.isLoop(), 0, 5, 0, 1, new int[]{60, 120, 180, 120, 80}));
        animatorController.addCollision(EAnimation.KNEES_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKneesAttack.json")));
        animatorController.addConfig(EAnimation.KNEES_ATTACK, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("kneesAttack", GameObject.class), false, EAnimation.FALL));
        animatorController.addRepercussion(EAnimation.KNEES_ATTACK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_PROJECTED, 250, new Pair<>(GameConfig.speedTravel, -GameConfig.speedTravel)));
        // KICK_PROPELS
        animatorController.addAnimation(EAnimation.KICK_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKickPropels.png", 247, 247), EAnimation.KICK_PROPELS.isLoop(), 0, 5, 0, 1, new int[]{180, 120, 120, 80, 80}));
        animatorController.addCollision(EAnimation.KICK_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKickPropels.json")));
        animatorController.addConfig(EAnimation.KICK_PROPELS, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("kickPropelsAttack", GameObject.class), false, EAnimation.IDLE));
        animatorController.addRepercussion(EAnimation.KICK_PROPELS, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_FLIP, 200, new Pair<>(GameConfig.speedTravel / 2f, -GameConfig.speedTravel)));
        animatorController.addEffect(EAnimation.KICK_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_KICK_PROPELS));
        //KICK_ATTACK
        animatorController.addAnimation(EAnimation.KICK_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKickAttack1.png", 247, 247), EAnimation.KICK_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 100}));
        animatorController.addCollision(EAnimation.KICK_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKickAttack1.json")));
        animatorController.addConfig(EAnimation.KICK_ATTACK, 0, new AnimationConfigItem(EAnimation.KICK_ATTACK, 1));
        animatorController.addAnimation(EAnimation.KICK_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKickAttack2.png", 247, 247), EAnimation.KICK_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{100, 60, 120, 60}));
        animatorController.addCollision(EAnimation.KICK_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKickAttack2.json")));
        animatorController.addConfig(EAnimation.KICK_ATTACK, 1, new AnimationConfigItem(VegetaS1Actions.class.getMethod("kickAttack", GameObject.class), false));
        animatorController.addRepercussion(EAnimation.KICK_ATTACK, 1, new AnimationRepercussionItem(EAnimation.TOUCHED_SIMPLE, 100, new Pair<>(GameConfig.speedTravel, 0f)));
        // SPIRAL_KICK
        animatorController.addAnimation(EAnimation.SPIRAL_KICK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKickFlip.png", 247, 247), EAnimation.SPIRAL_KICK.isLoop(), 0, 5, 0, 1, new int[]{130, 130, 130, 130, 100}));
        animatorController.addCollision(EAnimation.SPIRAL_KICK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKickFlip.json")));
        animatorController.addConfig(EAnimation.SPIRAL_KICK, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("spiralKick", GameObject.class), false));
        animatorController.addRepercussion(EAnimation.SPIRAL_KICK, 0, new AnimationRepercussionItem(EAnimation.TOUCHED_MEDIUM, 200, new Pair<>(GameConfig.speedTravel, 0f)));
        // KI_FINAL_ATTACK
        animatorController.addAnimation(EAnimation.KI_FINAL_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaBigBang.png", 247, 247), EAnimation.KI_FINAL_ATTACK.isLoop(), 0, 8, 0, 1, new int[]{250, 300, 300, 300, 250, 250, 200, 200}));
        animatorController.addCollision(EAnimation.KI_FINAL_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaBigBang.json")));
        animatorController.addConfig(EAnimation.KI_FINAL_ATTACK, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("kiFinalAttack", GameObject.class), false));
        animatorController.addEffect(EAnimation.KI_FINAL_ATTACK, 0, 0, EffectFactory.createSoundEffect(ESound.VEGETA_FINAL_FLASH));
        // KI_SPE_ATTACK
        animatorController.addAnimation(EAnimation.KI_SPE_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiBurst.png", 247, 247), EAnimation.KI_SPE_ATTACK.isLoop(), 0, 6, 0, 1, new int[]{150, 150, 150, 200, 300, 150}));
        animatorController.addCollision(EAnimation.KI_SPE_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiBurst.json")));
        animatorController.addConfig(EAnimation.KI_SPE_ATTACK, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("kiSpeAttack", GameObject.class), false));
        // KI_BASIC_ATTACK
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiBlast1.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 2, 0, 1, new int[]{100, 100}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiBlast1.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 0, new AnimationConfigItem(EAnimation.KI_BASIC_ATTACK, 1));
        animatorController.addAnimation(EAnimation.KI_BASIC_ATTACK, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiBlast2.png", 247, 247), EAnimation.KI_BASIC_ATTACK.isLoop(), 0, 4, 0, 1, new int[]{80, 150, 80, 150}));
        animatorController.addCollision(EAnimation.KI_BASIC_ATTACK, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiBlast2.json")));
        animatorController.addConfig(EAnimation.KI_BASIC_ATTACK, 1, new AnimationConfigItem(VegetaS1Actions.class.getMethod("kiBasicAttack", GameObject.class), false));
        // KI_EXPLODE
        animatorController.addAnimation(EAnimation.KI_EXPLODE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiExplode.png", 247, 247), EAnimation.KI_EXPLODE.isLoop(), 0, 6, 0, 1, new int[]{100, 100, 150, 150, 300, 150}));
        animatorController.addCollision(EAnimation.KI_EXPLODE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiExplode.json")));
        animatorController.addConfig(EAnimation.KI_EXPLODE, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("kiExplode", GameObject.class), false));
        // KI_CHARGE
        animatorController.addAnimation(EAnimation.KI_CHARGE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiCharge1.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 2, 0, 1, new int[]{200, 150}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiCharge1.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 0, new AnimationConfigItem(EAnimation.IDLE, 0));
        animatorController.addAnimation(EAnimation.KI_CHARGE, 1, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiCharge2.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 4, 0, 1, new int[]{100, 100, 100, 100}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 1, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiCharge2.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 1, new AnimationConfigItem(VegetaS1Actions.class.getMethod("kiChargeAction", GameObject.class), false, EAnimation.KI_CHARGE, 2));
        animatorController.addEffect(EAnimation.KI_CHARGE, 1, 0, EffectFactory.createAnimationEffect(AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKi.png", 247, 247), false, 0, 4, 0, 1, new int[]{100, 100, 100, 100}), -60, 0, false));
        animatorController.addAnimation(EAnimation.KI_CHARGE, 2, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaKiCharge3.png", 247, 247), EAnimation.KI_CHARGE.isLoop(), 0, 1, 0, 1, new int[]{150}));
        animatorController.addCollision(EAnimation.KI_CHARGE, 2, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaKiCharge3.json")));
        animatorController.addConfig(EAnimation.KI_CHARGE, 2, new AnimationConfigItem(EAnimation.IDLE, 0));
        // TOUCHED_SIMPLE
        animatorController.addAnimation(EAnimation.TOUCHED_SIMPLE, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedSimple.png", 247, 247), EAnimation.TOUCHED_SIMPLE.isLoop(), 0, 1, 0, 1, new int[]{300}));
        animatorController.addCollision(EAnimation.TOUCHED_SIMPLE, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedSimple.json")));
        animatorController.addConfig(EAnimation.TOUCHED_SIMPLE, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("touchedSimple", GameObject.class), true));
        animatorController.addEffect(EAnimation.TOUCHED_SIMPLE, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_SIMPLE));
        // TOUCHED_MEDIUM
        animatorController.addAnimation(EAnimation.TOUCHED_MEDIUM, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedMedium.png", 247, 247), EAnimation.TOUCHED_MEDIUM.isLoop(), 0, 1, 0, 1, new int[]{400}));
        animatorController.addCollision(EAnimation.TOUCHED_MEDIUM, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedMedium.json")));
        animatorController.addConfig(EAnimation.TOUCHED_MEDIUM, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("touchedMedium", GameObject.class), false));
        animatorController.addEffect(EAnimation.TOUCHED_MEDIUM, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_MEDIUM));
        // TOUCHED_PROPELS
        animatorController.addAnimation(EAnimation.TOUCHED_PROPELS, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedPropels.png", 247, 247), EAnimation.TOUCHED_PROPELS.isLoop(), 0, 2, 0, 1, new int[]{300, 300}));
        animatorController.addCollision(EAnimation.TOUCHED_PROPELS, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedPropels.json")));
        animatorController.addConfig(EAnimation.TOUCHED_PROPELS, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("touchedPropels", GameObject.class), false));
        animatorController.addEffect(EAnimation.TOUCHED_PROPELS, 0, 0, EffectFactory.createSoundEffect(ESound.GOKU_TOUCHED_PROPELS));
        // TOUCHED_FLIP
        animatorController.addAnimation(EAnimation.TOUCHED_FLIP, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedFlip.png", 247, 247), EAnimation.TOUCHED_FLIP.isLoop(), 0, 4, 0, 1, new int[]{150, 150, 150, 150}));
        animatorController.addCollision(EAnimation.TOUCHED_FLIP, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedFlip.json")));
        animatorController.addConfig(EAnimation.TOUCHED_FLIP, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("touchedFlip", GameObject.class), true, EAnimation.TOUCHED_FALL));
        // TOUCHED_PROJECTED
        animatorController.addAnimation(EAnimation.TOUCHED_PROJECTED, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedProjected.png", 247, 247), EAnimation.TOUCHED_PROJECTED.isLoop(), 0, 1, 0, 1, new int[]{500}));
        animatorController.addCollision(EAnimation.TOUCHED_PROJECTED, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedProjected.json")));
        animatorController.addConfig(EAnimation.TOUCHED_PROJECTED, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("touchedProjected", GameObject.class), true, EAnimation.TOUCHED_FALL));
        //TOUCHED_FALL
        animatorController.addAnimation(EAnimation.TOUCHED_FALL, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedFall.png", 247, 247), EAnimation.TOUCHED_FALL.isLoop(), 0, 1, 0, 1, new int[]{10000}));
        animatorController.addCollision(EAnimation.TOUCHED_FALL, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedFall.json")));
        animatorController.addConfig(EAnimation.TOUCHED_FALL, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("touchedFall", GameObject.class), true, EAnimation.TOUCHED_RECEIPT));
        // TOUCHED_RECEIPT
        animatorController.addAnimation(EAnimation.TOUCHED_RECEIPT, 0, GameConfig.scaleGameSprite, AnimationFactory.createAnimation(new SpriteSheet(ConfigPath.image_game + id + "/vegetaTouchedReceipt.png", 247, 247), EAnimation.TOUCHED_RECEIPT.isLoop(), 0, 6, 0, 1, new int[]{150, 200, 150, 150, 150, 150}));
        animatorController.addCollision(EAnimation.TOUCHED_RECEIPT, 0, FilesTools.readInput(getClass().getClassLoader().getResourceAsStream(ConfigPath.jsonCollision + "/player" + id + "/vegetaTouchedReceipt.json")));
        animatorController.addConfig(EAnimation.TOUCHED_RECEIPT, 0, new AnimationConfigItem(VegetaS1Actions.class.getMethod("touchedReceipt", GameObject.class), true));
        return animatorController;
    }
}

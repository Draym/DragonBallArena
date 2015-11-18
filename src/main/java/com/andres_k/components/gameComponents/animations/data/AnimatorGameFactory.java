package com.andres_k.components.gameComponents.animations.data;

import com.andres_k.components.gameComponents.animations.EnumAnimation;
import com.andres_k.components.gameComponents.animations.EnumSprites;
import com.andres_k.components.gameComponents.animations.container.AnimatorConfig;
import com.andres_k.components.gameComponents.animations.container.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.commands.actions.Actions;
import com.andres_k.utils.configs.Config;
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
            animatorController.addAnimation(EnumAnimation.IDLE, 0, this.loadAnimation(new SpriteSheet(Config.images + name + state + "/gokuIDLE.png", 49, 70), EnumAnimation.IDLE.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
            animatorController.addCollision(EnumAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuIDLE.json")));
            animatorController.addConfig(EnumAnimation.IDLE, 0, new AnimatorConfig(Actions.class.getMethod("idle", GameObject.class)));

            animatorController.addAnimation(EnumAnimation.RUSH, 0, this.loadAnimation(new SpriteSheet(Config.images + name + state + "/gokuRUSH.png", 65, 70), EnumAnimation.RUSH.isLoop(), 0, 2, 0, 1, new int[]{200, 200}));
            animatorController.addCollision(EnumAnimation.RUSH, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuRUSH.json")));
            animatorController.addConfig(EnumAnimation.RUSH, 0, new AnimatorConfig(Actions.class.getMethod("rush", GameObject.class)));

            animatorController.addAnimation(EnumAnimation.RUN, 0, this.loadAnimation(new SpriteSheet(Config.images + name + state + "/gokuRUN.png", 56, 70), EnumAnimation.RUN.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
            animatorController.addCollision(EnumAnimation.RUN, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuRUN.json")));
            animatorController.addConfig(EnumAnimation.RUN, 0, new AnimatorConfig(Actions.class.getMethod("run", GameObject.class)));

            animatorController.addAnimation(EnumAnimation.RECEIPT, 0, this.loadAnimation(new SpriteSheet(Config.images + name + state + "/gokuRECEIPT.png", 50, 70), EnumAnimation.RECEIPT.isLoop(), 0, 1, 0, 1, new int[]{130}));
            animatorController.addCollision(EnumAnimation.RECEIPT, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuRECEIPT.json")));
            animatorController.addConfig(EnumAnimation.RECEIPT, 0, new AnimatorConfig(Actions.class.getMethod("receipt", GameObject.class), EnumAnimation.IDLE));
            animatorController.addAnimation(EnumAnimation.RECEIPT, 1, this.loadAnimation(new SpriteSheet(Config.images + name + state + "/gokuRECEIPT.png", 50, 70), EnumAnimation.RECEIPT.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 100}));
            animatorController.addCollision(EnumAnimation.RECEIPT, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuRECEIPT.json")));
            animatorController.addConfig(EnumAnimation.RECEIPT, 1, new AnimatorConfig(Actions.class.getMethod("receipt", GameObject.class), EnumAnimation.IDLE));

            animatorController.addAnimation(EnumAnimation.JUMP, 0, this.loadAnimation(new SpriteSheet(Config.images + name + state + "/gokuJUMP.png", 46, 70), EnumAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 300}));
            animatorController.addCollision(EnumAnimation.JUMP, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuJUMP.json")));
            animatorController.addConfig(EnumAnimation.JUMP, 0, new AnimatorConfig(Actions.class.getMethod("jump", GameObject.class), 1, EnumAnimation.FALL, 0));
            animatorController.addAnimation(EnumAnimation.JUMP, 1, this.loadAnimation(new SpriteSheet(Config.images + name + state + "/gokuJUMP2.png", 46, 70), EnumAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 300}));
            animatorController.addCollision(EnumAnimation.JUMP, 1, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuJUMP2.json")));
            animatorController.addConfig(EnumAnimation.JUMP, 1, new AnimatorConfig(Actions.class.getMethod("jump", GameObject.class), 1, EnumAnimation.FALL, 0));

            animatorController.addAnimation(EnumAnimation.DEF, 0, this.loadAnimation(new SpriteSheet(Config.images + name + state + "/gokuDEF.png", 48, 70), EnumAnimation.DEF.isLoop(), 1, 4, 0, 1, new int[]{200, 300, 300, 300}));
            animatorController.addCollision(EnumAnimation.DEF, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuDEF.json")));
            animatorController.addConfig(EnumAnimation.DEF, 0, new AnimatorConfig(Actions.class.getMethod("defense", GameObject.class)));

            animatorController.addAnimation(EnumAnimation.BLOCK, 0, this.loadAnimation(new SpriteSheet(Config.images + name + state + "/gokuBLOCK.png", 45, 70), EnumAnimation.BLOCK.isLoop(), 0, 1, 0, 1, new int[]{200}));
            animatorController.addCollision(EnumAnimation.BLOCK, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuBLOCK.json")));
            animatorController.addConfig(EnumAnimation.BLOCK, 0, new AnimatorConfig(Actions.class.getMethod("block", GameObject.class)));

            animatorController.addAnimation(EnumAnimation.FALL, 0, this.loadAnimation(new SpriteSheet(Config.images + name + state + "/gokuFALL.png", 45, 70), EnumAnimation.FALL.isLoop(), 0, 1, 0, 1, new int[]{200}));
            animatorController.addCollision(EnumAnimation.FALL, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuFALL.json")));
            animatorController.addConfig(EnumAnimation.FALL, 0, new AnimatorConfig(Actions.class.getMethod("fall", GameObject.class), EnumAnimation.RECEIPT));
        }
        return animatorController;
    }

    public AnimatorController getItemAnimator(EnumSprites index) throws SlickException, JSONException {
        AnimatorController animatorController = new AnimatorController();
        if (index == EnumSprites.GROUND) {
            animatorController.addCollision(EnumAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/map/ground.json")));
        }
        if (index == EnumSprites.WALL) {
            animatorController.addCollision(EnumAnimation.IDLE, 0, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/map/wall.json")));
        }
        return animatorController;
    }
}

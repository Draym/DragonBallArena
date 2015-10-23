package com.andres_k.components.gameComponents.animations;

import com.andres_k.utils.configs.Config;
import com.andres_k.utils.tools.StringTools;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by andres_k on 13/03/2015.
 */
public class AnimatorGameFactory extends AnimatorFactory {
    public Animator getAnimator(EnumSprites index) throws SlickException, JSONException {
        if (index.getIndex() == EnumSprites.PLAYER.getIndex()) {
            return this.getPlayerAnimator(index);
        } else if (index.getIndex() == EnumSprites.ITEM.getIndex()) {
            return this.getItemAnimator(index);
        }
        return null;
    }

    public Animator getPlayerAnimator(EnumSprites index) throws SlickException, JSONException {
        Animator animator = new Animator();
        if (index == EnumSprites.GOKU) {
            animator.addAnimation(EnumAnimation.IDLE, this.loadAnimation(new SpriteSheet(Config.images + "/gokuIDLE.png", 49, 70), EnumAnimation.IDLE.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
            animator.addCollision(EnumAnimation.IDLE, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuIDLE.json")));

            animator.addAnimation(EnumAnimation.RUSH, this.loadAnimation(new SpriteSheet(Config.images + "/gokuRUSH.png", 65, 70), EnumAnimation.RUSH.isLoop(), 0, 2, 0, 1, new int[]{200, 200}));

            animator.addAnimation(EnumAnimation.RUN, this.loadAnimation(new SpriteSheet(Config.images + "/gokuRUN.png", 56, 70), EnumAnimation.RUN.isLoop(), 0, 4, 0, 1, new int[]{200, 200, 200, 200}));
            animator.addCollision(EnumAnimation.RUN, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuRUN.json")));

            animator.addAnimation(EnumAnimation.JUMP, this.loadAnimation(new SpriteSheet(Config.images + "/gokuJUMP.png", 46, 70), EnumAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 300}));
            animator.addCollision(EnumAnimation.JUMP, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuJUMP.json")));
            animator.addAnimation(EnumAnimation.JUMP, this.loadAnimation(new SpriteSheet(Config.images + "/gokuJUMP2.png", 46, 70), EnumAnimation.JUMP.isLoop(), 0, 3, 0, 1, new int[]{100, 100, 300}));
            animator.addCollision(EnumAnimation.JUMP, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuJUMP2.json")));

            animator.addAnimation(EnumAnimation.DEF, this.loadAnimation(new SpriteSheet(Config.images + "/gokuDEF.png", 46, 70), EnumAnimation.DEF.isLoop(), 0, 2, 0, 1, new int[]{300, 300}));
            animator.addCollision(EnumAnimation.DEF, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuDEF.json")));

            animator.addAnimation(EnumAnimation.BLOCK, this.loadAnimation(new SpriteSheet(Config.images + "/gokuBLOCK.png", 45, 70), EnumAnimation.BLOCK.isLoop(), 0, 1, 0, 1, new int[]{200}));
            animator.addCollision(EnumAnimation.BLOCK, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuBLOCK.json")));

            animator.addAnimation(EnumAnimation.FALL, this.loadAnimation(new SpriteSheet(Config.images + "/gokuFALL.png", 45, 70), EnumAnimation.FALL.isLoop(), 0, 1, 0, 1, new int[]{200}));
            animator.addCollision(EnumAnimation.FALL, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/player/goku/gokuFALL.json")));
        }
        return animator;
    }

    public Animator getItemAnimator(EnumSprites index) throws SlickException, JSONException {
        Animator animator = new Animator();
        if (index == EnumSprites.GROUND) {
//            animator.addAnimation(EnumAnimation.IDLE, this.loadAnimation(new SpriteSheet("image/game/gokuRun.png", 42, 54), true, 0, 2, 0, 1, 400));
            animator.addCollision(EnumAnimation.IDLE, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/map/ground.json")));
        }
        if (index == EnumSprites.WALL) {
            animator.addCollision(EnumAnimation.IDLE, StringTools.readInput(getClass().getClassLoader().getResourceAsStream(Config.jsonCollision + "/map/wall.json")));
        }
        return animator;
    }
}

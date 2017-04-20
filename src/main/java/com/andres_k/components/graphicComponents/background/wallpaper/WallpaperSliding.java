package com.andres_k.components.graphicComponents.background.wallpaper;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.List;

/**
 * Created by andres_k on 10/07/2015.
 */
public class WallpaperSliding extends Wallpaper {
    private float speed;
    private boolean horizontal;
    private boolean moveRight;

    public WallpaperSliding(AnimatorController animator, float speed, boolean horizontal, boolean moveRight) throws SlickException {
        super(animator, 0, 0);
        this.speed = speed;
        this.horizontal = horizontal;
        this.moveRight = moveRight;
    }

    @Override
    public void draw(Graphics g) {
        if (this.animator != null)
            try {
                this.animator.draw(g, this.x, this.y);
                if (this.moveRight) {
                    this.animator.draw(g, (this.horizontal ? this.x + animator.currentAnimation().getCurrentFrame().getWidth() : this.x),
                            (this.horizontal ? this.y : this.y - animator.currentAnimation().getCurrentFrame().getHeight()));
                } else {
                    this.animator.draw(g, (this.horizontal ? this.x - animator.currentAnimation().getCurrentFrame().getWidth() : this.x),
                            (this.horizontal ? this.y : this.y + animator.currentAnimation().getCurrentFrame().getHeight()));
                }
            } catch (SlickException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void update() {
        if (this.running) {
            try {
                float increase = (this.speed * ((float) GameConfig.currentTimeLoop / 1000));
                if (this.horizontal) {
                    if (this.moveRight) {
                        this.x -= increase;
                        if (this.x <= -animator.currentAnimation().getCurrentFrame().getWidth()) {
                            this.x = 0;
                        }
                    } else {
                        this.x += increase;
                        if (this.x >= animator.currentAnimation().getCurrentFrame().getWidth()) {
                            this.x = 0;
                        }
                    }
                } else {
                    if (this.moveRight) {
                        this.y += increase;
                        if (this.y >= animator.currentAnimation().getCurrentFrame().getHeight()) {
                            this.y = 0;
                        }
                    } else {
                        this.y -= increase;
                        if (this.y <= -animator.currentAnimation().getCurrentFrame().getHeight()) {
                            this.y = 0;
                        }
                    }


                }
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.andres_k.components.graphicComponents.background.wallpaper;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.graphicComponents.graphic.EnumWindow;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.configs.WindowConfig;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 10/07/2015.
 */
public class WallpaperSliding extends Wallpaper {
    private float speed;
    private boolean horizontal;
    private boolean moveRight;
    private int nbImage;
    private int nbImageX;

    public WallpaperSliding(AnimatorController animator, float speed, boolean horizontal, boolean moveRight) throws SlickException {
        super(animator, 0, 0);
        this.speed = speed;
        this.horizontal = horizontal;
        this.moveRight = moveRight;

        float nbX = ((float) WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV1() / (float)animator.currentAnimation().getCurrentFrame().getWidth()) + 1.0f;
        float nbY = ((float) WindowConfig.get().getWindowSizes(EnumWindow.GAME).getV2() / (float)animator.currentAnimation().getCurrentFrame().getHeight()) + 1.0f;
        this.nbImageX = (nbX % 1 == 0 ? (int)nbX : (int)nbX + 1);
        this.nbImage = (nbY % 1 == 0 ? (int)nbY * this.nbImageX : ((int)nbY + 1) * this.nbImageX);
    }

    @Override
    public void draw(Graphics g) {
        if (this.animator != null && this.activated) {
            int currentX = 0;
            int currentY = 0;
            int incrX;
            int incrY;
            try {
                for (int i = 0; i < this.nbImage; ++i) {
                    if (currentX == this.nbImageX) {
                        currentX = 0;
                        currentY += 1;
                    }
                    incrX = animator.currentAnimation().getCurrentFrame().getWidth() * currentX;
                    incrY = animator.currentAnimation().getCurrentFrame().getHeight() * currentY;
                    if (this.moveRight) {
                        this.animator.draw(g, this.x + incrX, this.y + incrY - animator.currentAnimation().getCurrentFrame().getWidth());
                    } else {
                        this.animator.draw(g, this.x + incrX - animator.currentAnimation().getCurrentFrame().getWidth(), this.y + incrY);
                    }
                    ++currentX;
                }
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update() {
        if (this.running && this.activated) {
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

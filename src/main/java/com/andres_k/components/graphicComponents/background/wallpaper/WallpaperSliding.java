package com.andres_k.components.graphicComponents.background.wallpaper;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.utils.configs.GameConfig;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andres_k on 10/07/2015.
 */
public class WallpaperSliding extends Wallpaper {

    protected List<Pair<Integer, Integer>> positions;
    protected List<Image> images;

    protected float backgroundSizeY;
    private float speed;

    public WallpaperSliding(AnimatorController animator, float speed) throws SlickException {
        super(animator, 0, 0);
        this.images = new ArrayList<>();
        this.positions = new ArrayList<>();
        this.ready = false;
        this.speed = speed;
    }

    @Override
    public void draw(Graphics g) {
        if (this.ready)
            for (int i = 0; i < this.images.size(); ++i) {
                g.drawImage(this.images.get(i), this.positions.get(i).getV1(), this.positions.get(i).getV2());
            }
    }

    @Override
    public void update() {
        if (this.ready) {
            for (Pair<Integer, Integer> pos : this.positions) {
                pos.setV2((int) Math.ceil(((float) pos.getV2() + (this.speed * (GameConfig.currentTimeLoop / 1000)))));
            }
            for (int i = 0; i < this.positions.size(); ++i) {
                if (this.positions.get(i).getV2() > WindowConfig.wGame_sY) {
                    int posPrev = i - 1;
                    posPrev = (posPrev < 0 ? this.positions.size() - 1 : posPrev);

                    this.positions.get(i).setV2((int) Math.ceil((float) this.positions.get(posPrev).getV2() - this.backgroundSizeY + 5));
                }
            }
        }
    }

    @Override
    public void initialize() throws SlickException {
        Image background = animator.currentAnimation().getCurrentFrame();

        this.images.clear();
        this.positions.clear();
        this.backgroundSizeY = background.getHeight();
        int number = (int) (WindowConfig.wGame_sY / this.backgroundSizeY) + 2;

        int x = 0;
        int y = (int)(WindowConfig.wGame_sY - this.backgroundSizeY);

        y = (y < 0 ? 0 : y);
        for (int i = 0; i < number; ++i) {
            this.images.add(background.copy());
            this.positions.add(new Pair<>(x, y));
            y -= this.backgroundSizeY;
        }
    }
}

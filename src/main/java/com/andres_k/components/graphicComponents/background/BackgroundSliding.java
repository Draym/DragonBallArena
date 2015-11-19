package com.andres_k.components.graphicComponents.background;

import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 10/07/2015.
 */
public class BackgroundSliding extends Background{

    private float speed;

    public BackgroundSliding(float speed) {
        super();
        this.speed = speed;
    }

    @Override
    public void update() {
        if (launched == true) {
            for (Pair<Integer, Integer> pos : this.positions) {
                pos.setV2((int) Math.ceil(((float) pos.getV2() + (this.speed * (GlobalVariable.currentTimeLoop / 1000)))));
            }
            for (int i = 0; i < this.positions.size(); ++i) {
                if (this.positions.get(i).getV2() > WindowConfig.w2_sY) {
                    int posPrev = i - 1;
                    posPrev = (posPrev < 0 ? this.positions.size() - 1 : posPrev);

                    this.positions.get(i).setV2((int) Math.ceil((float) this.positions.get(posPrev).getV2() - this.backgroundSizeY + 5));
                }
            }
        }
    }

    @Override
    public void instanceCurrentBackground() throws SlickException {
        Image background = new Image(this.path.get(this.current));

        this.images.clear();
        this.positions.clear();
        this.backgroundSizeY = background.getHeight();
        int number = (int) (WindowConfig.w2_sY / this.backgroundSizeY) + 2;

        int x = 0;
        int y = (int)(WindowConfig.w2_sY - this.backgroundSizeY);

        y = (y < 0 ? 0 : y);
        for (int i = 0; i < number; ++i) {
            this.images.add(background.copy());
            this.positions.add(new Pair<>(x, y));
            y -= this.backgroundSizeY;
        }
    }
}

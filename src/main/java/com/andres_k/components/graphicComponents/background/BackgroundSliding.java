package com.andres_k.components.graphicComponents.background;

import com.andres_k.utils.configs.GlobalVariable;
import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;

/**
 * Created by andres_k on 10/07/2015.
 */
public class BackgroundSliding extends Background{

    public BackgroundSliding() {
        super();
    }

    @Override
    public void update() {
        if (launched == true) {
            for (Pair<Integer, Integer> pos : this.positions) {
                pos.setV2((int) Math.ceil(((float) pos.getV2() + (GlobalVariable.currentSpeed / 2))));
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
}

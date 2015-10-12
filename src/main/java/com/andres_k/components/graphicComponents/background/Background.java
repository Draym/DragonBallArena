package com.andres_k.components.graphicComponents.background;

import com.andres_k.utils.configs.WindowConfig;
import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by andres_k on 07/10/2015.
 */
public class Background {
    protected boolean   launched;
    protected String    current;
    protected float backgroundSizeY;
    protected List<Image> images;
    protected List<Pair<Integer, Integer>> positions;
    protected Map<String, String> path;

    public Background() {
        this.images = new ArrayList<>();
        this.positions = new ArrayList<>();
        this.launched = false;
    }

    // FUNCTIONS
    public void draw(Graphics g) {
        if (launched == true)
            for (int i = 0; i < this.images.size(); ++i) {
                g.drawImage(this.images.get(i), this.positions.get(i).getV1(), this.positions.get(i).getV2());
            }
    }


    public void update(){
    }

    public boolean init() {
        try {
            this.instanceCurrentBackground();
            this.launched = true;
        } catch (SlickException e) {
            e.printStackTrace();
            this.launched = false;
            return false;
        }
        return true;
    }

    public void instanceCurrentBackground() throws SlickException {
        Image background = new Image(this.path.get(this.current));

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

    // SETTERS
    public void addBackground(String name, String path) {
        this.current = name;
        this.path.put(name, path);
    }

    public void changeCurrent(String name){
        if (path.containsKey(name))
            this.current = name;
    }
}

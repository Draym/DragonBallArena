package com.andres_k.components.graphicComponents.background;

import com.andres_k.utils.stockage.Pair;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andres_k on 07/10/2015.
 */
public class Background {
    protected List<Pair<Integer, Integer>> positions;
    protected Map<String, String> path;
    protected float backgroundSizeY;
    protected List<Image> images;
    protected boolean launched;
    protected String current;

    public Background() {
        this.images = new ArrayList<>();
        this.positions = new ArrayList<>();
        this.launched = false;
        this.path = new HashMap<>();
    }

    // FUNCTIONS
    public void draw(Graphics g) {
        if (this.launched)
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

        this.images.clear();
        this.images.add(background);
        this.backgroundSizeY = background.getHeight();
        this.positions.clear();
        this.positions.add(new Pair<>(0, 0));
    }

    // SETTERS
    public void addBackground(BackgroundEnum background) {
        this.current = background.getName();
        this.path.put(background.getName(), background.getPath());
    }

    public void changeCurrent(String name){
        if (path.containsKey(name))
            this.current = name;
    }
}

package com.andres_k;

import com.andres_k.master.MasterGame;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 08/07/2015.
 */

public class Main {

    public static void main(String args[]) {
        try {
            MasterGame game = new MasterGame();
            game.start();
        } catch (SlickException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

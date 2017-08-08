package com.andres_k.custom.component.graphicComponents.graphic;

import com.andres_k.custom.component.graphicComponents.graphic.multiWait.WindowBattleConnection;
import com.andres_k.custom.component.graphicComponents.graphic.select.WindowSelectMulti;
import com.andres_k.custom.component.graphicComponents.graphic.select.WindowSelectSolo;
import com.andres_k.custom.component.graphicComponents.graphic.select.WindowSelectVersus;
import com.andres_k.gameToolsLib.components.graphicComponents.graphic.Windows;
import org.codehaus.jettison.json.JSONException;
import org.newdawn.slick.SlickException;

/**
 * Created by kevin on 03/05/2017.
 */
public class MyWindows extends Windows {
    public MyWindows(String name) throws JSONException, SlickException {
        super(name);
    }

    @Override
    protected void addWindows() throws SlickException, JSONException {
        this.windows.add(new WindowSelectSolo(this.windowsTask));
        this.windows.add(new WindowSelectVersus(this.windowsTask));
        this.windows.add(new WindowSelectMulti(this.windowsTask));
        this.windows.add(new WindowBattleConnection(this.windowsTask));
    }
}

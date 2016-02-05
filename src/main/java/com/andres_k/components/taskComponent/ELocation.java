package com.andres_k.components.taskComponent;

import com.andres_k.utils.configs.GlobalVariable;

/**
 * Created by andres_k on 16/05/2015.
 */
public enum ELocation {
    UNKNOWN("UNKNOWN"), MASTER("MASTER"),

    //category
    WINDOWS("WINDOWS", MASTER.getId()), SERVER("SERVER", MASTER.getId()),

    // server
    SERVER_MESSAGE("SERVER_MESSAGE", SERVER.getId()),
    SERVER_CONFIG("SERVER_CONFIG", SERVER.getId()),
    //windows
    INPUT("INPUT", WINDOWS.getId()),
    GAME_CONTROLLER("GAME_CONTROLLER", WINDOWS.getId()), GAME_OBJECT_CONTROLLER("GAME_OBJECT_CONTROLLER", GAME_CONTROLLER.getId()),
    HOME_CONTROLLER("HOME_CONTROLLER", WINDOWS.getId()),
    LOAD_CONTROLLER("LOAD_CONTROLLER", WINDOWS.getId()),
    // gui
    HOME_GUI("HOME_GUI", WINDOWS.getId()),
    GAME_GUI("GAME_GUI", WINDOWS.getId()),
    LOAD_GUI("LOAD_GUI", WINDOWS.getId()),

    //LOAD_GUI
    LOAD_GUI_LoadingBar("LoadingBar", LOAD_GUI.getId());

    private String name;
    private String parent;

    ELocation(String parent) {
        this.name = parent;
        this.parent = parent;
    }

    ELocation(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    private String getParent() {
        return this.parent;
    }

    private String getName() {
        return this.name;
    }

    public boolean isIn(ELocation parent) {
        return this.getId().contains(parent.getName());
    }

    public String getId() {
        if (this.parent.equals(this.name)) {
            return this.parent;
        } else {
            return this.parent + GlobalVariable.id_delimiter + this.name;
        }
    }

    public static ELocation getEnumByLocation(String location) {
        ELocation[] enums = ELocation.values();
        for (ELocation type : enums) {
            if (type.getId().equals(location)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return this.getId();
    }
}

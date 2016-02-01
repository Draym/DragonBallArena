package com.andres_k.components.taskComponent;

import com.andres_k.utils.configs.GlobalVariable;

/**
 * Created by andres_k on 16/05/2015.
 */
public enum EnumLocation {
    UNKNOWN("UNKNOWN"), MASTER("MASTER"),

    //category
    WINDOWS("WINDOWS", MASTER.getLocation()), SERVER("SERVER", MASTER.getLocation()),

    // server
    SERVER_MESSAGE("SERVER_MESSAGE", SERVER.getLocation()),
    SERVER_CONFIG("SERVER_CONFIG", SERVER.getLocation()),
    //windows
    INPUT("INPUT", WINDOWS.getLocation()),
    GAME_CONTROLLER("GAME_CONTROLLER", WINDOWS.getLocation()), GAME_OBJECT_CONTROLLER("GAME_OBJECT_CONTROLLER", GAME_CONTROLLER.getLocation()),
    HOME_CONTROLLER("HOME_CONTROLLER", WINDOWS.getLocation()),
    LOAD_CONTROLLER("LOAD_CONTROLLER", WINDOWS.getLocation()),
    // gui
    HOME_GUI("HOME_GUI", HOME_CONTROLLER.getLocation()),
    GAME_GUI("GAME_GUI", GAME_CONTROLLER.getLocation()),
    LOAD_GUI("LOAD_GUI", LOAD_CONTROLLER.getLocation());

    private String value;
    private String parent;

    EnumLocation(String parent) {
        this.value = parent;
        this.parent = parent;
    }

    EnumLocation(String value, String parent) {
        this.value = value;
        this.parent = parent;
    }

    private String getParent() {
        return this.parent;
    }

    private String getValue() {
        return this.value;
    }

    public boolean isIn(EnumLocation parent) {
        return this.getLocation().contains(parent.getValue());
    }

    public String getLocation() {
        if (this.parent.equals(this.value)) {
            return this.parent;
        } else {
            return this.parent + GlobalVariable.id_delimiter + this.value;
        }
    }

    public static EnumLocation getEnumByLocation(String location) {
        EnumLocation[] enums = EnumLocation.values();
        for (EnumLocation type : enums) {
            if (type.getLocation().equals(location)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}

package com.andres_k.components.taskComponent;

import com.andres_k.utils.configs.GlobalVariable;

/**
 * Created by andres_k on 16/05/2015.
 */
public enum ELocation {
    UNKNOWN("UNKNOWN"), MASTER("MASTER"),

    //tools
    MUSIC_CONTROLLER("MUSIC_CONTROLLER", MASTER.getId()), SOUND_CONTROLLER("SOUND_CONTROLLER", MASTER.getId()),

    //category
    WINDOWS("WINDOWS", MASTER.getId()), SERVER("SERVER", MASTER.getId()),

    //windows
    INPUT("INPUT", WINDOWS.getId()),
    GAME_CONTROLLER("GAME_CONTROLLER", WINDOWS.getId()), GAME_OBJECT_CONTROLLER("GAME_OBJECT_CONTROLLER", GAME_CONTROLLER.getId()),
    HOME_CONTROLLER("HOME_CONTROLLER", WINDOWS.getId()),
    LOAD_CONTROLLER("LOAD_CONTROLLER", WINDOWS.getId()),
    SELECT_SOLO_CONTROLLER("SELECT_SOLO_CONTROLLER", WINDOWS.getId()),
    SELECT_VERSUS_CONTROLLER("SELECT_VERSUS_CONTROLLER", WINDOWS.getId()),
    SELECT_MULTI_CONTROLLER("SELECT_MULTI_CONTROLLER", WINDOWS.getId()),
    BATTLE_CONNECTION_CONTROLLER("BATTLE_CONNECTION_CONTROLLER", WINDOWS.getId()),

    // gui
    HOME_GUI("HOME_GUI", WINDOWS.getId()),
    GAME_GUI("GAME_GUI", WINDOWS.getId()),
    LOAD_GUI("LOAD_GUI", WINDOWS.getId()),
    SELECT_SOLO_GUI("SELECT_SOLO_GUI", WINDOWS.getId()),
    SELECT_VERSUS_GUI("SELECT_VERSUS_GUI", WINDOWS.getId()),
    SELECT_MULTI_GUI("SELECT_MULTI_GUI", WINDOWS.getId()),
    BATTLE_CONNECTION_GUI("BATTLE_CONNECTION_GUI", WINDOWS.getId()),

    //GUI_GLOBAL_ELEMENT
    GUI_ELEMENT_PlayerControls("Controls", MASTER.getId()),
    GUI_ELEMENT_CombosList("CombosList", MASTER.getId()),
    GUI_ELEMENT_Volumes("Volumes", MASTER.getId()),

    //LOAD_GUI
    LOAD_GUI_LoadingBar_value("LoadingBar_value", LOAD_GUI.getId()),
    LOAD_GUI_LoadingBar_title("LoadingBar_title", LOAD_GUI.getId()),

    //HOME_GUI
    HOME_GUI_Menu("Menu", HOME_GUI.getId()),
    HOME_GUI_Settings("Settings", HOME_GUI.getId()),
    HOME_GUI_Controls("Controls", HOME_GUI.getId()),

    //SELECT_SOLO_GUI
    SELECT_SOLO_GUI_Options("Options", SELECT_SOLO_GUI.getId()),
    SELECT_SOLO_GUI_Settings("Settings", SELECT_SOLO_GUI.getId()),
    SELECT_SOLO_GUI_Controls("Controls", SELECT_SOLO_GUI.getId()),
    SELECT_SOLO_GUI_Combos("Combos", SELECT_SOLO_GUI.getId()),
    SELECT_SOLO_GUI_SelectPackPlayer("SelectPackPlayer", SELECT_SOLO_GUI.getId()),
    SELECT_SOLO_GUI_ChoicePlayer1("ChoicePlayer1", SELECT_SOLO_GUI.getId()),

    //SELECT_VERSUS_GUI
    SELECT_VERSUS_GUI_Options("Options", SELECT_VERSUS_GUI.getId()),
    SELECT_VERSUS_GUI_Settings("Settings", SELECT_VERSUS_GUI.getId()),
    SELECT_VERSUS_GUI_Controls("Controls", SELECT_VERSUS_GUI.getId()),
    SELECT_VERSUS_GUI_Combos("Combos", SELECT_VERSUS_GUI.getId()),
    SELECT_VERSUS_GUI_SelectPackPlayer("SelectPackPlayer", SELECT_VERSUS_GUI.getId()),
    SELECT_VERSUS_GUI_ChoicePlayer1("ChoicePlayer1", SELECT_VERSUS_GUI.getId()),
    SELECT_VERSUS_GUI_ChoicePlayer2("ChoicePlayer2", SELECT_VERSUS_GUI.getId()),

    //SELECT_MULTI_GUI
    SELECT_MULTI_GUI_Options("Options", SELECT_MULTI_GUI.getId()),
    SELECT_MULTI_GUI_Settings("Settings", SELECT_MULTI_GUI.getId()),
    SELECT_MULTI_GUI_Controls("Controls", SELECT_MULTI_GUI.getId()),
    SELECT_MULTI_GUI_Combos("Combos", SELECT_MULTI_GUI.getId()),
    SELECT_MULTI_GUI_SelectPackPlayer("SelectPackPlayer", SELECT_MULTI_GUI.getId()),
    SELECT_MULTI_GUI_ChoicePlayer1("ChoicePlayer1", SELECT_MULTI_GUI.getId()),

    //BATTLE_CONNECTION_GUI
    BATTLE_CONNECTION_GUI_Options("Options", BATTLE_CONNECTION_GUI.getId()),
    BATTLE_CONNECTION_GUI_Settings("Settings", BATTLE_CONNECTION_GUI.getId()),
    BATTLE_CONNECTION_GUI_Controls("Controls", BATTLE_CONNECTION_GUI.getId()),
    BATTLE_CONNECTION_GUI_Combos("Combos", BATTLE_CONNECTION_GUI.getId()),

    //GAME_GUI
    GAME_GUI_Options("Options", GAME_GUI.getId()),
    GAME_GUI_Settings("Settings", GAME_GUI.getId()),
    GAME_GUI_Controls("Controls", GAME_GUI.getId()),
    GAME_GUI_Combos("Combos", GAME_GUI.getId()),
    GAME_GUI_AnimStart("AnimStart", GAME_GUI.getId()),
    GAME_GUI_PanelQuit("PanelQuit", GAME_GUI.getId()),
    GAME_GUI_PanelQuit_Details("Details", GAME_GUI_PanelQuit.getId()),
    GAME_GUI_State_AlliedPlayers("StateAlliedPlayers", GAME_GUI.getId()),
    GAME_GUI_State_EnemyPlayers("StateEnemyPlayers", GAME_GUI.getId());

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

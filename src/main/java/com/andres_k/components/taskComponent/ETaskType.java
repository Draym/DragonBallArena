package com.andres_k.components.taskComponent;

/**
 * Created by andres_k on 11/07/2015.
 */
public enum ETaskType {
    //Interface generic
    ADD,
    CREATE,
    DELETE,
    GETTER,
    SETTER,

    // gui
    RELAY,
    SEND_TO,
    EVENT,
    CUT,
    START_ACTIVITY,
    STOP_ACTIVITY,
    CLEAR_ACTIVITY,
    START_TIMER,
    STOP_TIMER,
    ON_CREATE,
    ON_KILL,
    ON_FOCUS,
    OFF_FOCUS,
    ON_CLICK,
    OFF_CLICK,

    // ACTION
    LAUNCH,
    EXIT,
    NEXT,

    //others
    UPGRADE_SCORE,
    CHANGE_VOLUME,
    TRANSFORM,

    //Sound
    PLAY_SOUND,

    //Game move
    MOVE,
    GRAVITY,
    STATIC
}

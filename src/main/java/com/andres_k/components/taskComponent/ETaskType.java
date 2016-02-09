package com.andres_k.components.taskComponent;

/**
 * Created by andres_k on 11/07/2015.
 */
public enum ETaskType {
    //Interface generic
    GETTER,
    SETTER,

    SEND_TO,
    EVENT,
    CUT,

    START_ACTIVITY,
    STOP_ACTIVITY,
    CLEAR_ACTIVITY,
    START_TIMER,
    STOP_TIMER,

    // ACTION
    START,
    EXIT,
    NEXT,

    //others
    UPGRADE_SCORE,

    //Game move
    MOVE,
    GRAVITY,
    STATIC,

    //Switch Screen
    TO_SELECT_SOLO,
    TO_SELECT_VERSUS,
    TO_SELECT_MULTI
}

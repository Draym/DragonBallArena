package com.andres_k.components.eventComponent.events;

import com.andres_k.components.eventComponent.input.EInput;
import com.andres_k.utils.tools.Console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andres_k on 23/10/2015.
 */
public class EventController {
    private HashMap<EInput, Boolean> activatedEvent;
    private List<EInput> eventHistory;
    private List<EInput> eventStack;
    private List<EInput> validToHistory;
    private EInput eventSaved;

    public EventController() {
        this.activatedEvent = new HashMap<>();
        this.eventHistory = new ArrayList<>();
        this.eventStack = new ArrayList<>();
        this.validToHistory = new ArrayList<>();
        this.validToHistory.add(EInput.MOVE_UP);
        //this.validToHistory.add(EInput.MOVE_DOWN);
        this.validToHistory.add(EInput.MOVE_RIGHT);
        this.validToHistory.add(EInput.MOVE_LEFT);
        this.eventSaved = EInput.NOTHING;
    }

    public void reset() {
        for (Map.Entry<EInput, Boolean> entry : this.activatedEvent.entrySet()) {
            entry.setValue(false);
        }
        this.eventHistory.clear();
        this.eventStack.clear();
        this.eventSaved = EInput.NOTHING;
    }

    // SETTERS
    public void addEvent(EInput event) {
        this.activatedEvent.putIfAbsent(event, false);
    }

    public void setActivated(EInput event, boolean value) {
        if (this.activatedEvent.containsKey(event)) {
            if (value) {
                this.eventSaved = event;
            }
            this.activatedEvent.put(event, value);
            if (value) {
                if (this.validToHistory.contains(event)) {
                    this.eventHistory.add(0, event);
                }
                this.eventStack.add(event);
            } else {
                if (this.eventHistory.contains(event)) {
                    this.eventHistory.remove(this.eventHistory.lastIndexOf(event));
                }
                if (this.eventStack.contains(event)) {
                    this.eventStack.remove(this.eventStack.indexOf(event));
                }
            }
        }
    }

    // GETTERS
    public boolean isActivated(EInput event) {
        if (this.activatedEvent.containsKey(event)) {
            return this.activatedEvent.get(event);
        }
        return false;
    }

    public boolean allInactive() {
        for (Map.Entry<EInput, Boolean> entry : this.activatedEvent.entrySet()) {
            if (entry.getValue()) {
                return false;
            }
        }
        return this.eventSaved == EInput.NOTHING;
    }

    public EInput getMoreRecentEventBetween(EInput v1, EInput v2) {
        int result1 = this.eventHistory.indexOf(v1);
        int result2 = this.eventHistory.indexOf(v2);

        if (result1 == -1 && result2 == -1)
            return EInput.NOTHING;
        else if (result1 == -1)
            return v2;
        else if (result2 == -1)
            return v1;
        else if (result1 < result2)
            return v1;
        else
            return v2;
    }

    public EInput getTheLastEvent() {
        if (this.eventHistory.size() > 0) {
            return this.eventHistory.get(0);
        }
        return EInput.NOTHING;
    }

    public EInput consumeStackEvent() {
        if (this.eventStack.size() > 0) {
            if (this.eventSaved == this.eventStack.get(0)) {
                this.eventSaved = EInput.NOTHING;
            }
            return this.eventStack.remove(0);
        } else {
            EInput result = this.eventSaved;
            this.eventSaved = EInput.NOTHING;
            return result;
        }
    }

    public void addStackEvent(EInput input) {
        Console.write("input: " + input);
        this.eventStack.add(input);
    }
}

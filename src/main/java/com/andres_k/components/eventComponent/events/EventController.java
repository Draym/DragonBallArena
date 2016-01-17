package com.andres_k.components.eventComponent.events;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.utils.tools.Console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andres_k on 23/10/2015.
 */
public class EventController {
    private HashMap<EnumInput, Boolean> activatedEvent;
    private List<EnumInput> eventHistory;
    private List<EnumInput> eventStack;
    private List<EnumInput> validToHistory;

    public EventController() {
        this.activatedEvent = new HashMap<>();
        this.eventHistory = new ArrayList<>();
        this.eventStack = new ArrayList<>();
        this.validToHistory = new ArrayList<>();
        this.validToHistory.add(EnumInput.MOVE_UP);
        this.validToHistory.add(EnumInput.MOVE_DOWN);
        this.validToHistory.add(EnumInput.MOVE_RIGHT);
        this.validToHistory.add(EnumInput.MOVE_LEFT);
    }

    public void reset() {
        for (Map.Entry<EnumInput, Boolean> entry : this.activatedEvent.entrySet()) {
            entry.setValue(false);
        }
        this.eventHistory.clear();
        this.eventStack.clear();
    }

    // SETTERS
    public void addEvent(EnumInput event) {
        this.activatedEvent.putIfAbsent(event, false);
    }

    public void setActivated(EnumInput event, boolean value) {
        if (this.activatedEvent.containsKey(event)) {
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
    public boolean isActivated(EnumInput event) {
        if (this.activatedEvent.containsKey(event)) {
            return this.activatedEvent.get(event);
        }
        return false;
    }

    public boolean allInactive() {
        for (Map.Entry<EnumInput, Boolean> entry : this.activatedEvent.entrySet()) {
            if (entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public EnumInput getMoreRecentEventBetween(EnumInput v1, EnumInput v2) {
        int result1 = this.eventHistory.indexOf(v1);
        int result2 = this.eventHistory.indexOf(v2);

        if (result1 == -1 && result2 == -1)
            return EnumInput.NOTHING;
        else if (result1 == -1)
            return v2;
        else if (result2 == -1)
            return v1;
        else if (result1 < result2)
            return v1;
        else
            return v2;
    }

    public EnumInput getTheLastEvent() {
        if (this.eventHistory.size() > 0) {
            return this.eventHistory.get(0);
        } else {
            return EnumInput.NOTHING;
        }
    }

    public EnumInput consumeStackEvent() {
        if (this.eventStack.size() > 0) {
            return this.eventStack.remove(0);
        } else {
            return EnumInput.NOTHING;
        }
    }

    public void addStackEvent(EnumInput input)
    {
        Console.write("input: " + input);
        this.eventStack.add(input);
    }
}
package com.andres_k.components.eventComponent.events;

import com.andres_k.components.eventComponent.input.EnumInput;
import com.andres_k.utils.stockage.Pair;

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
    private List<Pair<EnumInput, EnumInput>> eventStack;

    public EventController() {
        this.activatedEvent = new HashMap<>();
        this.eventHistory = new ArrayList<>();
        this.eventStack = new ArrayList<>();
    }

    // SETTERS
    public void addEvent(EnumInput event) {
        this.activatedEvent.putIfAbsent(event, false);
    }

    public void setActivated(EnumInput event, boolean value) {
        if (this.activatedEvent.containsKey(event)) {
            this.activatedEvent.put(event, value);
            if (value) {
                this.eventHistory.add(0, event);
                this.eventStack.add(new Pair<>(EnumInput.PRESSED, event));
            } else {
                if (this.eventHistory.lastIndexOf(event) != -1)
                    this.eventHistory.remove(this.eventHistory.lastIndexOf(event));
                this.eventStack.add(new Pair<>(EnumInput.RELEASED, event));
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
        if (this.eventHistory.size() > 0)
            return this.eventHistory.get(0);
        else
            return EnumInput.NOTHING;
    }

    public List<Pair<EnumInput, EnumInput>> getEventStack() {
        return this.eventStack;
    }
}

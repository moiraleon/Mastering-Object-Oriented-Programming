package com.codinghelmet.moreoojava.rules;

import com.codinghelmet.moreoojava.Action;
import com.codinghelmet.moreoojava.states.DeviceStatus;

import java.util.function.Consumer;

public class ReducedRule<T extends DeviceStatus> implements Action {
    private T status;
    private Consumer<T> action;

    public ReducedRule(T status, Consumer<T> action) {
        this.status = status;
        this.action = action;
    }

    @Override
    public void apply() {
        this.action.accept(this.status);
    }
}

































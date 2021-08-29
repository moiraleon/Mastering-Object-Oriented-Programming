package com.codinghelmet.moreoojava.rules;

import com.codinghelmet.moreoojava.Action;
import com.codinghelmet.moreoojava.ClaimingRule;
import com.codinghelmet.moreoojava.states.DeviceStatus;
import com.codinghelmet.moreoojava.states.OperationalStatus;

import java.util.Optional;
import java.util.function.Consumer;

public class RuleFixture<T extends DeviceStatus> implements ClaimingRule {
    private RootCondition<T> condition;
    private Consumer<T> action;

    public RuleFixture(RootCondition<T> condition, Consumer<T> action) {
        this.condition = condition;
        this.action = action;
    }

    @Override
    public Optional<Action> applicableTo(DeviceStatus status) {
        return this.condition
            .applicableTo(status)
            .map(s -> new ReducedRule<>(s, this.action));
    }
}

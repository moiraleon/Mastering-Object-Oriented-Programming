package com.codinghelmet.moreoojava.rules;

import com.codinghelmet.moreoojava.ClaimingRule;
import com.codinghelmet.moreoojava.states.DeviceStatus;
import com.codinghelmet.moreoojava.states.OperationalStatus;

import java.util.Optional;
import java.util.function.Consumer;

public interface RootCondition<T extends DeviceStatus> {
    Optional<T> applicableTo(DeviceStatus status);

    default ClaimingRule applies(Consumer<T> action) {
        return new RuleFixture<>(this, action);
    }
}

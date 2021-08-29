package com.codinghelmet.moreoojava.rules;

import com.codinghelmet.moreoojava.states.DeviceStatus;
import com.codinghelmet.moreoojava.states.OperationalStatus;

public class State {
    public static RootCondition<DeviceStatus> matching(OperationalStatus status) {
        return matching(status, DeviceStatus.class);
    }

    public static <T extends DeviceStatus> RootCondition<T> matching(OperationalStatus pattern, Class<T> stateType) {
        return new AppendingCondition<>(
            new StatusTypeCondition<>(stateType),
            new OperationalCondition<>(pattern)
        );
    }
}

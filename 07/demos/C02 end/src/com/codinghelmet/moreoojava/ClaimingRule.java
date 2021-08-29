package com.codinghelmet.moreoojava;

import java.util.Optional;

public interface ClaimingRule {
    Optional<Action> applicableTo(DeviceStatus status);
}

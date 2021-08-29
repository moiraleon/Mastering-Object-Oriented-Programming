package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.states.DeviceStatus;
import com.codinghelmet.moreoojava.states.SensorFailedStatus;

import java.util.function.Consumer;

public interface ClaimingRulesBuilder {
    ClaimingRulesBuilder onMoneyBack(Consumer<DeviceStatus> action);
    ClaimingRulesBuilder onClaimExpress(Consumer<DeviceStatus> action);
    ClaimingRulesBuilder onClaimExtended(Consumer<SensorFailedStatus> action);
    ClaimingRule build();
}

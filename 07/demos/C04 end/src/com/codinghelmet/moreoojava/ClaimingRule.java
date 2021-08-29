package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.rules.ChainedRule;
import com.codinghelmet.moreoojava.states.OperationalStatus;

import java.util.Optional;

public interface ClaimingRule {
    Optional<Action> applicableTo(OperationalStatus status);
    default ClaimingRule orElse(ClaimingRule next) {
        return new ChainedRule(this, next);
    }
}







































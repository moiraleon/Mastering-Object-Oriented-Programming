package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.common.Money;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface Painter {
    Optional<Painter> available();
    Duration estimateTimeToPaint(double sqMeters);
    Money estimateCompensation(double sqMeters);
    String getName();

    default WorkAssignment assign(double sqMeters) {
        return new WorkAssignment(this, sqMeters);
    }

    default Velocity estimateVelocity(double sqMeters) {
        return new Velocity(sqMeters, this.estimateTimeToPaint(sqMeters));
    }

    static PaintersStream stream(List<Painter> painters) {
        return new PaintersStream(painters.stream());
    }
}

package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.common.Money;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface Painter {
    OptionalPainter available();
    Duration estimateTimeToPaint(double sqMeters);
    Money estimateCompensation(double sqMeters);
    String getName();
    double estimateSqMeters(Duration time);

    default PaintersStream with(Painter other) {
        return new PaintersStream(Stream.of(this, other));
    }

    default PaintersStream with(Optional<Painter> other) {
        return other
            .map(someone -> this.with(someone))
            .orElse(new PaintersStream(Stream.of(this)));
    }

    default PaintersStream with(OptionalPainter other) {
        return this.with(other.asOptional());
    }

    default Velocity estimateVelocity(double sqMeters) {
        return new Velocity(sqMeters, this.estimateTimeToPaint(sqMeters));
    }

    default WorkAssignment assign(double sqMeters) {
        return new WorkAssignment(this, sqMeters);
    }

    static PaintersStream stream(List<Painter> painters) {
        return new PaintersStream(painters.stream());
    }
}

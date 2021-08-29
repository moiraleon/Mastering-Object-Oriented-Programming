package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.common.Money;
import com.codinghelmet.moreoojava.common.TimeUtils;

import java.time.Duration;
import java.util.stream.Stream;

public class WorkAssignment {
    private Painter painter;
    private double sqMeters;

    public WorkAssignment(Painter painter, double sqMeters) {
        this.painter = painter;
        this.sqMeters = sqMeters;
    }

    public Money estimateCompensation() {
        return this.painter.estimateCompensation(this.sqMeters);
    }

    public Duration estimateTimeToPaint() {
        return this.painter.estimateTimeToPaint(this.sqMeters);
    }

    public static WorkStream stream(Stream<WorkAssignment> assignments) {
        return new WorkStream(assignments);
    }

    @Override
    public String toString() {
        Money compensation = this.painter.estimateCompensation(sqMeters);
        Duration totalTime = this.painter.estimateTimeToPaint(sqMeters);
        String formattedTime = TimeUtils.format(totalTime);

        return String.format(
                "Letting %s paint %.2f sq. meters during %s at total cost %s",
                this.painter.getName(), this.sqMeters, formattedTime, compensation);
    }
}

package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.common.Money;
import com.codinghelmet.moreoojava.common.MoneyRate;
import com.codinghelmet.moreoojava.common.TimeUtils;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Demo {
    private MoneyRate perHour(double amount) {
        return MoneyRate.hourly(new Money(new BigDecimal(amount)));
    }

    private List<Painter> createPainters1() {
        return Arrays.asList(
            new ProportionalPainter("Joe", 2.12, this.perHour(44)),
            new ProportionalPainter("Jill", 4.16, this.perHour(60)),
            new ProportionalPainter("Jack", 1.19, this.perHour(16))
        );
    }

    private void print(List<Painter> painters) {
        System.out.println("Painters:");
        for (Painter painter: painters) {
            System.out.println(painter);
        }
    }

    private void print(Painter painter, double sqMeters) {
        Money compensation = painter.estimateCompensation(sqMeters);
        Duration totalTime = painter.estimateTimeToPaint(sqMeters);
        String formattedTime = TimeUtils.format(totalTime);

        System.out.printf(
            "Letting %s paint %.2f sq. meters during %s at total cost %s\n",
            painter.getName(), sqMeters, formattedTime, compensation);
    }

    private static Optional<Painter> findCheapest1(double sqMeters, List<Painter> painters) {
        return painters.stream()
            .filter(Painter::isAvailable)
            .min(Comparator.comparing(painter -> painter.estimateCompensation(sqMeters)));
    }

    private static Optional<Painter> findCheapest2(double sqMeters, List<Painter> painters) {
        return Painter.stream(painters).available().cheapest(sqMeters);
    }

    private static Money getTotalCost(double sqMeters, List<Painter> painters) {
        return painters.stream()
            .filter(Painter::isAvailable)
            .map(painter -> painter.estimateCompensation(sqMeters))
            .reduce(Money::add)
            .orElse(Money.ZERO);
    }

    public void workTogether1(double sqMeters, List<Painter> painters) {
        Velocity groupVelocity =
            Painter.stream(painters).available()
                .map(painter -> painter.estimateVelocity(sqMeters))
                .reduce(Velocity::add)
                .orElse(Velocity.ZERO);

        Painter.stream(painters).available()
            .forEach(painter -> {
                double partialSqMeters = sqMeters * painter.estimateVelocity(sqMeters).divideBy(groupVelocity);
                this.print(painter, partialSqMeters);
            });
    }

    public void run() {
        List<Painter> painters1 = this.createPainters1();
        double sqMeters = 200;

        this.print(painters1);

        System.out.println();
        System.out.println("Demo #1 - Letting all painters work together");
        this.workTogether1(sqMeters, painters1);
    }
}
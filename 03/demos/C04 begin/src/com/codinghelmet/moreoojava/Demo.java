package com.codinghelmet.moreoojava;

import java.util.Comparator;
import java.util.List;

public class Demo {
    private static Painter findCheapest1(double sqMeters, List<Painter> painters) {
        return painters.stream()
            .filter(Painter::isAvailable)
            .min(Comparator.comparing(painter -> painter.estimateCompensation(sqMeters)))
            .get();
    }

    private static Money getTotalCost(double sqMeters, List<Painter> painters) {
        return painters.stream()
            .filter(Painter::isAvailable)
            .map(painter -> painter.estimateCompensation(sqMeters))
            .reduce(Money::add)
            .orElse(Money.ZERO);
    }

    public void run() {
    }
}
package com.codinghelmet.moreoojava;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class SelectingScheduler implements PaintingScheduler {
    private Function<Double, Comparator<Painter>> minByFactory;

    public SelectingScheduler(Function<Double, Comparator<Painter>> minBy) {
        this.minByFactory = minBy;
    }

    @Override
    public WorkStream schedule(List<Painter> painters, double sqMeters) {
        return this.getWinner(painters, sqMeters).assign(sqMeters).stream();
    }

    public static PaintingScheduler fastest() {
        return new SelectingScheduler(SelectingScheduler::faster);
    }

    public static PaintingScheduler cheapest() {
        return new SelectingScheduler(SelectingScheduler::cheaper);
    }

    private static Comparator<Painter> faster(double sqMeters) {
        return Comparator.comparing(painter -> painter.estimateTimeToPaint(sqMeters));
    }

    private static Comparator<Painter> cheaper(double sqMeters) {
        return Comparator.comparing(painter -> painter.estimateCompensation(sqMeters));
    }

    private OptionalPainter getWinner(List<Painter> painters, double sqMeters) {
        return this.getWinner(painters, this.getComparator(sqMeters));
    }

    private OptionalPainter getWinner(List<Painter> painters, Comparator<Painter> minBy) {
        return this.available(painters)
            .min(minBy)
            .map(OptionalPainter::of)
            .orElse(OptionalPainter.empty());
    }

    private PaintersStream available(List<Painter> painters) {
        return Painter.stream(painters).available();
    }

    private Comparator<Painter> getComparator(double sqMeters) {
        return this.minByFactory.apply(sqMeters);
    }
}

package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.common.Money;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompositePainter implements Painter {
    private List<Painter> subordinatePainters;
    private PaintingScheduler scheduler;

    private CompositePainter(List<Painter> subordinatePainters, PaintingScheduler scheduler) {
        this.subordinatePainters = subordinatePainters;
        this.scheduler = scheduler;
    }

    public static OptionalPainter of(List<Painter> subordinatePainters, PaintingScheduler scheduler) {
        return subordinatePainters.isEmpty()
            ? OptionalPainter.empty()
            : OptionalPainter.of(new CompositePainter(subordinatePainters, scheduler));
    }

    @Override
    public OptionalPainter available() {
        return painters().available().workTogether(this.scheduler);
    }

    @Override
    public Duration estimateTimeToPaint(double sqMeters) {
        return schedule(sqMeters).timesToPaint().maxOfMany();
    }

    @Override
    public Money estimateCompensation(double sqMeters) {
        return schedule(sqMeters).compensations().sum();
    }

    @Override
    public double estimateSqMeters(Duration time) {
        return painters().estimateSqMeters(time);
    }

    private WorkStream schedule(double sqMeters) {
        return this.scheduler.schedule(this.subordinatePainters, sqMeters);
    }

    @Override
    public String getName() {
        return this.getPainterNames().collect(Collectors.joining(", ", "{ ", " }"));
    }

    private PaintersStream painters() {
        return Painter.stream(this.subordinatePainters);
    }

    private Stream<String> getPainterNames() {
        return Painter.stream(this.subordinatePainters).map(Painter::getName);
    }
}
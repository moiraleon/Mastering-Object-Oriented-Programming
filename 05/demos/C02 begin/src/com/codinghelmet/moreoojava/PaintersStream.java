package com.codinghelmet.moreoojava;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

public class PaintersStream implements ForwardingStream<Painter> {
    private Stream<Painter> stream;

    public PaintersStream(Stream<Painter> stream) {
        this.stream = stream;
    }

    @Override
    public Stream<Painter> getStream() { return this.stream; }

    public PaintersStream available() {
        return new PaintersStream(this.getStream()
            .map(Painter::available)
            .filter(Optional::isPresent)
            .map(Optional::get));
    }

    public Optional<Painter> cheapest(double sqMeters) {
        return this.getStream()
            .min(Comparator.comparing(painter -> painter.estimateCompensation(sqMeters)));
    }

    public Painter fastest(double sqMeters) {
        return this.getStream()
            .min(Comparator.comparing(painter -> painter.estimateTimeToPaint(sqMeters)))
            .get();
    }
}


package com.codinghelmet.moreoojava.common;

import java.util.stream.Stream;

public class MoneysStream implements ForwardingStream<Money> {
    private Stream<Money> stream;

    public MoneysStream(Stream<Money> stream) {
        this.stream = stream;
    }

    @Override
    public Stream<Money> getStream() { return this.stream; }

    public Money sum() {
        return this.stream.reduce(Money::add).orElse(Money.ZERO);
    }
}

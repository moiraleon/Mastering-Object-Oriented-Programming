package com.codinghelmet.moreoojava;

import com.google.common.collect.Streams;

import java.util.Arrays;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class DigitsStream implements ForwardingStream<Integer> {
    private Stream<Integer> stream;

    @Override
    public Stream<Integer> getStream() { return this.stream; }

    private DigitsStream(Stream<Integer> stream) {
        this.stream = stream;
    }

    public static DigitsStream of(Stream<Integer> stream) {
        return new DigitsStream(stream);
    }

    public StraightNumber multiplyWith(int... factors) {
        return new StraightNumber(this.multiplyWithAsInt(factors));
    }

    private int multiplyWithAsInt(int... factors) {
        return Streams.zip(
                this.getStream(),
                this.repeatEndlessly(factors),
                (a, b) -> a * b)
            .mapToInt(n -> n)
            .sum();
    }

    private Stream<Integer> repeatEndlessly(int... factors) {
        return Stream
            .iterate(factors, UnaryOperator.identity())
            .flatMapToInt(Arrays::stream)
            .boxed();
    }
}

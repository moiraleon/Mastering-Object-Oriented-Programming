package com.codinghelmet.moreoojava;

import java.util.stream.Stream;

public class StraightNumber {
    private int value;

    public StraightNumber(int value) {
        if (value <= 0) throw new IllegalArgumentException();
        this.value = value;
    }

    public Stream<Integer> digitsFromLeastSignificant() {
        return Stream
            .iterate(this.value, n -> n / 10)
            .takeWhile(n -> n > 0)
            .map(n -> n % 10);
    }
}

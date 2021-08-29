package com.codinghelmet.moreoojava;

import java.util.stream.Stream;

public class StraightNumber {
    private int value;

    public StraightNumber(int value) {
        if (value <= 0) throw new IllegalArgumentException();
        this.value = value;
    }

    public DigitsStream digitsFromLeastSignificant() {
        return DigitsStream.of(Stream
            .iterate(this.value, n -> n / 10)
            .takeWhile(n -> n > 0)
            .map(n -> n % 10));
    }

    public StraightNumber modulo(int divisor) {
        return new StraightNumber(this.value % divisor);
    }

    public int asDigitOr(int substitute) {
        return this.value < 10 ? this.value : substitute;
    }
}

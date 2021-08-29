package com.codinghelmet.moreoojava;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StraightNumber {
    private int value;

    public StraightNumber(int value) {
        if (value <= 0) throw new IllegalArgumentException();
        this.value = value;
    }

    public DigitsStream digitsFromLeastSignificant() {
        return DigitsStream.of(this.rawDigitsFromLeastSignificant());
    }

    public DigitsStream digitsFromMostSignificant() {
        List<Integer> digits = this.rawDigitsFromLeastSignificant().collect(Collectors.toList());
        Collections.reverse(digits);
        return DigitsStream.of(digits.stream());
    }

    private Stream<Integer> rawDigitsFromLeastSignificant() {
        return Stream
            .iterate(this.value, n -> n / 10)
            .takeWhile(n -> n > 0)
            .map(n -> n % 10);
    }

    public StraightNumber modulo(int divisor) {
        return new StraightNumber(this.value % divisor);
    }

    public int asDigitOr(int substitute) {
        return this.value < 10 ? this.value : substitute;
    }

    public int asDigit() {
        if (this.value >= 10) throw new IllegalStateException();
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}

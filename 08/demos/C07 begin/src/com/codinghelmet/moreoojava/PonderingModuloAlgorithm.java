package com.codinghelmet.moreoojava;

import java.util.function.Function;

public class PonderingModuloAlgorithm implements ControlDigitAlgorithm {
    private Function<StraightNumber, DigitsStream> digitsExtractor;
    private int[] factors;
    private Function<StraightNumber, Integer> reduce;

    public PonderingModuloAlgorithm(
            Function<StraightNumber, DigitsStream> digitsExtractor,
            int[] factors,
            Function<StraightNumber, Integer> reduce) {
        this.digitsExtractor = digitsExtractor;
        this.factors = factors;
        this.reduce = reduce;
    }

    public int getControlDigit(StraightNumber number) {
        return this.reduce.apply(
            this.digitsExtractor.apply(number)
                .multiplyWith(this.factors));
    }
}

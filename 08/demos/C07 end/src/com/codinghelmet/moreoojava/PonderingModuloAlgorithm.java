package com.codinghelmet.moreoojava;

import java.util.function.Function;

public class PonderingModuloAlgorithm implements ControlDigitAlgorithm {
    private Function<StraightNumber, DigitsStream> digitsExtractor;
    private int[] factors;
    private Function<StraightNumber, Integer> reduce;

    private PonderingModuloAlgorithm(
            Function<StraightNumber, DigitsStream> digitsExtractor,
            int[] factors,
            Function<StraightNumber, Integer> reduce) {
        this.digitsExtractor = digitsExtractor;
        this.factors = factors;
        this.reduce = reduce;
    }

    public static ControlDigitAlgorithm multipleDigitsModulo(
            Function<StraightNumber, DigitsStream> digitsExtractor,
            int divisor, int substitute, int... factors) {
        return new PonderingModuloAlgorithm(
            digitsExtractor, factors, n -> n.modulo(divisor).asDigitOr(substitute));
    }

    public static ControlDigitAlgorithm singleDigitModulo(
            Function<StraightNumber, DigitsStream> digitsExtractor,
            int divisor, int... factors) {
        return new PonderingModuloAlgorithm(
                digitsExtractor, factors, n -> n.modulo(divisor).asDigit());
    }

    public int getControlDigit(StraightNumber number) {
        return this.reduce.apply(
            this.digitsExtractor.apply(number)
                .multiplyWith(this.factors));
    }
}

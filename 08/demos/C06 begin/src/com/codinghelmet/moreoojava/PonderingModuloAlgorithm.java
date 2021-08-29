package com.codinghelmet.moreoojava;

public class PonderingModuloAlgorithm implements ControlDigitAlgorithm {
    public int getControlDigit(StraightNumber number) {
        return number.digitsFromLeastSignificant()
            .multiplyWith(3, 1)
            .modulo(11)
            .asDigitOr(0);
    }
}

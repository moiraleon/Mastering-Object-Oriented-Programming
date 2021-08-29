package com.codinghelmet.moreoojava;

public class Demo {
    private int getControlDigit(StraightNumber number) {
        return number.digitsFromLeastSignificant()
            .multiplyWith(3, 1)
            .modulo(11)
            .asDigitOr(0);
    }

    private int getControlDigit(int number) {
        return getControlDigit(new StraightNumber(number));
    }

    public void run() {
        ControlDigitAlgorithm algorithm = new PonderingModuloAlgorithm(
            StraightNumber::digitsFromMostSignificant,
            new int[] { 3, 1 },
            n -> n.modulo(11).asDigitOr(0));

        DocumentNumber docNumber = new DocumentNumber(123456, algorithm);
        System.out.println(docNumber);
    }
}
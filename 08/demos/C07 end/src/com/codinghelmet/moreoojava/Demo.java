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
        System.out.println(new DocumentNumber(123456, ControlDigit.accountingAlgorithm()));
        System.out.println(new DocumentNumber(123456, ControlDigit.salesAlgorithmLegacy()));
        System.out.println(new DocumentNumber(123456, ControlDigit.salesAlgorithmMay2017()));
    }
}
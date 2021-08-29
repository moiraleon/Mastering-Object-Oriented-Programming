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
        int docNumber = 123456;
        int controlDigit = this.getControlDigit(docNumber);
        System.out.println(docNumber + "-" + controlDigit);
    }
}
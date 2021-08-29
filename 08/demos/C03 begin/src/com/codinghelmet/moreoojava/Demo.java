package com.codinghelmet.moreoojava;

public class Demo {
    private int getControlDigit(int number) {
        int sum = 0;
        boolean isOddPos = true;

        while (number > 0) {
            int digit = (int) (number%10);

            if (isOddPos) {
                sum += 3 * digit;
            }
            else {
                sum += digit;
            }

            number /= 10;
            isOddPos = !isOddPos;
        }

        int modulo = sum%11;
        if (modulo > 9)
            modulo = 0;

        return modulo;
    }

    public void run() {
        int docNumber = 123456;
        int controlDigit = this.getControlDigit(docNumber);
        System.out.println(docNumber + "-" + controlDigit);
    }
}
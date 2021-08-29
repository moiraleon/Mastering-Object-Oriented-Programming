package com.codinghelmet.moreoojava;

public class DocumentNumber {
    private StraightNumber raw;
    private ControlDigitAlgorithm algorithm;

    public DocumentNumber(int raw, ControlDigitAlgorithm algorithm) {
        this.raw = new StraightNumber(raw);
        this.algorithm = algorithm;
    }

    private int getControlDigit() {
        return this.algorithm.getControlDigit(this.raw);
    }

    @Override
    public String toString() {
        return this.raw + "-" + this.getControlDigit();
    }
}

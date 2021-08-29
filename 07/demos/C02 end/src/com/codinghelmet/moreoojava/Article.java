package com.codinghelmet.moreoojava;

import java.util.Optional;

public class Article {
    private Warranty moneyBackGuarantee;
    private Warranty expressWarranty;
    private Optional<Part> sensor;
    private Warranty extendedWarranty;

    public Article(Warranty moneyBackGuarantee, Warranty expressWarranty) {
        this(moneyBackGuarantee, expressWarranty, Optional.empty(), Warranty.VOID);
    }

    private Article(
        Warranty moneyBackGuarantee,
        Warranty expressWarranty,
        Optional<Part> sensor,
        Warranty extendedWarranty) {
        this.moneyBackGuarantee = moneyBackGuarantee;
        this.expressWarranty = expressWarranty;
        this.sensor = sensor;
        this.extendedWarranty = extendedWarranty;
    }

    public Warranty getMoneyBackGuarantee() { return this.moneyBackGuarantee; }
    public Warranty getExpressWarranty() { return this.expressWarranty; }

    public Warranty getExtendedWarranty() {
        return this.sensor.map(part -> this.extendedWarranty).orElse(Warranty.VOID);
    }

    public Article install(Part sensor, Warranty extendedWarranty) {
        return new Article(this.moneyBackGuarantee, this.expressWarranty, Optional.of(sensor), extendedWarranty);
    }
}

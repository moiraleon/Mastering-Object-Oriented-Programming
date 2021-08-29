package com.codinghelmet.moreoojava;

import com.codinghelmet.moreoojava.warranties.LifetimeWarranty;
import com.codinghelmet.moreoojava.warranties.VoidWarranty;

import java.time.LocalDate;
import java.util.Optional;

public interface Warranty {
    Warranty on(LocalDate date);
    Optional<Warranty> filter(LocalDate date);
    default void claim(Runnable action) { action.run(); }

    Warranty VOID = new VoidWarranty();

    static Warranty lifetime(LocalDate issuedOn) {
        return new LifetimeWarranty(issuedOn);
    }
}

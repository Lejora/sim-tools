package io.github.Lejora.simtools.eutran;

import io.github.Lejora.simtools.plmn.Plmn;

import static java.util.Objects.requireNonNull;

// E-UTRAN Cell Global Identifier
public record Ecgi(Plmn plmn, Eci eci) {
    public Ecgi {
        requireNonNull(plmn, "PLMN must not be null");
        requireNonNull(eci, "ECI must not be null");
    }
}

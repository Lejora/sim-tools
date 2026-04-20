package io.github.Lejora.simtools.nr;

import io.github.Lejora.simtools.plmn.Plmn;

import static java.util.Objects.requireNonNull;

// NR Cell Global Identifier
public record Ncgi(Plmn plmn, Nci nci) {
    public Ncgi {
        requireNonNull(plmn, "PLMN must not be null");
        requireNonNull(nci, "NCI must not be null");
    }
}

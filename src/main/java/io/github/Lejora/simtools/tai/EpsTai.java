package io.github.Lejora.simtools.tai;

import io.github.Lejora.simtools.plmn.Plmn;

import static java.util.Objects.requireNonNull;

// EPS/LTE Tracking Area Identity
public record EpsTai(Plmn plmn, EpsTac tac) implements Tai {
    public EpsTai {
        requireNonNull(plmn, "PLMN must not be null");
        requireNonNull(tac, "EPS TAC must not be null");
    }

    @Override
    public boolean representsNoValidIdentity() {
        return tac.isReserved() || plmn.isNonCanonical();
    }
}

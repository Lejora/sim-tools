package io.github.Lejora.simtools.tai;

import io.github.Lejora.simtools.plmn.Plmn;

import static java.util.Objects.requireNonNull;

// 5GS Tracking Area Identity
public record FiveGsTai(Plmn plmn, FiveGsTac tac) implements Tai {
    public FiveGsTai {
        requireNonNull(plmn, "PLMN must not be null");
        requireNonNull(tac, "5GS TAC must not be null");
    }

    @Override
    public boolean representsNoValidIdentity() {
        return tac.isReserved() || plmn.isNonCanonical();
    }
}

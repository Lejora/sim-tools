package io.github.Lejora.simtools.lai;

import io.github.Lejora.simtools.plmn.Plmn;

import static java.util.Objects.requireNonNull;

public record Lai(Plmn plmn, int lac) {
    public Lai {
        requireNonNull(plmn, "PLMN must not be null");
    }

    public boolean hasReservedLac() {
        return lac == 0xFFFE || lac == 0x0000;
    }

    public boolean representsNoValidIdentity() {
        return hasReservedLac() || plmn.isNonCanonical();
    }

    @Deprecated
    public boolean hasDeletedLacEncoding() {
        return hasReservedLac();
    }

    @Deprecated
    public boolean shouldBeTreatedAsDeleted() {
        return representsNoValidIdentity();
    }
}

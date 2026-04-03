package io.github.Lejora.simtools.lai;

import io.github.Lejora.simtools.plmn.Plmn;

public record Lai(Plmn plmn, int lac) {
    public boolean hasDeletedLacEncoding() {
        return lac == 0xFFFE || lac == 0x0000;
    }

    public boolean shouldBeTreatedAsDeleted() {
        return hasDeletedLacEncoding() || plmn.isAbnormal();
    }
}

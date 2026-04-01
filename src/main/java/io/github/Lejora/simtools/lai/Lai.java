package io.github.Lejora.simtools.lai;

import io.github.Lejora.simtools.plmn.Plmn;

public record Lai(
        Plmn plmn,
        int lac,
        boolean deleted
) {
    public boolean abnormalPlmn() {
        return plmn.abnormal();
    }
}

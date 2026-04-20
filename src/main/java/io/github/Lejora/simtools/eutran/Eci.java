package io.github.Lejora.simtools.eutran;

import io.github.Lejora.simtools.internal.HexStrings;

// E-UTRAN Cell Identifier
public record Eci(String value) {
    public Eci {
        value = HexStrings.normalize(value, "ECI", "exactly 7 hex digits", 7);
    }
}

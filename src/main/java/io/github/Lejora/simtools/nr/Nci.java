package io.github.Lejora.simtools.nr;

import io.github.Lejora.simtools.internal.HexStrings;

// NR Cell Identifier
public record Nci(String value) {
    public Nci {
        value = HexStrings.normalize(value, "NCI", "exactly 9 hex digits", 9);
    }
}

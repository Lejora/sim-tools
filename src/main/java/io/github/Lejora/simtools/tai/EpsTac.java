package io.github.Lejora.simtools.tai;

import io.github.Lejora.simtools.internal.HexStrings;

// EPS/LTE Tracking Area Code
public record EpsTac(String value) implements Tac {
    private static final String RESERVED_ZERO = "0000";
    private static final String RESERVED_FFFE = "FFFE";

    public EpsTac {
        value = HexStrings.normalize(value, "EPS TAC", "exactly 4 hex digits", 4);
    }

    @Override
    public boolean isReserved() {
        return RESERVED_ZERO.equals(value) || RESERVED_FFFE.equals(value);
    }

    @Override
    public int octetLength() {
        return 2;
    }
}

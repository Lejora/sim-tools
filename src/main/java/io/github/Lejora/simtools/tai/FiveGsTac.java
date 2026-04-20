package io.github.Lejora.simtools.tai;

import io.github.Lejora.simtools.internal.HexStrings;

// 5GS Tracking Area Code
public record FiveGsTac(String value) implements Tac {
    private static final String RESERVED_ZERO = "000000";
    private static final String RESERVED_FFFFFE = "FFFFFE";

    public FiveGsTac {
        value = HexStrings.normalize(value, "5GS TAC", "exactly 6 hex digits", 6);
    }

    @Override
    public boolean isReserved() {
        return RESERVED_ZERO.equals(value) || RESERVED_FFFFFE.equals(value);
    }

    @Override
    public int octetLength() {
        return 3;
    }
}

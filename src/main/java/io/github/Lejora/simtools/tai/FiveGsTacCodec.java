package io.github.Lejora.simtools.tai;

import static java.util.Objects.requireNonNull;

public final class FiveGsTacCodec {
    private FiveGsTacCodec() {}

    public static FiveGsTac decode(byte[] data) {
        return decode(data, 0);
    }

    public static FiveGsTac decode(byte[] data, int offset) {
        if (data == null || data.length < offset + 3) {
            throw new IllegalArgumentException("5GS TAC must be exactly 3 bytes");
        }

        String value = String.format(
            "%02X%02X%02X",
            data[offset] & 0xFF,
            data[offset + 1] & 0xFF,
            data[offset + 2] & 0xFF
        );
        return new FiveGsTac(value);
    }

    public static byte[] encode(FiveGsTac tac) {
        requireNonNull(tac, "5GS TAC must not be null");
        String value = tac.value();

        return new byte[] {
            (byte) Integer.parseInt(value.substring(0, 2), 16),
            (byte) Integer.parseInt(value.substring(2, 4), 16),
            (byte) Integer.parseInt(value.substring(4, 6), 16)
        };
    }
}

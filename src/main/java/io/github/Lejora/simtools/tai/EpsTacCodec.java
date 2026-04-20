package io.github.Lejora.simtools.tai;

import static java.util.Objects.requireNonNull;

public final class EpsTacCodec {
    private EpsTacCodec() {}

    public static EpsTac decode(byte[] data) {
        return decode(data, 0);
    }

    public static EpsTac decode(byte[] data, int offset) {
        if (data == null || data.length < offset + 2) {
            throw new IllegalArgumentException("EPS TAC must be exactly 2 bytes");
        }

        String value = String.format("%02X%02X", data[offset] & 0xFF, data[offset + 1] & 0xFF);
        return new EpsTac(value);
    }

    public static byte[] encode(EpsTac tac) {
        requireNonNull(tac, "EPS TAC must not be null");
        String value = tac.value();

        return new byte[] {
            (byte) Integer.parseInt(value.substring(0, 2), 16),
            (byte) Integer.parseInt(value.substring(2, 4), 16)
        };
    }
}


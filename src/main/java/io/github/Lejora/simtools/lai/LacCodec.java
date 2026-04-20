package io.github.Lejora.simtools.lai;

import static java.util.Objects.requireNonNull;

public class LacCodec {
    private LacCodec() {}

    public static Lac decode(byte[] data, int offset) {
        if (data == null || data.length < offset + 2) {
            throw new IllegalArgumentException("LAC must be exactly 2 bytes");
        }

        // According to 3GPP TS 24.008 v4.17.0:
        // LAC = 0xFFFE or 0x0000 indicates a deleted LAI
        int lac = ((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF);

        return new Lac(lac);
    }

    public static byte[] encode(Lac lac) {
        requireNonNull(lac, "LAC must not be null");
        return encode(lac.value());
    }

    public static byte[] encode(int value) {
        byte msb = (byte) ((value >> 8) & 0xFF);
        byte lsb = (byte) (value & 0xFF);

        return new byte[] { msb, lsb };
    }

    public static byte[] encodeDeleted() {
        return encode(Lac.reserved().value());
    }
}

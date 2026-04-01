package io.github.Lejora.simtools.lai;

public class LacCodec {
    private LacCodec() {}

    public static Lac decode(byte[] data, int offset) {
        if (data == null || data.length < offset + 2) {
            throw new IllegalArgumentException("LAC must be exactly 2 bytes");
        }

        // According to 3GPP TS 24.008 v4.17.0:
        // LAC = 0xFFFE or 0x0000 indicates a deleted LAI
        int lac = ((data[offset] & 0xFF) << 8) | (data[offset + 1] & 0xFF);
        boolean deleted = (lac == 0xFFFE || lac == 0x0000);

        return new Lac(lac, deleted);
    }

    public static byte[] encode(Lac lac) {
        return encode(lac.value(), lac.deleted());
    }

    public static byte[] encode(int value, boolean deleted) {
        byte msb = (byte) ((value >> 8) & 0xFF);
        byte lsb = (byte) (value & 0xFF);

        if (deleted) {
            // on spec, 0xFFFE or 0x0000
            msb = (byte) 0xFF;
            lsb = (byte) 0xFE;
        }

        return new byte[] { msb, lsb };
    }
}
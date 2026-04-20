package io.github.Lejora.simtools.internal;

public final class PackedHexCodec {
    private PackedHexCodec() {}

    public static byte[] encode(String value, int expectedHexDigits, String label) {
        String normalized = HexStrings.normalize(
            value,
            label,
            "exactly " + expectedHexDigits + " hex digits",
            expectedHexDigits
        );

        String padded = "0" + normalized;
        byte[] encoded = new byte[padded.length() / 2];
        for (int i = 0; i < encoded.length; i++) {
            int index = i * 2;
            encoded[i] = (byte) Integer.parseInt(padded.substring(index, index + 2), 16);
        }
        return encoded;
    }

    public static String decode(byte[] data, int offset, int octetLength, int expectedHexDigits, String label) {
        if (data == null || data.length < offset + octetLength) {
            throw new IllegalArgumentException(label + " must be exactly " + octetLength + " bytes");
        }

        StringBuilder builder = new StringBuilder(octetLength * 2);
        for (int i = 0; i < octetLength; i++) {
            builder.append(String.format("%02X", data[offset + i] & 0xFF));
        }

        if (builder.charAt(0) != '0') {
            throw new IllegalArgumentException(label + " spare bits must be zero");
        }

        return builder.substring(1, expectedHexDigits + 1);
    }
}

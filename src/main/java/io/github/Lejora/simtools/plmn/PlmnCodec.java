package io.github.Lejora.simtools.plmn;

import static java.util.Objects.requireNonNull;

public final class PlmnCodec {
    private PlmnCodec() {}

    public static Plmn decode(byte[] data, int offset) {
        if (data == null || data.length < offset + 3) {
            throw new IllegalArgumentException("PLMN must be exactly 3 bytes");
        }

        int b1 = data[offset] & 0xFF;
        int b2 = data[offset + 1] & 0xFF;
        int b3 = data[offset + 2] & 0xFF;

        int mcc1 = b1 & 0x0F;
        int mcc2 = (b1 >> 4) & 0x0F;
        int mcc3 = b2 & 0x0F;

        int mnc3 = (b2 >> 4) & 0x0F;
        int mnc1 = b3 & 0x0F;
        int mnc2 = (b3 >> 4) & 0x0F;

        String mcc = "" + toHexChar(mcc1) + toHexChar(mcc2) + toHexChar(mcc3);

        String mnc;
        boolean twoDigitMnc = (mnc3 == 0xF);
        if (twoDigitMnc) {
            mnc = "" + toHexChar(mnc1) + toHexChar(mnc2);
        } else {
            mnc = "" + toHexChar(mnc1) + toHexChar(mnc2) + toHexChar(mnc3);
        }

        return new Plmn(mcc, mnc);
    }

    public static byte[] encode(Plmn plmn) {
        requireNonNull(plmn, "plmn must not be null");
        String mcc = plmn.mcc();
        String mnc = plmn.mnc();

        boolean isTwoDigitMnc = mnc.length() == 2;
        int[] mccDigits = toHexDigits(mcc);
        int[] mncDigits = toHexDigits(mnc);

        int mnc3 = isTwoDigitMnc ? 0xF : mncDigits[2];
        byte b1 = (byte) (mccDigits[0] | (mccDigits[1] << 4));
        byte b2 = (byte) (mccDigits[2] | (mnc3 << 4));
        byte b3 = (byte) (mncDigits[0] | (mncDigits[1]) << 4);

        return new byte[] { b1, b2, b3 };
    }

    private static int[] toHexDigits(String value) {
        int[] digits = new int[value.length()];
        for (int i = 0; i < value.length(); i++) {
            int nibble = Character.digit(value.charAt(i), 16);
            if (nibble < 0) {
                throw new IllegalArgumentException("Invalid hexadecimal digit '" + value.charAt(i) + "'");
            }
            digits[i] = nibble;
        }
        return digits;
    }

    private static char toHexChar(int nibble) {
        return Character.toUpperCase(Character.forDigit(nibble, 16));
    }
}

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

        boolean abnormal = false;

        if (!isDecimal(mcc1) || !isDecimal(mcc2) || !isDecimal(mcc3)) {
            abnormal = true;
        }
        String mcc = "" + mcc1 + mcc2 + mcc3;

        String mnc;
        boolean twoDigitMnc = (mnc3 == 0xF);
        if (twoDigitMnc) {
            if (!isDecimal(mnc1) || !isDecimal(mnc2)) {
                abnormal = true;
            }
            mnc = "" + mnc1 + mnc2;
        } else {
            if (!isDecimal(mnc1) || !isDecimal(mnc2) || !isDecimal(mnc3)) {
                abnormal = true;
            }
            mnc = "" + mnc1 + mnc2 + mnc3;
        }

        return new Plmn(mcc, mnc, abnormal);
    }

    public static byte[] encode(Plmn plmn) {
        requireNonNull(plmn, "plmn must not be null");
        String mcc = plmn.mcc();
        String mnc = plmn.mnc();

        if (mcc.length() != 3) throw new IllegalArgumentException("MCC must be exactly 3 digits");
        boolean isTwoDigitMnc = mnc.length() == 2;
        if (!isTwoDigitMnc && mnc.length() != 3) throw new IllegalArgumentException("MNC must be 2 or 3 digits");

        // When abnormal, encode digits using full hexadecimal representation
        int radix = plmn.abnormal() ? 16 : 10;
        int[] mccDigits = toDigits(mcc, radix);
        int[] mncDigits = toDigits(mnc, radix);

        int mnc3 = isTwoDigitMnc ? 0xF : mncDigits[2];
        byte b1 = (byte) (mccDigits[0] | (mccDigits[1] << 4));
        byte b2 = (byte) (mccDigits[2] | (mnc3 << 4));
        byte b3 = (byte) (mncDigits[0] | (mncDigits[1]) << 4);

        return new byte[] { b1, b2, b3 };
    }

    private static int[] toDigits(String value, int radix) {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
        int[] digits = new int[value.length()];
        for (int i = 0; i < value.length(); i++) {
            int nibble = Character.digit(value.charAt(i), radix);
            if (nibble < 0) {
                throw new IllegalArgumentException(
                        "Invalid digit '" + value.charAt(i) + "' for radix " + radix);
            }
            digits[i] = nibble;
        }
        return digits;
    }

    private static boolean isDecimal(int nibble) {
        return nibble >= 0x0 && nibble <= 0x9;
    }
}

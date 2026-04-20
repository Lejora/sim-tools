package io.github.Lejora.simtools.plmn;

import java.util.Locale;

import static java.util.Objects.requireNonNull;

public record Plmn(String mcc, String mnc) {
    public Plmn {
        requireNonNull(mcc, "MCC must not be null");
        requireNonNull(mnc, "MNC must not be null");

        if (mcc.length() != 3) {
            throw new IllegalArgumentException("MCC must be exactly 3 digits");
        }

        boolean twoDigitMnc = mnc.length() == 2;
        if (!twoDigitMnc && mnc.length() != 3) {
            throw new IllegalArgumentException("MNC must be 2 or 3 digits");
        }

        if (!isHexDigits(mcc)) {
            throw new IllegalArgumentException("MCC must contain only hexadecimal digits");
        }
        if (!isHexDigits(mnc)) {
            throw new IllegalArgumentException("MNC must contain only hexadecimal digits");
        }
        if (!twoDigitMnc && Character.toUpperCase(mnc.charAt(2)) == 'F') {
            throw new IllegalArgumentException("3-digit MNC must not use F as the third digit");
        }

        mcc = mcc.toUpperCase(Locale.ROOT);
        mnc = mnc.toUpperCase(Locale.ROOT);
    }

    public boolean isNonCanonical() {
        return !isDecimalDigits(mcc) || !isDecimalDigits(mnc);
    }

    @Deprecated
    public boolean isAbnormal() {
        return isNonCanonical();
    }

    private static boolean isHexDigits(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.digit(value.charAt(i), 16) < 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDecimalDigits(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.digit(value.charAt(i), 10) < 0) {
                return false;
            }
        }
        return true;
    }
}

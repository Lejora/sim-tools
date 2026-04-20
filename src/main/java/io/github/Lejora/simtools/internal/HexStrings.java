package io.github.Lejora.simtools.internal;

import java.util.Locale;

import static java.util.Objects.requireNonNull;

public final class HexStrings {
    private HexStrings() {}

    public static String normalize(String value, String label, String lengthDescription, int... allowedLengths) {
        requireNonNull(value, label + " must not be null");

        if (!hasAllowedLength(value, allowedLengths)) {
            throw new IllegalArgumentException(label + " must be " + lengthDescription);
        }

        if (!isHexDigits(value)) {
            throw new IllegalArgumentException(label + " must contain only hexadecimal digits");
        }

        return value.toUpperCase(Locale.ROOT);
    }

    private static boolean hasAllowedLength(String value, int[] allowedLengths) {
        for (int allowedLength : allowedLengths) {
            if (value.length() == allowedLength) {
                return true;
            }
        }
        return false;
    }

    private static boolean isHexDigits(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.digit(value.charAt(i), 16) < 0) {
                return false;
            }
        }
        return true;
    }
}

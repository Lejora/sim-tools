package io.github.Lejora.simtools.eutran;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EciTest {

    @Test
    void constructorNormalizesHexDigitsToUppercase() {
        assertEquals("2EEE010", new Eci("2eee010").value());
    }

    @Test
    void constructorRejectsInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> new Eci("2EEE01"));
        assertThrows(IllegalArgumentException.class, () -> new Eci("2EEE010F"));
    }

    @Test
    void constructorRejectsNonHexDigits() {
        assertThrows(IllegalArgumentException.class, () -> new Eci("2EEE01G"));
    }

    @Test
    void constructorRejectsNull() {
        assertThrows(NullPointerException.class, () -> new Eci(null));
    }
}

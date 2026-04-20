package io.github.Lejora.simtools.nr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NciTest {

    @Test
    void constructorNormalizesHexDigitsToUppercase() {
        assertEquals("123ABC789", new Nci("123abc789").value());
    }

    @Test
    void constructorRejectsInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> new Nci("123ABC78"));
        assertThrows(IllegalArgumentException.class, () -> new Nci("123ABC7890"));
    }

    @Test
    void constructorRejectsNonHexDigits() {
        assertThrows(IllegalArgumentException.class, () -> new Nci("123ABC78G"));
    }

    @Test
    void constructorRejectsNull() {
        assertThrows(NullPointerException.class, () -> new Nci(null));
    }
}

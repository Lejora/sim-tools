package io.github.Lejora.simtools.eutran;

import io.github.Lejora.simtools.plmn.Plmn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EcgiTest {

    @Test
    void constructorRejectsNullPlmn() {
        assertThrows(NullPointerException.class, () -> new Ecgi(null, new Eci("2EEE010")));
    }

    @Test
    void constructorRejectsNullEci() {
        assertThrows(NullPointerException.class, () -> new Ecgi(new Plmn("440", "10"), null));
    }
}

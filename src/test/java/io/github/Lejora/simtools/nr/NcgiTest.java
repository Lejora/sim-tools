package io.github.Lejora.simtools.nr;

import io.github.Lejora.simtools.plmn.Plmn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NcgiTest {

    @Test
    void constructorRejectsNullPlmn() {
        assertThrows(NullPointerException.class, () -> new Ncgi(null, new Nci("123ABC789")));
    }

    @Test
    void constructorRejectsNullNci() {
        assertThrows(NullPointerException.class, () -> new Ncgi(new Plmn("440", "10"), null));
    }
}

package io.github.Lejora.simtools.tai;

import io.github.Lejora.simtools.plmn.Plmn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaiTest {

    @Test
    void epsTaiConstructorRejectsNullPlmn() {
        assertThrows(NullPointerException.class, () -> new EpsTai(null, new EpsTac("00AF")));
    }

    @Test
    void epsTaiConstructorRejectsNullTac() {
        assertThrows(NullPointerException.class, () -> new EpsTai(new Plmn("440", "10"), null));
    }

    @Test
    void fiveGsTaiConstructorRejectsNullPlmn() {
        assertThrows(NullPointerException.class, () -> new FiveGsTai(null, new FiveGsTac("0000AF")));
    }

    @Test
    void fiveGsTaiConstructorRejectsNullTac() {
        assertThrows(NullPointerException.class, () -> new FiveGsTai(new Plmn("440", "10"), null));
    }

    @Test
    void taiDetectsNoValidIdentity() {
        assertTrue(new EpsTai(new Plmn("440", "10"), new EpsTac("FFFE")).representsNoValidIdentity());
        assertTrue(new FiveGsTai(new Plmn("A0A", "B1"), new FiveGsTac("0000AF")).representsNoValidIdentity());
        assertFalse(new FiveGsTai(new Plmn("440", "10"), new FiveGsTac("0000AF")).representsNoValidIdentity());
    }
}

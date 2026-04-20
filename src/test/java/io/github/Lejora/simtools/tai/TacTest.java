package io.github.Lejora.simtools.tai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TacTest {

    @Test
    void epsTacConstructorAcceptsFourHexDigits() {
        EpsTac tac = new EpsTac("00af");

        assertEquals("00AF", tac.value());
        assertEquals(2, tac.octetLength());
        assertFalse(tac.isReserved());
    }

    @Test
    void fiveGsTacConstructorAcceptsSixHexDigits() {
        FiveGsTac tac = new FiveGsTac("0001af");

        assertEquals("0001AF", tac.value());
        assertEquals(3, tac.octetLength());
        assertFalse(tac.isReserved());
    }

    @Test
    void constructorsRejectInvalidLength() {
        assertThrows(IllegalArgumentException.class, () -> new EpsTac("0AF"));
        assertThrows(IllegalArgumentException.class, () -> new EpsTac("0001AF"));
        assertThrows(IllegalArgumentException.class, () -> new FiveGsTac("0001A"));
        assertThrows(IllegalArgumentException.class, () -> new FiveGsTac("000001AF"));
    }

    @Test
    void constructorsRejectNonHexDigits() {
        assertThrows(IllegalArgumentException.class, () -> new EpsTac("GGGG"));
        assertThrows(IllegalArgumentException.class, () -> new FiveGsTac("GGGGGG"));
    }

    @Test
    void constructorsRejectNull() {
        assertThrows(NullPointerException.class, () -> new EpsTac(null));
        assertThrows(NullPointerException.class, () -> new FiveGsTac(null));
    }

    @Test
    void reservedValuesAreDetected() {
        assertTrue(new EpsTac("0000").isReserved());
        assertTrue(new EpsTac("FFFE").isReserved());
        assertTrue(new FiveGsTac("000000").isReserved());
        assertTrue(new FiveGsTac("FFFFFE").isReserved());
    }
}

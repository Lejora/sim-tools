package io.github.Lejora.simtools.lai;

import io.github.Lejora.simtools.plmn.Plmn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaiCodecTest {

    @Test
    void encodeDecodeRoundTripPreservesFields() {
        Lai original = new Lai(new Plmn("310", "260", false), 0x1234, false);

        byte[] encoded = LaiCodec.encode(original);
        assertEquals(5, encoded.length);

        Lai decoded = LaiCodec.decode(encoded);
        assertEquals(original.plmn(), decoded.plmn());
        assertEquals(original.lac(), decoded.lac());
        assertFalse(decoded.deleted());
    }

    @Test
    void encodeWithDeletedLacUsesSentinelValue() {
        Lai original = new Lai(new Plmn("901", "70", false), 0x2222, true);

        byte[] encoded = LaiCodec.encode(original);
        assertEquals((byte) 0xFF, encoded[3]);
        assertEquals((byte) 0xFE, encoded[4]);

        Lai decoded = LaiCodec.decode(encoded);
        assertEquals(0xFFFE, decoded.lac());
        assertTrue(decoded.deleted());
    }

    @Test
    void decodeMarksDeletedWhenPlmnIsAbnormal() {
        Lai original = new Lai(new Plmn("A0A", "B1", true), 0x0102, false);

        byte[] encoded = LaiCodec.encode(original);
        Lai decoded = LaiCodec.decode(encoded);

        assertTrue(decoded.plmn().abnormal());
        assertTrue(decoded.deleted());
        assertEquals(0x0102, decoded.lac());
    }
}

package io.github.Lejora.simtools.lai;

import io.github.Lejora.simtools.plmn.Plmn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaiCodecTest {

    @Test
    void encodeDecodeRoundTripPreservesFields() {
        Lai original = new Lai(new Plmn("310", "260"), 0x1234);

        byte[] encoded = LaiCodec.encode(original);
        assertEquals(5, encoded.length);

        Lai decoded = LaiCodec.decode(encoded);
        assertEquals(original.plmn(), decoded.plmn());
        assertEquals(original.lac(), decoded.lac());
        assertFalse(decoded.representsNoValidIdentity());
    }

    @Test
    void decodeReadsLaiFromOffset() {
        byte[] bytes = { 0x00, 0x13, 0x00, 0x62, 0x12, 0x34, 0x00 };

        Lai decoded = LaiCodec.decode(bytes, 1);

        assertEquals(new Plmn("310", "260"), decoded.plmn());
        assertEquals(0x1234, decoded.lac());
        assertFalse(decoded.representsNoValidIdentity());
    }

    @Test
    void encodeWithDeletedLacUsesSentinelValue() {
        Lai original = new Lai(new Plmn("901", "70"), 0xFFFE);

        byte[] encoded = LaiCodec.encode(original);
        assertEquals((byte) 0xFF, encoded[3]);
        assertEquals((byte) 0xFE, encoded[4]);

        Lai decoded = LaiCodec.decode(encoded);
        assertEquals(0xFFFE, decoded.lac());
        assertTrue(decoded.hasReservedLac());
        assertTrue(decoded.representsNoValidIdentity());
    }

    @Test
    void decodeMarksDeletedWhenPlmnIsAbnormal() {
        Lai original = new Lai(new Plmn("A0A", "B1"), 0x0102);

        byte[] encoded = LaiCodec.encode(original);
        Lai decoded = LaiCodec.decode(encoded);

        assertTrue(decoded.plmn().isNonCanonical());
        assertFalse(decoded.hasReservedLac());
        assertTrue(decoded.representsNoValidIdentity());
        assertEquals(0x0102, decoded.lac());
    }
}

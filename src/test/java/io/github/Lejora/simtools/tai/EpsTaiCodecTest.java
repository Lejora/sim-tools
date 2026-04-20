package io.github.Lejora.simtools.tai;

import io.github.Lejora.simtools.plmn.Plmn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EpsTaiCodecTest {

    @Test
    void encodeDecodeRoundTripPreservesFields() {
        EpsTai original = new EpsTai(new Plmn("440", "10"), new EpsTac("12AF"));

        byte[] encoded = EpsTaiCodec.encode(original);

        assertArrayEquals(new byte[] { 0x44, (byte) 0xF0, 0x01, 0x12, (byte) 0xAF }, encoded);
        assertEquals(original, EpsTaiCodec.decode(encoded));
    }

    @Test
    void decodeMarksReservedTaiAsNoValidIdentity() {
        EpsTai decoded = EpsTaiCodec.decode(new byte[] { 0x44, (byte) 0xF0, 0x01, (byte) 0xFF, (byte) 0xFE });

        assertEquals(new EpsTac("FFFE"), decoded.tac());
        assertTrue(decoded.representsNoValidIdentity());
    }
}

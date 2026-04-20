package io.github.Lejora.simtools.tai;

import io.github.Lejora.simtools.plmn.Plmn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FiveGsTaiCodecTest {

    @Test
    void encodeDecodeRoundTripPreservesFields() {
        FiveGsTai original = new FiveGsTai(new Plmn("440", "10"), new FiveGsTac("12ABCD"));

        byte[] encoded = FiveGsTaiCodec.encode(original);

        assertArrayEquals(new byte[] { 0x44, (byte) 0xF0, 0x01, 0x12, (byte) 0xAB, (byte) 0xCD }, encoded);
        assertEquals(original, FiveGsTaiCodec.decode(encoded));
    }

    @Test
    void decodeMarksReservedTaiAsNoValidIdentity() {
        FiveGsTai decoded = FiveGsTaiCodec.decode(
            new byte[] { 0x44, (byte) 0xF0, 0x01, (byte) 0xFF, (byte) 0xFF, (byte) 0xFE }
        );

        assertEquals(new FiveGsTac("FFFFFE"), decoded.tac());
        assertTrue(decoded.representsNoValidIdentity());
    }
}

package io.github.Lejora.simtools.tai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FiveGsTacCodecTest {

    @Test
    void encodeDecodeRoundTripPreservesValue() {
        FiveGsTac original = new FiveGsTac("12ABCD");

        byte[] encoded = FiveGsTacCodec.encode(original);

        assertArrayEquals(new byte[] { 0x12, (byte) 0xAB, (byte) 0xCD }, encoded);
        assertEquals(original, FiveGsTacCodec.decode(encoded));
    }

    @Test
    void decodeReadsFromOffset() {
        byte[] bytes = { 0x00, 0x12, 0x34, 0x56, 0x00 };

        assertEquals(new FiveGsTac("123456"), FiveGsTacCodec.decode(bytes, 1));
    }

    @Test
    void decodeRejectsTooShortInput() {
        assertThrows(IllegalArgumentException.class, () -> FiveGsTacCodec.decode(new byte[] { 0x12, 0x34 }));
    }
}

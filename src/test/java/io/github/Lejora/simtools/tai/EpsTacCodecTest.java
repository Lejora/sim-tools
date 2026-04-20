package io.github.Lejora.simtools.tai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EpsTacCodecTest {

    @Test
    void encodeDecodeRoundTripPreservesValue() {
        EpsTac original = new EpsTac("12AF");

        byte[] encoded = EpsTacCodec.encode(original);

        assertArrayEquals(new byte[] { 0x12, (byte) 0xAF }, encoded);
        assertEquals(original, EpsTacCodec.decode(encoded));
    }

    @Test
    void decodeReadsFromOffset() {
        byte[] bytes = { 0x00, 0x12, 0x34, 0x00 };

        assertEquals(new EpsTac("1234"), EpsTacCodec.decode(bytes, 1));
    }

    @Test
    void decodeRejectsTooShortInput() {
        assertThrows(IllegalArgumentException.class, () -> EpsTacCodec.decode(new byte[] { 0x12 }));
    }
}

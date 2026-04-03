package io.github.Lejora.simtools.lai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LacCodecTest {

    @Test
    void encodeDecodeRoundTripPreservesValue() {
        int[] values = { 0x0001, 0x1234, 0x7FFE, 0xFFFD };
        for (int value : values) {
            Lac original = new Lac(value);
            byte[] encoded = LacCodec.encode(original);
            assertEquals(2, encoded.length);

            Lac decoded = LacCodec.decode(encoded, 0);
            assertEquals(value, decoded.value());
            assertFalse(decoded.isDeletedEncoding());
        }
    }

    @Test
    void encodeDeletedUsesSentinelValue() {
        byte[] encoded = LacCodec.encodeDeleted();
        assertArrayEquals(new byte[] { (byte) 0xFF, (byte) 0xFE }, encoded);

        Lac decoded = LacCodec.decode(encoded, 0);
        assertEquals(0xFFFE, decoded.value());
        assertTrue(decoded.isDeletedEncoding());
    }

    @Test
    void decodeTreatsZeroValueAsDeleted() {
        byte[] deletedBytes = { 0x00, 0x00 };

        Lac decoded = LacCodec.decode(deletedBytes, 0);
        assertEquals(0x0000, decoded.value());
        assertTrue(decoded.isDeletedEncoding());
    }
}

package io.github.Lejora.simtools.eutran;

import io.github.Lejora.simtools.plmn.Plmn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EcgiCodecTest {

    @Test
    void encodeDecodeRoundTripPreservesFields() {
        Ecgi original = new Ecgi(new Plmn("440", "10"), new Eci("2EEE010"));

        byte[] encoded = EcgiCodec.encode(original);

        assertArrayEquals(
            new byte[] { 0x44, (byte) 0xF0, 0x01, 0x02, (byte) 0xEE, (byte) 0xE0, 0x10 },
            encoded
        );
        assertEquals(original, EcgiCodec.decode(encoded));
    }

    @Test
    void decodeRejectsNonZeroSpareBits() {
        byte[] invalid = { 0x44, (byte) 0xF0, 0x01, (byte) 0xF2, (byte) 0xEE, (byte) 0xE0, 0x10 };

        assertThrows(IllegalArgumentException.class, () -> EcgiCodec.decode(invalid));
    }
}

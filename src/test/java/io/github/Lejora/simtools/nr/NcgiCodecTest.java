package io.github.Lejora.simtools.nr;

import io.github.Lejora.simtools.plmn.Plmn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NcgiCodecTest {

    @Test
    void encodeDecodeRoundTripPreservesFields() {
        Ncgi original = new Ncgi(new Plmn("440", "10"), new Nci("123ABC789"));

        byte[] encoded = NcgiCodec.encode(original);

        assertArrayEquals(
            new byte[] { 0x44, (byte) 0xF0, 0x01, 0x01, 0x23, (byte) 0xAB, (byte) 0xC7, (byte) 0x89 },
            encoded
        );
        assertEquals(original, NcgiCodec.decode(encoded));
    }

    @Test
    void decodeRejectsNonZeroSpareBits() {
        byte[] invalid = { 0x44, (byte) 0xF0, 0x01, (byte) 0xF1, 0x23, (byte) 0xAB, (byte) 0xC7, (byte) 0x89 };

        assertThrows(IllegalArgumentException.class, () -> NcgiCodec.decode(invalid));
    }
}

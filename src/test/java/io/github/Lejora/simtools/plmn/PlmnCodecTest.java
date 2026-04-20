package io.github.Lejora.simtools.plmn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlmnCodecTest {

    @Test
    void encodeDecodeRoundTripWithThreeDigitMnc() {
        Plmn original = new Plmn("310", "260");

        byte[] encoded = PlmnCodec.encode(original);
        assertEquals(3, encoded.length);

        Plmn decoded = PlmnCodec.decode(encoded, 0);
        assertEquals(original, decoded);
    }

    @Test
    void encodeDecodeRoundTripWithTwoDigitMnc() {
        Plmn original = new Plmn("440", "10");

        byte[] encoded = PlmnCodec.encode(original);
        assertEquals(3, encoded.length);
        assertEquals(0xF0, encoded[1] & 0xF0);

        Plmn decoded = PlmnCodec.decode(encoded, 0);
        assertEquals(original, decoded);
    }

    @Test
    void encodeDecodeRoundTripWithAbnormalDigits() {
        Plmn abnormal = new Plmn("A0A", "B1");

        byte[] encoded = PlmnCodec.encode(abnormal);
        assertEquals(3, encoded.length);

        Plmn decoded = PlmnCodec.decode(encoded, 0);
        assertTrue(decoded.isNonCanonical());
        assertEquals(abnormal, decoded);
    }

    @Test
    void decodeRejectsTooShortInput() {
        byte[] invalid = { 0x01, 0x02 };
        assertThrows(IllegalArgumentException.class, () -> PlmnCodec.decode(invalid, 0));
    }

    @Test
    void encodeRejectsInvalidLengths() {
        assertThrows(IllegalArgumentException.class, () -> PlmnCodec.encode(new Plmn("31", "260")));
        assertThrows(IllegalArgumentException.class, () -> PlmnCodec.encode(new Plmn("310", "2")));
    }

    @Test
    void constructorRejectsThreeDigitMncUsingFAsThirdDigit() {
        assertThrows(IllegalArgumentException.class, () -> new Plmn("310", "26F"));
    }
}

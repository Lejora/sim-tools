package io.github.Lejora.simtools.plmn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlmnCodecTest {

    @Test
    void encodeDecodeRoundTripWithThreeDigitMnc() {
        Plmn original = new Plmn("310", "260", false);

        byte[] encoded = PlmnCodec.encode(original);
        assertEquals(3, encoded.length);

        Plmn decoded = PlmnCodec.decode(encoded, 0);
        assertEquals(original, decoded);
    }

    @Test
    void encodeDecodeRoundTripWithTwoDigitMnc() {
        Plmn original = new Plmn("440", "10", false);

        byte[] encoded = PlmnCodec.encode(original);
        assertEquals(3, encoded.length);
        assertEquals(0xF0, encoded[1] & 0xF0);

        Plmn decoded = PlmnCodec.decode(encoded, 0);
        assertEquals(original, decoded);
    }

    @Test
    void encodeAllowsAbnormalDigitsAndDecodeMarksThem() {
        Plmn abnormal = new Plmn("A0A", "B1", true);

        byte[] encoded = PlmnCodec.encode(abnormal);
        assertEquals(3, encoded.length);

        Plmn decoded = PlmnCodec.decode(encoded, 0);
        assertTrue(decoded.abnormal());
        assertFalse(decoded.mcc().isBlank());
        assertFalse(decoded.mnc().isBlank());
    }

    @Test
    void decodeRejectsTooShortInput() {
        byte[] invalid = { 0x01, 0x02 };
        assertThrows(IllegalArgumentException.class, () -> PlmnCodec.decode(invalid, 0));
    }

    @Test
    void encodeRejectsInvalidLengths() {
        assertThrows(IllegalArgumentException.class, () -> PlmnCodec.encode(new Plmn("31", "260", false)));
        assertThrows(IllegalArgumentException.class, () -> PlmnCodec.encode(new Plmn("310", "2", false)));
    }
}

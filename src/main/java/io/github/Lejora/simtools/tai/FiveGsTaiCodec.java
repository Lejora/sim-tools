package io.github.Lejora.simtools.tai;

import io.github.Lejora.simtools.plmn.Plmn;
import io.github.Lejora.simtools.plmn.PlmnCodec;

import static java.util.Objects.requireNonNull;

public final class FiveGsTaiCodec {
    private FiveGsTaiCodec() {}

    public static FiveGsTai decode(byte[] data) {
        return decode(data, 0);
    }

    public static FiveGsTai decode(byte[] data, int offset) {
        if (data == null || data.length < offset + 6) {
            throw new IllegalArgumentException("5GS TAI must be exactly 6 bytes");
        }

        Plmn plmn = PlmnCodec.decode(data, offset);
        FiveGsTac tac = FiveGsTacCodec.decode(data, offset + 3);
        return new FiveGsTai(plmn, tac);
    }

    public static byte[] encode(FiveGsTai tai) {
        requireNonNull(tai, "5GS TAI must not be null");

        byte[] plmnBytes = PlmnCodec.encode(tai.plmn());
        byte[] tacBytes = FiveGsTacCodec.encode(tai.tac());
        byte[] taiBytes = new byte[6];
        System.arraycopy(plmnBytes, 0, taiBytes, 0, 3);
        System.arraycopy(tacBytes, 0, taiBytes, 3, 3);
        return taiBytes;
    }
}

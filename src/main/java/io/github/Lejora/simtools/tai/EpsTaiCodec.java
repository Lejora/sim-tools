package io.github.Lejora.simtools.tai;

import io.github.Lejora.simtools.plmn.Plmn;
import io.github.Lejora.simtools.plmn.PlmnCodec;

import static java.util.Objects.requireNonNull;

public final class EpsTaiCodec {
    private EpsTaiCodec() {}

    public static EpsTai decode(byte[] data) {
        return decode(data, 0);
    }

    public static EpsTai decode(byte[] data, int offset) {
        if (data == null || data.length < offset + 5) {
            throw new IllegalArgumentException("EPS TAI must be exactly 5 bytes");
        }

        Plmn plmn = PlmnCodec.decode(data, offset);
        EpsTac tac = EpsTacCodec.decode(data, offset + 3);
        return new EpsTai(plmn, tac);
    }

    public static byte[] encode(EpsTai tai) {
        requireNonNull(tai, "EPS TAI must not be null");

        byte[] plmnBytes = PlmnCodec.encode(tai.plmn());
        byte[] tacBytes = EpsTacCodec.encode(tai.tac());
        byte[] taiBytes = new byte[5];
        System.arraycopy(plmnBytes, 0, taiBytes, 0, 3);
        System.arraycopy(tacBytes, 0, taiBytes, 3, 2);
        return taiBytes;
    }
}

package io.github.Lejora.simtools.lai;

import io.github.Lejora.simtools.plmn.Plmn;
import io.github.Lejora.simtools.plmn.PlmnCodec;

import static java.util.Objects.requireNonNull;

public class LaiCodec {
    private LaiCodec() {}

    public static Lai decode(byte[] data) {
        return decode(data, 0);
    }

    public static Lai decode(byte[] data, int offset) {
        if (data == null || data.length < offset + 5) {
            throw new IllegalArgumentException("LAI must be exactly 5 bytes");
        }

        Plmn plmn = PlmnCodec.decode(data, offset);
        Lac lac = LacCodec.decode(data, offset + 3);

        return new Lai(plmn, lac.value());
    }

    public static byte[] encode(Lai lai) {
        requireNonNull(lai, "LAI must not be null");
        Plmn plmn = requireNonNull(lai.plmn(), "PLMN must not be null");

        byte[] plmnBytes = PlmnCodec.encode(plmn);
        byte[] lacBytes = LacCodec.encode(lai.lac());

        byte[] laiBytes = new byte[5];
        System.arraycopy(plmnBytes, 0, laiBytes, 0, 3);
        System.arraycopy(lacBytes, 0, laiBytes, 3, 2);
        return laiBytes;
    }
}

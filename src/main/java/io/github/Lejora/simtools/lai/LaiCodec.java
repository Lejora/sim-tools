package io.github.Lejora.simtools.lai;

import io.github.Lejora.simtools.plmn.Plmn;
import io.github.Lejora.simtools.plmn.PlmnCodec;

import static java.util.Objects.requireNonNull;

public class LaiCodec {
    private LaiCodec() {}

    public static Lai decode(byte[] data) {
        if (data == null || data.length != 5) {
            throw new IllegalArgumentException("LAI must be exactly 5 bytes");
        }

        Plmn plmn = PlmnCodec.decode(data, 0);
        Lac lac = LacCodec.decode(data, 3);

        boolean deleted = lac.deleted() || plmn.abnormal();

        return new Lai(
                plmn,
                lac.value(),
                deleted
        );
    }

    public static byte[] encode(Lai lai) {
        requireNonNull(lai, "LAI must not be null");
        Plmn plmn = requireNonNull(lai.plmn(), "PLMN must not be null");

        byte[] plmnBytes = PlmnCodec.encode(plmn);
        byte[] lacBytes = LacCodec.encode(lai.lac(), lai.deleted());

        byte[] laiBytes = new byte[5];
        System.arraycopy(plmnBytes, 0, laiBytes, 0, 3);
        System.arraycopy(lacBytes, 0, laiBytes, 3, 2);
        return laiBytes;
    }
}

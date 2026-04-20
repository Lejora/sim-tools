package io.github.Lejora.simtools.eutran;

import io.github.Lejora.simtools.internal.PackedHexCodec;
import io.github.Lejora.simtools.plmn.Plmn;
import io.github.Lejora.simtools.plmn.PlmnCodec;

import static java.util.Objects.requireNonNull;

public final class EcgiCodec {
    private EcgiCodec() {}

    public static Ecgi decode(byte[] data) {
        return decode(data, 0);
    }

    public static Ecgi decode(byte[] data, int offset) {
        if (data == null || data.length < offset + 7) {
            throw new IllegalArgumentException("ECGI must be exactly 7 bytes");
        }

        Plmn plmn = PlmnCodec.decode(data, offset);
        Eci eci = new Eci(PackedHexCodec.decode(data, offset + 3, 4, 7, "ECI"));
        return new Ecgi(plmn, eci);
    }

    public static byte[] encode(Ecgi ecgi) {
        requireNonNull(ecgi, "ECGI must not be null");

        byte[] plmnBytes = PlmnCodec.encode(ecgi.plmn());
        byte[] eciBytes = PackedHexCodec.encode(ecgi.eci().value(), 7, "ECI");
        byte[] ecgiBytes = new byte[7];
        System.arraycopy(plmnBytes, 0, ecgiBytes, 0, 3);
        System.arraycopy(eciBytes, 0, ecgiBytes, 3, 4);
        return ecgiBytes;
    }
}

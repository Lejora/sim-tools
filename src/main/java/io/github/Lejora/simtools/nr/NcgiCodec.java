package io.github.Lejora.simtools.nr;

import io.github.Lejora.simtools.internal.PackedHexCodec;
import io.github.Lejora.simtools.plmn.Plmn;
import io.github.Lejora.simtools.plmn.PlmnCodec;

import static java.util.Objects.requireNonNull;

public final class NcgiCodec {
    private NcgiCodec() {}

    public static Ncgi decode(byte[] data) {
        return decode(data, 0);
    }

    public static Ncgi decode(byte[] data, int offset) {
        if (data == null || data.length < offset + 8) {
            throw new IllegalArgumentException("NCGI must be exactly 8 bytes");
        }

        Plmn plmn = PlmnCodec.decode(data, offset);
        Nci nci = new Nci(PackedHexCodec.decode(data, offset + 3, 5, 9, "NCI"));
        return new Ncgi(plmn, nci);
    }

    public static byte[] encode(Ncgi ncgi) {
        requireNonNull(ncgi, "NCGI must not be null");

        byte[] plmnBytes = PlmnCodec.encode(ncgi.plmn());
        byte[] nciBytes = PackedHexCodec.encode(ncgi.nci().value(), 9, "NCI");
        byte[] ncgiBytes = new byte[8];
        System.arraycopy(plmnBytes, 0, ncgiBytes, 0, 3);
        System.arraycopy(nciBytes, 0, ncgiBytes, 3, 5);
        return ncgiBytes;
    }
}

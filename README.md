# sim-tools

Minimal codecs for PLMN, LAC and LAI values (3GPP TS 24.008) implemented with Java records.

## Usage
```java
import io.github.Lejora.simtools.plmn.*;
import io.github.Lejora.simtools.lai.*;

Plmn plmn = new Plmn("310", "260", false);
Lai lai = new Lai(plmn, 0x1234, false);

byte[] encodedLai = LaiCodec.encode(lai);      // -> { 0x13, 0xF2, 0x03, 0x12, 0x34 }
Lai decodedLai = LaiCodec.decode(encodedLai);  // -> Lai(plmn=310/260, lac=0x1234, deleted=false)

byte[] plmnBytes = PlmnCodec.encode(plmn);     // -> { 0x13, 0xF2, 0x03 }
Plmn decodedPlmn = PlmnCodec.decode(plmnBytes, 0); // -> Plmn(mcc=310, mnc=260, abnormal=false)

byte[] lacBytes = LacCodec.encode(0x1234, false); // -> { 0x12, 0x34 }
Lac decodedLac = LacCodec.decode(lacBytes, 0);     // -> Lac(value=0x1234, deleted=false)
```
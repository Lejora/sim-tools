# sim-tools

Minimal codecs for PLMN, LAC and LAI values (3GPP TS 24.008) implemented with Java records.

## Terms
- `PLMN` (`Public Land Mobile Network`) identifies a mobile network by `MCC` (`Mobile Country Code`) and `MNC` (`Mobile Network Code`).
- `LAC` (`Location Area Code`) identifies a location area within a PLMN.
- `LAI` (`Location Area Identification`) is the combination of `PLMN + LAC`.

## Usage
```java
import io.github.Lejora.simtools.lai.*;
import io.github.Lejora.simtools.plmn.*;

// Encode values to bytes
Plmn plmn = new Plmn("310", "260");
Lai lai = new Lai(plmn, 0x1234);

byte[] plmnBytes = PlmnCodec.encode(plmn);    // -> { 0x13, 0x00, 0x62 }
byte[] lacBytes = LacCodec.encode(0x1234);    // -> { 0x12, 0x34 }
byte[] laiBytes = LaiCodec.encode(lai);       // -> { 0x13, 0x00, 0x62, 0x12, 0x34 }

// Decode bytes to values
Plmn decodedPlmn = PlmnCodec.decode(plmnBytes, 0);
Lac decodedLac = LacCodec.decode(lacBytes, 0);
Lai decodedLai = LaiCodec.decode(laiBytes);

decodedPlmn.mcc();  // "310"
decodedPlmn.mnc();  // "260"
decodedLac.value(); // 0x1234
decodedLai.lac();   // 0x1234
```

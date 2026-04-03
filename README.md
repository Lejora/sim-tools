# sim-tools

Minimal codecs for PLMN, LAC and LAI values (3GPP TS 24.008) implemented with Java records.

## Usage
```java
import io.github.Lejora.simtools.lai.*;
import io.github.Lejora.simtools.plmn.*;

// Normal PLMN / LAI
Plmn plmn = new Plmn("310", "260");
Lai lai = new Lai(plmn, 0x1234);

byte[] encodedLai = LaiCodec.encode(lai);
Lai decodedLai = LaiCodec.decode(encodedLai);

decodedLai.plmn().mcc();                   // "310"
decodedLai.plmn().mnc();                   // "260"
decodedLai.plmn().isAbnormal();            // false
decodedLai.hasDeletedLacEncoding();        // false
decodedLai.shouldBeTreatedAsDeleted();     // false

// Deleted LAI
Lai deletedLai = new Lai(new Plmn("901", "70"), Lac.deleted().value());
Lai decodedDeletedLai = LaiCodec.decode(LaiCodec.encode(deletedLai));

decodedDeletedLai.lac();                      // 0xFFFE
decodedDeletedLai.hasDeletedLacEncoding();   // true
decodedDeletedLai.shouldBeTreatedAsDeleted();// true

// Abnormal PLMN
Plmn abnormalPlmn = new Plmn("A0A", "B1");
Plmn decodedAbnormalPlmn = PlmnCodec.decode(PlmnCodec.encode(abnormalPlmn), 0);

decodedAbnormalPlmn.mcc();          // "A0A"
decodedAbnormalPlmn.mnc();          // "B1"
decodedAbnormalPlmn.isAbnormal();   // true

Lai abnormalLai = new Lai(abnormalPlmn, 0x0102);
Lai decodedAbnormalLai = LaiCodec.decode(LaiCodec.encode(abnormalLai));

decodedAbnormalLai.hasDeletedLacEncoding();    // false
decodedAbnormalLai.shouldBeTreatedAsDeleted(); // true

// LAC only
byte[] lacBytes = LacCodec.encode(0x1234);
Lac decodedLac = LacCodec.decode(lacBytes, 0);

decodedLac.value();               // 0x1234
decodedLac.isDeletedEncoding();   // false
```

# sim-tools

Minimal Java value objects and codecs for common 3GPP location and cell identifiers.

## Terms
- `PLMN` (`Public Land Mobile Network`) identifies a mobile network by `MCC` (`Mobile Country Code`) and `MNC` (`Mobile Network Code`).
- `LAC` (`Location Area Code`) identifies a location area within a PLMN.
- `LAI` (`Location Area Identification`) is the combination of `PLMN + LAC`.
- `TAC` (`Tracking Area Code`) identifies a tracking area within a PLMN.
- `TAI` (`Tracking Area Identity`) is the combination of `PLMN + TAC`.
- `ECI` (`E-UTRAN Cell Identity`) identifies an LTE/E-UTRAN cell.
- `ECGI` (`E-UTRAN Cell Global Identifier`) is the combination of `PLMN + ECI`.
- `NCI` (`NR Cell Identity`) identifies a 5G NR cell.
- `NCGI` (`NR Cell Global Identifier`) is the combination of `PLMN + NCI`.

## Supported Types
- `Plmn` / `PlmnCodec`
- `Lac`, `Lai`, `LacCodec`, `LaiCodec`
- `EpsTac`, `EpsTai`, `EpsTacCodec`, `EpsTaiCodec`
- `FiveGsTac`, `FiveGsTai`, `FiveGsTacCodec`, `FiveGsTaiCodec`
- `Eci`, `Ecgi`, `EcgiCodec`
- `Nci`, `Ncgi`, `NcgiCodec`

## Usage
```java
import io.github.Lejora.simtools.lai.*;
import io.github.Lejora.simtools.plmn.*;
import io.github.Lejora.simtools.tai.*;
import io.github.Lejora.simtools.eutran.*;
import io.github.Lejora.simtools.nr.*;

Plmn plmn = new Plmn("310", "260");
Lai lai = new Lai(plmn, 0x1234);
EpsTai epsTai = new EpsTai(plmn, new EpsTac("12AF"));
FiveGsTai fiveGsTai = new FiveGsTai(plmn, new FiveGsTac("12ABCD"));
Ecgi ecgi = new Ecgi(plmn, new Eci("2EEE010"));
Ncgi ncgi = new Ncgi(plmn, new Nci("123ABC789"));

byte[] plmnBytes = PlmnCodec.encode(plmn);    // -> { 0x13, 0x00, 0x62 }
byte[] lacBytes = LacCodec.encode(0x1234);    // -> { 0x12, 0x34 }
byte[] laiBytes = LaiCodec.encode(lai);       // -> { 0x13, 0x00, 0x62, 0x12, 0x34 }
byte[] epsTaiBytes = EpsTaiCodec.encode(epsTai);
byte[] fiveGsTaiBytes = FiveGsTaiCodec.encode(fiveGsTai);
byte[] ecgiBytes = EcgiCodec.encode(ecgi);
byte[] ncgiBytes = NcgiCodec.encode(ncgi);

Plmn decodedPlmn = PlmnCodec.decode(plmnBytes, 0);
Lac decodedLac = LacCodec.decode(lacBytes, 0);
Lai decodedLai = LaiCodec.decode(laiBytes);
EpsTai decodedEpsTai = EpsTaiCodec.decode(epsTaiBytes);
FiveGsTai decodedFiveGsTai = FiveGsTaiCodec.decode(fiveGsTaiBytes);
Ecgi decodedEcgi = EcgiCodec.decode(ecgiBytes);
Ncgi decodedNcgi = NcgiCodec.decode(ncgiBytes);

decodedPlmn.mcc();  // "310"
decodedPlmn.mnc();  // "260"
decodedLac.value(); // 0x1234
decodedLai.lac();   // 0x1234
decodedEpsTai.tac().value();    // "12AF"
decodedFiveGsTai.tac().value(); // "12ABCD"
decodedEcgi.eci().value();      // "2EEE010"
decodedNcgi.nci().value();      // "123ABC789"
```

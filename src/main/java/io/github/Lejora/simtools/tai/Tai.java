package io.github.Lejora.simtools.tai;

import io.github.Lejora.simtools.plmn.Plmn;

// Tracking Area Identity
public sealed interface Tai permits EpsTai, FiveGsTai {
    Plmn plmn();

    Tac tac();

    boolean representsNoValidIdentity();
}

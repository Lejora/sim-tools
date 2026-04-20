package io.github.Lejora.simtools.tai;

// Tracking Area Code
public sealed interface Tac permits EpsTac, FiveGsTac {
    String value();

    boolean isReserved();

    int octetLength();
}

package io.github.Lejora.simtools.lai;

public record Lac(int value) {
    public boolean isReserved() {
        return  value == 0xFFFE || value == 0x0000;
    }

    public static Lac reserved() {
        return new Lac(0xFFFE);
    }

    @Deprecated
    public boolean isDeletedEncoding() {
        return isReserved();
    }

    @Deprecated
    public static Lac deleted() {
        return reserved();
    }
}

package io.github.Lejora.simtools.lai;

public record Lac(int value) {
    public boolean isDeletedEncoding() {
        return  value == 0xFFFE || value == 0x0000;
    }

    public static Lac deleted() {
        return new Lac(0xFFFE);
    }
}
